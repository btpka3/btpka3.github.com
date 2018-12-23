package me.test.my.spring.boot.mock.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(MockitoJUnitRunner.class)  // MockitoAnnotations.initMocks(ServiceATestMockito2.class);
public class ServiceATestMockito2 {

    protected final Logger log = LoggerFactory.getLogger(getClass());

//    @Before
//    public void init(){
//        MockitoAnnotations.initMocks(ServiceATestMockito2.class);
//    }

    @Mock
    private ServiceC mockC;

    @Test
    public void test0() throws IOException {

        List mockedList = mock(List.class);

        // using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }


    @Test
    public void test1() throws IOException {

        ServiceB mockB = mock(ServiceB.class);
        //ServiceC mockC = mock(ServiceC.class);

        ServiceA a = new ServiceA();
        a.b = mockB;
        a.c = mockC;

        // stubbing
        when(mockB.add(1)).thenReturn(3);
        when(mockC.str(8)).thenReturn("88");


        String msg = a.hi("zhang3");
        assertThat(msg).isEqualTo("hi zhang3 3 88");
    }

    @Test
    public void test2() throws IOException {
        ServiceA a = new ServiceA();
        try {
            a.hi("li4");
            fail("应当抛出异常");
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("ERR");
        }

    }

    @Test
    public void s1() throws IOException {
        ServiceA a = new ServiceA();

        // FIXME: 修改 consumer 调用
        a.s();
    }

    @Test
    public void x1() {
        ServiceA a = new ServiceA();
        a.c = mockC;

        when(mockC.str(anyInt()))
                .thenAnswer(iv -> "C999");
        a.x();
    }

}