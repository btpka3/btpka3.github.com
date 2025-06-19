package me.test.jdk.java.rmi;

/**
 * @author dangqian.zll
 * @date 2025/6/19
 */
public class MyRmiServiceImpl implements MyRmiService {
    public MyRmiServiceImpl() {
    }

    @Override
    public String sayHello(String name) {
        return "hi " + name;
    }
}
