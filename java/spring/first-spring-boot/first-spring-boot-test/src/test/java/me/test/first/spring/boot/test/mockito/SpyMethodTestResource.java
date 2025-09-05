package me.test.first.spring.boot.test.mockito;

/**
 *
 * @author dangqian.zll
 * @date 2025/8/15
 */
public class SpyMethodTestResource {

    public static class XxxApi {

        public String hello(String name) {
            return "hello " + findRealName(name);
        }

        protected String findRealName(String name) {
            return name + ".real";
        }
    }


}
