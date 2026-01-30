package me.test.jdk.javax.security.auth.login;

import com.sun.security.auth.callback.TextCallbackHandler;
import javax.security.auth.Subject;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/19
 */
public class LoginContextTest {


    @SneakyThrows
    @Test
    public void x() {
        Configuration config = Configuration.getConfiguration();

        //Subject subject = new Subject(true,);

//        LoginContext lc = new LoginContext(
//                "CountFiles",
//                subject,
//                new TextCallbackHandler(),
//                config
//        );
        LoginContext lc = new LoginContext(
                "Client",
                new TextCallbackHandler()
        );

        try {
            lc.login();
            //如果没有异常抛出，则表示认证成功
        } catch (Exception e) {
            System.out.println("Login failed: " + e);
            System.exit(-1);
        }

        //以认证用户的身份执行代码
        Object o = Subject.callAs(lc.getSubject(), () -> {
            return "private data with " + lc.getSubject();
        });
        System.out.println("User " + lc.getSubject() + " found " + o + " files.");
        System.exit(0);
    }
}
