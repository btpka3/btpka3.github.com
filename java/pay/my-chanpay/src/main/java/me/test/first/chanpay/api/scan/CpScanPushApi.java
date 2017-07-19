package me.test.first.chanpay.api.scan;

import me.test.first.chanpay.api.*;

/**
 * 畅捷支付 - 二维码扫码产品 - 推送API。
 *
 * 参考 : 《畅捷支付商户接入文档 - 二维码扫码产品》
 */
public interface CpScanPushApi extends CpApi {


    // 4.3.1 支付结果通知
    void tradeNotify();


}
