package me.test.first.chanpay.api.scan.dto;


import javax.annotation.*;
import java.io.*;
import java.util.*;

/**
 *
 */
public class Req implements Serializable {


    //private static final DateTimeFormatter _tradeDateFmt = DateTimeFormatter.ofPattern("yyyyMMdd");
    //private static final DateTimeFormatter _tradeTimeFmt = DateTimeFormatter.ofPattern("HHmmss");

    private static final long serialVersionUID = 1L;

    @Nonnull
    private String service;

    @Nonnull
    private String version = "1.0";

    @Nonnull
    private String partnerId;

    @Nonnull
    private final String inputCharset = "UTF-8";
    //    private String tradeDate;

    @Nonnull
    private Date tradeDateTime;

    @Nonnull
    private String sign = "";

    @Nonnull
    private String signType = "RSA";

    @Nullable
    private String returnUrl = "";

    @Nullable
    private String memo = "";

    // ------------------------------------ getter && setter

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Nonnull
    public String getService() {
        return service;
    }

    public void setService(@Nonnull String service) {
        this.service = service;
    }

    @Nonnull
    public String getVersion() {
        return version;
    }

    public void setVersion(@Nonnull String version) {
        this.version = version;
    }

    @Nonnull
    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(@Nonnull String partnerId) {
        this.partnerId = partnerId;
    }

    @Nonnull
    public String getInputCharset() {
        return inputCharset;
    }

    @Nonnull
    public Date getTradeDateTime() {
        return tradeDateTime;
    }

    public void setTradeDateTime(@Nonnull Date tradeDateTime) {
        this.tradeDateTime = tradeDateTime;
    }

    @Nonnull
    public String getSign() {
        return sign;
    }

    public void setSign(@Nonnull String sign) {
        this.sign = sign;
    }

    @Nonnull
    public String getSignType() {
        return signType;
    }

    public void setSignType(@Nonnull String signType) {
        this.signType = signType;
    }

    @Nullable
    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(@Nullable String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Nullable
    public String getMemo() {
        return memo;
    }

    public void setMemo(@Nullable String memo) {
        this.memo = memo;
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
                Objects.equals(tradeDateTime, req.tradeDateTime) &&
                Objects.equals(sign, req.sign) &&
                Objects.equals(signType, req.signType) &&
                Objects.equals(returnUrl, req.returnUrl) &&
                Objects.equals(memo, req.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(service, version, partnerId, inputCharset, tradeDateTime, sign, signType, returnUrl, memo);
    }

}
