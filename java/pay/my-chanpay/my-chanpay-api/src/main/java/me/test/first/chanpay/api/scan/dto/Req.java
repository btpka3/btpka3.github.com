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


    private String service;


    private String version = "1.0";


    private String partnerId;


    private final String inputCharset = "UTF-8";
    //    private String tradeDate;


    private Date tradeDateTime;


    private String sign = "";


    private String signType = "RSA";

    @Nullable
    private String returnUrl = "";

    @Nullable
    private String memo = "";

    // ------------------------------------ getter && setter

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


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


    public Date getTradeDateTime() {
        return tradeDateTime;
    }

    public void setTradeDateTime(Date tradeDateTime) {
        this.tradeDateTime = tradeDateTime;
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
