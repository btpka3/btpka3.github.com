package me.test.first.chanpay.api.quick;

import me.test.first.chanpay.api.*;

/**
 * 畅捷支付 - 快捷支付。
 *
 * 参考 : 《代收付产品接口说明书》
 */
public interface CpQuickPushApi extends CpApi {


    // 4.4.3.1 鉴权状态异步通知
    void bindNotify();

    // 4.4.3.2 支付状态异步通知
    void tradeNotify();

}
