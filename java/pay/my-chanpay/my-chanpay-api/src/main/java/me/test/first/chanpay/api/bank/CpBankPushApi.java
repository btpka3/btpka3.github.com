package me.test.first.chanpay.api.bank;

import me.test.first.chanpay.api.*;

/**
 * 畅捷支付 - 网银支付。
 *
 * 参考 : 《互联网商户接入接口文档》
 */
public interface CpBankPushApi extends CpApi {

    // 2.8 交易状态通知接口
    void tradeNotify();

    // 2.9 退款状态变更通知
    void refundNotify();
}
