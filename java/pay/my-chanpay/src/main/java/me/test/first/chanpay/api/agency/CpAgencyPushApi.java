package me.test.first.chanpay.api.agency;

import me.test.first.chanpay.api.*;

/**
 * 畅捷支付 - 代收付。
 *
 * 参考 : 《代收付产品接口说明书》
 */
public interface CpAgencyPushApi extends CpApi {


    // 4.3.1 代付状态变更
    void tradeNotify();

    // 4.3.2 代扣状态变更
    void withholdNotify();

    // 4.3.3 退款状态变更
    void refundNotify();
}
