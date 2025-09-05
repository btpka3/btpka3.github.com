package me.test.first.spring.boot.test.mockito;

/**
 *
 * @author dangqian.zll
 * @date 2025/8/15
 */
public class InjectMockTestResource {

    public static class XxxApi {
        XxxService xxxService;

        public String hello(String name) {
            return "hello " + xxxService.doService(name);
        }
    }

    public interface XxxService {

        String doService(String eventCode);
    }

}
