package me.test;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class PrintAllClz {

    public static void main(String[] args) throws Exception {
        VirtualMachine vm = VirtualMachine.attach(args[0]);
        String file = PrintAllClz.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        System.out.println("begin to load agent : " + file);
        try {
            vm.loadAgent(file);
        } finally {
            vm.detach();
        }
    }

    public static void agentmain(String agentArgs, Instrumentation inst) throws IOException {
        System.out.println("agent come in");
        File file = new File("/tmp/PrintAllClz.log");
        if (!file.exists()) {
            file.createNewFile();
        }
        List<String> targetJarList = getTargetJarList(agentArgs);

        try (Writer w = new FileWriter(file, StandardCharsets.UTF_8)) {
            Class[] loadedClass = inst.getAllLoadedClasses();
            Stream.of(loadedClass)
                    .filter(c -> {
                        if (targetJarList.isEmpty()) {
                            return true;
                        }
                        String location = getLocation(c);
                        return targetJarList.stream()
                                .anyMatch(targetJar -> location.contains(targetJar));
                    })
                    .forEach(c -> {
                        try {
                            String location = getLocation(c);
                            w.write(c.getName());
                            w.write(" ");
                            w.write(location);
                            w.write("\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
            System.out.println("Done.");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        }

    }

    protected static String getLocation(Class c) {
        return Optional.ofNullable(c)
                .map(Class::getProtectionDomain)
                .map(ProtectionDomain::getCodeSource)
                .map(CodeSource::getLocation)
                .map(URL::getFile)
                .orElse("");
    }


    protected static List<String> getTargetJarList(String agentArgs) {
        List<String> targetJarList = new ArrayList<>();
        if (agentArgs != null) {
            String[] arr = agentArgs.split(",");
            for (String str : arr) {
                str = str.trim();
                if (str.length() > 0) {
                    targetJarList.add(str);
                }
            }
        }
        return targetJarList;
    }
}
