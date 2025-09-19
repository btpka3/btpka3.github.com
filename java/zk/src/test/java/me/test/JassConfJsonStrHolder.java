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
                    "username": "g9_sup_monitor",
                    "password": "A-95Mf560lx_3rd-2_9WvczM"
                  }
                }
              ]
            }
            """;
}
