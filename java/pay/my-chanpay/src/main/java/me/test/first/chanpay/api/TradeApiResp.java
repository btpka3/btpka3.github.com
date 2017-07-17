package me.test.first.chanpay.api;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.*;
import java.util.*;

/**
 *
 */
public class TradeApiResp extends BaseResp {

    /**
     * 付款二维码状态
     * <p>
     * - `0` : 成功
     * - `1` : 失败
     */
    @XmlAttribute(name = "status", required = true)
    @NotNull
    private String status;

    /**
     * 失败原因
     */
    @XmlAttribute(name = "err_msg")
    private String errMsg;

    /**
     * 扫码支付付款二维码URL地址
     */
    @XmlAttribute(name = "pay_url")
    private String payUrl;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TradeApiResp that = (TradeApiResp) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(errMsg, that.errMsg) &&
                Objects.equals(payUrl, that.payUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status, errMsg, payUrl);
    }
}