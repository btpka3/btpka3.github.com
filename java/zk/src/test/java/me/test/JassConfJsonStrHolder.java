package me.test;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/19
 */
public class JassConfJsonStrHolder {

    // @see javax.security.auth.login.AppConfigurationEntry
    // language=JSON
    public static String jassConfJsonStr = """
            {
              "Client": [
                {
                  "loginModuleName": "org.apache.zookeeper.server.auth.DigestLoginModule",
                  "controlFlag": "required",
                  "options": {
                    "username": "xxxUser",
                    "password": "xxxPassword"
                  }
                }
              ]
            }
            """;
}
