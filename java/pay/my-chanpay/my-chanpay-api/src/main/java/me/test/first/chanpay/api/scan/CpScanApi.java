package me.test.first.chanpay.api.scan;

import me.test.first.chanpay.api.*;
import me.test.first.chanpay.api.scan.dto.*;

/**
 * 畅捷支付 - 二维码扫码产品。
 *
 * 参考 : 《畅捷支付商户接入文档 - 二维码扫码产品》
 */
public interface CpScanApi extends CpApi {


    String S_userScan = "mag_init_code_pay";
    String S_merchantScan = "mag_pass_code_pay";
    String S_oneCodePay = "mag_one_code_pay";
    String S_chanpayWxComPay = "mag_pub_com_pay";
    String S_merchantWxComPay = "mag_pub_sinm_pay";
    String S_merchantAliPubPay = "mag_ali_pub_sinm_pay";
    String S_aliWapPay = "mag_ali_wap_pay";
    String S_confirmEnsureTrade = "nmg_ensure_trade_confirm";
    String S_queryTrade = "nmg_api_query_trade";
    String S_refund = "nmg_api_refund";
    String S_getDailyTradeFile = "nmg_api_everyday_trade_file";
    String S_getDailyRefundFile = "nmg_api_refund_trade_file";


    // 4.2.1 二维码主扫 (消费者扫码)
    UserScanResp userScan(UserScanReq req);

    // 4.2.2 二维码被扫 (商家扫码)
    void merchantScan();

    // 4.2.3 一码付
    void oneCodePay();

    // 4.2.4 微信公众号 (大商户模式)
    void chanpayWxComPay();

    // 4.2.5 微信公众号 (一户一码模式)
    void merchantWxComPay();

    // 4.2.6 支付宝服务窗 (一户一码模式)
    void merchantAliPubPay();

    // 4.2.7 支付宝WAP支付
    void aliWapPay();


    // 4.2.8 担保交易确认接口
    void confirmEnsureTrade();

    // 4.2.9 商户订单状态查询
    void queryTrade();

    // 4.2.10 商户退款申请请求
    void refund();

    // 4.2.11 交易对账单
    void getDailyTradeFile();

    // 4.2.11 退款对账单
    void getDailyRefundFile();



}
