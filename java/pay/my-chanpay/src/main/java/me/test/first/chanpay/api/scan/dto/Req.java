package me.test.first.chanpay.api.scan.dto;


import org.springframework.util.*;

import javax.annotation.*;
import javax.validation.constraints.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 *
 */
public abstract class Req {


    private static final DateTimeFormatter _tradeDateFmt = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter _tradeTimeFmt = DateTimeFormatter.ofPattern("HHmmss");


    @NotNull
    private String service;

    @NotNull
    private String version = "1.0";

    @NotNull
    private String partnerId;

    @NotNull
    private final String inputCharset = "UTF-8";
    //    private String tradeDate;

    @NotNull
    private Date tradeTime;

    @NotNull
    private String sign;

    @NotNull
    private String signType;

    @Nullable
    private String returnUrl;

    @Nullable
    private String memo;

    // ------------------------------------ getter && setter
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getInputCharset() {
        return inputCharset;
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

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    // ------------------------------------ equals && hashCode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Req req = (Req) o;
        return Objects.equals(service, req.service) &&
                Objects.equals(version, req.version) &&
                Objects.equals(partnerId, req.partnerId) &&
                Objects.equals(inputCharset, req.inputCharset) &&
                Objects.equals(tradeTime, req.tradeTime) &&
                Objects.equals(sign, req.sign) &&
                Objects.equals(signType, req.signType) &&
                Objects.equals(returnUrl, req.returnUrl) &&
                Objects.equals(memo, req.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(service, version, partnerId, inputCharset, tradeTime, sign, signType, returnUrl, memo);
    }

    // ------------------------------------ toMap
    Map<String, String> toMap() {
        Map<String, String> map = new LinkedHashMap<>();

        if (!StringUtils.isEmpty(service)) {
            map.put("Service", service);
        }
        if (!StringUtils.isEmpty(version)) {
            map.put("Version", version);
        }
        if (!StringUtils.isEmpty(partnerId)) {
            map.put("PartnerId", partnerId);
        }
        if (!StringUtils.isEmpty(inputCharset)) {
            map.put("InputCharset", inputCharset);
        }
        if (!StringUtils.isEmpty(sign)) {
            map.put("Sign", sign);
        }
        if (tradeTime != null) {
            LocalDateTime ldt = LocalDateTime.ofInstant(
                    tradeTime.toInstant(),
                    ZoneId.of("Asia/Shanghai"));
            map.put("TradeDate", _tradeDateFmt.format(ldt));
            map.put("TradeTime", _tradeTimeFmt.format(ldt));
        }

        if (!StringUtils.isEmpty(signType)) {
            map.put("SignType", signType);
        }
        if (!StringUtils.isEmpty(returnUrl)) {
            map.put("ReturnUrl", returnUrl);
        }
        if (!StringUtils.isEmpty(memo)) {
            map.put("Memo", memo);
        }
        return map;
    }
}
