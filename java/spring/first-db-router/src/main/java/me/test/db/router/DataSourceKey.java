
package me.test.db.router;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设置作为数据源的Key。
 * <p>
 * 使用方式有以下两种：
 * <ul>
 * <li>使用在方法级别上：只允许设置 value 属性，value值是SpEL表达式。SpEL表达式中可以：
 * <ul>
 * <li>以当前被AOP的对象为rootObject，以 <code>"attr"</code> 方式引用当前对象的属性</li>
 * <li>以 <code>"@springBeanName"</code> 方式引用Spring管理的bean</li>
 * <li>以 <code>"#methodParamName"</code> 方式引用被AOP方法的参数（按参数名称）</li>
 * <li>以 <code>"#p0"</code>、<code>"#a0"</code> 方式引用被AOP方法的参数（按参数位置）</li>
 * </ul>
 * </li>
 * <li>使用在方法参数上：则只允许可选设置 prefix 属性。数据库路由key的值 = prefix + paramValue.toString()。</li>
 * </ul>
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.PARAMETER })
public @interface DataSourceKey {

    /** SpEl表达式 */
    String value() default "";

    /** 加在参数值上的前缀 */
    String prefix() default "";
}
