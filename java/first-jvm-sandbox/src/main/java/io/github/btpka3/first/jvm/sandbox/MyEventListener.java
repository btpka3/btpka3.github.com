package io.github.btpka3.first.jvm.sandbox;

import com.alibaba.jvm.sandbox.api.event.BeforeEvent;
import com.alibaba.jvm.sandbox.api.event.CallBeforeEvent;
import com.alibaba.jvm.sandbox.api.event.Event;
import com.alibaba.jvm.sandbox.api.listener.EventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2024/10/21
 */
public class MyEventListener implements EventListener {

    /**
     * KEY: 分钟的时间戳（毫秒）
     * VALUE: KEY: ClassLoader hash, VALUE: ClassLoaderStatInfo
     */
    Map<Long, Map<Integer, MyClassLoaderStatInfo>> classLoaderStatInfoMap = new HashMap<>();


    @Override
    public void onEvent(Event event) throws Throwable {
        if (event instanceof CallBeforeEvent) {
            CallBeforeEvent callBeforeEvent = (CallBeforeEvent) event;


        }
        if (event instanceof BeforeEvent) {
            BeforeEvent beforeEvent = (BeforeEvent) event;
            //beforeEvent.argumentArray.length;
        }

        //classLoaderStatInfoMap.get();
    }
}
