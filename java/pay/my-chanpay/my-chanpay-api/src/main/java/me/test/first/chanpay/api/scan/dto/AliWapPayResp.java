package me.test.first.chanpay.api.scan.dto;

import javax.annotation.*;
import javax.xml.bind.annotation.*;
import java.util.*;

/**
 *
 */
public class AliWapPayResp extends Resp {


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
     * 商户唯一订单号
     */
    @XmlAttribute(name = "OutTradeNo", required = true)
    @Nonnull
    private String outTradeNo;

    /**
     * 畅捷支付平台订单号
     */
    @XmlAttribute(name = "InnerTradeNo", required = true)
    @Nonnull
    private String innerTradeNo;

    /**
     * 支付信息
     */
    @XmlAttribute(name = "PayInfo")
    private String payInfo;

    /**
     * 支付结果
     *
     * - `0` : 正在支付
     * - `1` : 支付成功
     * - `2` : 支付失败
     */
    @XmlAttribute(name = "Status", required = true)
    @Nonnull
    private String status;

    /**
     * 备注
     */
    @XmlAttribute(name = "Ext")
    private String ext;

    // ------------------------------------ getter && setter

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

    @Nonnull
    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(@Nonnull String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    @Nonnull
    public String getInnerTradeNo() {
        return innerTradeNo;
    }

    public void setInnerTradeNo(@Nonnull String innerTradeNo) {
        this.innerTradeNo = innerTradeNo;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    @Nonnull
    public String getStatus() {
        return status;
    }

    public void setStatus(@Nonnull String status) {
        this.status = status;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }


    // ------------------------------------ equals && hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AliWapPayResp that = (AliWapPayResp) o;
        return Objects.equals(retCode, that.retCode) &&
                Objects.equals(retMsg, that.retMsg) &&
                Objects.equals(outTradeNo, that.outTradeNo) &&
                Objects.equals(innerTradeNo, that.innerTradeNo) &&
                Objects.equals(payInfo, that.payInfo) &&
                Objects.equals(status, that.status) &&
                Objects.equals(ext, that.ext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), retCode, retMsg, outTradeNo, innerTradeNo, payInfo, status, ext);
    }
}