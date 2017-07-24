package me.test.first.chanpay.api.scan.dto;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 *
 */
public class MerchantAliPubPayResp extends Resp {


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

    private String outTradeNo;

    /**
     * 畅捷支付平台订单号
     */
    @XmlAttribute(name = "InnerTradeNo", required = true)

    private String innerTradeNo;

    /**
     * 回话标识
     */
    @XmlAttribute(name = "PayData", required = true)

    private String payData;

    /**
     * 商户号
     */
    @XmlAttribute(name = "MchId", required = true)

    private String mchId;

    /**
     * 子商户标示ID
     */
    @XmlAttribute(name = "SubMchId")
    private String subMchId;

    /**
     * 支付结果
     *
     * - `0` : 正在支付
     * - `1` : 支付成功
     * - `2` : 支付失败
     */
    @XmlAttribute(name = "Status", required = true)

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


    public String getPayData() {
        return payData;
    }

    public void setPayData(String payData) {
        this.payData = payData;
    }

    @Override

    public String getMchId() {
        return mchId;
    }

    @Override
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
        MerchantAliPubPayResp that = (MerchantAliPubPayResp) o;
        return Objects.equals(retCode, that.retCode) &&
                Objects.equals(retMsg, that.retMsg) &&
                Objects.equals(outTradeNo, that.outTradeNo) &&
                Objects.equals(innerTradeNo, that.innerTradeNo) &&
                Objects.equals(payData, that.payData) &&
                Objects.equals(mchId, that.mchId) &&
                Objects.equals(subMchId, that.subMchId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(ext, that.ext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), retCode, retMsg, outTradeNo, innerTradeNo, payData, mchId, subMchId, status, ext);
    }
}