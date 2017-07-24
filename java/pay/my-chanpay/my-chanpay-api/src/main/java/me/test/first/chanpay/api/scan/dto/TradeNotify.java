package me.test.first.chanpay.api.scan.dto;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 *
 */
public class TradeNotify extends NotifyBase {


    /**
     * 商户唯一订单号
     */
    @XmlAttribute(name = "OutTradeNo", required = true)

    private String outTradeNo;

    /**
     * 畅捷支付平台订单号
     */
    @XmlAttribute(name = "InnerTradeNo", required = true)

    private String innerTradeNo;


    /**
     * 交易状态
     */
    @XmlAttribute(name = "TradeStatus", required = true)

    private String tradeStatus;

    /**
     * 交易金额
     */
    @XmlAttribute(name = "TradeAmount", required = true)

    private Double tradeAmount;

    /**
     * 交易创建时间
     *
     * 格式: `yyyyMMddHHmmss`
     */
    @XmlAttribute(name = "GmtCreate", required = true)

    private Date gmtCreate;

    /**
     * 交易支付时间
     *
     * 格式: `yyyyMMddHHmmss`
     */
    @XmlAttribute(name = "GmtPayment")
    private Date gmtPayment;

    /**
     * 交易关闭时间
     *
     * 格式: `yyyyMMddHHmmss`
     */
    @XmlAttribute(name = "mtClose")
    private Date gmtClose;

    /**
     * 扩展参数
     */
    @XmlAttribute(name = "Extension")
    private String extension;

    // ------------------------------------ getter && setter


    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }


    public String getInnerTradeNo() {
        return innerTradeNo;
    }

    public void setInnerTradeNo(String innerTradeNo) {
        this.innerTradeNo = innerTradeNo;
    }


    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }


    public Double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }


    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtPayment() {
        return gmtPayment;
    }

    public void setGmtPayment(Date gmtPayment) {
        this.gmtPayment = gmtPayment;
    }

    public Date getGmtClose() {
        return gmtClose;
    }

    public void setGmtClose(Date gmtClose) {
        this.gmtClose = gmtClose;
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
        TradeNotify that = (TradeNotify) o;
        return Objects.equals(outTradeNo, that.outTradeNo) &&
                Objects.equals(innerTradeNo, that.innerTradeNo) &&
                Objects.equals(tradeStatus, that.tradeStatus) &&
                Objects.equals(tradeAmount, that.tradeAmount) &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtPayment, that.gmtPayment) &&
                Objects.equals(gmtClose, that.gmtClose) &&
                Objects.equals(extension, that.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), outTradeNo, innerTradeNo, tradeStatus, tradeAmount, gmtCreate, gmtPayment, gmtClose, extension);
    }
}
