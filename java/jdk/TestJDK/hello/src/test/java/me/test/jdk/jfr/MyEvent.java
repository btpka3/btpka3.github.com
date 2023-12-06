package me.test.jdk.jfr;


import jdk.jfr.*;

/**
 * 自定义 JFR 事件
 */
// 默认不开启
@Enabled(false)
// 不记录栈信息
@StackTrace(false)
@Registered(true)
public class MyEvent extends Event {
    public String info;

    // 对应方法需要加上该注解, 参数为RegExpControl
    // 返回类型必须为boolean, 参数数目只能为1个, 必须为集成SettingControl类
//    @SettingDefinition
//    protected boolean infoFilter(RegExpControl control) {
//        return control.matches(uri);
//    }
}
