package me.test.jdk.javax.security.auth.login;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import java.security.AuthProvider;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/19
 */
public class MyAuthProvider extends AuthProvider {

    protected MyAuthProvider(String name, String version, String info) {
        super(name, version, info);
        // ⭕️： 注册可以提供的 服务列表
        putService(new Service(this, "Configuration", "aaa", MyConfigurationSpi.class.getName(), null, null));
    }

    @Override
    public void login(Subject subject, CallbackHandler handler) throws LoginException {

    }

    @Override
    public void logout() throws LoginException {

    }

    @Override
    public void setCallbackHandler(CallbackHandler handler) {

    }
}


