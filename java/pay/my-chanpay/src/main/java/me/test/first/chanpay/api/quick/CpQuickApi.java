package me.test.first.chanpay.api.quick;

import me.test.first.chanpay.api.*;

/**
 * 畅捷支付 - 快捷支付。
 *
 * 参考 : 《代收付产品接口说明书》
 */
public interface CpQuickApi extends CpApi {


    String S_authReq_merchant = "nmg_biz_api_auth_req";
    String S_authReq_bank = "nmg_canal_api_auth_req";
    String S_pageAuthReq = "nmg_canal_api_auth_req";
    String S_authSms = "nmg_api_auth_sms";
    String S_unbind = "nmg_assign_api_auth_unbind";
    String S_pay = "nmg_biz_api_quick_payment";
    String S_confirmPaySms = "nmg_api_quick_payment_smsconfirm";
    String S_directPay = "nmg_zft_api_quick_payment";
    String S_oneKeyPay = "nmg_quick_onekeypay";
    String S_pay1 = "nmg_nquick_onekeypay";
    String S_queryBind = "nmg_api_auth_info_qry";
    String S_resendPaySms = "nmg_api_quick_payment_resend";
    String S_refund = "nmg_api_refund";
    String S_getRefundTradeFile = "nmg_api_refund_trade_file";
    String S_receiptConfirm = "nmg_api_quick_payment_receiptconfirm";
    String S_queryTrade = "nmg_api_query_trade";
    String S_getDailyTradeFile = "nmg_api_everyday_trade_file";
    String S_balancePay = "nmg_balance_payment_trade";

    // 4.4.2.1 鉴权绑卡请求（API）
    void authReq();

    // 4.4.2.2 鉴权绑卡请求（畅捷前台）
    void pageAuthReq();

    // 4.4.2.3 鉴权绑卡确认接口（API）
    void authSms();

    // 4.4.2.4 鉴权解绑接口（API）
    void unbind();

    // 4.4.2.5 支付请求接口（API）
    void pay();

    // 4.4.2.6 支付确认接口（API）
    void confirmPaySms();

    // 4.4.2.7 直接支付请求接口（API）
    void directPay();

    // 4.4.2.8 直接支付请求接口（畅捷前台）
    void oneKeyPay();

    // 4.4.2.9 支付请求接口（畅捷前台）
    void pay1();

    // 4.4.2.10 绑卡查询接口
    void queryBInd();

    // 4.4.2.11 短信验证码重发接口
    void resendPaySms();

    // 4.4.2.12 退款接口
    void refund();

    // 4.4.2.13 退款对账单
    void getRefundTradeFile();

    // 4.4.2.14 确认收货接口
    void receiptConfirm();

    // 4.4.2.15 订单查询
    void queryTrade();

    // 4.4.2.16 交易对账单
    void getDailyTradeFile();

    // 4.4.2.17 余额支付接口
    void balancePay();

}
