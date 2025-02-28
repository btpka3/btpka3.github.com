package com.github.btpka3.first.javaagent;

import java.lang.instrument.Instrumentation;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;
/**
 * @date 2023/8/15
 */
public class MyAgent {

    /**
     * jvm 参数形式启动 : `-javaagent:xxx.jar`
     *
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst) {


        System.out.println("premain start: agentArgs=" + agentArgs);

        inst.addTransformer(new WatchSetTccl());

//        new AgentBuilder.Default()
//                .type(ElementMatchers.nameContains("YourTargetClass"))  // 指定需要监控的类
//                .transform((builder, typeDescription, classLoader, module) ->
//                        builder.method(ElementMatchers.isSynchronized())  // 匹配所有同步方法
//                                .intercept(Advice.to(MethodCallAdvice.class)))
//                .installOn(inst);
    }

    /**
     * 动态 attach 方式启动
     *
     * @param agentArgs
     * @param inst
     */

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agentmain start: agentArgs=" + agentArgs);
    }
}
