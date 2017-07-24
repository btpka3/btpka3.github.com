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
    String S_confirmWarrantTrade = "nmg_ensure_trade_confirm";
    String S_queryTrade = "nmg_api_query_trade";
    String S_refund = "nmg_api_refund";
    String S_getDailyTradeFile = "nmg_api_everyday_trade_file";
    String S_getDailyRefundFile = "nmg_api_refund_trade_file";


    /**
     * mag_init_code_pay - 二维码主扫 (消费者扫码)
     *
     *
     * @param req 请求信息
     * @return 响应信息
     */
    UserScanResp userScan(UserScanReq req);

    /**
     * mag_pass_code_pay -  二维码被扫 (商家扫码)
     *
     * @param req 请求信息
     * @return 响应信息
     */
    MerchantScanResp merchantScan(MerchantScanReq req);

    /**
     * mag_one_code_pay 一码付
     *
     * @param req 请求信息
     * @return 响应信息
     */
    OneCodePayResp oneCodePay(OneCodePayReq req);

    /**
     * mag_pub_com_pay - 微信公众号 (大商户模式)
     *
     * @param req 请求信息
     * @return 响应信息
     */
    ChanpayWxComPayResp chanpayWxComPay(ChanpayWxComPayReq req);


    /**
     * mag_pub_sinm_pay - 微信公众号 (一户一码模式)
     *
     * @param req 请求信息
     * @return 响应信息
     */
    MerchantWxComPayResp merchantWxComPay(MerchantWxComPayReq req);

    /**
     * mag_ali_pub_sinm_pay - 支付宝服务窗 (一户一码模式)
     *
     * @param req 请求信息
     * @return 响应信息
     */
    MerchantAliPubPayResp merchantAliPubPay(MerchantAliPubPayReq req);

    // 4.2.7 支付宝WAP支付

    /**
     * mag_ali_wap_pay - 支付宝WAP支付
     *
     * @param req 请求信息
     * @return 响应信息
     */
    AliWapPayResp aliWapPay(AliWapPayReq req);

    /**
     * nmg_ensure_trade_confirm - 担保交易确认接口
     *
     * @param req 请求信息
     * @return 响应信息
     */
    ConfirmWarrantTradeResp confirmWarrantTrade(ConfirmWarrantTradeReq req);

    /**
     * nmg_api_query_trade - 商户订单状态查询
     *
     * @param req 请求信息
     * @return 响应信息
     */
    QueryTradeResp queryTrade(QueryTradeReq req);

    /**
     * nmg_api_refund - 商户退款申请请求
     *
     * @param req 请求信息
     * @return 响应信息
     */
    RefundResp refund(RefundReq req);

    /**
     * nmg_api_everyday_trade_file - 交易对账单
     *
     * @param req 请求信息
     * @return 响应信息
     */
    GetDailyTradeFileResp getDailyTradeFile(GetDailyTradeFileReq req);

    // 4.2.11 退款对账单

    /**
     * nmg_api_query_trade - 退款对账单
     *
     * @param req 请求信息
     * @return 响应信息
     */
    GetDailyRefundFileResp getDailyRefundFile(GetDailyRefundFileReq req);


}
