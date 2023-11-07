package me.test.first.spring.boot.test.bfpp;


/**
 * 通过 BeanFactoryPostProcessor 优先执行的特性，在所有 spring bean 初始化完成前，优先完成部分初始化工作。
 * <p>
 * 缩写说明：
 * - BFPP : BeanFactoryPostProcessor
 * - BDRPP : BeanDefinitionRegistryPostProcessor  (是 BeanFactoryPostProcessor 的子接口）
 * - BPP   : BeanPostProcessor
 * <p>
 * 初始化的先后顺序：
 * <pre><code>
 * 1. 先调用 实现了 BeanDefinitionRegistryPostProcessor 的 BFPP
 *     1.1 AbstractApplicationContext#beanFactoryPostProcessors 中所有实现了 BDRPP 接口的 BFPP，
 *         这些对象 与 PriorityOrdered/Ordered 无关，按照编程的注册顺序来
 *     1.2 beanFactory 中 所有实现了 BDRPP + PriorityOrdered 接口的 BFPP （及其依赖的bean）.
 *         这些对象 按照 PriorityOrdered 的优先级来
 *     1.3 beanFactory 中 所有实现了 BDRPP + Ordered 接口的 BFPP （及其依赖的bean）.
 *         这些对象 按照 Ordered 的优先级来。
 *     1.4 【递归】beanFactory 中 其他 BDRPP （未实现 PriorityOrdered/Ordered 接口）的 BFPP （及其依赖的bean）.
 *         这些对象 的顺序不保障。
 *         如果 在 BDRPP 中又引入了新的 BDRPP，则新引入的这一小批内部按照 PriorityOrdered/Ordered/无 排序后，整批排队后执行。
 * 2. 再调用 未实现 BDRPP 的 BFPP
 *     2.1 AbstractApplicationContext#beanFactoryPostProcessors 中 未实现  BDRPP 接口的 BFPP ,
 *         这些对象 与 PriorityOrdered/Ordered 无关，按照编程的注册顺序来
 *     2.2 beanFactory 中 未实现 BDRPP, 但实现了 PriorityOrdered 接口的 BFPP （及其依赖的bean）.
 *         这些对象 按照 PriorityOrdered 的优先级来
 *     2.3 beanFactory 中 未实现 BDRPP, 但实现了 Ordered 接口的 BFPP （及其依赖的bean）.
 *         这些对象 按照 Ordered 的优先级来
 *     2.4 beanFactory 中 未实现 BDRPP/PriorityOrdered/Ordered 接口的 BFPP （及其依赖的bean）.
 *         这些对象 的顺序不保障。
 * 3. 实例化并注册 BPP
 *     3.1 beanFactory 中 实现了 PriorityOrdered 接口的 BPP（及其依赖的bean）
 *     3.2 beanFactory 中 实现了 Ordered 接口的 BPP（及其依赖的bean）
 *     3.3 beanFactory 中 未实现 PriorityOrdered/Ordered 接口的 BPP（及其依赖的bean）
 * </code></pre>
 * 更多源码细节请参考: <a href="https://github.com/spring-projects/spring-framework/blob/2b4c1e265c7fd3d1baa52da137831136d486ce1e/spring-context/src/main/java/org/springframework/context/support/PostProcessorRegistrationDelegate.java#L59">org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors</a>
 * <p>
 * 建议：
 * <pre><code>
 * 1. 优先使用 Ordered + BeanPostProcessor: 因为:
 *    1.1 可以以 spring bean 的方式初始化
 *    1.2 可以使用几乎所有 普通 bean 初始化的功能：比如: XxxAwre, @Value, placeHolder 等
 * 2. 尽量使用 Ordered + BeanFactoryPostProcessor
 * 3. 尽量不要使用 PriorityOrdered + BeanFactoryPostProcessor,
 *    因为 PriorityOrdered 一般是 spring 框架内部使用，使用时需要对 spring 源码相当熟悉，
 *    且熟知 spring 什么阶段、在哪里、 注入了 什么 PriorityOrdered 的 BeanFactoryPostProcessor。
 *    以便知道新加的类的执行的准确顺序，可以使用的特性（比如哪些 Aware 接口）。
 * 4. 尽量不要使用 BeanDefinitionRegistryPostProcessor ，原因同上。
 * 5. 实现的 BeanFactoryPostProcessor 类请尽量实现 Ordered 接口，否则不容易控制其执行的先后顺序。
 * </code></pre>
 * <p>
 * 常见 BFPP:
 * <pre><code>
 * - PriorityOrdered + BeanFactoryPostProcessor
 *      - org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
 * - rdered + BeanFactoryPostProcessor
 * </code></pre>
 * <p>
 * <p>
 * 更多源码细节请参考: org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors
 *
 * @see org.springframework.core.PriorityOrdered
 * @see org.springframework.core.Ordered
 */
public class SpringOrderConstants {
    /*
      ================================= 实现 PriorityOrdered 接口的 BeanFactoryPostProcessor 的顺序。
        请按照 从小到大的 顺序声明（数值越小，越早执行）。声明示例DEMO:
        public static final int PRIORITY_ORDERED_BFPP_XXX = 100;
     */



    /*
      ================================= 实现 Ordered 接口的 BeanFactoryPostProcessor 的顺序。
        请按照 从小到大的 顺序声明（数值越小，越早执行）。声明示例DEMO:
        public static final int ORDERED_BFPP_YYY = 100;
     */


     /*
      ================================= 实现 PriorityOrdered 接口的 BeanPostProcessor 的顺序。
        请按照 从小到大的 顺序声明（数值越小，越早执行）。声明示例DEMO:
        public static final int PRIORITY_ORDERED_BPP_XXX = 100;
     */

     /*
      ================================= 实现 Ordered 接口的 BeanPostProcessor 的顺序。
        请按照 从小到大的 顺序声明（数值越小，越早执行）。声明示例DEMO:
        public static final int ORDERED_BPP_XXX = 100;
     */
}
