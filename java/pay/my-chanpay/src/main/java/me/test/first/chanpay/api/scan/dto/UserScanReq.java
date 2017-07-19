package me.test.first.chanpay.api.scan.dto;

import me.test.first.chanpay.api.scan.*;

import javax.validation.constraints.*;
import java.time.format.*;
import java.util.*;

/**
 *
 */
public class UserScanReq extends Req {

    private static final DateTimeFormatter _orderStartTimeFmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter _orderEndTimeFmt = _orderStartTimeFmt;


    public UserScanReq() {
        this.setService(CpScanApi.S_userScan);
    }

    @NotNull
    private String outTradeNo;

    @NotNull
    private String mchId;
    private String subMchId;

    @NotNull
    private String tradeType;

    @NotNull
    private String bankCode;
    private String appId;
    private String deviceInfo;
    private String currency;

    @NotNull
    private Double tradeAmount;
    private Double ensureAmount;

    @NotNull
    private String goodsName;
    private String tradeMemo;

    @NotNull
    private String subject;

    @NotNull
    private Date orderStartTime;
    private Date orderEndTime;
    private String limitCreditPay;
    private String notifyUrl;

    @NotNull
    private String spBillCreateIp;

    private List<Split> splitList;
    private String ext;


    public static class Split {

        private String payeeId;
        private Number amount;

        // ------------------------------------ getter && setter


        public String getPayeeId() {
            return payeeId;
        }

        public void setPayeeId(String payeeId) {
            this.payeeId = payeeId;
        }

        public Number getAmount() {
            return amount;
        }

        public void setAmount(Number amount) {
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

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getSpBillCreateIp() {
        return spBillCreateIp;
    }

    public void setSpBillCreateIp(String spBillCreateIp) {
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
        UserScanReq that = (UserScanReq) o;
        return Objects.equals(outTradeNo, that.outTradeNo) &&
                Objects.equals(mchId, that.mchId) &&
                Objects.equals(subMchId, that.subMchId) &&
                Objects.equals(tradeType, that.tradeType) &&
                Objects.equals(bankCode, that.bankCode) &&
                Objects.equals(appId, that.appId) &&
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
                Objects.equals(spBillCreateIp, that.spBillCreateIp) &&
                Objects.equals(splitList, that.splitList) &&
                Objects.equals(ext, that.ext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), outTradeNo, mchId, subMchId, tradeType, bankCode, appId, deviceInfo, currency, tradeAmount, ensureAmount, goodsName, tradeMemo, subject, orderStartTime, orderEndTime, limitCreditPay, notifyUrl, spBillCreateIp, splitList, ext);
    }


    // ------------------------------------ toMap


//    @Override
//    Map<String, String> toMap() {
//        Map<String, String> map = new LinkedHashMap<>();
//        map.putAll(super.toMap());
//
//        if (!StringUtils.isEmpty(outTradeNo)) {
//            map.put("OutTradeNo", outTradeNo);
//        }
//        if (!StringUtils.isEmpty(mchId)) {
//            map.put("MchId", mchId);
//        }
//        if (!StringUtils.isEmpty(subMchId)) {
//            map.put("SubMchId", subMchId);
//        }
//        if (!StringUtils.isEmpty(tradeType)) {
//            map.put("TradeType", tradeType);
//        }
//        if (!StringUtils.isEmpty(bankCode)) {
//            map.put("BankCode", bankCode);
//        }
//        if (!StringUtils.isEmpty(appId)) {
//            map.put("AppId", appId);
//        }
//        if (!StringUtils.isEmpty(deviceInfo)) {
//            map.put("DeviceInfo", deviceInfo);
//        }
//        if (!StringUtils.isEmpty(currency)) {
//            map.put("Currency", currency);
//        }
//        if (tradeAmount != null) {
//            map.put("TradeAmount", tradeAmount.toString());
//        }
//        if (ensureAmount != null) {
//            map.put("EnsureAmount", ensureAmount.toString());
//        }
//        if (!StringUtils.isEmpty(goodsName)) {
//            map.put("GoodsName", goodsName);
//        }
//        if (!StringUtils.isEmpty(tradeMemo)) {
//            map.put("TradeMemo", tradeMemo);
//        }
//        if (!StringUtils.isEmpty(subject)) {
//            map.put("Subject", subject);
//        }
//        if (orderStartTime != null) {
//            LocalDateTime ldt = LocalDateTime.ofInstant(
//                    orderStartTime.toInstant(),
//                    ZoneId.of("Asia/Shanghai"));
//            map.put("OrderStartTime", _orderStartTimeFmt.format(ldt));
//        }
//        if (orderEndTime != null) {
//            LocalDateTime ldt = LocalDateTime.ofInstant(
//                    orderStartTime.toInstant(),
//                    ZoneId.of("Asia/Shanghai"));
//            map.put("OrderEndTime", _orderEndTimeFmt.format(ldt));
//        }
//        if (!StringUtils.isEmpty(limitCreditPay)) {
//            map.put("LimitCreditPay", limitCreditPay);
//        }
//        if (!StringUtils.isEmpty(notifyUrl)) {
//            map.put("NotifyUrl", notifyUrl);
//        }
//        if (!StringUtils.isEmpty(spBillCreateIp)) {
//            map.put("SpbillCreateIp", spBillCreateIp);
//        }
//        if (splitList != null) {
//            map.put("SplitList", );
//        }
//        if (!StringUtils.isEmpty(ext)) {
//            map.put("Ext", ext);
//        }
//
//        return map;
//    }
}
