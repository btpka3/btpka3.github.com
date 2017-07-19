package me.test.first.chanpay.api.bank;

import me.test.first.chanpay.api.*;

/**
 * 畅捷支付 - 网银支付。
 *
 * 参考 : 《互联网商户接入接口文档》
 * TODO
 */
public interface CpBankApi extends CpApi {

    String S_WEB_createTrade = "cjt_create_instant_trade";
    String S_WAP_createTrade = "cjt_wap_create_instant_trade";
    String S_WEB_createTrades = "cjt_create_batch_instant_trade";
    String S_WAP_createTrades = "cjt_wap_create_batch_instant_trade";
    String S_refund = "cjt_create_refund";
    String S_queryTrade = "cjt_query_trade";
    String S_getPayChannelList = "cjt_get_paychannel";
    String S_viewReceipt = "cjt_view_receipt";
    String S_getDailyTradeFile = "cjt_everyday_trade_file";
    String S_getRefundTradeFile = "cjt_refund_trade_file";
    String S_getFeeTradeFile = "cjt_fee_trade_file";

    // 2.3 单笔订单支付接口
    void createTrade();

    // 2.4 批量订单支付接口
    void createTrades();

    // 2.5 退款网关接口
    void refund();

    // 2.6 交易查询网关接口
    void queryTrade();

    // 2.7 查询银行列表接口
    void getPayChannelList();

    // 2.10 查看回单接口
    void viewReceipt();

    // 2.11 商户日支付交易对账单文件
    void getDailyTradeFile();

    // 2.12 商户日退款对账单文件
    void getRefundTradeFile();

    // 2.13 商户手续费对账文件
    void getFeeTradeFile();
}
