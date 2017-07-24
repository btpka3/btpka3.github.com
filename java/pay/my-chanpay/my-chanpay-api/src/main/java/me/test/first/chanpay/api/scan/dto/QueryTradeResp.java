package me.test.first.chanpay.api.scan.dto;

import javax.annotation.*;
import javax.xml.bind.annotation.*;
import java.util.*;

/**
 *
 */
public class QueryTradeResp extends Resp {

    /**
     * 商户唯一订单号
     */
    @XmlAttribute(name = "TrxId", required = true)
    @Nonnull
    private String trxId;

    /**
     * 畅捷支付平台订单号
     */
    @XmlAttribute(name = "OrderTrxId", required = true)
    @Nonnull
    private String orderTrxId;

    /**
     * 支付结果
     *
     * - `S` : 成功
     * - `P` : 处理中
     * - `F` : 失败
     */
    @XmlAttribute(name = "Status", required = true)
    @Nonnull
    private String status;

    /**
     * 返回码
     */
    @XmlAttribute(name = "RetCode")
    private String retCode;

    /**
     * 返回信息
     */
    @XmlAttribute(name = "RetMsg")
    private String retMsg;

    /**
     * 扩展字段
     */
    @XmlAttribute(name = "Extension")
    private String extension;

    // ------------------------------------ getter && setter

    @Nonnull
    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(@Nonnull String trxId) {
        this.trxId = trxId;
    }

    @Nonnull
    public String getOrderTrxId() {
        return orderTrxId;
    }

    public void setOrderTrxId(@Nonnull String orderTrxId) {
        this.orderTrxId = orderTrxId;
    }

    @Nonnull
    public String getStatus() {
        return status;
    }

    public void setStatus(@Nonnull String status) {
        this.status = status;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
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
        QueryTradeResp that = (QueryTradeResp) o;
        return Objects.equals(trxId, that.trxId) &&
                Objects.equals(orderTrxId, that.orderTrxId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(retCode, that.retCode) &&
                Objects.equals(retMsg, that.retMsg) &&
                Objects.equals(extension, that.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), trxId, orderTrxId, status, retCode, retMsg, extension);
    }
}