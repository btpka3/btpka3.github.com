package me.test.first.chanpay.api.scan.dto;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.*;
import java.util.*;

/**
 *
 */
public class UserScanResp extends Resp {

    @XmlAttribute(name = "RetCode")
    private String retCode;

    @XmlAttribute(name = "RetMsg")
    private String retMsg;

    @XmlAttribute(name = "OutTradeNo", required = true)
    @NotNull
    private String outTradeNo;

    @XmlAttribute(name = "InnerTradeNo", required = true)
    @NotNull
    private String innerTradeNo;

    @XmlAttribute(name = "CodeUrl", required = true)
    @NotNull
    private String codeUrl;

    @XmlAttribute(name = "Status", required = true)
    @NotNull
    private String status;

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

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
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
        UserScanResp that = (UserScanResp) o;
        return Objects.equals(retCode, that.retCode) &&
                Objects.equals(retMsg, that.retMsg) &&
                Objects.equals(outTradeNo, that.outTradeNo) &&
                Objects.equals(innerTradeNo, that.innerTradeNo) &&
                Objects.equals(codeUrl, that.codeUrl) &&
                Objects.equals(status, that.status) &&
                Objects.equals(ext, that.ext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), retCode, retMsg, outTradeNo, innerTradeNo, codeUrl, status, ext);
    }
}