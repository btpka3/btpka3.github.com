package me.test.first.chanpay.api.scan.dto;

import javax.annotation.*;
import javax.xml.bind.annotation.*;
import java.util.*;

/**
 *
 */
public class ConfirmWarrantTradeResp extends Resp {


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
     * 支付结果
     *
     * - `0` : 正在支付
     * - `1` : 支付成功
     * - `2` : 支付失败
     */
    @XmlAttribute(name = "Status", required = true)
    @Nonnull
    private String status;


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

    @Nonnull
    public String getStatus() {
        return status;
    }

    public void setStatus(@Nonnull String status) {
        this.status = status;
    }


    // ------------------------------------ equals && hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ConfirmWarrantTradeResp that = (ConfirmWarrantTradeResp) o;
        return Objects.equals(retCode, that.retCode) &&
                Objects.equals(retMsg, that.retMsg) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), retCode, retMsg, status);
    }
}