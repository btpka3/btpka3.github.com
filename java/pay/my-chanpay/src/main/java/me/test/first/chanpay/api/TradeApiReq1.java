package me.test.first.chanpay.api;

import org.springframework.util.*;

import java.util.*;

/**
 *
 * | service | value | 说明|
 * |---------|-------|-----|
 * |         | `cjt_create_instant_trade`       | |
 * |         | `cjt_wap_create_instant_trade` | 仅使用手机版(wap)收银台时使用 |
 *
 */
public class TradeApiReq1 extends BaseReq1 {

    /**
     * 商户网站唯一订单号
     */
    private String outTradeNo;

    /**
     * 交易金额
     */
    private String tradeAmount;

    /**
     * 用户手续费
     */
    private String userPoundage;

    /**
     * 商户手续费
     */
    private String merPoundage;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 交易描述
     *
     * 可放透传的信息。
     * 存放原订单号和原订单付款金额等信息。
     * 不要用，给特殊商户定制开发的
     */
    private String actionDesc;

    /**
     * 卖家标示ID
     */
    private String sellId;

    /**
     * 卖家标示ID类型
     *
     * - UID
     * - MEMBER_ID
     * - MOBILE
     * - LOGIN_NAME
     * - COMPANY_ID
     *
     */
    private String sellIdType;

    /**
     * 卖家手机号
     */
    private String sellMobile;

    /**
     * 商品展示URL。
     *
     *  收银台页面上，商品展示的超链接。
     */
    private String productUrl;

    /**
     * 服务器异步通知页面路径
     */
    private String notifyUrl;

    /**
     * 支付过期时间
     *
     * 设置未付款交易的超时时间，一旦超时，该笔交易就会自动被关闭；
     * 处在关闭的交易如收到状态变更，系统会自动发起退款操作。
     * 取值范围：1m～15d。m-分钟，h-小时，d-天。
     * 该参数数值不接受小数点，如1.5h，可转换为90m。
     */
    private String expiredTime;

    /**
     * 商户订单提交时间
     */
    private String orderTime;

    /**
     * 买家ID
     *
     * is_anonymous = `Y` 时，勿传
     */
    private String buyerId;

    /**
     * 买家ID类型
     *
     * is_anonymous = `Y` 时，勿传。
     *
     * 可选值:
     *
     * - UID
     * - MEMBER_ID
     * - MOBILE
     * - LOGIN_NAME
     * - COMPANY_ID
     */
    private String buyerIdType;

    /**
     * 买家手机号
     */
    private String buyerMobile;

    /**
     * 用户在商户平台下单时候的ip地址
     */
    private String buyerIp;


    // ------------------------------------ 支付相关参数


    /**
     * 支付方式
     *
     * - `1` : 直连。合作方自己有收银台，选择银行时候，调用该接口直接跳转到选中的银行网银
     * - `2` : 收银台。合作方没有收银台，订单支付时候，调用该接口到畅捷支付收银台
     * - `3` : 余额支付。合作方选择畅捷支付余额付款时候，到畅捷支付账户余额付款页面
     */
    private String payMethod;

    /**
     * 借记贷记,对公对私
     *
     * - 对公：B
     * - 对私：C
     * - 借记：DC
     * - 贷记：CC
     * - 综合：GC
     */
    private String payType;

    /**
     * 银行简码
     *
     * pay_method 为 `1` 时候，传递银行简码，否则为空；比如：
     *
     * - `ICBC` : 工行
     * - `WXPAY` : 微信扫码支付
     * - `ALIPAY` : 支付宝扫码支付
     */
    private String bankCode;

    /**
     * 是否匿名支付（跳转收银台的场景使用）
     *
     * 若该值为Y，表示该笔订单的用户不需要是畅捷支付的用户。
     */
    private String isAnonymous;

    /**
     * 是否同步返回支付URL。
     *
     * 目前只支持扫码支付同步返回付款二维码URL地址
     *
     * - Y：需要返回扫码支付付款二维码URL地址；
     * - N：不同步返回（默认）
     */
    private String isReturnPayUrl;

    /**
     * 付款方名称
     * （客户姓名或者企业开户时全称）
     */
    private String payerTrueName;

    /**
     * 付款方银行名称（详细到支行）
     */
    private String payerBankName;

    /**
     * 付款方银行账号
     */
    private String payerBankAccountNo;

    /**
     * 交易金额分润账号集。
     *
     * 示例：
     *
     * ```json
     * [{
     *     "PID": "2",              // 平台类型
     *     "userId": "13890009900", // 外部用户号
     *                              //      PID = `1` 时，值为 UID
     *                              //      PID = `2` 时，值为 手机号/邮箱
     *     "account_type": "101",   // 账户类型
     *                              //      `101` : 个人会员账户
     *                              //      `201` : 企业会员账户
     *     "amount": "100.00"       // 要分润的钱
     * },{},{}]
     * ```
     *
     * 注意：
     *
     * - 不能超过10个账户
     * - 支付时，则默认卖家是付款方；退款时相反
     * - 分润者要先收到钱才能再付给其他被分润者
     * - 收到的钱一定要大于等于付出的钱，即先入后出，入要大于等于出
     *
     */
    private String royaltyParameters;

    /**
     * 扩展字段1
     *
     * 示例：
     *
     * ```json
     * [{
     *     "hasUserSign" : "true",              // 是否快捷绑卡
     *                                          //      - true      : 要绑卡
     *                                          //      - false     : 不需要绑卡
     *     "userSign" : "",                     // 用户标识。不建议用户手机号作为标识
     *     "version"  : "2.0",                  // 收银台版本号
     *     "subMerchantNo" : "",                // 扫码支付，用来结算的子商户商户号
     *     "webChatOfficialAccounts": "true",   // 是否微信公众号访问
     *                                          //      - true      : 公众号微信扫码
     *     "externalSettlement" : "true",       // 是否动态扫码自动结算模式
     *                                          //      - true      : 表示动态扫码自动结算
     *     "offLinePay" : "true",               // 是否为线下付款订单
     *                                          //      - true      : 表示商户针对此订单进行线下付
     *     "barCodeScan" : "",                  // 条码扫描时的买家ID, 微信app支付参数
     *     "terminalOs" : "",                   // 终端标识
     *                                          //      - `ANDROID` : 安卓
     *                                          //      - `IOS`     : 苹果
     *     "paymentWay" : ""                    // 支付方式
     * },{},{}]
     * ```
     */
    private String ext1;

    /**
     * 扩展字段2
     */
    private String ext2;

    // ------------------------------------ getter && setter


    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getUserPoundage() {
        return userPoundage;
    }

    public void setUserPoundage(String userPoundage) {
        this.userPoundage = userPoundage;
    }

    public String getMerPoundage() {
        return merPoundage;
    }

    public void setMerPoundage(String merPoundage) {
        this.merPoundage = merPoundage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
    }

    public String getSellIdType() {
        return sellIdType;
    }

    public void setSellIdType(String sellIdType) {
        this.sellIdType = sellIdType;
    }

    public String getSellMobile() {
        return sellMobile;
    }

    public void setSellMobile(String sellMobile) {
        this.sellMobile = sellMobile;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerIdType() {
        return buyerIdType;
    }

    public void setBuyerIdType(String buyerIdType) {
        this.buyerIdType = buyerIdType;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    public String getBuyerIp() {
        return buyerIp;
    }

    public void setBuyerIp(String buyerIp) {
        this.buyerIp = buyerIp;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(String isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public String getIsReturnPayUrl() {
        return isReturnPayUrl;
    }

    public void setIsReturnPayUrl(String isReturnPayUrl) {
        this.isReturnPayUrl = isReturnPayUrl;
    }

    public String getPayerTrueName() {
        return payerTrueName;
    }

    public void setPayerTrueName(String payerTrueName) {
        this.payerTrueName = payerTrueName;
    }

    public String getPayerBankName() {
        return payerBankName;
    }

    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }

    public String getPayerBankAccountNo() {
        return payerBankAccountNo;
    }

    public void setPayerBankAccountNo(String payerBankAccountNo) {
        this.payerBankAccountNo = payerBankAccountNo;
    }

    public String getRoyaltyParameters() {
        return royaltyParameters;
    }

    public void setRoyaltyParameters(String royaltyParameters) {
        this.royaltyParameters = royaltyParameters;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    // ------------------------------------ equals && hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TradeApiReq1 that = (TradeApiReq1) o;
        return Objects.equals(outTradeNo, that.outTradeNo) &&
                Objects.equals(tradeAmount, that.tradeAmount) &&
                Objects.equals(userPoundage, that.userPoundage) &&
                Objects.equals(merPoundage, that.merPoundage) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(actionDesc, that.actionDesc) &&
                Objects.equals(sellId, that.sellId) &&
                Objects.equals(sellIdType, that.sellIdType) &&
                Objects.equals(sellMobile, that.sellMobile) &&
                Objects.equals(productUrl, that.productUrl) &&
                Objects.equals(notifyUrl, that.notifyUrl) &&
                Objects.equals(expiredTime, that.expiredTime) &&
                Objects.equals(orderTime, that.orderTime) &&
                Objects.equals(buyerId, that.buyerId) &&
                Objects.equals(buyerIdType, that.buyerIdType) &&
                Objects.equals(buyerMobile, that.buyerMobile) &&
                Objects.equals(buyerIp, that.buyerIp) &&
                Objects.equals(payMethod, that.payMethod) &&
                Objects.equals(payType, that.payType) &&
                Objects.equals(bankCode, that.bankCode) &&
                Objects.equals(isAnonymous, that.isAnonymous) &&
                Objects.equals(isReturnPayUrl, that.isReturnPayUrl) &&
                Objects.equals(payerTrueName, that.payerTrueName) &&
                Objects.equals(payerBankName, that.payerBankName) &&
                Objects.equals(payerBankAccountNo, that.payerBankAccountNo) &&
                Objects.equals(royaltyParameters, that.royaltyParameters) &&
                Objects.equals(ext1, that.ext1) &&
                Objects.equals(ext2, that.ext2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), outTradeNo, tradeAmount, userPoundage, merPoundage, productName, actionDesc, sellId, sellIdType, sellMobile, productUrl, notifyUrl, expiredTime, orderTime, buyerId, buyerIdType, buyerMobile, buyerIp, payMethod, payType, bankCode, isAnonymous, isReturnPayUrl, payerTrueName, payerBankName, payerBankAccountNo, royaltyParameters, ext1, ext2);
    }

    // ------------------------------------ toMap

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = super.toMap();

        if (StringUtils.isEmpty(outTradeNo)) {
            map.put("out_trade_no", outTradeNo);
        }
        if (StringUtils.isEmpty(tradeAmount)) {
            map.put("trade_amount", tradeAmount);
        }
        if (StringUtils.isEmpty(userPoundage)) {
            map.put("user_poundage", userPoundage);
        }
        if (StringUtils.isEmpty(merPoundage)) {
            map.put("mer_poundage", merPoundage);
        }
        if (StringUtils.isEmpty(productName)) {
            map.put("product_name", productName);
        }
        if (StringUtils.isEmpty(actionDesc)) {
            map.put("action_desc", actionDesc);
        }
        if (StringUtils.isEmpty(sellId)) {
            map.put("sell_id", sellId);
        }
        if (StringUtils.isEmpty(sellIdType)) {
            map.put("sell_id_type", sellIdType);
        }
        if (StringUtils.isEmpty(sellMobile)) {
            map.put("sell_mobile", sellMobile);
        }
        if (StringUtils.isEmpty(productUrl)) {
            map.put("produc_url", productUrl);
        }
        if (StringUtils.isEmpty(notifyUrl)) {
            map.put("notify_url", notifyUrl);
        }
        if (StringUtils.isEmpty(expiredTime)) {
            map.put("expired_time", expiredTime);
        }
        if (StringUtils.isEmpty(orderTime)) {
            map.put("order_time", orderTime);
        }
        if (StringUtils.isEmpty(buyerId)) {
            map.put("buyer_id", buyerId);
        }
        if (StringUtils.isEmpty(buyerIdType)) {
            map.put("buyer_id_type", buyerIdType);
        }
        if (StringUtils.isEmpty(buyerMobile)) {
            map.put("buyer_mobile", buyerMobile);
        }
        if (StringUtils.isEmpty(buyerIp)) {
            map.put("buyer_ip", buyerIp);
        }
        if (StringUtils.isEmpty(payMethod)) {
            map.put("pay_method", payMethod);
        }
        if (StringUtils.isEmpty(payType)) {
            map.put("pay_type", payType);
        }
        if (StringUtils.isEmpty(bankCode)) {
            map.put("bank_code", bankCode);
        }
        if (StringUtils.isEmpty(isAnonymous)) {
            map.put("is_anonymous", isAnonymous);
        }
        if (StringUtils.isEmpty(isReturnPayUrl)) {
            map.put("is_returnpayurl", isReturnPayUrl);
        }
        if (StringUtils.isEmpty(payerTrueName)) {
            map.put("payer_truename", payerTrueName);
        }
        if (StringUtils.isEmpty(payerBankName)) {
            map.put("payer_bankname", payerBankName);
        }
        if (StringUtils.isEmpty(payerBankAccountNo)) {
            map.put("payer_bankaccountNo", payerBankAccountNo);
        }
        if (StringUtils.isEmpty(royaltyParameters)) {
            map.put("royalty_parameters", royaltyParameters);
        }
        if (StringUtils.isEmpty(ext1)) {
            map.put("ext1", ext1);
        }
        if (StringUtils.isEmpty(ext2)) {
            map.put("ext2", ext2);
        }

        return map;
    }
}