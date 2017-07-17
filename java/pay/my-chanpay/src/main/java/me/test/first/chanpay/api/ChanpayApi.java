package me.test.first.chanpay.api;

import org.springframework.http.*;

/**
 * 参考：《互联网商户接入接口文档》
 * http://dev.chanpay.com/doku.php
 *
 * - aa
 * - bb
 *
 * ```java
 * public class A {
 *     public static void main (String[]args){
 *         System.out.println ("aaa" + 111);
 *     }
 * }
 * ```
 *
 * ![Example Diagram](example.png)
 *
 * @uml example.png
 * Alice -> Bob: 你好呀
 * Bob --> Alice: 滚犊子
 */
public interface ChanpayApi<Req extends BaseReq, Resp extends BaseResp> {

    ResponseEntity<Resp> execute(
            HttpEntity<Req> reqEntity
    );

    Resp execute(
            Req req
    );

    String getGatewayUrl();

}
