package me.test.first.chanpay.api.warrant;

import me.test.first.chanpay.api.*;

/**
 * 畅捷支付 - 担保API。
 *
 * 参考 : 《互联网商户接入接口文档》
 */
public interface CpWarrantPushApi extends CpApi {

    // 2.8 交易状态通知接口
    void tradeNotify();

    // 2.9 退款状态变更通知
    void refundNotify();

    // 2.15 出款状态变更通知
    void outNOtify();

    // 2.28 转账状态变更通知
    void transferNotify();
}
