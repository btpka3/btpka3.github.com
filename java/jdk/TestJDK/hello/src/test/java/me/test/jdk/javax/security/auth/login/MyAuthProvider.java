package me.test.jdk.javax.security.auth.login;

import java.security.AuthProvider;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

/**
 * 默认的AuthProvider是 $JAVA_HOME/conf/security/java.security 中 属性 "login.configuration.provider" 配置的。
 * 如果未配置，则使用默认的: sun.security.provider.ConfigFile
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
    public void login(Subject subject, CallbackHandler handler) throws LoginException {}

    @Override
    public void logout() throws LoginException {}

    @Override
    public void setCallbackHandler(CallbackHandler handler) {}
}
