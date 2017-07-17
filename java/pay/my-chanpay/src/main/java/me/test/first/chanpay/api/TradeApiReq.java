package me.test.first.chanpay.api;

/**
 *
 */
public class TradeApiReq extends BaseReq {

    /**
     * 商户网站唯一订单号
     */
    public static final String OUT_TRADE_NO = "out_trade_no";

    /**
     * 交易金额
     */
    public static final String TRADE_AMOUNT = "trade_amount";

    /**
     * 用户手续费
     */
    public static final String USER_POUNDAGE = "user_poundage";

    /**
     * 商户手续费
     */
    public static final String MER_POUNDAGE = "mer_poundage";

    /**
     * 商品名称
     */
    public static final String PRODUCT_NAME = "product_name";

    /**
     * 交易描述
     */
    public static final String ACTION_DESC = "action_desc";

    /**
     * 卖家标示ID
     */
    public static final String SELL_ID = "sell_id";

    /**
     * 卖家标示ID类型
     */
    public static final String SELL_ID_TYPE = "sell_id_type";

    /**
     * 卖家手机号
     */
    public static final String SELL_MOBILE = "sell_mobile";

    /**
     * 商品展示URL
     */
    public static final String PRODUC_URL = "produc_url";

    /**
     * 服务器异步通知页面路径
     */
    public static final String NOTIFY_URL = "notify_url";

    /**
     * 支付过期时间
     */
    public static final String EXPIRED_TIME = "expired_time";

    /**
     * 商户订单提交时间
     */
    public static final String ORDER_TIME = "order_time";

    /**
     * 买家ID
     */
    public static final String BUYER_ID = "buyer_id";

    /**
     * 买家ID类型
     */
    public static final String BUYER_ID_TYPE = "buyer_id_type";

    /**
     * 买家手机号
     */
    public static final String BUYER_MOBILE = "buyer_mobile";

    /**
     * 用户在商户平台下单时候的ip地址
     */
    public static final String BUYER_IP = "buyer_ip";


    // ------ 支付相关参数

    /**
     * 支付方式
     */
    public static final String PAY_METHOD = "pay_method";

    /**
     * 借记贷记,对公对私
     */
    public static final String PAY_TYPE = "pay_type";

    /**
     * 银行简码
     */
    public static final String BANK_CODE = "bank_code";

    /**
     * 是否匿名支付（跳转收银台的场景使用）
     */
    public static final String IS_ANONYMOUS = "is_anonymous";

    /**
     * 是否同步返回支付URL
     */
    public static final String IS_RETURN_PAYURL = "is_returnpayurl";

    /**
     * 付款方名称
     * （客户姓名或者企业开户时全称）
     */
    public static final String PAYER_TRUENAME = "payer_truename";

    /**
     * 付款方银行名称（详细到支行）
     */
    public static final String PAYER_BANK_NAME = "payer_bankname";

    /**
     * 付款方银行账号
     */
    public static final String PAYER_BANK_ACCOUNTNO = "payer_bankaccountNo";

    /**
     * 交易金额分润账号集
     */
    public static final String ROYALTY_PARAMETERS = "royalty_parameters";

    /**
     * 扩展字段1
     */
    public static final String EXT1 = "ext1";

    /**
     * 扩展字段2
     */
    public static final String EXT2 = "ext2";


//    private String outTradeNo;
//    private String tradeAmount;
//    private String userPoundage;
//    private String merPoundage;
//    private String productName;
//    private String actionDesc;
//    private String sellId;
//    private String sellIdType;
//    private String sellMobile;
//    private String producUrl;
//    private String notifyUrl;
//    private String expiredTime;
//    private String orderTime;
//    private String buyerId;
//    private String buyerIdType;
//    private String buyerMobile;
//    private String buyerIp;
//    private String payMethod;
//    private String payType;
//    private String bankCode;
//    private String isAnonymous;
//    private String isReturnPayUrl;
//    private String payerTrueName;
//    private String payerBankName;
//    private String payerBankAccountNo;
//    private String royaltyParameters;
//    private String ext1;
//    private String ext2;

    public String getOutTradeNo() {
        return getFirst(OUT_TRADE_NO);
    }

    public void setOutTradeNo(String outTradeNo) {
        set(OUT_TRADE_NO, outTradeNo);
    }

    public String getTradeAmount() {
        return getFirst(TRADE_AMOUNT);
    }

    public void setTradeAmount(String tradeAmount) {
        set(TRADE_AMOUNT, tradeAmount);
    }

    public String getUserPoundage() {
        return getFirst(USER_POUNDAGE);
    }

    public void setUserPoundage(String userPoundage) {
        set(USER_POUNDAGE, userPoundage);
    }

    public String getMerPoundage() {
        return getFirst(MER_POUNDAGE);
    }

    public void setMerPoundage(String merPoundage) {
        set(MER_POUNDAGE, merPoundage);
    }

    public String getProductName() {
        return getFirst(PRODUCT_NAME);
    }

    public void setProductName(String productName) {
        set(PRODUCT_NAME, productName);
    }

    public String getActionDesc() {
        return getFirst(ACTION_DESC);
    }

    public void setActionDesc(String actionDesc) {
        set(ACTION_DESC, actionDesc);
    }

    public String getSellId() {
        return getFirst(SELL_ID);
    }

    public void setSellId(String sellId) {
        set(SELL_ID, sellId);
    }

    public String getSellIdType() {
        return getFirst(SELL_ID_TYPE);
    }

    public void setSellIdType(String sellIdType) {
        set(SELL_ID_TYPE, sellIdType);
    }

    public String getSellMobile() {
        return getFirst(SELL_MOBILE);
    }

    public void setSellMobile(String sellMobile) {
        set(SELL_MOBILE, sellMobile);
    }

    public String getProducUrl() {
        return getFirst(PRODUC_URL);
    }

    public void setProducUrl(String producUrl) {
        set(PRODUC_URL, producUrl);
    }

    public String getNotifyUrl() {
        return getFirst(NOTIFY_URL);
    }

    public void setNotifyUrl(String notifyUrl) {
        set(NOTIFY_URL, notifyUrl);
    }

    public String getExpiredTime() {
        return getFirst(EXPIRED_TIME);
    }

    public void setExpiredTime(String expiredTime) {
        set(EXPIRED_TIME, expiredTime);
    }

    public String getOrderTime() {
        return getFirst(ORDER_TIME);
    }

    public void setOrderTime(String orderTime) {
        set(ORDER_TIME, orderTime);
    }

    public String getBuyerId() {
        return getFirst(BUYER_ID);
    }

    public void setBuyerId(String buyerId) {
        set(BUYER_ID, buyerId);
    }

    public String getBuyerIdType() {
        return getFirst(BUYER_ID_TYPE);
    }

    public void setBuyerIdType(String buyerIdType) {
        set(BUYER_ID_TYPE, buyerIdType);
    }

    public String getBuyerMobile() {
        return getFirst(BUYER_MOBILE);
    }

    public void setBuyerMobile(String buyerMobile) {
        set(BUYER_MOBILE, buyerMobile);
    }

    public String getBuyerIp() {
        return getFirst(BUYER_IP);
    }

    public void setBuyerIp(String buyerIp) {
        set(BUYER_IP, buyerIp);
    }

    public String getPayMethod() {
        return getFirst(PAY_METHOD);
    }

    public void setPayMethod(String payMethod) {
        set(PAY_METHOD, payMethod);
    }

    public String getPayType() {
        return getFirst(PAY_TYPE);
    }

    public void setPayType(String payType) {
        set(PAY_TYPE, payType);
    }

    public String getBankCode() {
        return getFirst(BANK_CODE);
    }

    public void setBankCode(String bankCode) {
        set(BANK_CODE, bankCode);
    }

    public String getIsAnonymous() {
        return getFirst(IS_ANONYMOUS);
    }

    public void setIsAnonymous(String isAnonymous) {
        set(IS_ANONYMOUS, isAnonymous);
    }

    public String getIsReturnPayUrl() {
        return getFirst(IS_RETURN_PAYURL);
    }

    public void setIsReturnPayUrl(String isReturnPayUrl) {
        set(IS_RETURN_PAYURL, isReturnPayUrl);
    }

    public String getPayerTrueName() {
        return getFirst(PAYER_TRUENAME);
    }

    public void setPayerTrueName(String payerTrueName) {
        set(PAYER_TRUENAME, payerTrueName);
    }

    public String getPayerBankName() {
        return getFirst(PAYER_BANK_NAME);
    }

    public void setPayerBankName(String payerBankName) {
        set(PAYER_BANK_NAME, payerBankName);
    }

    public String getPayerBankAccountNo() {
        return getFirst(PAYER_BANK_ACCOUNTNO);
    }

    public void setPayerBankAccountNo(String payerBankAccountNo) {
        set(PAYER_BANK_ACCOUNTNO, payerBankAccountNo);
    }

    public String getRoyaltyParameters() {
        return getFirst(ROYALTY_PARAMETERS);
    }

    public void setRoyaltyParameters(String royaltyParameters) {
        set(ROYALTY_PARAMETERS, royaltyParameters);
    }

    public String getExt1() {
        return getFirst(EXT1);
    }

    public void setExt1(String ext1) {
        set(EXT1, ext1);
    }

    public String getExt2() {
        return getFirst(EXT2);
    }

    public void setExt2(String ext2) {
        set(EXT2, ext2);
    }
}