package me.test.first.chanpay.api.warrant;

import me.test.first.chanpay.api.*;

/**
 * 畅捷支付 - 担保API。
 *
 * 参考 : 《互联网商户接入接口文档》
 */
public interface CpWarrantApi extends CpApi {


    /**
     * 接口名称（service）- 2.5 退款网关接口
     */
    String S_refund = "cjt_create_refund";

    /**
     * 接口名称（service）- 2.7 查询银行列表接口
     */
    String S_getPayChannels = "cjt_get_paychannel";

    /**
     * 接口名称（service）- 2.10 查看回单接口
     */
    String S_viewReceipt = "cjt_view_receipt";

    /**
     * 接口名称（service）- 2.11 商户日支付交易对账单文件
     */
    String S_getDailiyTradeFile = "cjt_everyday_trade_file";

    /**
     * 接口名称（service）- 2.12 商户日退款对账单文件
     */
    String S_getDailiyRefundFile = "cjt_refund_trade_file";

    /**
     * 接口名称（service）- 2.13 商户手续费对账文件
     */
    String S_getDailiyBrokerageFile = "cjt_fee_trade_file";

    /**
     * 接口名称（service）- 2.14 付款到卡网关接口
     */
    String S_payToCard = "cjt_payment_to_card";

    /**
     * 接口名称（service）- 2.19 快捷支付交易确认接口
     */
    String S_confirmQuickPay = "cjt_quick_payment_confirm";

    /**
     * 接口名称（service）- 2.20 单笔订单提现接口
     */
    String S_orderWithdraw = "cjt_order_withdraw";

    /**
     * 接口名称（service）- 2.23 账户余额普通提现API接口
     */
    String S_siteWithdraw = "cjt_site_withdraw";

    /**
     * 接口名称（service）- 2.24 单笔订单担保交易接口
     */
    String S_ensureTrade = "cjt_create_ensure_trade";

    /**
     * 接口名称（service）- 2.25 担保交易确认结算（分润）接口
     */
    String S_confirmEnsureTrade = "cjt_create_settle";

    /**
     * 接口名称（service）- 2.27 转账到户网关接口
     */
    String S_balanceTransfer = "cjt_balance_transfer";

    /**
     * 接口名称（service）- 2.31 单笔订单担保交易快捷支付接口
     */
    String S_quickEnsureTrade = "cjt_quick_ensure_trade";

    /**
     * 接口名称（service）- 2.33 批量订单担保交易接口
     */
    String S_ensureTrades = "cjt_batch_ensure_trade";

    /**
     * 接口名称（service）- 2.34 批量订单担保交易快捷支付接口
     */
    String S_quickEnsureTrades = "cjt_quick_batch_ensure_trade";

    /**
     * 接口名称（service）- 2.35 批量快捷支付交易确认接口
     */
    String S_confirmQuickEnsureTrades = "cjt_batch_quick_payment_confirm";


    // 2.5 退款网关接口
    void refund();

    // 2.7 查询银行列表接口
    void getPayChannels();

    // 2.10 查看回单接口
    void viewReceipt();

    // 2.11 商户日支付交易对账单文件
    void getDailiyTradeFile();

    // 2.12 商户日退款对账单文件
    void getDailiyRefundFile();

    // 2.13 商户手续费对账文件
    void getDailiyBrokerageFile();

    // 2.14 付款到卡网关接口
    void payToCard();

    // 2.19 快捷支付交易确认接口
    void confirmQuickPay();

    // 2.20 单笔订单提现接口
    void orderWithdraw();

    // 2.23 账户余额普通提现API接口
    void siteWithdraw();

    // 2.24 单笔订单担保交易接口
    void ensureTrade();

    // 2.27 转账到户网关接口
    void balanceTransfer();

    // 2.25 担保交易确认结算（分润）接口
    void confirmEnsureTrade();

    // 2.31 单笔订单担保交易快捷支付接口
    void quickEnsureTrade();

    // 2.33 批量订单担保交易接口
    void ensureTrades();

    // 2.34 批量订单担保交易快捷支付接口
    void quickEnsureTrades();

    // 2.35 批量快捷支付交易确认接口
    void confirmQuickEnsureTrades();
}
