package me.test.first.chanpay.api.scan.dto;

import me.test.first.chanpay.api.scan.*;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 *
 */
public class OneCodePayReq extends Req {

    public OneCodePayReq() {
        this.setService(CpScanApi.S_oneCodePay);
    }

    /**
     * 商户唯一订单号
     */

    private String outTradeNo;

    /**
     * 商户标示ID
     */

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

    private String tradeType;

    /**
     * 业务类型
     *
     * - `1` : 动码
     * - `2` : 固码
     *
     */

    private String busiType;


    /**
     * 设备信息
     */
    private String deviceInfo;

    /**
     * 币种
     */
    private String currency;

    /**
     * 交易金额
     */

    private Double tradeAmount;

    /**
     * 担保金额
     *
     * 如果交易类型为 12-担保 金额必填，
     * 担保金额可以小于订单金额，
     * 订单金额减去担保金额为及时到账部分。
     */
    private Double ensureAmount;

    /**
     * 商品名称
     */

    private String goodsName;

    /**
     * 商品描述
     */
    private String tradeMemo;

    /**
     * 订单标题
     */

    private String subject;

    /**
     * 订单起始提交时间
     *
     * 格式为 `yyyyMMddHHmmss`
     */

    private Date orderStartTime;

    /**
     * 订单失效时间
     *
     * 订单失效时间默认为5分钟。
     * 格式为 `yyyyMMddHHmmss`
     */
    private Date orderEndTime;

    /**
     * 限制信用卡
     *
     * - `` ：不限制
     * - `NoCredit` : 不使用信用卡
     */
    private String limitCreditPay;

    /**
     * 服务器异步通知页面路径
     */
    private String notifyUrl;


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

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }


    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Double getEnsureAmount() {
        return ensureAmount;
    }

    public void setEnsureAmount(Double ensureAmount) {
        this.ensureAmount = ensureAmount;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getTradeMemo() {
        return tradeMemo;
    }

    public void setTradeMemo(String tradeMemo) {
        this.tradeMemo = tradeMemo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(Date orderStartTime) {
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


    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }
    // ------------------------------------ equals && hashCode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OneCodePayReq that = (OneCodePayReq) o;
        return Objects.equals(outTradeNo, that.outTradeNo) &&
                Objects.equals(mchId, that.mchId) &&
                Objects.equals(subMchId, that.subMchId) &&
                Objects.equals(tradeType, that.tradeType) &&
                Objects.equals(busiType, that.busiType) &&
                Objects.equals(deviceInfo, that.deviceInfo) &&
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
                Objects.equals(splitList, that.splitList) &&
                Objects.equals(ext, that.ext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), outTradeNo, mchId, subMchId, tradeType, busiType, deviceInfo, currency, tradeAmount, ensureAmount, goodsName, tradeMemo, subject, orderStartTime, orderEndTime, limitCreditPay, notifyUrl, splitList, ext);
    }
}
