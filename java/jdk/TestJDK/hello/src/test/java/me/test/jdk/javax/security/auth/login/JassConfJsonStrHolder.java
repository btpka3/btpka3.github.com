package me.test.jdk.javax.security.auth.login;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/19
 */
public class JassConfJsonStrHolder {

    // @see javax.security.auth.login.AppConfigurationEntry
    // language=JSON
    public static String jassConfJsonStr =
            """
            {
              "Client": [
                {
                  "loginModuleName": "org.apache.zookeeper.server.auth.DigestLoginModule",
                  "controlFlag": "required",
                  "options": {
                    "username": "zhang3",
                    "password": "123456"
                  }
                }
              ]
            }
            """;
}
