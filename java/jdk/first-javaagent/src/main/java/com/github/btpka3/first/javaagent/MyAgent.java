package com.github.btpka3.first.javaagent;

import java.lang.instrument.Instrumentation;

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
