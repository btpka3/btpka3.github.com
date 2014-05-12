package me.test;

import java.util.EventObject;

public class MyEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    // FIXME: 这是是否应当限定类型
    public MyEvent(Object source) {
        super(source);
    }

}
