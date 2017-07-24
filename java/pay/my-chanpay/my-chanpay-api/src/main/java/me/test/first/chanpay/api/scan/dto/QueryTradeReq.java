package me.test.first.chanpay.api.scan.dto;

import me.test.first.chanpay.api.scan.*;

import java.util.*;

/**
 *
 */
public class QueryTradeReq extends Req {

    public QueryTradeReq() {
        this.setService(CpScanApi.S_aliWapPay);
    }

    /**
     * 商户唯一订单号
     */

    private String trxId;

    /**
     * 支付订单号
     */

    private String orderTrxId;

    /**
     * 原业务订单类型
     *
     * - `pay_order` : 支付订单
     * - `refund_order` : 退款订单
     */

    private String tradeType;

    /**
     * 扩展字段
     */
    private String extension;


    // ------------------------------------ getter && setter


    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }


    public String getOrderTrxId() {
        return orderTrxId;
    }

    public void setOrderTrxId(String orderTrxId) {
        this.orderTrxId = orderTrxId;
    }


    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }


    // ------------------------------------ equals && hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        QueryTradeReq that = (QueryTradeReq) o;
        return Objects.equals(trxId, that.trxId) &&
                Objects.equals(orderTrxId, that.orderTrxId) &&
                Objects.equals(tradeType, that.tradeType) &&
                Objects.equals(extension, that.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), trxId, orderTrxId, tradeType, extension);
    }
}
