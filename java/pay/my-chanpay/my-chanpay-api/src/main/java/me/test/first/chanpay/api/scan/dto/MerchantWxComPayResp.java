package me.test.first.chanpay.api.scan.dto;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 *
 */
public class MerchantWxComPayResp extends Resp {


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
     * 微信/支付宝标识
     */
    @XmlAttribute(name = "JsapiAppId", required = true)

    private String jsapiAppId;

    /**
     * 随机字符串
     */
    @XmlAttribute(name = "JsapiNonceStr", required = true)

    private String jsapiNonceStr;

    /**
     * 回话标识
     */
    @XmlAttribute(name = "JsapiPackage", required = true)

    private String jsapiPackage;

    /**
     * 微信返回的签名值
     */
    @XmlAttribute(name = "JsapiPaySign", required = true)

    private String jsapiPaySign;


    /**
     * 微信返回的签名的签名方式
     */
    @XmlAttribute(name = "JsapiSignType", required = true)

    private String jsapiSignType;

    /**
     * 时间戳
     */
    @XmlAttribute(name = "JsapiTimeStamp", required = true)

    private String jsapiTimeStamp;


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


    public String getJsapiAppId() {
        return jsapiAppId;
    }

    public void setJsapiAppId(String jsapiAppId) {
        this.jsapiAppId = jsapiAppId;
    }


    public String getJsapiNonceStr() {
        return jsapiNonceStr;
    }

    public void setJsapiNonceStr(String jsapiNonceStr) {
        this.jsapiNonceStr = jsapiNonceStr;
    }


    public String getJsapiPackage() {
        return jsapiPackage;
    }

    public void setJsapiPackage(String jsapiPackage) {
        this.jsapiPackage = jsapiPackage;
    }


    public String getJsapiPaySign() {
        return jsapiPaySign;
    }

    public void setJsapiPaySign(String jsapiPaySign) {
        this.jsapiPaySign = jsapiPaySign;
    }


    public String getJsapiSignType() {
        return jsapiSignType;
    }

    public void setJsapiSignType(String jsapiSignType) {
        this.jsapiSignType = jsapiSignType;
    }


    public String getJsapiTimeStamp() {
        return jsapiTimeStamp;
    }

    public void setJsapiTimeStamp(String jsapiTimeStamp) {
        this.jsapiTimeStamp = jsapiTimeStamp;
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
        MerchantWxComPayResp that = (MerchantWxComPayResp) o;
        return Objects.equals(retCode, that.retCode) &&
                Objects.equals(retMsg, that.retMsg) &&
                Objects.equals(outTradeNo, that.outTradeNo) &&
                Objects.equals(innerTradeNo, that.innerTradeNo) &&
                Objects.equals(jsapiAppId, that.jsapiAppId) &&
                Objects.equals(jsapiNonceStr, that.jsapiNonceStr) &&
                Objects.equals(jsapiPackage, that.jsapiPackage) &&
                Objects.equals(jsapiPaySign, that.jsapiPaySign) &&
                Objects.equals(jsapiSignType, that.jsapiSignType) &&
                Objects.equals(jsapiTimeStamp, that.jsapiTimeStamp) &&
                Objects.equals(mchId, that.mchId) &&
                Objects.equals(subMchId, that.subMchId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(ext, that.ext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), retCode, retMsg, outTradeNo, innerTradeNo, jsapiAppId, jsapiNonceStr, jsapiPackage, jsapiPaySign, jsapiSignType, jsapiTimeStamp, mchId, subMchId, status, ext);
    }
}