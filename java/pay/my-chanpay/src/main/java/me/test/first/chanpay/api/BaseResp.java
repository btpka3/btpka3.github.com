package me.test.first.chanpay.api;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.*;
import java.util.*;

/**
 *
 */
public abstract class BaseResp {

    /**
     * 成功标识。
     * <p>
     * 表示接口调用是否成功，并不表明业务处理结果。
     */
    @XmlAttribute(name = "is_success", required = true)
    @NotNull
    private String isSuccess;

    /**
     * 合作者身份ID
     */
    @XmlAttribute(name = "partnerId")
    private String partnerId;

    /**
     * 参数编码字符集。
     * <p>
     * 商户网站使用的编码格式，如utf-8、gbk、gb2312等
     */
    @XmlAttribute(name = "_input_charset", required = true)
    @NotNull
    private String inputCharset;

    /**
     * 错误码
     */
    @XmlAttribute(name = "error_code")
    private String errorCode;

    /**
     * 错误原因
     */
    @XmlAttribute(name = "error_message")
    private String errorMessage;

    /**
     * 备注
     */
    @XmlAttribute(name = "memo")
    private String memo;

    /**
     * 签名
     */
    @XmlAttribute(name = "sign", required = true)
    @NotNull
    private String sign;

    /**
     * 签名方式
     */
    @XmlAttribute(name = "sign_type", required = true)
    @NotNull
    private String signType;

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
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

    public void setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
        if (!(o instanceof BaseResp)) return false;
        BaseResp baseResp = (BaseResp) o;
        return Objects.equals(isSuccess, baseResp.isSuccess) &&
                Objects.equals(partnerId, baseResp.partnerId) &&
                Objects.equals(inputCharset, baseResp.inputCharset) &&
                Objects.equals(errorCode, baseResp.errorCode) &&
                Objects.equals(errorMessage, baseResp.errorMessage) &&
                Objects.equals(memo, baseResp.memo) &&
                Objects.equals(sign, baseResp.sign) &&
                Objects.equals(signType, baseResp.signType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSuccess, partnerId, inputCharset, errorCode, errorMessage, memo, sign, signType);
    }
}
