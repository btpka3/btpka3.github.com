package com.github.btpka3.first.spring.loader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;

public class MyLoaderMain {

//    public static void main2(String[] args) throws Exception {
//        VirtualMachine vm = VirtualMachine.attach(args[0]);
//        String file = PrintAllClz.class.getProtectionDomain().getCodeSource().getLocation().getFile();
//        System.out.println("begin to load agent : " + file);
//        try {
//            vm.loadAgent(file);
//        } finally {
//            vm.detach();
//        }
//    }


/*
JAR_PATH=$(realpath ./build/libs/first-spring-loader-0.0.1-SNAPSHOT.jar)

# mainInvoke2Boot
# - 将 MyLoaderMain,MyLoaderClassLoader暴露到 AppClassLoader。
# - 在 PlatformClassLoader 下挂在两个 spring boot loader 的 classLoader
java -cp "${JAR_PATH}" com.github.btpka3.first.spring.loader.MyLoaderMain  mainInvoke2Boot 3600 "${JAR_PATH}" "${JAR_PATH}"
java -jar "${JAR_PATH}"   mainInvoke2Boot 3600 "${JAR_PATH}" "${JAR_PATH}"

# mainInvokeBootInvokeBoot
# - 将 MyLoaderMain,MyLoaderClassLoader暴露到 AppClassLoader。
# - 在 PlatformClassLoader 下挂在两个 spring boot loader 的 classLoader,
#   但第二个 spring boot loader 是由第一个 spring boot loader 触发加载的
java -jar "${JAR_PATH}" mainInvokeBootInvokeBoot 3600 "${JAR_PATH}" "${JAR_PATH}"
*/

    /**
     * 模拟AgentMain来启动。
     * <p>
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        System.out.println("111111");

        if (args.length == 0) {
            // 走到这里 预期 是 spring boot loader 加载的。
            MyBiz.show();
            return;
        }
        String[] emptyArgs = new String[0];

        System.out.println("args[0] = " + args[0]);
        if (Objects.equals("mainInvoke2Boot", args[0])) {
            for (int i = 2; i < args.length; i++) {
                String springBootJar = args[i];
                invokeBoot(springBootJar, emptyArgs);
            }
            sleep(Integer.valueOf(args[1]));
        }
        if (Objects.equals("mainInvokeBootInvokeBoot", args[0])) {
            String springBootJar = args[1];
            String[] newArgs = new String[1 + args.length - 2];
            newArgs[0] = "invokeBoot";
            System.arraycopy(args, 2, newArgs, 1, args.length - 2);
            invokeBoot(springBootJar, newArgs);

            sleep(Integer.valueOf(args[1]));
        }

        if (Objects.equals("invokeBoot", args[0])) {

            MyBiz.show();

            String springBootJar = args[1];
            invokeBoot(springBootJar, emptyArgs);
        }
    }

    protected static void invokeBoot(String springBootJar, String[] args) throws Exception {
        URL[] urls = new URL[]{
                new File(springBootJar).toURI().toURL()
        };
        System.out.println("url ====== " + new File(springBootJar).toURI().toURL());
        URLClassLoader classLoader = new MyLoaderClassLoader(urls, ClassLoader.getPlatformClassLoader());
        Class jarLauncherClass = classLoader.loadClass("org.springframework.boot.loader.launch.JarLauncher");
        Method mainMethod = jarLauncherClass.getMethod("main", String[].class);

        mainMethod.invoke(null, new Object[]{args});
    }

    protected static void sleep(int seconds) throws Exception {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {
            // NOOP
        }
    }
}