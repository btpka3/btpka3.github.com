package com.alibaba.security.gong9.sandbox.tools.sandbox;

import com.alibaba.jvm.sandbox.api.http.printer.Printer;
import com.alibaba.jvm.sandbox.api.listener.ext.Advice;
import com.alibaba.jvm.sandbox.api.listener.ext.AdviceListener;

/**
 * @author dangqian.zll
 * @date 2024/10/22
 */
public class MyAdviceListener extends AdviceListener {

    final Printer printer;

    public MyAdviceListener(Printer printer) {
        this.printer = printer;
    }

    private String getTracingTitle(final Advice advice) {
        return "Tracing for : "
                + advice.getBehavior().getDeclaringClass().getName()
                + "."
                + advice.getBehavior().getName()
                + " by "
                + Thread.currentThread().getName()
                ;
    }

    private String getEnterTitle(final Advice advice) {
        return "Enter : "
                + advice.getBehavior().getDeclaringClass().getName()
                + "."
                + advice.getBehavior().getName()
                + "(...);"
                ;
    }

    @Override
    protected void before(Advice advice) throws Throwable {
        final TTree tTree;
        if (advice.isProcessTop()) {
            advice.attach(tTree = new TTree(true, getTracingTitle(advice)));
        } else {
            tTree = advice.getProcessTop().attachment();
        }
        tTree.begin(getEnterTitle(advice));
    }

    @Override
    protected void afterReturning(Advice advice) throws Throwable {
        final TTree tTree = advice.getProcessTop().attachment();
        tTree.end();
        finish(advice);
    }

    @Override
    protected void afterThrowing(Advice advice) throws Throwable {
        final  TTree tTree = advice.getProcessTop().attachment();
        tTree.begin("throw:" + advice.getThrowable().getClass().getName() + "()").end();
        tTree.end();
        finish(advice);
    }

    private void finish(Advice advice) {
        if (advice.isProcessTop()) {
            final TTree tTree = advice.attachment();
            printer.println(tTree.rendering());
        }
    }

    @Override
    protected void beforeCall(final Advice advice,
                              final int callLineNum,
                              final String callJavaClassName,
                              final String callJavaMethodName,
                              final String callJavaMethodDesc) {
        final TTree tTree = advice.getProcessTop().attachment();
        tTree.begin(callJavaClassName + ":" + callJavaMethodName + "(@" + callLineNum + ")");
    }

    @Override
    protected void afterCallReturning(final Advice advice,
                                      final int callLineNum,
                                      final String callJavaClassName,
                                      final String callJavaMethodName,
                                      final String callJavaMethodDesc) {
        final TTree tTree = advice.getProcessTop().attachment();
        tTree.end();
    }

    @Override
    protected void afterCallThrowing(final Advice advice,
                                     final int callLineNum,
                                     final String callJavaClassName,
                                     final String callJavaMethodName,
                                     final String callJavaMethodDesc,
                                     final String callThrowJavaClassName) {
        final TTree tTree = advice.getProcessTop().attachment();
        tTree.set(tTree.get() + "[throw " + callThrowJavaClassName + "]").end();
    }
}
