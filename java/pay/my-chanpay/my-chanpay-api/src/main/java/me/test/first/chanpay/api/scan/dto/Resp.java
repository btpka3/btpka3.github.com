package me.test.first.chanpay.api.scan.dto;

import javax.annotation.*;
import javax.xml.bind.annotation.*;
import java.io.*;
import java.util.*;

/**
 *
 */
public class Resp implements Serializable {


    private static final long serialVersionUID = 1L;


//    @XmlEnum
//    public enum AcceptStatusEnum {
//        S, F, R
//    }

    /**
     * 受理状态。
     *
     * - `S`: 受理成功
     * - `F`: 受理失败
     * - `R`: 处理中
     */
    @XmlAttribute(name = "AcceptStatus", required = true)
    @Nonnull
    private String acceptStatus;

    /**
     * 合作者身份ID
     */
    @XmlAttribute(name = "PartnerId")
    private String partnerId;

    /**
     * 交易日期。
     */
    @XmlAttribute(name = "TradeDate", required = true)
    @Nonnull
    private String tradeDate;

    /**
     * 交易时间。
     */
    @XmlAttribute(name = "TradeTime", required = true)
    @Nonnull
    private String tradeTime;

    /**
     * 参数编码字符集。
     */
    @XmlAttribute(name = "InputCharset", required = true)
    @Nonnull
    private String inputCharset;

    /**
     * 商户ID
     */
    @XmlAttribute(name = "MchId")
    private String mchId;

    /**
     * 备注
     */
    @XmlAttribute(name = "Memo")
    private String memo;

    /**
     * 签名
     */
    @XmlAttribute(name = "Sign", required = true)
    @Nonnull
    private String sign;

    /**
     * 签名方式
     */
    @XmlAttribute(name = "SignType", required = true)
    @Nonnull
    private String signType;


    public String getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(String acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getInputCharset() {
        return inputCharset;
    }

    public void setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resp resp = (Resp) o;
        return Objects.equals(acceptStatus, resp.acceptStatus) &&
                Objects.equals(partnerId, resp.partnerId) &&
                Objects.equals(tradeDate, resp.tradeDate) &&
                Objects.equals(tradeTime, resp.tradeTime) &&
                Objects.equals(inputCharset, resp.inputCharset) &&
                Objects.equals(mchId, resp.mchId) &&
                Objects.equals(memo, resp.memo) &&
                Objects.equals(sign, resp.sign) &&
                Objects.equals(signType, resp.signType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acceptStatus, partnerId, tradeDate, tradeTime, inputCharset, mchId, memo, sign, signType);
    }
}
