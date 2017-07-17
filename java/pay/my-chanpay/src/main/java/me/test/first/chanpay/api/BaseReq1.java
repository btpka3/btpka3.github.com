package me.test.first.chanpay.api;

import org.thymeleaf.util.*;

import java.util.*;

/**
 *
 */
public abstract class BaseReq1 {


    private String service;
    private String version = "1.0";
    private String partnerId;
    private final String inputCharset = "UTF-8";
    private String sign;
    private String signType;
    private String returnUrl;
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

    // ------------------------------------ equals && hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseReq1 baseReq1 = (BaseReq1) o;
        return Objects.equals(service, baseReq1.service) &&
                Objects.equals(version, baseReq1.version) &&
                Objects.equals(partnerId, baseReq1.partnerId) &&
                Objects.equals(inputCharset, baseReq1.inputCharset) &&
                Objects.equals(sign, baseReq1.sign) &&
                Objects.equals(signType, baseReq1.signType) &&
                Objects.equals(returnUrl, baseReq1.returnUrl) &&
                Objects.equals(memo, baseReq1.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), service, version, partnerId, inputCharset, sign, signType, returnUrl, memo);
    }


    // ------------------------------------ toMap
    Map<String, String> toMap() {
        Map<String, String> map = new LinkedHashMap<>();

        if (!StringUtils.isEmpty(service)) {
            map.put("service", service);
        }
        if (!StringUtils.isEmpty(version)) {
            map.put("version", version);
        }
        if (!StringUtils.isEmpty(partnerId)) {
            map.put("partner_id", partnerId);
        }
        if (!StringUtils.isEmpty(inputCharset)) {
            map.put("_input_charset", inputCharset);
        }
        if (!StringUtils.isEmpty(sign)) {
            map.put("sign", sign);
        }
        if (!StringUtils.isEmpty(signType)) {
            map.put("sign_type", signType);
        }
        if (!StringUtils.isEmpty(returnUrl)) {
            map.put("return_url", returnUrl);
        }
        if (!StringUtils.isEmpty(memo)) {
            map.put("memo", memo);
        }
        return map;
    }
}
