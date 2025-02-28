package io.github.btpka3.first.jvm.sandbox;

import com.alibaba.jvm.sandbox.api.Information;
import com.alibaba.jvm.sandbox.api.Module;
import com.alibaba.jvm.sandbox.api.ModuleLifecycle;
import com.alibaba.jvm.sandbox.api.ProcessController;
import com.alibaba.jvm.sandbox.api.annotation.Command;
import com.alibaba.jvm.sandbox.api.event.BeforeEvent;
import com.alibaba.jvm.sandbox.api.event.CallBeforeEvent;
import com.alibaba.jvm.sandbox.api.event.Event;
import com.alibaba.jvm.sandbox.api.filter.Filter;
import com.alibaba.jvm.sandbox.api.listener.EventListener;
import com.alibaba.jvm.sandbox.api.listener.ext.Advice;
import com.alibaba.jvm.sandbox.api.listener.ext.AdviceListener;
import com.alibaba.jvm.sandbox.api.listener.ext.EventWatchBuilder;
import com.alibaba.jvm.sandbox.api.resource.ModuleEventWatcher;
import org.kohsuke.MetaInfServices;

import javax.annotation.Resource;

/**
 * @author dangqian.zll
 * @date 2023/12/7
 */
@MetaInfServices(Module.class)
@Information(id = "broken-clock-tinker")
public class BrokenClockTinkerModule implements Module, ModuleLifecycle {

    @Resource
    private ModuleEventWatcher moduleEventWatcher;

    @Override
    public void loadCompleted() {
        System.out.println("======loadCompleted");
        Filter filter = new Filter() {
            @Override
            public boolean doClassFilter(
                    int access,
                    String javaClassName,
                    String superClassTypeJavaClassName,
                    String[] interfaceTypeJavaClassNameArray,
                    String[] annotationTypeJavaClassNameArray
            ) {
                return "com.test.XxxClass".equals(javaClassName);
            }

            @Override
            public boolean doMethodFilter(
                    int access,
                    String javaMethodName,
                    String[] parameterTypeJavaClassNameArray,
                    String[] throwsTypeJavaClassNameArray,
                    String[] annotationTypeJavaClassNameArray
            ) {
                return "xxxMethod".equals(javaMethodName);
            }

        };

//        EventWatcher eventWatcher = new EventWatcher() {
//
//            @Override
//            public EventWatchBuilder.IBuildingForUnWatching withProgress(ModuleEventWatcher.Progress progress) {
//                return null;
//            }
//
//            @Override
//            public void onUnWatched() {
//
//            }
//
//            @Override
//            public int getWatchId() {
//                return 0;
//            }
//        };

        EventListener eventListener = new EventListener() {

            @Override
            public void onEvent(Event event) throws Throwable {
                if (event instanceof CallBeforeEvent) {
                    CallBeforeEvent callBeforeEvent = (CallBeforeEvent) event;
                }
                if (event instanceof BeforeEvent) {
                    BeforeEvent beforeEvent = (BeforeEvent) event;
                    //beforeEvent.argumentArray.length;
                }
            }
        };


        moduleEventWatcher.watch(
                filter,
                eventListener,
                Event.Type.CALL_BEFORE,
                Event.Type.CALL_RETURN,
                Event.Type.CALL_THROWS
        );
    }

    @Command("repairCheckState")
    public void repairCheckState() {

        new EventWatchBuilder(moduleEventWatcher)
                .onClass("com.taobao.demo.Clock")
                .onBehavior("checkState")
                .onWatch(new AdviceListener() {
                    @Override
                    protected void before(Advice advice) throws Throwable {
                        // 开始计时
                    }

                    @Override
                    protected void after(Advice advice) throws Throwable {
                        // 计数，计时
                    }

                    /**
                     * 拦截{@code com.taobao.demo.Clock#checkState()}方法，当这个方法抛出异常时将会被
                     * AdviceListener#afterThrowing()所拦截
                     */
                    @Override
                    protected void afterThrowing(Advice advice) throws Throwable {

                        // 在此，你可以通过ProcessController来改变原有方法的执行流程
                        // 这里的代码意义是：改变原方法抛出异常的行为，变更为立即返回；void返回值用null表示
                        ProcessController.returnImmediately(null);
                    }
                });

    }

//    void foo() {
//        // BEFORE-EVENT
//        try {
//            // do something...
//            try {
//                //LINE-EVENT
//                //CALL_BEFORE-EVENT
//                a();
//                //CALL_RETURN-EVENT
//            } catch (Throwable cause) {
//                // CALL_THROWS-EVENT
//            }
//            //LiNE-EVENT
//            // RETURN-EVENT
//            return;
//        } catch (Throwable cause) {
//            // THROWS-EVENT
//        }
//    }

    @Override
    public void onLoad() throws Throwable {

    }

    @Override
    public void onUnload() throws Throwable {

    }

    @Override
    public void onActive() throws Throwable {

    }

    @Override
    public void onFrozen() throws Throwable {

    }
}