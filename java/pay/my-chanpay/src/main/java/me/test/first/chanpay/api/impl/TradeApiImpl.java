package me.test.first.chanpay.api.impl;

import me.test.first.chanpay.api.*;

/**
 *
 */
public class TradeApiImpl
        extends AbstractChanpayApiImpl<TradeApiReq, TradeApiResp>
        implements TradeApi {

    public TradeApiImpl() {
        this.setRespClass(TradeApiResp.class);
    }

}
