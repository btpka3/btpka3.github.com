package com.alibaba.security.gong9.sandbox.tools.sandbox;

import com.alibaba.jvm.sandbox.api.Information;
import com.alibaba.jvm.sandbox.api.Module;
import com.alibaba.jvm.sandbox.api.ModuleLifecycleAdapter;
import com.alibaba.jvm.sandbox.api.http.printer.ConcurrentLinkedQueuePrinter;
import com.alibaba.jvm.sandbox.api.http.printer.Printer;
import com.alibaba.jvm.sandbox.api.listener.ext.EventWatchBuilder;
import com.alibaba.jvm.sandbox.api.listener.ext.EventWatcher;
import com.alibaba.jvm.sandbox.api.resource.ModuleEventWatcher;
import org.kohsuke.MetaInfServices;

import javax.annotation.Resource;
import java.io.PrintWriter;

/**
 * @author dangqian.zll
 * @date 2024/10/21
 */
@MetaInfServices(Module.class)
@Information(id = "stat-load-class")
public class StatLoadClassModule extends ModuleLifecycleAdapter implements Module{

    @Resource
    private ModuleEventWatcher moduleEventWatcher;

    @Override
    public void loadCompleted() {
        System.out.println("======loadCompleted");
//        Filter filter = new MyFilter();
//        EventListener listener = new MyEventListener();
//        moduleEventWatcher.watch(
//                filter,
//                listener,
//                Event.Type.CALL_BEFORE,
//                Event.Type.CALL_RETURN,
//                Event.Type.CALL_THROWS
//        );
//        ;


        final Printer printer = new ConcurrentLinkedQueuePrinter(new PrintWriter(System.out));

        final EventWatcher watcher = new EventWatchBuilder(moduleEventWatcher)
                .onClass("com.alibaba.security.gong9.sandbox.tools.test.MyDemoService*")
                .includeSubClasses()
                .onBehavior("*dd")
                .onWatching()
                .withCall()
                .withProgress(new ProgressPrinter(printer))
                .onWatch(new MyAdviceListener(printer));
    }
}
