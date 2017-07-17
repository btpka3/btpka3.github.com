package me.test.first.chanpay.api;

/**
 * 单笔订单支付接口
 */
public interface TradeApi extends ChanpayApi<TradeApiReq, TradeApiResp> {

    String SERVICE_WEB = "cjt_create_instant_trade";
    String SERVICE_WAP = "cjt_wap_create_instant_trade";

}
