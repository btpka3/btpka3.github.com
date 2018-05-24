package com.github.btpka3.first.antlr4;

public class Test {
    private final java.lang.String CN_STRING = "This is 中国";
    private final String EN_STRING = "This is Chin" + "a" + ".";

    @Qualifier( "xxxBean")
    private String NUM = 1 + 2 + 3.0;

    // 处理不了，因为语法分析时，不知道其 getTime() 返回值的类型的。
    private final String STR1 = 1 + getTime();


    private void func1(String name) {
        System.out.println("1你好: " + name + " " + getTime());
    }

    private String getTime() {
        return new Date().toString();
    }

    private void func2(String name) {

        // 变量类型的，应该只提醒
        String s = "2你" + "好";
        // 多条语句匹配不了
        s += name;
        System.out.println(s + " ~");
    }

    private void func3(String name) {
        System.out.printf("3你好 {0} ~", name);
    }

    private void func4(String name) {
        Logger logger;
        // 可能潜在日志格式中文输出的，只提示
        logger.info("4你好 {} ~", name);
    }

}