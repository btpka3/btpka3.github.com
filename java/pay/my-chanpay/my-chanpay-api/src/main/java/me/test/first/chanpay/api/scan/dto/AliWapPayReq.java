package me.test.first.chanpay.api.scan.dto;

import me.test.first.chanpay.api.scan.*;

import javax.annotation.*;
import javax.xml.bind.annotation.*;
import java.util.*;

/**
 *
 */
public class AliWapPayReq extends Req {

    public AliWapPayReq() {
        this.setService(CpScanApi.S_aliWapPay);
    }

    /**
     * 商户唯一订单号
     */
    @Nonnull
    private String outTradeNo;

    /**
     * 商户标示ID
     */
    @Nonnull
    private String mchId;

    /**
     * 子商户标示ID
     */
    private String subMchId;

    /**
     * 交易类型
     *
     * - `11` : 即时
     * - `12` : 担保
     */
    @Nonnull
    private String tradeType;

    /**
     * 微信/支付宝标识
     */
    private String appId;


    /**
     * 币种
     */
    private String currency;

    /**
     * 交易金额
     */
    @Nonnull
    private Double tradeAmount;

    /**
     * 担保金额
     */
    private Double ensureAmount;

    /**
     * 商品名称
     */
    @Nonnull
    private String goodsName;

    /**
     * 商品描述
     */
    private String tradeMemo;

    /**
     * 订单标题
     */
    @Nonnull
    private String subject;

    /**
     * 订单起始提交时间
     */
    @Nonnull
    private Date orderStartTime;

    /**
     * 订单失效时间
     */
    private Date orderEndTime;

    /**
     * 限制信用卡
     */
    private String limitCreditPay;

    /**
     * 服务器异步通知页面路径
     */
    private String notifyUrl;

    /**
     * 终端IP
     */
    @Nonnull
    private String spBillCreateIp;

    /**
     * 分账列表
     */
    private List<Split> splitList;

    /**
     * 扩展字段
     */
    private String ext;


    public static class Split {

        /**
         * 收方商户号
         */
        @XmlAttribute(name = "PayeeId")
        private String payeeId;

        /**
         * 分账金额
         */
        @XmlAttribute(name = "Amount")
        private Double amount;

        // ------------------------------------ getter && setter


        public String getPayeeId() {
            return payeeId;
        }

        public void setPayeeId(String payeeId) {
            this.payeeId = payeeId;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        // ------------------------------------ equals && hashCode


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Split split = (Split) o;
            return Objects.equals(payeeId, split.payeeId) &&
                    Objects.equals(amount, split.amount);
        }

        @Override
        public int hashCode() {
            return Objects.hash(payeeId, amount);
        }
    }

    // ------------------------------------ getter && setter

    @Nonnull
    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(@Nonnull String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    @Nonnull
    public String getMchId() {
        return mchId;
    }

    public void setMchId(@Nonnull String mchId) {
        this.mchId = mchId;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    @Nonnull
    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(@Nonnull String tradeType) {
        this.tradeType = tradeType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Nonnull
    public Double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(@Nonnull Double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Double getEnsureAmount() {
        return ensureAmount;
    }

    public void setEnsureAmount(Double ensureAmount) {
        this.ensureAmount = ensureAmount;
    }

    @Nonnull
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(@Nonnull String goodsName) {
        this.goodsName = goodsName;
    }

    public String getTradeMemo() {
        return tradeMemo;
    }

    public void setTradeMemo(String tradeMemo) {
        this.tradeMemo = tradeMemo;
    }

    @Nonnull
    public String getSubject() {
        return subject;
    }

    public void setSubject(@Nonnull String subject) {
        this.subject = subject;
    }

    @Nonnull
    public Date getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(@Nonnull Date orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public Date getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(Date orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public String getLimitCreditPay() {
        return limitCreditPay;
    }

    public void setLimitCreditPay(String limitCreditPay) {
        this.limitCreditPay = limitCreditPay;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    @Nonnull
    public String getSpBillCreateIp() {
        return spBillCreateIp;
    }

    public void setSpBillCreateIp(@Nonnull String spBillCreateIp) {
        this.spBillCreateIp = spBillCreateIp;
    }

    public List<Split> getSplitList() {
        return splitList;
    }

    public void setSplitList(List<Split> splitList) {
        this.splitList = splitList;
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
        AliWapPayReq that = (AliWapPayReq) o;
        return Objects.equals(outTradeNo, that.outTradeNo) &&
                Objects.equals(mchId, that.mchId) &&
                Objects.equals(subMchId, that.subMchId) &&
                Objects.equals(tradeType, that.tradeType) &&
                Objects.equals(appId, that.appId) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(tradeAmount, that.tradeAmount) &&
                Objects.equals(ensureAmount, that.ensureAmount) &&
                Objects.equals(goodsName, that.goodsName) &&
                Objects.equals(tradeMemo, that.tradeMemo) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(orderStartTime, that.orderStartTime) &&
                Objects.equals(orderEndTime, that.orderEndTime) &&
                Objects.equals(limitCreditPay, that.limitCreditPay) &&
                Objects.equals(notifyUrl, that.notifyUrl) &&
                Objects.equals(spBillCreateIp, that.spBillCreateIp) &&
                Objects.equals(splitList, that.splitList) &&
                Objects.equals(ext, that.ext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), outTradeNo, mchId, subMchId, tradeType, appId, currency, tradeAmount, ensureAmount, goodsName, tradeMemo, subject, orderStartTime, orderEndTime, limitCreditPay, notifyUrl, spBillCreateIp, splitList, ext);
    }
}
