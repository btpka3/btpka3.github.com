package me.test.first.spring.boot.test.arthas;

import org.junit.jupiter.api.Test;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/23
 */
public class ArthasApiImplTest {


    @Test
    public void x() {

        /*
        """
        curl -vSs -XPOST http://localhost:8563/api -d '
        {
          "action":"exec",
          "command":"version"
        }
        ' | jq

        curl -vSs -XPOST http://localhost:8563/api -d '
        {
          "action":"exec",
          "command":"sysenv"
        }
        ' | jq


        curl -vSs -XPOST http://localhost:8563/api -d '
        {
          "action":"exec",
          "command":"ognl 0.1+0.2"
        }
        ' | jq
        """
         */


        ArthasApiImpl api = new ArthasApiImpl();
        ArthasApi.ExecResp resp = api.exec("sysprop");
        System.out.println(resp);

    }
}

