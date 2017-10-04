package io.github.btpka3.cglib;

import net.sf.cglib.beans.*;
import net.sf.cglib.core.*;
import net.sf.cglib.proxy.*;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.reflect.*;
import net.sf.cglib.util.*;
import org.junit.*;

import java.lang.reflect.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

public class Test01 {

    @Test
    public void testFixedValue() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "Hello cglib!";
            }
        });
        SampleClass proxy = (SampleClass) enhancer.create();
        assertThat(proxy.test(null)).isEqualTo("Hello cglib!");
        assertThat(proxy.toString()).isEqualTo("Hello cglib!");
    }

    @Test
    public void testInvocationHandler() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args)
                    throws Throwable {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return "Hello cglib!";
                } else {
                    throw new RuntimeException("Do not know what to do.");
                }
            }
        });
        SampleClass proxy = (SampleClass) enhancer.create();
        assertThat(proxy.test(null)).isEqualTo("Hello cglib!");

        assertThatThrownBy(() -> proxy.toString())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Do not know what to do.");
    }

    @Test
    public void testMethodInterceptor() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
                    throws Throwable {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return "Hello cglib!";
                } else {
                    return proxy.invokeSuper(obj, args);
                }
            }
        });
        SampleClass proxy = (SampleClass) enhancer.create();
        assertThat(proxy.test(null)).isEqualTo("Hello cglib!");
        assertThat(proxy.toString()).isNotEqualTo("Hello cglib!");
        proxy.hashCode(); // Does not throw an exception or result in an endless loop.
    }

    @Test
    public void testCallbackFilter() throws Exception {
        Enhancer enhancer = new Enhancer();
        CallbackHelper callbackHelper = new CallbackHelper(SampleClass.class, new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return new FixedValue() {
                        @Override
                        public Object loadObject() throws Exception {
                            return "Hello cglib!";
                        }

                        ;
                    };
                } else {
                    return NoOp.INSTANCE; // A singleton provided by NoOp.
                }
            }
        };
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallbackFilter(callbackHelper);
        enhancer.setCallbacks(callbackHelper.getCallbacks());
        SampleClass proxy = (SampleClass) enhancer.create();
        assertEquals("Hello cglib!", proxy.test(null));
        assertNotEquals("Hello cglib!", proxy.toString());
        proxy.hashCode(); // Does not throw an exception or result in an endless loop.
    }


    @Test
    public void testImmutableBean() throws Exception {
        SampleBean bean = new SampleBean();
        bean.setValue("aaa");


        SampleBean immutableBean = (SampleBean) ImmutableBean.create(bean);
        assertEquals("aaa", immutableBean.getValue());

        // 从原始的接口上可以修改
        bean.setValue("bbb");
        assertEquals("bbb", immutableBean.getValue());

        // 但从创建类上，就无法修改
        assertThatThrownBy(() -> immutableBean.setValue("ccc"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Bean is immutable");

    }

    @Test
    public void testBeanGenerator() throws Exception {
        BeanGenerator beanGenerator = new BeanGenerator();
        beanGenerator.addProperty("value", String.class);
        Object myBean = beanGenerator.create();

        Method setter = myBean.getClass().getMethod("setValue", String.class);
        setter.invoke(myBean, "aaa");
        Method getter = myBean.getClass().getMethod("getValue");
        assertEquals("aaa", getter.invoke(myBean));
    }


    @Test
    public void testBeanCopier() throws Exception {
        BeanCopier copier = BeanCopier.create(SampleBean.class, OtherSampleBean.class, false);
        SampleBean bean = new SampleBean();
        bean.setValue("Hello cglib!");
        OtherSampleBean otherBean = new OtherSampleBean();
        copier.copy(bean, otherBean, null);
        assertEquals("Hello cglib!", otherBean.getValue());
    }

    @Test
    public void testBulkBean() throws Exception {
        BulkBean bulkBean = BulkBean.create(
                SampleBean.class,
                new String[]{"getValue"},
                new String[]{"setValue"},
                new Class[]{String.class}
        );
        SampleBean bean = new SampleBean();
        bean.setValue("Hello world!");
        assertEquals(1, bulkBean.getPropertyValues(bean).length);
        assertEquals("Hello world!", bulkBean.getPropertyValues(bean)[0]);
        bulkBean.setPropertyValues(bean, new Object[]{"Hello cglib!"});
        assertEquals("Hello cglib!", bean.getValue());
    }

    @Test
    public void testKeyFactory() throws Exception {
        // 使用多个值来创建用于 Map 存储时的 key
        SampleKeyFactory keyFactory = (SampleKeyFactory) KeyFactory.create(SampleKeyFactory.class);
        Object key = keyFactory.newInstance("foo", 42);
        Map<Object, String> map = new HashMap<>();
        map.put(key, "Hello cglib!");
        assertEquals("Hello cglib!", map.get(keyFactory.newInstance("foo", 42)));
    }

    @Test
    public void testMixin() throws Exception {
        Mixin mixin = Mixin.create(
                new Class[]{
                        Interface1.class,
                        Interface2.class,
                        MixinInterface.class
                },
                new Object[]{
                        new Class1(),
                        new Class2()
                }
        );
        MixinInterface mixinDelegate = (MixinInterface) mixin;
        assertEquals("first", mixinDelegate.first());
        assertEquals("second", mixinDelegate.second());
    }

    @Test
    public void testStringSwitcher() throws Exception {
        String[] strings = new String[]{"one", "two"};
        int[] values = new int[]{10, 20};
        StringSwitcher stringSwitcher = StringSwitcher.create(strings, values, true);
        assertEquals(10, stringSwitcher.intValue("one"));
        assertEquals(20, stringSwitcher.intValue("two"));
        assertEquals(-1, stringSwitcher.intValue("three"));
    }


    @Test
    public void testInterfaceMaker() throws Exception {
        Signature signature = new Signature(
                "foo",
                org.objectweb.asm.Type.DOUBLE_TYPE,
                new org.objectweb.asm.Type[]{
                        org.objectweb.asm.Type.INT_TYPE
                }
        );
        InterfaceMaker interfaceMaker = new InterfaceMaker();
        interfaceMaker.add(signature, new org.objectweb.asm.Type[0]);
        Class iface = interfaceMaker.create();
        assertEquals(1, iface.getMethods().length);
        assertEquals("foo", iface.getMethods()[0].getName());
        assertEquals(double.class, iface.getMethods()[0].getReturnType());
    }

    @Test
    public void testMethodDelegate() throws Exception {
        SampleBean bean = new SampleBean();
        bean.setValue("Hello cglib!");
        BeanDelegate delegate = (BeanDelegate) MethodDelegate.create(
                bean, "getValue", BeanDelegate.class);
        assertEquals("Hello cglib!", delegate.getValueFromDelegate());
    }

    @Test
    public void testMulticastDelegate() throws Exception {
        MulticastDelegate multicastDelegate = MulticastDelegate.create(
                DelegatationProvider.class
        );
        SimpleMulticastBean first = new SimpleMulticastBean();
        SimpleMulticastBean second = new SimpleMulticastBean();
        multicastDelegate = multicastDelegate.add(first);
        multicastDelegate = multicastDelegate.add(second);

        DelegatationProvider provider = (DelegatationProvider) multicastDelegate;
        provider.setValue("Hello world!");

        assertEquals("Hello world!", first.getValue());
        assertEquals("Hello world!", second.getValue());
    }

    @Test
    public void testConstructorDelegate() throws Exception {
        SampleBeanConstructorDelegate constructorDelegate = (SampleBeanConstructorDelegate) ConstructorDelegate.create(
                SampleBean.class,
                SampleBeanConstructorDelegate.class
        );
        SampleBean bean = (SampleBean) constructorDelegate.newInstance();
        assertTrue(SampleBean.class.isAssignableFrom(bean.getClass()));
    }

    @Test
    public void testParallelSorter() throws Exception {
        Integer[][] value = {
                {4, 3, 9, 0},
                {2, 1, 6, 0}
        };
        ParallelSorter.create(value).mergeSort(0);
        for(Integer[] row : value) {
            int former = -1;
            for(int val : row) {
                assertTrue(former < val);
                former = val;
            }
        }
    }
//
//    @Test
//    public void testFastClass() throws Exception {
//        FastClass fastClass = FastClass.create(SampleBean.class);
//        FastMethod fastMethod = fastClass.getMethod(
//                SampleBean.class.getMethod("getValue")
//        );
//
//        MyBean myBean = new MyBean();
//        myBean.setValue("Hello cglib!");
//        assertTrue("Hello cglib!", fastMethod.invoke(myBean, new Object[0]));
//    }
}
