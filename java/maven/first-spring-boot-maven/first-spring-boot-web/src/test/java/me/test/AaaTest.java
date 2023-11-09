package me.test;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import static org.mockito.Mockito.when;

//@PrepareForTest({Aaa.class})
@ContextConfiguration
public class AaaTest {


    //@Rule
    //public PowerMockRule rule = new PowerMockRule();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    String str;

    //@Autowired
    @Mock
    Bbb bbb;


    @InjectMocks
    Aaa aaa;

//    @Before
//    public void initMocks() {
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    public void test() {


        // 通过  SpringClassRule + SpringMethodRule 可以不通过 Runner 注入到 spring bean
        System.out.println(str);


        // 通过 MockitoRule +  @Mock 已经自动创建了 mock 对象
        System.out.println(bbb);

        // 由于 mock 的类 是 Bbb 的子类。Bbb 中的 @Autowired 会被 spring 处理
        System.out.println(bbb.getClass());
        System.out.println(bbb.getClass().getSuperclass());


        System.out.println(aaa);
        System.out.println(aaa.hi());

        // mock
        when(bbb.hello()).thenReturn("xxx");
        System.out.println(aaa.hi());
    }

}
