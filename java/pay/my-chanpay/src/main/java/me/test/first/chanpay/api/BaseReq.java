package me.test.first.chanpay.api;

/**
 *
 */
public abstract class BaseReq extends AbstractNamedMultiValueMap {

    public static final String SERVICE = "service";
    public static final String VERSION = "version";
    public static final String PARTNER_ID = "partner_id";
    public static final String INPUT_CHARSET = "_input_charset";
    public static final String SIGN = "sign";
    public static final String SIGN_TYPE = "sign_type";
    public static final String RETURN_URL = "return_url";
    public static final String MEMO = "memo";

//    private String service;
//    private String version;
//    private String partnerId;
//    private String inputCharset;
//    private String sign;
//    private String signType;
//    private String returnUrl;
//    private String memo;

    public BaseReq(){
        this.setInputCharset("UTF-8");
    }

    /**
     * 获取 接口名称。
     */
    public String getService() {
        return getFirst(SERVICE);
    }

    /**
     * 设置 接口名称。
     */
    public void setService(String service) {
        set(SERVICE, service);
    }

    /**
     * 获取 接口版本。
     */
    public String getVersion() {
        return getFirst(VERSION);
    }

    /**
     * 设置 接口版本。
     */
    public void setVersion(String version) {
        set(VERSION, version);
    }

    /**
     * 获取 合作者身份ID。
     */
    public String getPartnerId() {
        return getFirst(PARTNER_ID);
    }

    /**
     * 设置 合作者身份ID。
     */
    public void setPartnerId(String partnerId) {
        set(PARTNER_ID, partnerId);
    }

    /**
     * 获取 参数编码字符集。
     */
    public String getInputCharset() {
        return getFirst(INPUT_CHARSET);
    }

    /**
     * 设置 参数编码字符集。
     */
    public void setInputCharset(String inputCharset) {
        set(INPUT_CHARSET, inputCharset);
    }

    /**
     * 获取 签名。
     */
    public String getSign() {
        return getFirst(SIGN);
    }

    /**
     * 设置 签名。
     */
    public void setSign(String sign) {
        set(SIGN, sign);
    }

    /**
     * 获取 签名方式。
     */
    public String getSignType() {
        return getFirst(SIGN_TYPE);
    }


    /**
     * 设置 签名方式。
     */
    public void setSignType(String signType) {
        set(SIGN_TYPE, signType);
    }

    /**
     * 获取 页面跳转同步返回页面路径。
     */
    public String getReturnUrl() {
        return getFirst(RETURN_URL);
    }

    /**
     * 设置 页面跳转同步返回页面路径。
     */
    public void setReturnUrl(String returnUrl) {
        set(RETURN_URL, returnUrl);
    }

    /**
     * 获取 备注。
     */
    public String getMemo() {
        return getFirst(MEMO);
    }

    /**
     * 设置 备注。
     */
    public void setMemo(String memo) {
        set(MEMO, memo);
    }
}
