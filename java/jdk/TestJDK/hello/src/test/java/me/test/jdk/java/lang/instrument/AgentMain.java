package me.test.jdk.java.lang.instrument;

import com.sun.tools.attach.VirtualMachine;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.security.ProtectionDomain;
import java.util.concurrent.atomic.AtomicReference;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

/**
 * @author dangqian.zll
 * @date 2023/8/15
 * @see <a href="https://stackoverflow.com/questions/18567552/how-to-retransform-a-class-at-runtime">how to retransform a class at runtime</a>
 * @see <a href="https://www.baeldung.com/jvm-list-all-classes-loaded">List All the Classes Loaded in the JVM</a>
 */
public class AgentMain {

    public static void agentmain(String agentArgs, Instrumentation inst)
            throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException,
                    MBeanRegistrationException {
        TransformerService ts = new TransformerService(inst);
        ObjectName on = new ObjectName("transformer:service=DemoTransformer");
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        server.registerMBean(ts, on);
    }

    public static class AgentInstaller {
        /**
         * The created agent jar file name
         */
        protected static final AtomicReference<String> agentJar = new AtomicReference<String>(null);

        /**
         * Self installs the agent, then runs a person sayHello in a loop
         *
         * @param args None
         */
        public static void main(String[] args) {
            try {
                // Get this JVM's PID
                String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
                // Attach (to ourselves)
                VirtualMachine vm = VirtualMachine.attach(pid);
                // Create an agent jar (since we're in DEV mode)
                String fileName = createAgent();
                // Load the agent into this JVM
                vm.loadAgent(fileName);
                System.out.println("Agent Loaded");
                ObjectName on = new ObjectName("transformer:service=DemoTransformer");
                System.out.println("Instrumentation Deployed:"
                        + ManagementFactory.getPlatformMBeanServer().isRegistered(on));
                // Run sayHello in a loop
                Person person = new Person();
                for (int i = 0; i < 1000; i++) {
                    person.sayHello(i);
                    person.sayHello("" + (i * -1));
                    Thread.currentThread().join(5000);
                }
            } catch (Exception ex) {
                System.err.println("Agent Installation Failed. Stack trace follows...");
                ex.printStackTrace(System.err);
            }
        }

        /**
         * Creates the temporary agent jar file if it has not been created
         *
         * @return The created agent file name
         */
        public static String createAgent() {
            if (agentJar.get() == null) {
                synchronized (agentJar) {
                    if (agentJar.get() == null) {
                        FileOutputStream fos = null;
                        JarOutputStream jos = null;
                        try {
                            File tmpFile = File.createTempFile(AgentMain.class.getName(), ".jar");
                            System.out.println("Temp File:" + tmpFile.getAbsolutePath());
                            tmpFile.deleteOnExit();
                            StringBuilder manifest = new StringBuilder();
                            manifest.append("Manifest-Version: 1.0\nAgent-Class: " + AgentMain.class.getName() + "\n");
                            manifest.append("Can-Redefine-Classes: true\n");
                            manifest.append("Can-Retransform-Classes: true\n");
                            manifest.append("Premain-Class: " + AgentMain.class.getName() + "\n");
                            ByteArrayInputStream bais =
                                    new ByteArrayInputStream(manifest.toString().getBytes());
                            Manifest mf = new Manifest(bais);
                            fos = new FileOutputStream(tmpFile, false);
                            jos = new JarOutputStream(fos, mf);
                            addClassesToJar(
                                    jos,
                                    AgentMain.class,
                                    DemoTransformer.class,
                                    TransformerService.class,
                                    TransformerServiceMBean.class);
                            jos.flush();
                            jos.close();
                            fos.flush();
                            fos.close();
                            agentJar.set(tmpFile.getAbsolutePath());
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to write Agent installer Jar", e);
                        } finally {
                            if (fos != null)
                                try {
                                    fos.close();
                                } catch (Exception e) {
                                }
                        }
                    }
                }
            }
            return agentJar.get();
        }

        /**
         * Writes the passed classes to the passed JarOutputStream
         *
         * @param jos     the JarOutputStream
         * @param clazzes The classes to write
         * @throws IOException on an IOException
         */
        protected static void addClassesToJar(JarOutputStream jos, Class<?>... clazzes) throws IOException {
            for (Class<?> clazz : clazzes) {
                jos.putNextEntry(new ZipEntry(clazz.getName().replace('.', '/') + ".class"));
                jos.write(getClassBytes(clazz));
                jos.flush();
                jos.closeEntry();
            }
        }

        /**
         * Returns the bytecode bytes for the passed class
         *
         * @param clazz The class to get the bytecode for
         * @return a byte array of bytecode for the passed class
         */
        public static byte[] getClassBytes(Class<?> clazz) {
            InputStream is = null;
            try {
                is = clazz.getClassLoader().getResourceAsStream(clazz.getName().replace('.', '/') + ".class");
                ByteArrayOutputStream baos = new ByteArrayOutputStream(is.available());
                byte[] buffer = new byte[8092];
                int bytesRead = -1;
                while ((bytesRead = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                baos.flush();
                return baos.toByteArray();
            } catch (Exception e) {
                throw new RuntimeException("Failed to read class bytes for [" + clazz.getName() + "]", e);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    public static class Person {
        public void sayHello(String name) {
            System.out.println("Hello [" + name + "]");
        }

        public void sayHello(int x) {
            System.out.println("Hello [" + x + "]");
        }
    }

    public interface TransformerServiceMBean {
        void transformClass(String className, String methodName, String methodSignature);
    }

    public static class TransformerService implements TransformerServiceMBean {

        protected final Instrumentation instrumentation;

        /**
         * Creates a new TransformerService
         *
         * @param instrumentation The JVM's instrumentation instance
         */
        public TransformerService(Instrumentation instrumentation) {
            this.instrumentation = instrumentation;
        }

        @Override
        public void transformClass(String className, String methodName, String methodSignature) {
            Class<?> targetClazz = null;
            ClassLoader targetClassLoader = null;
            // first see if we can locate the class through normal means
            try {
                targetClazz = Class.forName(className);
                targetClassLoader = targetClazz.getClassLoader();
                transform(targetClazz, targetClassLoader, methodName, methodSignature);
                return;
            } catch (Exception ex) {
                /* Nope */
            }
            // now try the hard/slow way
            for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
                if (clazz.getName().equals(className)) {
                    targetClazz = clazz;
                    targetClassLoader = targetClazz.getClassLoader();
                    transform(targetClazz, targetClassLoader, methodName, methodSignature);
                    return;
                }
            }
            throw new RuntimeException("Failed to locate class [" + className + "]");
        }

        /**
         * Registers a transformer and executes the transform
         *
         * @param clazz           The class to transform
         * @param classLoader     The classloader the class was loaded from
         * @param methodName      The method name to instrument
         * @param methodSignature The method signature to match
         */
        protected void transform(Class<?> clazz, ClassLoader classLoader, String methodName, String methodSignature) {
            ClassFileTransformer dt = new DemoTransformer();
            instrumentation.addTransformer(dt, true);
            try {
                instrumentation.retransformClasses(clazz);
            } catch (Exception ex) {
                throw new RuntimeException("Failed to transform [" + clazz.getName() + "]", ex);
            } finally {
                instrumentation.removeTransformer(dt);
            }
        }
    }

    public static class DemoTransformer implements ClassFileTransformer {

        @Override
        public byte[] transform(
                ClassLoader loader,
                String className,
                Class<?> classBeingRedefined,
                ProtectionDomain protectionDomain,
                byte[] classfileBuffer)
                throws IllegalClassFormatException {
            return classfileBuffer;
        }
    }
}
