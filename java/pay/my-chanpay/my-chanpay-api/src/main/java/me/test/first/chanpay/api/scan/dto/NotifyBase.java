package me.test.first.chanpay.api.scan.dto;

import javax.annotation.*;
import javax.xml.bind.annotation.*;
import java.io.*;
import java.util.*;

/**
 *
 */
public class NotifyBase implements Serializable {


    private static final long serialVersionUID = 1L;

//    @XmlEnum
//    public enum AcceptStatusEnum {
//        S, F, R
//    }

    /**
     * 通知ID
     */
    @XmlAttribute(name = "NotifyId", required = true)
    @Nonnull
    private String notifyId;

    /**
     * 通知类型
     */
    @XmlAttribute(name = "NotifyType", required = true)
    @Nonnull
    private String notifyType;


    /**
     * 通知时间.
     *
     * 格式 : `yyyyMMddHHmmss`
     */
    @XmlAttribute(name = "NotifyTime", required = true)
    @Nonnull
    private Date notifyTime;

    /**
     * 参数字符集编码.
     */
    @XmlAttribute(name = "Charset", required = true)
    @Nonnull
    private Date charset;


    /**
     * 签名.
     */
    @XmlAttribute(name = "Sign", required = true)
    @Nonnull
    private String sign;


    /**
     * 签名方式.
     */
    @XmlAttribute(name = "SignType", required = true)
    @Nonnull
    private String signType;

    /**
     * 版本号.
     */
    @XmlAttribute(name = "Version", required = true)
    @Nonnull
    private String version;
    // ------------------------------------ getter && setter

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Nonnull
    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(@Nonnull String notifyId) {
        this.notifyId = notifyId;
    }

    @Nonnull
    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(@Nonnull String notifyType) {
        this.notifyType = notifyType;
    }

    @Nonnull
    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(@Nonnull Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    @Nonnull
    public Date getCharset() {
        return charset;
    }

    public void setCharset(@Nonnull Date charset) {
        this.charset = charset;
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

    @Nonnull
    public String getVersion() {
        return version;
    }

    public void setVersion(@Nonnull String version) {
        this.version = version;
    }

    // ------------------------------------ equals && hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotifyBase that = (NotifyBase) o;
        return Objects.equals(notifyId, that.notifyId) &&
                Objects.equals(notifyType, that.notifyType) &&
                Objects.equals(notifyTime, that.notifyTime) &&
                Objects.equals(charset, that.charset) &&
                Objects.equals(sign, that.sign) &&
                Objects.equals(signType, that.signType) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notifyId, notifyType, notifyTime, charset, sign, signType, version);
    }
}
