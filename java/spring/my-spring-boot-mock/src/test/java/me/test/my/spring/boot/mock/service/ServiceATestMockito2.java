package me.test.my.spring.boot.mock.service;


import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.junit.*;
import org.slf4j.*;

import java.io.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
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

}