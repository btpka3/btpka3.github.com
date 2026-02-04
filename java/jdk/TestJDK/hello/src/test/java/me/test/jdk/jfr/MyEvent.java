package me.test.jdk.jfr;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Enabled;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Period;
import jdk.jfr.Registered;
import jdk.jfr.StackTrace;

/**
 * 自定义 JFR 事件
 */
// 默认不开启
@Name("me.test.jdk.jfr.MyEvent")
@Label("btpka3.MyEvent")
@Description("xxx")
@Category({"jmc上显示的1级分类", "jmc上显示的2级分类"})
@Enabled(false)
// 不记录栈信息
@StackTrace(false)
@Registered(true)
@Period("10s")
public class MyEvent extends Event {

    @Label("info")
    public String info;

    // 对应方法需要加上该注解, 参数为RegExpControl
    // 返回类型必须为boolean, 参数数目只能为1个, 必须为集成SettingControl类
    //    @SettingDefinition
    //    protected boolean infoFilter(RegExpControl control) {
    //        return control.matches(uri);
    //    }
}
