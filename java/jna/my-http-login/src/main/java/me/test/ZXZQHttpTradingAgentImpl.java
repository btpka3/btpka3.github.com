/*
 * @(#)ZXZQHttpTradingAgentImpl.java
 *
 * Copyright @ Hangzhou Xdstock Internet Tech. Co., Ltd.
 */
package me.test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.shengrensoft.stock.center.Constants;
//import com.shengrensoft.stock.center.activityStock.ActivityStockList;
//import com.shengrensoft.stock.center.market.StockMarketInfo;
//import com.shengrensoft.stock.common.javabean.StockInventoryRec;
//import com.shengrensoft.stock.common.javabean.UserAccountCommissionRec;
//import com.shengrensoft.stock.common.javabean.UserAccountCreditInfo;
//import com.shengrensoft.stock.common.javabean.UserAccountHoldingStatusRec;
//import com.shengrensoft.stock.common.javabean.UserAccountMoneyStatusRec;
//import com.shengrensoft.stock.common.javabean.UserAccountTradedRec;
//import com.shengrensoft.stock.common.util.DateUtil;
//import com.shengrensoft.stock.common.util.JSONUtil;
//import com.shengrensoft.stock.common.util.StringUtil;
//import com.shengrensoft.stock.common.util.ValidationUtil;
//import com.shengrensoft.stock.ss03.batch.AutoTradingConstants;
//import com.shengrensoft.stock.ss03.batch.agent.TradingAgent;
//import com.shengrensoft.stock.ss03.web.common.service.RuntimeException;
//import com.show.api.ShowapiRequest;
//import com.show.api.util.Base64;

/**
 * 【中信证券HTTP协议】的交易代理实现。
 * 
 * @author 杭州迅动 2017/07/11
 */
public class ZXZQHttpTradingAgentImpl  {

    /** 文件日志记录器 */
    private final Log logger = LogFactory.getLog(ZXZQHttpTradingAgentImpl.class);

//    /** 证券代码前缀: 上海A股 */
//    private static final String PREFIX_SH = Constants.STK_CODE_SHA_PRE;
//
//    /** 证券代码前缀: 深证A股 */
//    private static final String PREFIX_SZ = Constants.STK_CODE_SZA_PRE;

    /** 连接使用的协议*/
    private static final String SCHEME = "https";

    /** 服务器：服务器地址 生产环境 */
    private String SERVER_URL = "trade.dwzq.com.cn";

    /** 服务器：服务器地址 生产环境 */
    private int PORT = -1;

    /** 服务器：渠道号 生产环境*/
    private String LY = "0773";

    /** 伪装手机浏览器访问 */
    private String USER_AGENT = "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Mobile Safari/537.36";
                                 
    /** 验证码请求随机数 */
    private String MOBILE_KEY = "2563125907";
//
//    /** 阿里图片验证码识别API地址 */
//    private String ALI_CHECK_CODE = "http://ali-checkcode2.showapi.com/checkcode";
//
//    /** 阿里图片验证码识别AppCode */
//    private String ALI_CHECK_APP_CODE = "1aa219cf8b8748caa1306620acbcb5e9";

    /** 请求返回错误号：正确返回 0 */
    private static final String RESPONSE_OK = "1";

//    /** 请求返回错误号：密码错误 3 */
//    private static final String RESPONSE_PWD_ERROR = "3";
//
//    /** 请求返回错误号：验证码错误 -10005 */
//    private static final String RESPONSE_CODE_ERROR = "-10005";
//
//    /** 委托方式 6 */
//    private static final String ENTRUST_WAY = "6";
//
//    /** 委托方向 买入 0 */
//    private static final String ENTRUST_BUY = "0";
//
//    /** 委托方向 卖出 1 */
//    private static final String ENTRUST_SELL = "1";
//
//    /** 市场类型 上证 */
//    private static final String EXCHANGE_TYPE_SH = "2";
//
//    /** 市场类型 深证 */
//    private static final String EXCHANGE_TYPE_SZ = "0";
//
//    /** 功能号：账户登录 */
//    private static final String FUNC_NO_LOGIN = "300100";
//
//    /** 功能号：委托 */
//    private static final String FUNC_NO_COMMIT = "301501";
//
//    /** 功能号：撤单 */
//    private static final String FUNC_NO_REVOKE = "301502";
//
//    /** 功能号：持仓查询 */
//    private static final String FUNC_NO_POSITION = "301503";
//
//    /** 功能号：查询资金 */
//    private static final String FUNC_NO_MONEY_STATUS = "301504";
//
//    /** 功能号：查询当日委托 */
//    private static final String FUNC_NO_COMMISSION = "301508";
//
//    /** 功能号：查询当日成交 */
//    private static final String FUNC_NO_TODAY_TRADED = "301509";
//
//    /** 功能号：新股查询 */
//    private static final String FUNC_NO_NEW_STOCK = "301514";

    /*---------------------成员变量----------------------------*/

    /** HTTP连接器 */
    private DefaultHttpClient httpclient = null;

    /** Cookie存储器 */
    private CookieStore cookieStore = null;

    /** HTTP连接状态信息 */
    private HttpContext localContext = null;
//
//    /** 客户账号 */
//    private String loginId = null;
//
//    /** 账号密码*/
//    private String password = null;

    /** 账号ID */
    private String accountId = null;
//
//    /** 营业部编号 */
//    private String branchNo = null;
//
//    /** 上证股东卡号 */
//    private String holderCodeSha = null;
//
//    /** 深证股东卡号 */
//    private String holderCodeSza = null;
//
//    /** 客户代码 */
//    private String custCode = null;
//
//    /** 可用的证券列表 */
//    private ActivityStockList activityStockList = null;
//
//    /** 交易市场信息服务 */
//    private StockMarketInfo stockMarketInfo =null;
//
//    /** 监测连接状态线程 */
//    private Thread monitorThread = null;
//
//    /** 账户状态 */
//    private int accountStatus = AutoTradingConstants.ACCOUNT_LOGOUT;
//
//    /** 退出标志 */
//    private boolean stop = false;

    /** 手机型号 */
    private String phoneModel = null;

    /** MAC地址 */
    private String mac = null;

    /** 手机唯一识别码 */
    private String imei = null;
//
//    /** 备注 */
//    private String opstation = null;

    /** 生成8位随机数 */
    private String[] chars = new String[] { "a", "b", "c", "d", "e", "f",  
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
        "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
        "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",  
        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
        "W", "X", "Y", "Z" }; 

    /** 生成随机的安卓手机机型 */
    private String[] phoneModels = new String[] {"MI NOTE", "M4", 
            "M4C", "M5", "HUAWEI P6", "HUAWEIG700", "HUAWEIA199", 
            "HUAWEIG52", "HUAWEIMate", "HUAWEIC8815", "HUAWEIG700T", 
            "HUAWEIG610C", "HUAWEIU9508", "HUAWEIG606", "SAMSUNGI9220", 
            "SAMSUNGi9502"};

    /**
     * 构造方法。
     */
    public ZXZQHttpTradingAgentImpl() {

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        Scheme http = new Scheme("http", 80, PlainSocketFactory
                .getSocketFactory());
        schemeRegistry.register(http);
        try {
            Scheme https = new Scheme("https", 443, new SSLSocketFactory(
                    new TrustStrategy() {

                        public boolean isTrusted(
                                X509Certificate[] arg0,
                                String arg1) throws CertificateException {
                            return true;
                        }

                    }));
            schemeRegistry.register(https);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 自定义管理Cookie
        CookieSpecFactory csf = new CookieSpecFactory() {
            public CookieSpec newInstance(
                    HttpParams params) {
                return new BrowserCompatSpec() {
                    @Override
                    public void validate(
                            Cookie cookie,
                            CookieOrigin origin)
                            throws MalformedCookieException {
                    }
                };
            }
        };

        ClientConnectionManager cm = new ThreadSafeClientConnManager(
                schemeRegistry);
        httpclient = new DefaultHttpClient(cm);
        httpclient.getCookieSpecs().register("easy", csf);
        httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");
        cookieStore = new BasicCookieStore();
        localContext = new BasicHttpContext();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

        // 生成手机型号
        Random rand = new Random();
        int num = rand.nextInt(16);
        this.phoneModel = phoneModels[num];

        // 生成mac地址
        this.mac = new RandomMac().getFormatMacAddr(":");

        // 生成手机唯一识别码
        this.imei = makeImei();

        return;

    }
//
//    /**
//     *  发起买入委托。
//     *
//     *  @param userId 用户ID
//     *  @param accountNo 账户编号
//     *  @param stkCode 证券代码
//     *  @param amount 委托数量
//     *  @param price 委托价格
//     *
//     *  @return String 委托编号
//     */
//    public String buyInNormal(
//            String userId,
//            Long accountNo,
//            String stkCode,
//            Long amount,
//            Integer price) {
//
//        String commitNo = null; // 委托编号
//
//        try {
//
//            // 账号登录请求参数
//            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
//
//            // 请求功能号
//            qparams.add(new BasicNameValuePair("funcNo", FUNC_NO_COMMIT));
//
//            // 委托方式
//            qparams.add(new BasicNameValuePair("entrust_way", ENTRUST_WAY));
//
//            // 营业部编号
//            qparams.add(new BasicNameValuePair("branch_no", this.branchNo));
//
//            // 资金账号
//            qparams.add(new BasicNameValuePair("fund_account", this.custCode));
//            qparams.add(new BasicNameValuePair("cust_code", this.custCode));
//
//            // 密码
//            qparams.add(new BasicNameValuePair("password", this.password));
//
//            // 买卖方向
//            qparams.add(new BasicNameValuePair("entrust_bs", ENTRUST_BUY));
//
//            // 8位数随机码
//            qparams.add(new BasicNameValuePair("uid", getUid()));
//
//            // 备注
//            qparams.add(new BasicNameValuePair("op_station", this.opstation));
//
//            // 市场以及股东代码
//            String exchangeType = null;
//            String stockAccount = null;
//
//            if (stkCode.startsWith(PREFIX_SH)) {
//                exchangeType = EXCHANGE_TYPE_SH;
//                stockAccount = this.holderCodeSha;
//            } else if (stkCode.startsWith(PREFIX_SZ)) {
//                exchangeType = EXCHANGE_TYPE_SZ;
//                stockAccount = this.holderCodeSza;
//            }
//
//            // 市场
//            qparams.add(new BasicNameValuePair("exchange_type", exchangeType));
//            qparams.add(new BasicNameValuePair("stock_account", stockAccount));
//
//            // 证券代码
//            qparams.add(new BasicNameValuePair("stock_code", StringUtil.getNoPrefixCode(stkCode)));
//
//            // 股票价格按分处理，基金或债券按厘处理
//            double commitPrice = 0.0d;
//            String stkType = this.activityStockList.getStkType(stkCode); // 证券类型
//            if (Constants.STK_TYPE_A_STOCK.equals(stkType)) {
//
//                // 股票价格保留两位小数不四舍五入单位(元)
//                DecimalFormat formater = new DecimalFormat("0.00");
//                formater.setMaximumFractionDigits(2);
//                formater.setGroupingSize(0);
//                formater.setRoundingMode(RoundingMode.FLOOR);
//                String strPrice = formater.format(price / 100.0);
//                commitPrice = Double.parseDouble(strPrice);
//
//            } else if (Constants.STK_TYPE_CEF.equals(stkType)
//                    || Constants.STK_TYPE_OEF.equals(stkType)
//                    || Constants.STK_TYPE_ETF.equals(stkType)
//                    || Constants.STK_TYPE_LOF.equals(stkType)
//                    || Constants.STK_TYPE_BOND.equals(stkType)) {
//
//                // 基金或债券的价格保留三位小数不四舍五入单位(元)
//                DecimalFormat formater = new DecimalFormat("0.000");
//                formater.setMaximumFractionDigits(3);
//                formater.setGroupingSize(0);
//                formater.setRoundingMode(RoundingMode.FLOOR);
//                String strPrice = formater.format(price / 1000.0);
//                commitPrice = Double.parseDouble(strPrice);
//
//            } else {
//
//                // 容错处理
//                DecimalFormat formater = new DecimalFormat("0.00");
//                formater.setMaximumFractionDigits(2);
//                formater.setGroupingSize(0);
//                formater.setRoundingMode(RoundingMode.FLOOR);
//                String strPrice = formater.format(price / 100.0);
//                commitPrice = Double.parseDouble(strPrice);
//
//            }
//            qparams.add(new BasicNameValuePair("entrust_price", String.valueOf(commitPrice)));
//
//            // 委托数量
//            qparams.add(new BasicNameValuePair("entrust_amount", String.valueOf(amount)));
//
//            // 委托请求地址
//            URI uri = URIUtils.createURI(
//                    SCHEME,
//                    SERVER_URL,
//                    PORT,
//                    "/servlet/json",
//                    null,
//                    null);
//
//            HttpPost httpPost = new HttpPost(uri);
//            httpPost.setHeader("User-Agent",USER_AGENT);
//            httpPost.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
//            HttpResponse response = httpclient.execute(httpPost, localContext);
//            HttpEntity entity = response.getEntity();
//            InputStream instream = entity.getContent();
//            String html = IOUtils.toString(instream, "UTF-8");
//            instream.close();
//
//            // 解析登录返回的JSON字符串
//            JSONObject jsonObject = (JSONObject) JSONUtil.getJSONObjectFromJSONString(html);
//
//            // 请求错误
//            String errorNo  = jsonObject.getString("error_no");
//            if (!RESPONSE_OK.equals(errorNo)) {
//
//                // 委托出错消息
//                String errorMessage = jsonObject.getString("error_info");
//                if (StringUtil.isEmpty(errorMessage)) {
//                    errorMessage = "委托失败，请重试。";
//                }
//
//                throw new RuntimeException(errorMessage);
//
//            }
//
//            // 请求成功获取资金信息
//            JSONArray jsonArray  = jsonObject.getJSONArray("results");
//            JSONObject result = jsonArray.getJSONObject(0);
//            commitNo = result.getString("entrust_no");
//
//        } catch (RuntimeException e) {
//            throw e;
//        } catch (Exception e) {
//            if (logger.isErrorEnabled()) {
//                logger.warn("发出买入委托时出错", e);
//            }
//        }
//
//        return commitNo;
//
//    }

//    /**
//     *  发起卖出委托。
//     *
//     *  @param userId 用户ID
//     *  @param accountNo 账户编号
//     *  @param stkCode 证券代码
//     *  @param amount 委托数量
//     *  @param price 委托价格
//     *
//     *  @return String 委托编号
//     */
//    public String sellInNormal(
//            String userId,
//            Long accountNo,
//            String stkCode,
//            Long amount,
//            Integer price) throws RuntimeException {
//
//        String commitNo = null; // 委托编号
//
//        try {
//
//            // 账号登录请求参数
//            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
//
//            // 请求功能号
//            qparams.add(new BasicNameValuePair("funcNo", FUNC_NO_COMMIT));
//
//            // 委托方式
//            qparams.add(new BasicNameValuePair("entrust_way", ENTRUST_WAY));
//
//            // 营业部编号
//            qparams.add(new BasicNameValuePair("branch_no", this.branchNo));
//
//            // 资金账号
//            qparams.add(new BasicNameValuePair("fund_account", this.custCode));
//            qparams.add(new BasicNameValuePair("cust_code", this.custCode));
//
//            // 密码
//            qparams.add(new BasicNameValuePair("password", this.password));
//
//            // 买卖方向
//            qparams.add(new BasicNameValuePair("entrust_bs", ENTRUST_SELL));
//
//            // 8位数随机码
//            qparams.add(new BasicNameValuePair("uid", getUid()));
//
//            // 备注
//            qparams.add(new BasicNameValuePair("op_station", this.opstation));
//
//            // 市场以及股东代码
//            String exchangeType = null;
//            String stockAccount = null;
//
//            if (stkCode.startsWith(PREFIX_SH)) {
//                exchangeType = EXCHANGE_TYPE_SH;
//                stockAccount = this.holderCodeSha;
//            } else if (stkCode.startsWith(PREFIX_SZ)) {
//                exchangeType = EXCHANGE_TYPE_SZ;
//                stockAccount = this.holderCodeSza;
//            }
//
//            // 市场
//            qparams.add(new BasicNameValuePair("exchange_type", exchangeType));
//            qparams.add(new BasicNameValuePair("stock_account", stockAccount));
//
//            // 证券代码
//            qparams.add(new BasicNameValuePair("stock_code", StringUtil.getNoPrefixCode(stkCode)));
//
//            // 股票价格按分处理，基金或债券按厘处理
//            double commitPrice = 0.0d;
//            String stkType = this.activityStockList.getStkType(stkCode); // 证券类型
//            if (Constants.STK_TYPE_A_STOCK.equals(stkType)) {
//
//                // 股票价格保留两位小数不四舍五入单位(元)
//                DecimalFormat formater = new DecimalFormat("0.00");
//                formater.setMaximumFractionDigits(2);
//                formater.setGroupingSize(0);
//                formater.setRoundingMode(RoundingMode.FLOOR);
//                String strPrice = formater.format(price / 100.0);
//                commitPrice = Double.parseDouble(strPrice);
//
//            } else if (Constants.STK_TYPE_CEF.equals(stkType)
//                    || Constants.STK_TYPE_OEF.equals(stkType)
//                    || Constants.STK_TYPE_ETF.equals(stkType)
//                    || Constants.STK_TYPE_LOF.equals(stkType)
//                    || Constants.STK_TYPE_BOND.equals(stkType)) {
//
//                // 基金或债券的价格保留三位小数不四舍五入单位(元)
//                DecimalFormat formater = new DecimalFormat("0.000");
//                formater.setMaximumFractionDigits(3);
//                formater.setGroupingSize(0);
//                formater.setRoundingMode(RoundingMode.FLOOR);
//                String strPrice = formater.format(price / 1000.0);
//                commitPrice = Double.parseDouble(strPrice);
//
//            }
//            qparams.add(new BasicNameValuePair("entrust_price", String.valueOf(commitPrice)));
//
//            // 委托数量：华林证券逆回购的情况，数量乘10（特别处理）
//            if (Constants.STK_CODE_GC001_ALIAS.equals(stkCode)) {
//                qparams.add(new BasicNameValuePair("entrust_amount", String.valueOf(amount * 10)));
//            } else {
//                qparams.add(new BasicNameValuePair("entrust_amount", String.valueOf(amount)));
//            }
//
//            // 委托请求地址
//            URI uri = URIUtils.createURI(
//                    SCHEME,
//                    SERVER_URL,
//                    -1,
//                    "/servlet/json",
//                    null,
//                    null);
//
//            HttpPost httpPost = new HttpPost(uri);
//            httpPost.setHeader("User-Agent",USER_AGENT);
//            httpPost.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
//            HttpResponse response = httpclient.execute(httpPost, localContext);
//            HttpEntity entity = response.getEntity();
//            InputStream instream = entity.getContent();
//            String html = IOUtils.toString(instream, "UTF-8");
//            instream.close();
//
//            // 解析登录返回的JSON字符串
//            JSONObject jsonObject = (JSONObject) JSONUtil.getJSONObjectFromJSONString(html);
//
//            // 请求错误
//            String errorNo  = jsonObject.getString("error_no");
//            if (!RESPONSE_OK.equals(errorNo)) {
//
//                // 委托出错消息
//                String errorMessage = jsonObject.getString("error_info");
//                if (StringUtil.isEmpty(errorMessage)) {
//                    errorMessage = "委托失败，请重试。";
//                }
//                throw new RuntimeException(errorMessage);
//            }
//
//            // 请求成功获取资金信息
//            JSONArray jsonArray  = jsonObject.getJSONArray("results");
//            JSONObject result = jsonArray.getJSONObject(0);
//            commitNo = result.getString("entrust_no");
//
//        } catch (RuntimeException e) {
//            throw e;
//        } catch (Exception e) {
//            if (logger.isErrorEnabled()) {
//                logger.warn("发出卖出委托时出错", e);
//            }
//        }
//
//        return commitNo;
//
//    }
//
//    /**
//     *  发起撤单委托。
//     *
//     *  @param userId 用户ID
//     *  @param accountNo 账户编号
//     *  @param stkCode 证券代码
//     *  @param commitNo 委托编号
//     *
//     *  @return String 委托编号
//     */
//    public String revoke(
//            String userId,
//            Long accountNo,
//            String stkCode,
//            String commitNo) throws RuntimeException {
//
//        String revokeCommitNo = null; // 委托编号
//
//        try {
//
//            // 账号登录请求参数
//            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
//
//            // 请求功能号
//            qparams.add(new BasicNameValuePair("funcNo", FUNC_NO_REVOKE));
//
//            // 委托方式
//            qparams.add(new BasicNameValuePair("entrust_way", ENTRUST_WAY));
//
//            // 营业部编号
//            qparams.add(new BasicNameValuePair("branch_no", this.branchNo));
//
//            // 资金账号
//            qparams.add(new BasicNameValuePair("fund_account", this.custCode));
//            qparams.add(new BasicNameValuePair("cust_code", this.custCode));
//
//            // 密码
//            qparams.add(new BasicNameValuePair("password", this.password));
//
//            // 委托编号
//            qparams.add(new BasicNameValuePair("entrust_no", commitNo));
//
//            // 8位数随机码
//            qparams.add(new BasicNameValuePair("uid", getUid()));
//
//            // 备注
//            qparams.add(new BasicNameValuePair("op_station", this.opstation));
//
//            // 市场以及股东代码
//            String exchangeType = null;
//
//            if (stkCode.startsWith(PREFIX_SH)) {
//                exchangeType = EXCHANGE_TYPE_SH;
//            } else if (stkCode.startsWith(PREFIX_SZ)) {
//                exchangeType = EXCHANGE_TYPE_SZ;
//            }
//
//            // 市场
//            qparams.add(new BasicNameValuePair("exchange_type", exchangeType));
//
//            qparams.add(new BasicNameValuePair("batch_flag", "0"));
//
//            // 委托请求地址
//            URI uri = URIUtils.createURI(
//                    SCHEME,
//                    SERVER_URL,
//                    PORT,
//                    "/servlet/json",
//                    null,
//                    null);
//
//            HttpPost httpPost = new HttpPost(uri);
//            httpPost.setHeader("User-Agent",USER_AGENT);
//            httpPost.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
//            HttpResponse response = httpclient.execute(httpPost, localContext);
//            HttpEntity entity = response.getEntity();
//            InputStream instream = entity.getContent();
//            String html = IOUtils.toString(instream, "UTF-8");
//            instream.close();
//
//            // 解析登录返回的JSON字符串
//            JSONObject jsonObject = (JSONObject) JSONUtil.getJSONObjectFromJSONString(html);
//
//            // 请求错误
//            String errorNo  = jsonObject.getString("error_no");
//            if (!RESPONSE_OK.equals(errorNo)) {
//
//                // 委托出错消息
//                String errorMessage = jsonObject.getString("error_info");
//                if (StringUtil.isEmpty(errorMessage)) {
//                    errorMessage = "委托失败，请重试。";
//                }
//                throw new RuntimeException(errorMessage);
//            }
//
//            // 请求成功获取资金信息
//            JSONArray jsonArray  = jsonObject.getJSONArray("results");
//            JSONObject result = jsonArray.getJSONObject(0);
//            revokeCommitNo = result.getString("entrust_no");
//
//        } catch (RuntimeException e) {
//            throw e;
//        }  catch (Exception e) {
//            if (logger.isErrorEnabled()) {
//                logger.warn("发出撤单时出错", e);
//            }
//        }
//
//        return revokeCommitNo;
//
//    }

    /**
     *   查询资金状况。
     *
     *   @param userId 用户ID
     *   @param accountNo 账户编号
     *
     *   @return 资金状况
     */
    public UserAccountMoneyStatusRec getMoneyStatusRec(
            String userId,
            Long accountNo){

        // 资金状况
        UserAccountMoneyStatusRec moneyStatus = new UserAccountMoneyStatusRec();
        moneyStatus.setAccountId(this.accountId);
        moneyStatus.setAccountNo(accountNo);
        try {

            // 账号登录请求参数
            List<NameValuePair> qparams = new ArrayList<NameValuePair>();

            // 请求功能号
            qparams.add(new BasicNameValuePair("cmd", "cmd_zijin_query"));

            // 委托方式
            qparams.add(new BasicNameValuePair("client_cmd", "cmd_zijin_query"));

            // 营业部编号
            qparams.add(new BasicNameValuePair("table", "data_tb"));

            // 资金账号
            qparams.add(new BasicNameValuePair("zjzh", "010000124552"));
            qparams.add(new BasicNameValuePair("token_id", "5cqi7bcd28c37f9387lm7qhdo7_1"));

            // 资金类别
            qparams.add(new BasicNameValuePair("gdzh", "A790391203"));

            // 8位数随机码
            qparams.add(new BasicNameValuePair("mkcode", "2"));

            // 查询资金状况地址
            URI uri = URIUtils.createURI(
                    SCHEME,
                    SERVER_URL,
                    PORT,
                    "/stock_query.php",
                    null,
                    null);

            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeader("User-Agent",USER_AGENT);
            httpPost.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
            HttpResponse response = httpclient.execute(httpPost, localContext);
            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();
            String html = IOUtils.toString(instream, "UTF-8");
            instream.close();

            System.out.println(html);

            // 解析登录返回的JSON字符串
            JSONObject jsonObject = (JSONObject) JSONUtil.getJSONObjectFromJSONString(html);

            // 请求错误
            String errorNo  = jsonObject.getString("error_no");
            if (!RESPONSE_OK.equals(errorNo)) {
                return null;
            }

            // 请求成功获取资金信息
            JSONArray jsonArray  = jsonObject.getJSONArray("results");
            JSONObject result = jsonArray.getJSONObject(0);

            // 可用余额精确到分
            Double dAvailableMoney = result.getDouble("enable_balance");
            BigDecimal tempAvailableMoney = new BigDecimal(dAvailableMoney);
            Double dTempAvailableMoney = (tempAvailableMoney.setScale(2,
                    BigDecimal.ROUND_HALF_UP).doubleValue()) * 100;
            long availableMoney = dTempAvailableMoney.longValue();
            moneyStatus.setAvailableMoney(availableMoney);

            // 可提取余额精确到分
            double dExtractableMoney = result.getDouble("fetch_balance");
            BigDecimal temp = new BigDecimal(dExtractableMoney);
            Double tempExtractableMoney= (temp.setScale(2,
                    BigDecimal.ROUND_HALF_UP).doubleValue()) * 100;
            long extractableMoney = tempExtractableMoney.longValue();
            moneyStatus.setExtractableMoney(extractableMoney);
            moneyStatus.setUserId(userId);

            // 当前资产精确到分
            double dAssertVal = result.getDouble("assert_val");
            BigDecimal tempAssertVal = new BigDecimal(dAssertVal);
            Double dTempAssertVal = (tempAssertVal.setScale(2,
                    BigDecimal.ROUND_HALF_UP).doubleValue()) * 100;
            long assertVal = dTempAssertVal.longValue();

            // 当前市值
            double dMarketVal = result.getDouble("market_val");
            BigDecimal tempMarketVal = new BigDecimal(dMarketVal);
            Double dTempMarketVal = (tempMarketVal.setScale(2,
                    BigDecimal.ROUND_HALF_UP).doubleValue()) * 100;
            long marketVal = dTempMarketVal.longValue();

            // 资金余额
            moneyStatus.setTotalMoney(assertVal - marketVal);

        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.warn("查询资金状况时出错", e);
            }
        }

        return moneyStatus;

    }
//
//    /**
//     *  查询持仓情况。
//     *
//     *  @param userId 用户ID
//     *  @param accountNo 账户编号
//     *
//     *  @return 持仓信息
//     */
//    public List<UserAccountHoldingStatusRec> getHoldingStatusRecs(
//            String userId,
//            Long accountNo) throws RuntimeException{
//
//        // 当日持仓记录
//        List<UserAccountHoldingStatusRec> holdingRecs = new ArrayList<UserAccountHoldingStatusRec>();
//
//        try {
//
//            // 账号登录请求参数
//            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
//
//            // 请求功能号
//            qparams.add(new BasicNameValuePair("funcNo", FUNC_NO_POSITION));
//
//            // 委托方式
//            qparams.add(new BasicNameValuePair("entrust_way", ENTRUST_WAY));
//
//            // 营业部编号
//            qparams.add(new BasicNameValuePair("branch_no", this.branchNo));
//
//            // 资金账号
//            qparams.add(new BasicNameValuePair("fund_account", this.custCode));
//            qparams.add(new BasicNameValuePair("cust_code", this.custCode));
//
//            // 密码
//            qparams.add(new BasicNameValuePair("password", this.password));
//
//            // 8位数随机码
//            qparams.add(new BasicNameValuePair("uid", getUid()));
//
//            // 备注
//            qparams.add(new BasicNameValuePair("op_station", this.opstation));
//
//            // 查询持仓情况地址
//            URI uri = URIUtils.createURI(
//                    SCHEME,
//                    SERVER_URL,
//                    PORT,
//                    "/servlet/json",
//                    null,
//                    null);
//
//            HttpPost httpPost = new HttpPost(uri);
//            httpPost.setHeader("User-Agent",USER_AGENT);
//            httpPost.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
//            HttpResponse response = httpclient.execute(httpPost, localContext);
//            HttpEntity entity = response.getEntity();
//            InputStream instream = entity.getContent();
//            String html = IOUtils.toString(instream, "UTF-8");
//            instream.close();
//
//            // 解析登录返回的JSON字符串
//            JSONObject jsonObject = (JSONObject) JSONUtil.getJSONObjectFromJSONString(html);
//
//            // 请求错误
//            String errorNo  = jsonObject.getString("error_no");
//            if (!RESPONSE_OK.equals(errorNo)) {
//                return null;
//            }
//
//            // 请求成功获取资金信息
//            JSONArray jsonArray  = jsonObject.getJSONArray("results");
//
//            // 循环取得持仓记录
//            for (int i = 0 ; i < jsonArray.length() ; i++) {
//
//                JSONObject result = jsonArray.getJSONObject(i);
//
//                UserAccountHoldingStatusRec hold = new UserAccountHoldingStatusRec();
//                hold.setAccountId(this.accountId);
//                hold.setAccountNo(accountNo);
//                hold.setUserId(userId);
//
//                // A股的情况下，过滤非法证券代码
//                if (!ValidationUtil.validateNumericString(
//                        StringUtils.trim(result.getString("stock_code")))
//                        && !Constants.STK_CODE_GC001.equals(
//                                PREFIX_SH.concat(StringUtils.trim(result.getString("stock_code"))))) {
//                    continue;
//                }
//
//                // 证券代码需要处理
//                String exchangeType = result.getString("exchange_type");
//                String stkCode = result.getString("stock_code");
//
//                if (EXCHANGE_TYPE_SH.equals(exchangeType)) {
//                    stkCode = PREFIX_SH.concat(stkCode);
//                } else if (EXCHANGE_TYPE_SZ.equals(exchangeType)) {
//                    stkCode = PREFIX_SZ.concat(stkCode);
//                } else {
//                    continue;
//                }
//
//                // 华林证券的逆回购证券代码为888880改为shSHRQ88统一处理
//                if ("sh888880".equals(stkCode)) {
//                    stkCode = Constants.STK_CODE_GC001;
//                }
//
//                // 过滤非股票代码(逆回购代码除外)
//                if (!activityStockList.isStkCode(stkCode)
//                        && !Constants.STK_CODE_GC001.equals(stkCode)) {
//                    continue;
//                }
//
//                // 逆回购持仓代码特别处理：shSHRQ88改写为sh204001
//                if (Constants.STK_CODE_GC001.equals(stkCode)) {
//                    hold.setStkCode(Constants.STK_CODE_GC001_ALIAS);
//                } else {
//                    hold.setStkCode(stkCode);
//                }
//
//                // 获得证券名称
//                String stkName = activityStockList.getStkName(stkCode);
//                if (StringUtil.isEmpty(stkName)
//                        && !Constants.STK_CODE_GC001.equals(stkCode)) {
//                    continue; // 如果股票名称取不出来则说明已经退市，过滤掉此类股票
//                }
//                hold.setStkName(stkName);
//
//                // 可用数量：华林证券逆回购的情况，数量乘100（特别处理）
//                if (Constants.STK_CODE_GC001_ALIAS.equals(hold.getStkCode())) {
//                    hold.setAvailableAmount(result.getLong("enable_amount") * 100);
//                } else {
//                    hold.setAvailableAmount(result.getLong("enable_amount"));
//                }
//
//                // 持仓数量：华林证券逆回购的情况，数量乘100（特别处理）
//                if (Constants.STK_CODE_GC001_ALIAS.equals(hold.getStkCode())) {
//                    hold.setTotalAmount(result.getLong("cost_amount") * 100);
//                } else {
//                    hold.setTotalAmount(result.getLong("cost_amount"));
//                }
//
//                // 股票持仓成本精确到分, 基金持仓成本精确到厘
//                Double costPrice =  result.getDouble("cost_price");
//
//                // 证券类型
//                String stkType = this.activityStockList.getStkType(hold.getStkCode());
//                BigDecimal decimal = new BigDecimal(costPrice.doubleValue());
//                if (Constants.STK_TYPE_A_STOCK.equals(stkType)) {
//                    costPrice = (decimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) * 100;
//                } else if (Constants.STK_TYPE_CEF.equals(stkType)
//                        || Constants.STK_TYPE_OEF.equals(stkType)
//                        || Constants.STK_TYPE_ETF.equals(stkType)
//                        || Constants.STK_TYPE_LOF.equals(stkType)
//                        || Constants.STK_TYPE_BOND.equals(stkType)) {
//                    costPrice = (decimal.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()) * 1000;
//                }
//                hold.setCostPrice(costPrice.intValue());
//                hold.setProfitAmount(Math.round(result.getDouble("float_yk") * 100));
//                holdingRecs.add(hold);
//
//            }
//
//        } catch (Exception e) {
//            if (logger.isErrorEnabled()) {
//                logger.warn("查询持仓情况时出错", e);
//            }
//        }
//        return holdingRecs;
//
//    }
//
//    /**
//     *  查询当日委托记录。
//     *
//     *  @param userId 用户ID
//     *  @param accountNo 账户编号
//     *
//     *  @return 委托明细
//     */
//    public List<UserAccountCommissionRec> getTodayCommissionRecs(
//            String userId,
//            Long accountNo) throws RuntimeException{
//
//        // 当日委托记录
//        List<UserAccountCommissionRec> commissionRecs = new ArrayList<UserAccountCommissionRec>();
//
//        try {
//
//            // 账号登录请求参数
//            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
//
//            // 请求功能号
//            qparams.add(new BasicNameValuePair("funcNo", FUNC_NO_COMMISSION));
//
//            // 委托方式
//            qparams.add(new BasicNameValuePair("entrust_way", ENTRUST_WAY));
//
//            // 营业部编号
//            qparams.add(new BasicNameValuePair("branch_no", this.branchNo));
//
//            // 资金账号
//            qparams.add(new BasicNameValuePair("fund_account", this.custCode));
//            qparams.add(new BasicNameValuePair("cust_code", this.custCode));
//
//            // 密码
//            qparams.add(new BasicNameValuePair("password", this.password));
//
//            // 8位数随机码
//            qparams.add(new BasicNameValuePair("uid", getUid()));
//
//            // 备注
//            qparams.add(new BasicNameValuePair("op_station", this.opstation));
//
//            // 查询委托记录地址
//            URI uri = URIUtils.createURI(
//                    SCHEME,
//                    SERVER_URL,
//                    PORT,
//                    "/servlet/json",
//                    null,
//                    null);
//
//            HttpPost httpPost = new HttpPost(uri);
//            httpPost.setHeader("User-Agent",USER_AGENT);
//            httpPost.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
//            HttpResponse response = httpclient.execute(httpPost, localContext);
//            HttpEntity entity = response.getEntity();
//            InputStream instream = entity.getContent();
//            String html = IOUtils.toString(instream, "UTF-8");
//            instream.close();
//
//            // 解析登录返回的JSON字符串
//            JSONObject jsonObject = (JSONObject) JSONUtil.getJSONObjectFromJSONString(html);
//
//            // 请求错误
//            String errorNo  = jsonObject.getString("error_no");
//            if (!RESPONSE_OK.equals(errorNo)) {
//                return null;
//            }
//
//            // 请求成功获取资金信息
//            JSONArray jsonArray  = jsonObject.getJSONArray("results");
//
//            // 循环取得委托记录
//            for (int i = 0 ; i < jsonArray.length() ; i++) {
//
//                JSONObject result = jsonArray.getJSONObject(i);
//
//                UserAccountCommissionRec commission = new UserAccountCommissionRec();
//                commission.setAccountId(this.accountId);
//                commission.setUserId(userId);
//                commission.setAccountNo(accountNo);
//                commission.setCommitAmount(result.getLong("entrust_amount"));
//                commission.setTradedAmount(result.getLong("business_amount"));
//                commission.setCommitNo(result.getString("entrust_no"));
//
//                // 证券代码需要处理
//                String exchangeType = result.getString("exchange_type");
//                String stkCode = result.getString("stock_code");
//
//                // 新股申购时委托记录里的证券代码是申购代码需要做一下变换
//                if (stkCode.startsWith("780")) {
//                    stkCode = stkCode.replaceAll("780", "601");
//                } else if (stkCode.startsWith("732")) {
//                    stkCode = stkCode.replaceAll("732", "603");
//                } else if (stkCode.startsWith("730")) {
//                    stkCode = stkCode.replaceAll("730", "600");
//                }
//
//                if (EXCHANGE_TYPE_SH.equals(exchangeType)) {
//                    stkCode = PREFIX_SH.concat(stkCode);
//                } else if (EXCHANGE_TYPE_SZ.equals(exchangeType)) {
//                    stkCode = PREFIX_SZ.concat(stkCode);
//                } else {
//                    continue;
//                }
//
//                // 过滤基金等非股票代码(逆回购代码除外)
//                if (!activityStockList.isStkCode(stkCode)
//                        && !Constants.STK_CODE_GC001_ALIAS.equals(stkCode)) {
//                    continue;
//                }
//
//                commission.setStkCode(stkCode);
//                commission.setStkName(activityStockList.getStkName(stkCode));
//
//                // 废单 10
//                // 已成 9
//                // 未成交 3
//                // 已撤 A
//                String entrustState = result.getString("entrust_state");
//                if ("10".equals(entrustState)) {
//                    commission.setCommitStatus(AutoTradingConstants.C039_IS_WASTED);
//                } else if ("9".equals(entrustState)) {
//                    commission.setCommitStatus(AutoTradingConstants.C039_ALL_TRADED);
//                } else if ("3".equals(entrustState)) {
//                    commission.setCommitStatus(AutoTradingConstants.C039_COMMITED);
//                } else if ("7".equals(entrustState)) {
//                    commission.setCommitStatus(AutoTradingConstants.C039_ALL_REVOKED);
//
//                // 未知的先原样输出
//                } else {
//                    commission.setCommitStatus(entrustState);
//                }
//
//                // 股票委托价格精确到分, 基金委托价格精确到厘
//                Double commitPrice =  result.getDouble("entrust_price");
//
//                // 证券类型
//                String stkType = this.activityStockList.getStkType(stkCode);
//                BigDecimal decimal = new BigDecimal(commitPrice.doubleValue());
//                if (Constants.STK_TYPE_A_STOCK.equals(stkType)) {
//                    commitPrice = (decimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) * 100;
//                } else if (Constants.STK_TYPE_CEF.equals(stkType)
//                        || Constants.STK_TYPE_OEF.equals(stkType)
//                        || Constants.STK_TYPE_ETF.equals(stkType)
//                        || Constants.STK_TYPE_LOF.equals(stkType)
//                        || Constants.STK_TYPE_BOND.equals(stkType)) {
//                    commitPrice = (decimal.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()) * 1000;
//                } else {
//                    commitPrice = (decimal.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()) * 1000;
//                }
//                commission.setCommitPrice(commitPrice.intValue());
//
//                String recDate = result.getString("entrust_date");
//                recDate = DateUtil.getFormatDateStr(recDate, "yyyy-MM-dd", "yyyyMMdd");
//                String recTime = result.getString("entrust_time");
//                recTime = DateUtil.getFormatDateStr(recTime, "HH:mm:ss", "HHmmss");
//                commission.setCommitTime(recDate.concat(recTime));
//
//                String entrustBs = result.getString("entrust_bs");
//                if (ENTRUST_BUY.equals(entrustBs)) {
//                    commission.setTransType(AutoTradingConstants.C038_BUY);
//                } else if (ENTRUST_SELL.equals(entrustBs)) {
//                    commission.setTransType(AutoTradingConstants.C038_SELL);
//                } else {
//                    commission.setTransType(AutoTradingConstants.C038_BUY);
//                }
//
//                commissionRecs.add(commission);
//
//            }
//
//        } catch (Exception e) {
//            if (logger.isErrorEnabled()) {
//                logger.warn("查询委托记录时出错", e);
//            }
//        }
//
//        return commissionRecs;
//
//    }
//
//    /**
//     *  查询当日成交明细。
//     *
//     *  @param userId 用户ID
//     *  @param accountNo 账户编号
//     *
//     *  @return 当日成交明细
//     */
//    public List<UserAccountTradedRec> getTodayTradedRecs(
//            String userId,
//            Long accountNo) throws RuntimeException {
//
//        // 当日成交记录
//        List<UserAccountTradedRec> tradedRecs = new ArrayList<UserAccountTradedRec>();
//
//        try {
//
//            // 账号登录请求参数
//            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
//
//            // 请求功能号
//            qparams.add(new BasicNameValuePair("funcNo", FUNC_NO_TODAY_TRADED));
//
//            // 委托方式
//            qparams.add(new BasicNameValuePair("entrust_way", ENTRUST_WAY));
//
//            // 营业部编号
//            qparams.add(new BasicNameValuePair("branch_no", this.branchNo));
//
//            // 资金账号
//            qparams.add(new BasicNameValuePair("fund_account", this.custCode));
//            qparams.add(new BasicNameValuePair("cust_code", this.custCode));
//
//            // 密码
//            qparams.add(new BasicNameValuePair("password", this.password));
//
//            // 8位数随机码
//            qparams.add(new BasicNameValuePair("uid", getUid()));
//
//            // 备注
//            qparams.add(new BasicNameValuePair("op_station", this.opstation));
//
//            // 查询当日成交明细地址
//            URI uri = URIUtils.createURI(
//                    SCHEME,
//                    SERVER_URL,
//                    PORT,
//                    "/servlet/json",
//                    null,
//                    null);
//
//            HttpPost httpPost = new HttpPost(uri);
//            httpPost.setHeader("User-Agent",USER_AGENT);
//            httpPost.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
//            HttpResponse response = httpclient.execute(httpPost, localContext);
//            HttpEntity entity = response.getEntity();
//            InputStream instream = entity.getContent();
//            String html = IOUtils.toString(instream, "UTF-8");
//            instream.close();
//
//            // 解析登录返回的JSON字符串
//            JSONObject jsonObject = (JSONObject) JSONUtil.getJSONObjectFromJSONString(html);
//
//            // 请求错误
//            String errorNo  = jsonObject.getString("error_no");
//            if (!RESPONSE_OK.equals(errorNo)) {
//                return null;
//            }
//
//            // 请求成功获取资金信息
//            JSONArray jsonArray  = jsonObject.getJSONArray("results");
//
//            // 循环取得交易记录
//            for (int i = 0 ; i < jsonArray.length() ; i++) {
//
//                JSONObject result = jsonArray.getJSONObject(i);
//
//                // 过滤掉成交金额为0的记录
//                String businessBalance = result.getString("business_balance");
//                if ("0.00".equals(businessBalance)) {
//                    continue;
//                }
//
//                UserAccountTradedRec traded = new UserAccountTradedRec();
//                traded.setAccountId(this.accountId);
//                traded.setAccountNo(accountNo);
//                traded.setUserId(userId);
//                traded.setCommitNo(result.getString("entrust_no"));
//
//                //TODO 股票交易不返回成交编号只能使用交易金额临时代替
//                traded.setContractNo(businessBalance.replace(".", ""));
//
//                String recDate = result.getString("business_date");
//                recDate = DateUtil.getFormatDateStr(recDate, "yyyy-MM-dd", "yyyyMMdd");
//                String recTime = result.getString("business_time");
//                recTime = DateUtil.getFormatDateStr(recTime, "HH:mm:ss", "HHmmss");
//                traded.setTradedTime(recDate.concat(recTime));
//
//                // 证券代码需要处理
//                String exchangeType = result.getString("exchange_type");
//                String stkCode = result.getString("stock_code");
//
//                if (EXCHANGE_TYPE_SH.equals(exchangeType)) {
//                    stkCode = PREFIX_SH.concat(stkCode);
//                } else if (EXCHANGE_TYPE_SZ.equals(exchangeType)) {
//                    stkCode = PREFIX_SZ.concat(stkCode);
//                } else {
//                    continue;
//                }
//
//                // 过滤基金等非股票代码(逆回购代码除外)
//                if (!activityStockList.isStkCode(stkCode)
//                        && !Constants.STK_CODE_GC001_ALIAS.equals(stkCode)) {
//                    continue;
//                }
//
//                traded.setStkCode(stkCode);
//                traded.setStkName(activityStockList.getStkName(stkCode));
//
//                String entrustBs = result.getString("entrust_bs");
//                if (ENTRUST_BUY.equals(entrustBs)) {
//                    traded.setTransType(AutoTradingConstants.C038_BUY);
//                } else if (ENTRUST_SELL.equals(entrustBs)) {
//                    traded.setTransType(AutoTradingConstants.C038_SELL);
//                } else {
//                    continue;
//                }
//
//                traded.setTradedAmount(result.getLong("business_amount"));
//
//                // 股票成交价格精确到分, 基金成交价格精确到厘
//                Double tradedPrice = result.getDouble("business_price");
//
//                // 证券类型
//                String stkType = this.activityStockList.getStkType(stkCode);
//                BigDecimal decimal = new BigDecimal(tradedPrice.doubleValue());
//                if (Constants.STK_TYPE_A_STOCK.equals(stkType)) {
//                    tradedPrice = (decimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) * 100;
//                } else if (Constants.STK_TYPE_CEF.equals(stkType)
//                        || Constants.STK_TYPE_OEF.equals(stkType)
//                        || Constants.STK_TYPE_ETF.equals(stkType)
//                        || Constants.STK_TYPE_LOF.equals(stkType)
//                        || Constants.STK_TYPE_BOND.equals(stkType)) {
//                    tradedPrice = (decimal.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()) * 1000;
//                } else {
//                    tradedPrice = (decimal.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()) * 1000;
//                }
//
//                traded.setTradedPrice(tradedPrice.intValue());
//                tradedRecs.add(traded);
//
//            }
//
//        } catch (Exception e) {
//            if (logger.isErrorEnabled()) {
//                logger.warn("查询成交记录时出错", e);
//            }
//        }
//        return tradedRecs;
//
//    }
//
//    /**
//     * 取得沪新股额。
//     *
//     * @param userId    用户ID
//     * @param accountNo 账户编号
//     *
//     * @return 最新的沪新股额
//     */
//    public Long getShxged(
//            String userId,
//            Long accountNo) throws RuntimeException {
//
//        try {
//
//            // 账号登录请求参数
//            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
//
//            // 请求功能号
//            qparams.add(new BasicNameValuePair("funcNo", "301519"));
//
//            // 委托方式
//            qparams.add(new BasicNameValuePair("entrust_way", ENTRUST_WAY));
//
//            // 营业部编号
//            qparams.add(new BasicNameValuePair("branch_no", this.branchNo));
//
//            // 资金账号
//            qparams.add(new BasicNameValuePair("fund_account", this.custCode));
//            qparams.add(new BasicNameValuePair("cust_code", this.custCode));
//
//            // 密码
//            qparams.add(new BasicNameValuePair("password", this.password));
//
//            // 市场类别
//            qparams.add(new BasicNameValuePair("exchange_type", EXCHANGE_TYPE_SH));
//
//            // 8位数随机码
//            qparams.add(new BasicNameValuePair("uid", getUid()));
//
//            // 备注
//            qparams.add(new BasicNameValuePair("op_station", this.opstation));
//
//            // 查询资金状况地址
//            URI uri = URIUtils.createURI(
//                    SCHEME,
//                    SERVER_URL,
//                    PORT,
//                    "servlet/json",
//                    null,
//                    null);
//
//            HttpPost httpPost = new HttpPost(uri);
//            httpPost.setHeader("User-Agent",USER_AGENT);
//            httpPost.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
//            HttpResponse response = httpclient.execute(httpPost, localContext);
//            HttpEntity entity = response.getEntity();
//            InputStream instream = entity.getContent();
//            String html = IOUtils.toString(instream, "UTF-8");
//            instream.close();
//
//            // 解析登录返回的JSON字符串
//            JSONObject jsonObject = (JSONObject) JSONUtil.getJSONObjectFromJSONString(html);
//
//            // 请求错误
//            String errorNo  = jsonObject.getString("error_no");
//            if (!RESPONSE_OK.equals(errorNo)) {
//                return null;
//            }
//
//            // 请求成功获取资金信息
//            JSONArray jsonArray  = jsonObject.getJSONArray("results");
//            JSONObject result = jsonArray.getJSONObject(0);
//
//            // 沪新股额
//            Long hxged = result.getLong("enable_amount");
//
//            return hxged;
//
//        } catch (Exception e) {
//            if (logger.isErrorEnabled()) {
//                logger.warn("查询资金状况时出错", e);
//            }
//        }
//        return 0L;
//
//    }
//
//    /**
//     * 取得深新股额。
//     *
//     * @param userId    用户ID
//     * @param accountNo 账户编号
//     *
//     * @return 最新的沪新股额
//     */
//    public Long getSzxged(
//            String userId,
//            Long accountNo) throws RuntimeException {
//
//        try {
//
//            // 账号登录请求参数
//            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
//
//            // 请求功能号
//            qparams.add(new BasicNameValuePair("funcNo", "301519"));
//
//            // 委托方式
//            qparams.add(new BasicNameValuePair("entrust_way", ENTRUST_WAY));
//
//            // 营业部编号
//            qparams.add(new BasicNameValuePair("branch_no", this.branchNo));
//
//            // 资金账号
//            qparams.add(new BasicNameValuePair("fund_account", this.custCode));
//            qparams.add(new BasicNameValuePair("cust_code", this.custCode));
//
//            // 密码
//            qparams.add(new BasicNameValuePair("password", this.password));
//
//            // 市场类别
//            qparams.add(new BasicNameValuePair("exchange_type", EXCHANGE_TYPE_SZ));
//
//            // 8位数随机码
//            qparams.add(new BasicNameValuePair("uid", getUid()));
//
//            // 备注
//            qparams.add(new BasicNameValuePair("op_station", this.opstation));
//
//            // 查询资金状况地址
//            URI uri = URIUtils.createURI(
//                    SCHEME,
//                    SERVER_URL,
//                    PORT,
//                    "servlet/json",
//                    null,
//                    null);
//
//            HttpPost httpPost = new HttpPost(uri);
//            httpPost.setHeader("User-Agent",USER_AGENT);
//            httpPost.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
//            HttpResponse response = httpclient.execute(httpPost, localContext);
//            HttpEntity entity = response.getEntity();
//            InputStream instream = entity.getContent();
//            String html = IOUtils.toString(instream, "UTF-8");
//            instream.close();
//
//            // 解析登录返回的JSON字符串
//            JSONObject jsonObject = (JSONObject) JSONUtil.getJSONObjectFromJSONString(html);
//
//            // 请求错误
//            String errorNo  = jsonObject.getString("error_no");
//            if (!RESPONSE_OK.equals(errorNo)) {
//                return null;
//            }
//
//            // 请求成功获取资金信息
//            JSONArray jsonArray  = jsonObject.getJSONArray("results");
//            if (jsonArray.length() == 0) {
//                return 0L;
//            }
//
//            JSONObject result = jsonArray.getJSONObject(0);
//
//            // 深新股额
//            Long sxged = result.getLong("enable_amount");
//
//            return sxged;
//
//        } catch (Exception e) {
//            if (logger.isErrorEnabled()) {
//                logger.warn("查询资金状况时出错", e);
//            }
//        }
//        return 0L;
//
//    }
//
//    /**
//     *  申购新股。
//     *
//     *  @param userId 用户ID
//     *  @param accountNo 账户编号
//     *  @param stkCode 证券代码
//     *  @param price 委托价格
//     *
//     *  @return String 委托编号
//     */
//    public void applNewStk(
//            String userId,
//            Long accountNo,
//            String stkCode,
//            Integer price) {
//
//        // 取得此新股最大申购数量
//        Long maxAmount = getStockMaxAmount(stkCode);
//
//        // 顶格申购
//        if (maxAmount != null && maxAmount > 0) {
//            buyInNormal(userId, accountNo, stkCode, maxAmount, price);
//        }
//        return;
//
//    }
//
//    /**
//     * 判断资金账户是否已接入。
//     *
//     * @param userId    用户ID
//     * @param accountNo 账户编号
//     *
//     * @return 是否已登入：true - 已登入，false - 未登入
//     */
//    public boolean isLogin(
//            String userId,
//            Long accountNo) throws RuntimeException {
//
//        if (accountStatus == AutoTradingConstants.ACCOUNT_LOGIN) {
//            return true;
//        } else {
//            return false;
//        }
//
//    }

//    /**
//     *  账户接入（连接、登录）。
//     *
//     *  @param userId 用户ID
//     *  @param accountNo 账户编号
//     *  @param authInfo 登录信息
//     */
//    public void connect(
//            String userId,
//            Long accountNo,
//            Map authInfo) throws RuntimeException {
//
//        // 取得资金账号密码
//        String account = String.valueOf(authInfo.get("loginId"));
//        String password = String.valueOf(authInfo.get("transPassword"));
//        String accoutId = String.valueOf(authInfo.get("accountId"));
//        this.loginId = account;
//        this.password = password;
//        this.accountId = accoutId;
//        this.stop = false;
//
//        // 生成备注信息
//        createOpstation(authInfo);
//
//        // 取得当前登录状态
//        boolean isLogin = isLogin(userId, accountNo);
//        if (isLogin) {
//            return;
//        }
//
//        // 登陆前预处理;
//        preLogin();
//
//        // 第一次请求登录
//        String result = login();
//
//        // 重新登录不能操作五次
//        int totalCount = 1;
//
//        // 如果是因为验证码识别错误则循环登录
//        while(RESPONSE_CODE_ERROR.equals(result)) {
//
//            if (totalCount > 5) {
//                totalCount = 0;
//                break;
//            }
//
//            // 休眠2秒
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                break;
//            }
//
//            if (logger.isInfoEnabled()) {
//                logger.info("第" + totalCount + "次重新登录");
//            }
//
//            result = login();
//            totalCount ++;
//        }
//
//        // 登录成功
//        if (RESPONSE_OK.equals(result)) {
//
//            if (logger.isInfoEnabled()) {
//                logger.info("用户:" + userId + "的资金帐户：" + this.loginId + "成功登录华林证券。");
//            }
//
//            // 账户状态已登录
//            this.accountStatus = AutoTradingConstants.ACCOUNT_LOGIN;
//
//            // 启动连接状态监视线程
//            startMThread(userId, accountNo);
//
//        // 资金账户密码错误
//        } else if (RESPONSE_PWD_ERROR.equals(result)) {
//
//            // 账户状态未登录
//            this.accountStatus = AutoTradingConstants.ACCOUNT_LOGOUT;
//
//            throw new RuntimeException("R0303.E001", "用户名或密码错误。");
//
//        // 其它错误
//        } else {
//
//            // 账户状态未登录
//            this.accountStatus = AutoTradingConstants.ACCOUNT_LOGOUT;
//
//            throw new RuntimeException("R0303.E003", "与证券公司连接错误。");
//
//        }
//
//        return;
//
//    }
//
//    /**
//     *  账户断开（退出登录）。
//     *
//     *  @param userId 用户ID
//     *  @param accountNo 账户编号
//     *  @param account 资金账号
//     */
//    public void disconnect(
//            String userId,
//            Long accountNo,
//            String account) {
//
//        if (logger.isInfoEnabled()) {
//            logger.info("用户:" + userId + "的资金帐户：" + this.loginId + "登出华林证券。");
//        }
//
//        // 设置账户登录状态
//        this.accountStatus = AutoTradingConstants.ACCOUNT_LOGOUT;
//
//        // 停止监测账户状态线程
//        this.stop();
//
//        return;
//
//    }
//
//    /**
//     * 停止监测账户状态线程。
//     */
//    private void stop() {
//
//        // 设置线程退出标志
//        stop = true;
//
//        // 如果线程正在休眠，先尝试中止。
//        if (monitorThread != null && monitorThread.isAlive()) {
//            monitorThread.interrupt();
//            monitorThread = null;
//        }
//        return;
//
//    }

    /**
     * 账户登录。
     * 
     * @return 错误代码
     */
    public String login() throws RuntimeException {

        
        String errorNo = null; // 华林证券返回的登录错误代码"0"表示成功
        try {

            // 账号登录请求参数
            List<NameValuePair> qparams = new ArrayList<NameValuePair>();

            // 请求功能号
            qparams.add(new BasicNameValuePair("yyb_name", "1"));

            qparams.add(new BasicNameValuePair("account_type", "0"));

            qparams.add(new BasicNameValuePair("account", "010000124552"));

            qparams.add(new BasicNameValuePair("crpttype", "1"));

            qparams.add(new BasicNameValuePair("tradekey", "960118"));

            qparams.add(new BasicNameValuePair("commkey", "369963"));
     
            qparams.add(new BasicNameValuePair("token", "icmuv2hmdk2te34ko7g5l14j62"));

            qparams.add(new BasicNameValuePair("embeded", ""));

            // 登录请求地址
            URI uri = URIUtils.createURI(
                    SCHEME,
                    SERVER_URL,
                    PORT,
                    "/login.php?do=do_verify",
                    null, 
                    null);

            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeader("User-Agent", USER_AGENT);
            httpPost.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
            HttpResponse response = httpclient.execute(httpPost, localContext);
            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();
            String html = IOUtils.toString(instream, "UTF-8"); // html代码
            instream.close();
            
            System.out.println(html);

            uri = URIUtils.createURI(
                    SCHEME,
                    SERVER_URL,
                    -1,
                    "/trade.php?do=sdxd&type=buy",
                    null,
                    null);

            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader( "User-Agent", USER_AGENT);
            response = httpclient.execute(httpGet, localContext);
            entity = response.getEntity();
            instream = entity.getContent();
            html = IOUtils.toString(instream,  "gb2312");
            instream.close();
            
            System.out.println(html);

            uri = URIUtils.createURI(
                    SCHEME,
                    SERVER_URL,
                    -1,
                    "/trade.php?do=sdxd&type=sell",
                    null,
                    null);

            httpGet = new HttpGet(uri);
            httpGet.setHeader( "User-Agent", USER_AGENT);
            response = httpclient.execute(httpGet, localContext);
            entity = response.getEntity();
            instream = entity.getContent();
            html = IOUtils.toString(instream,  "gb2312");
            instream.close();
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println(html);

            qparams = new ArrayList<NameValuePair>();

            uri = URIUtils.createURI(
                    SCHEME,
                    SERVER_URL,
                    PORT,
                    "/readcfg.php",
                    null, 
                    null);

            httpPost = new HttpPost(uri);
            httpPost.setHeader("User-Agent", USER_AGENT);
            httpPost.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
            response = httpclient.execute(httpPost, localContext);
            entity = response.getEntity();
            instream = entity.getContent();
            html = IOUtils.toString(instream, "UTF-8"); // html代码
            instream.close();
            
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println(html);
            
            
            uri = URIUtils.createURI(
                    SCHEME,
                    SERVER_URL,
                    -1,
                    "/login.php?do=do_login&auto=1&",
                    null,
                    null);

            httpGet = new HttpGet(uri);
            httpGet.setHeader( "User-Agent", USER_AGENT);
            response = httpclient.execute(httpGet, localContext);
            entity = response.getEntity();
            instream = entity.getContent();
            html = IOUtils.toString(instream,  "gb2312");
            instream.close();
            
            System.out.println(html);

            

        } catch (Exception e) {

            if (logger.isErrorEnabled()) {
                logger.warn("账户登录的时候发生错误", e);
            }

            throw new RuntimeException(e);

        }

        return errorNo;

    }
//
//    /**
//     * 判断账户是否已接入。
//     *
//     * @param userId    用户ID
//     * @param accountNo 账户编号
//     *
//     * @return 是否已登入：true - 已接入，false - 未接入
//     */
//    public boolean isConnected(String userId, Long accountNo) {
//        return isLogin(userId, accountNo);
//    }
//
//    /**
//     * 取得验证码。
//     *
//     * @param  mobile   电话号码
//     * @return 验证码。
//     */
//    public Map<String, String> getCheckCode(String mobile) {
//
//        // 返回结果
//        Map<String, String> result = new HashMap<String, String>();
//
//        try {
//
//            // 账号登录请求参数
//            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
//
//            // 取得当前时间
//            Date date = new Date();
//
//            // 请求功能号
//            qparams.add(new BasicNameValuePair("action", "41092"));
//
//            // 随机码
//            qparams.add(new BasicNameValuePair("reqno", String.valueOf(date.getTime())));
//
//            // 服务server
//            qparams.add(new BasicNameValuePair("intacttoserver", "@ClZvbHVtZUluZm8JAAAANDdBRi1DOTZE"));
//
//            // TOKEN
//            qparams.add(new BasicNameValuePair("token", "auIUS452930549"));
//
//            // 手机号
//            qparams.add(new BasicNameValuePair("MobileCode", mobile));
//
//            // 编号
//            qparams.add(new BasicNameValuePair("newindex", "1"));
//
//            // 服务形式H5
//            qparams.add(new BasicNameValuePair("tfrom", "h5"));
//
//            // 机型
//            qparams.add(new BasicNameValuePair("cfrom", "pc"));
//
//            // 登录请求地址
//            URI uri = URIUtils.createURI(
//                    SCHEME,
//                    SERVER_URL,
//                    PORT,
//                    "reqxml?",
//                    null,
//                    null);
//
//            HttpPost httpPost = new HttpPost(uri);
//            httpPost.setHeader("User-Agent", USER_AGENT);
//            httpPost.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
//            HttpResponse response = httpclient.execute(httpPost, localContext);
//            HttpEntity entity = response.getEntity();
//            InputStream instream = entity.getContent();
//            String html = IOUtils.toString(instream, "UTF-8"); // html代码
//            instream.close();
//
//            // 解析登录返回的JSON字符串
//            JSONObject jsonObject = (JSONObject) JSONUtil.getJSONObjectFromJSONString(html);
//
//            // 取得错误编码
//            String errorNo  = jsonObject.getString("ERRORNO");
//
//            // 取得验证码和TOKEN
//            if (RESPONSE_OK.equals(errorNo)) {
//                String checkCode = jsonObject.getString("CHECKCODE");
//                String checkToken = jsonObject.getString("CHECKTOKEN");
//                result.put("checkCode", checkCode);
//                result.put("checkToken", checkToken);
//            }
//
//        } catch (Exception e) {
//
//            if (logger.isErrorEnabled()) {
//                logger.warn("取得验证码发生错误", e);
//            }
//
//            throw new RuntimeException(e);
//
//        }
//
//        return result;
//
//    }
    
    
    /**
     * 取得登录时的验证码图片。
     *
     * @param session 认证会话
     * @return 始终为null。该证券公司由于有bug，不需要手动提供。
     */
    public byte[] getCaptchaImage() {

        // 验证码字节流
        byte[] imgData = null;
        
        // 构造验证码请求URL
        StringBuffer captchaImageUrl = new StringBuffer();
        captchaImageUrl.append("/servlet/NewImage?r=");
        captchaImageUrl.append(MOBILE_KEY);
        captchaImageUrl.append("&mobileKey=");
        captchaImageUrl.append(MOBILE_KEY);

        URI uri = null;
        try {
            
            // 获取验证码
            uri = URIUtils.createURI(
                    SCHEME,
                    this.SERVER_URL,
                    PORT,
                    captchaImageUrl.toString(),
                    null,
                    null);
            
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("User-Agent",USER_AGENT);
            HttpResponse response = httpclient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            InputStream imgIn = entity.getContent();
            imgData = IOUtils.toByteArray(imgIn);

        } catch (Exception e) {

            if(logger.isErrorEnabled()){
                logger.error("华林证券HTTP接口取得验证码失败", e);
            }

            throw new RuntimeException(e);

        }

        return imgData;

    }

    /**
     * 登录前的预处理(向券商发送渠道号)。
     */
    private void preLogin() {

        // 构造验证码请求URL
        StringBuffer captchaImageUrl = new StringBuffer();
        captchaImageUrl.append("/m1/trade/index.html#!/account/login.html?source=");
        captchaImageUrl.append(LY);
        captchaImageUrl.append("&ypxlh=");
        captchaImageUrl.append(this.imei);
        captchaImageUrl.append("&hardIdentifier=");
        captchaImageUrl.append(this.phoneModel);
        captchaImageUrl.append("&mac=");
        captchaImageUrl.append(this.mac);

        URI uri = null;
        try {

            // 获取验证码
            uri = URIUtils.createURI(
                    SCHEME,
                    this.SERVER_URL,
                    PORT,
                    captchaImageUrl.toString(),
                    null,
                    null);
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("User-Agent",USER_AGENT);
            HttpResponse response = httpclient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();
            instream.close();

        } catch (Exception e) {

            if(logger.isErrorEnabled()){
                logger.error("华林证券登录前预处理失败", e);
            }

            throw new RuntimeException(e);

        }

    }
//
//    /**
//     *  取得新股最大申购数量。
//     *
//     *  @param stkCode 证券代码
//     *
//     *  @return Long 最大申购数量
//     */
//    public Long getStockMaxAmount(String stkCode) {
//
//        Long maxAmount = 0L; // 委托编号
//
//        try {
//
//            // 账号登录请求参数
//            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
//
//            // 请求功能号
//            qparams.add(new BasicNameValuePair("funcNo", FUNC_NO_NEW_STOCK));
//
//            // 委托方式
//            qparams.add(new BasicNameValuePair("entrust_way", ENTRUST_WAY));
//
//            // 营业部编号
//            qparams.add(new BasicNameValuePair("branch_no", this.branchNo));
//
//            // 资金账号
//            qparams.add(new BasicNameValuePair("fund_account", this.loginId));
//            qparams.add(new BasicNameValuePair("cust_code", this.custCode));
//
//            // 密码
//            qparams.add(new BasicNameValuePair("password", this.password));
//
//            // 买卖方向
//            qparams.add(new BasicNameValuePair("entrust_bs", ENTRUST_BUY));
//
//            // 证券代码
//            qparams.add(new BasicNameValuePair("stock_code", StringUtils.getNoPrefixCode(stkCode)));
//
//            // 8位数随机码
//            qparams.add(new BasicNameValuePair("uid", getUid()));
//
//            // 委托请求地址
//            URI uri = URIUtils.createURI(
//                    SCHEME,
//                    SERVER_URL,
//                    PORT,
//                    "/servlet/json",
//                    null,
//                    null);
//
//            HttpPost httpPost = new HttpPost(uri);
//            httpPost.setHeader("User-Agent",USER_AGENT);
//            httpPost.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
//            HttpResponse response = httpclient.execute(httpPost, localContext);
//            HttpEntity entity = response.getEntity();
//            InputStream instream = entity.getContent();
//            String html = IOUtils.toString(instream, "UTF-8");
//            instream.close();
//
//            // 解析登录返回的JSON字符串
//            JSONObject jsonObject = (JSONObject) JSONUtil.getJSONObjectFromJSONString(html);
//
//            // 请求错误
//            String errorNo  = jsonObject.getString("error_no");
//            if (RESPONSE_OK.equals(errorNo)) {
//
//                // 请求成功获取资金信息
//                JSONArray jsonArray  = jsonObject.getJSONArray("results");
//                JSONObject result = jsonArray.getJSONObject(0);
//                maxAmount = result.getLong("stock_max_amount");
//
//            }
//
//        } catch (Exception e) {
//
//            if (logger.isErrorEnabled()) {
//                logger.warn("查询新股最大申购额度时出错", e);
//            }
//
//        }
//
//        return maxAmount;
//
//    }
//
//    /**
//     * 创建备注信息。
//     */
//    private void createOpstation(Map authInfo) {
//
//        String mobile = String.valueOf(authInfo.get("mobile"));; // 手机号(必传)
//        if (StringUtil.isBlank(mobile)) {
//            mobile = getMobile();
//        }
//
//        // 生成备注信息
//        StringBuilder strBuilder = new StringBuilder();
//        strBuilder.append("0");
//        strBuilder.append("|");
//        strBuilder.append("");
//        strBuilder.append("|");
//        strBuilder.append(this.mac);
//        strBuilder.append("|");
//        strBuilder.append(this.imei);
//        strBuilder.append("|");
//        strBuilder.append("");
//        strBuilder.append("|");
//        strBuilder.append(mobile);
//        strBuilder.append("|");
//        strBuilder.append(this.phoneModel);
//        strBuilder.append("|");
//        strBuilder.append("迅动平台");
//        strBuilder.append("|");
//        strBuilder.append("");
//        this.opstation = strBuilder.toString();
//        return;
//
//    }
//
//    /**
//     * 手机号随机生成器。
//     */
//    private static String getMobile() {
//
//        String[] telPrefix="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
//        int index=getNum(0, telPrefix.length-1);
//        String first=telPrefix[index];
//        String second=String.valueOf(getNum(1, 888)+10000).substring(1);
//        String third=String.valueOf(getNum(1, 9100)+10000).substring(1);
//        return first+second+third;
//    }
//
//    /**
//     * 随机数生成器。
//     * @param start 起始位
//     * @param end 末位
//     * @return 随机数
//     */
//    public static int getNum(int start,int end) {
//        return (int)(Math.random()*(end-start+1)+start);
//    }
//
//    /**
//     * 阿里云市场图片验证码识别(备用)。
//     *
//     * @param imgIn 验证码图片字节流
//     * @return 验证码文本。
//     */
//    private String getAliVerificationCode(byte[] imgIn){
//
//        // 验证码识别请求
//        ShowapiRequest req = new ShowapiRequest(ALI_CHECK_CODE, ALI_CHECK_APP_CODE);
//        String base64string = new String(Base64.encode(imgIn));
//        byte b[] = req.addTextPara("typeId","3040")
//                      .addTextPara("convert_to_jpg", "1")
//                      .addTextPara("img_base64", base64string)
//                      .postAsByte();
//
//        String res = null;
//        try {
//            res = new String(b, "utf-8");
//            JSONObject jsonObject = (JSONObject) JSONUtil.getJSONObjectFromJSONString(res);
//
//            // 错误号
//            String errorNo = jsonObject.getString("showapi_res_code");
//            if (RESPONSE_OK.equals(errorNo)) {
//                JSONObject result = jsonObject.getJSONObject("showapi_res_body");
//                return result.getString("Result");
//            }
//
//        } catch (UnsupportedEncodingException e) {
//            if(logger.isErrorEnabled()) {
//                logger.error("华林证券登录验证码第三方识别失败，图片转码时出错：", e);
//            }
//        } catch (JSONException e) {
//            if(logger.isErrorEnabled()) {
//                logger.error("华林证券登录验证码第三方识别失败，打码返回结果为：" + res, e);
//            }
//        }
//        return null;
//
//    }
//
//    /**
//     * 联众图片验证码识别(主用)。
//     *
//     * @param imgByte 验证码图片字节流
//     * @return 验证码文本。
//     */
//    private String getLzVerificationCode(byte[] imgIn){
//
//        String code = null;
//        String jsonResult = null;
//
//        //boundary就是request头和上传文件内容的分隔符
//        String bondary = "---------------------------68163001211748";
//
//        // 验证码上传URL
//        String uploadUrl="http://bbb4.hyslt.com/api.php?mod=php&act=upload";
//
//        // 第三方账号参数
//        Map<String, String> param = new HashMap<String, String>();
//        param.put("user_name", "zhangyao99");
//        param.put("user_pw", "(Nttdata003)");
//        param.put("yzm_minlen", "4");
//        param.put("yzm_maxlen", "4");
//        param.put("yzmtype_mark", "0"); // 数字字母类型
//        param.put("zztool_token", "123");
//
//        try {
//            URL url=new URL(uploadUrl);
//            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("content-type", "multipart/form-data; boundary="+bondary);
//            connection.setConnectTimeout(200000);
//            connection.setReadTimeout(200000);
//
//            // 验证码上传
//            OutputStream out = new DataOutputStream(connection.getOutputStream());
//            StringBuffer strBuf = new StringBuffer();
//            Iterator<Entry<String, String>> iter = param.entrySet().iterator();
//            while (iter.hasNext()) {
//                    Entry<String,String> entry = iter.next();
//                    String inputName = entry.getKey();
//                    String inputValue = entry.getValue();
//                    strBuf.append("\r\n").append("--").append(bondary).append("\r\n");
//                    strBuf.append("Content-Disposition: form-data; name=\""
//                                    + inputName + "\"\r\n\r\n");
//                    strBuf.append(inputValue);
//            }
//            out.write(strBuf.toString().getBytes());
//
//            // 上传验证码图片
//            String filename = "code"; // 验证码不下载到本地请求中文件名固定
//            String contentType = "image/jpeg";
//            StringBuffer strBuf1 = new StringBuffer();
//            strBuf1.append("\r\n").append("--").append(bondary).append("\r\n");
//            strBuf1.append("Content-Disposition: form-data; name=\""
//                                + "upload" + "\"; filename=\"" + filename+ "\"\r\n");
//            strBuf1.append("Content-Type:" + contentType + "\r\n\r\n");
//            out.write(strBuf1.toString().getBytes());
//
//            // 上传图片字节流
//            out.write(imgIn);
//            byte[] endData = ("\r\n--" + bondary + "--\r\n").getBytes();
//            out.write(endData);
//            out.flush();
//
//            //读取识别相应结果
//            InputStream in = connection.getInputStream();
//            ByteArrayOutputStream bout = new ByteArrayOutputStream();
//            byte[] buf = new byte[1024];
//            while (true) {
//                int rc = in.read(buf);
//                if (rc <= 0) {
//                        break;
//                } else {
//                        bout.write(buf, 0, rc);
//                }
//            }
//            in.close();
//
//            //结果输出
//            jsonResult = new String(bout.toByteArray());
//            out.close();
//
//            if (StringUtil.isEmpty(jsonResult)) {
//                return null;
//            }
//
//            // 解析返回的JSON
//            JSONObject codeJson = (JSONObject) JSONUtil
//                    .getJSONObjectFromJSONString(jsonResult);
//
//            JSONObject codeData = codeJson.getJSONObject("data");
//            code = codeData.getString("val");
//
//        } catch (Throwable t) {
//
//            if(logger.isErrorEnabled()) {
//                logger.error("华林证券登录验证码第三方识别失败，打码返回结果为：" + jsonResult, t);
//            }
//
//        }
//
//        return code;
//
//    }
//
//    /**
//     *  启动实时监测线程。
//     *
//     *  @param userId 用户ID
//     *  @param accountNo 资金账号
//     */
//    private void startMThread(String userId, Long accountNo) {
//
//        // 如果还没启动实时监测线程则启动
//        if (monitorThread == null || !monitorThread.isAlive()) {
//
//            if (logger.isInfoEnabled()) {
//                logger.info("用户:" + userId + "的资金帐户：" + this.loginId + "登录状态监测线程开始启动");
//            }
//            AccountStatusMonitor monitor = new AccountStatusMonitor();
//            monitor.setUserId(userId);
//            monitorThread = new Thread(monitor);
//            monitorThread.start();
//
//        }
//
//        return;
//
//    }
//
//    /**
//     * 监测账户状态线程。
//     */
//    private class AccountStatusMonitor implements Runnable {
//
//        /** 用户ID */
//        private String userId = null;
//
//        /**
//         * 账号状态监控线程体。
//         */
//        public void run() {
//
//            while (!stop) {
//
//                // 监测周期为1分钟
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    if (logger.isErrorEnabled()) {
//                        logger.error("监测线程休眠异常",e);
//                    }
//                    break;
//                }
//
//                // 如果是收盘状态则不进行同步
//                if (StockMarketInfo.CLOSE.equals(stockMarketInfo.getMarketStatus())) {
//                    logger.info("闭市时间同步工作结束。");
//                    return ;
//                }
//
//                // HTTP协议判断是否登录不需要参数
//                boolean isLogin =  false;
//
//                try {
//
//                    // 账号登录请求参数
//                    List<NameValuePair> qparams = new ArrayList<NameValuePair>();
//
//                    // 请求功能号
//                    qparams.add(new BasicNameValuePair("funcNo", FUNC_NO_MONEY_STATUS));
//
//                    // 委托方式
//                    qparams.add(new BasicNameValuePair("entrust_way", ENTRUST_WAY));
//
//                    // 营业部编号
//                    qparams.add(new BasicNameValuePair("branch_no", branchNo));
//
//                    // 资金账号
//                    qparams.add(new BasicNameValuePair("fund_account", custCode));
//                    qparams.add(new BasicNameValuePair("cust_code", custCode));
//
//                    // 资金类别
//                    qparams.add(new BasicNameValuePair("money_type", ""));
//
//                    // 8位数随机码
//                    qparams.add(new BasicNameValuePair("uid", getUid()));
//
//                    // 备注
//                    qparams.add(new BasicNameValuePair("op_station", opstation));
//
//                    // 请求地址
//                    URI uri = URIUtils.createURI(
//                            SCHEME,
//                            SERVER_URL,
//                            -1,
//                            "/servlet/json",
//                            null,
//                            null);
//
//                    HttpPost httpPost = new HttpPost(uri);
//                    httpPost.setHeader("User-Agent",USER_AGENT);
//                    httpPost.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
//                    HttpResponse response = httpclient.execute(httpPost, localContext);
//                    HttpEntity entity = response.getEntity();
//                    InputStream instream = entity.getContent();
//                    String html = IOUtils.toString(instream, "UTF-8"); // html代码
//                    instream.close();
//
//                    // 解析登录返回的JSON字符串
//                    JSONObject jsonObject = (JSONObject) JSONUtil.getJSONObjectFromJSONString(html);
//
//                    // 取得错误编码
//                    String errorNo  = jsonObject.getString("error_no");
//
//                    if (RESPONSE_OK.equals(errorNo)) {
//                        isLogin = true;
//                    }
//
//                } catch (Exception e) {
//
//                    if (logger.isErrorEnabled()) {
//                        logger.warn("账户登录的时候发生错误", e);
//                    }
//
//                }
//
//                if (!isLogin) {
//
//                    if (logger.isInfoEnabled()) {
//                        logger.info("用户:" + userId + "的资金账号:" + loginId + "链接发生中断，开始尝试重新登录。");
//                    }
//
//                    String result = login();
//
//                    // 尝试连接5次
//                    int retryCounts = 1;
//                    while(RESPONSE_CODE_ERROR.equals(result)) {
//
//                        if (retryCounts > 5) {
//                            break;
//                        }
//
//                        // 休眠2秒
//                        try {
//                            Thread.sleep(2000);
//                        } catch (InterruptedException e) {
//                            break;
//                        }
//
//                        if (logger.isInfoEnabled()) {
//                            logger.info("第" + retryCounts + "次重新登录");
//                        }
//
//                        result = login();
//                        retryCounts ++;
//
//                    }
//
//                    // 重新登录成功
//                    if (RESPONSE_OK.endsWith(result)) {
//
//                        // 登录状态恢复为已登录
//                        accountStatus = AutoTradingConstants.ACCOUNT_LOGIN;
//
//                        if (logger.isInfoEnabled()) {
//                            logger.info("用户:" + userId + "的资金账号:" + loginId + "连接已恢复。");
//                        }
//
//                    } else {
//
//                        // 登录状态置为未登录
//                        accountStatus = AutoTradingConstants.ACCOUNT_LOGOUT;
//
//                        if (logger.isInfoEnabled()) {
//                            logger.info("用户:" + userId + "的资金账号:" + loginId + "重新登录失败。");
//                        }
//
//                    }
//
//                }
//
//            }
//
//            // 重置线程退出标志
//            stop = false;
//            if (logger.isInfoEnabled()) {
//                logger.info("用户:" + userId + "的资金账号:" + loginId + "结束登录状态监测线程。");
//            }
//
//        }
//
//        /**
//         * 设置用户ID
//         *
//         * @param userId 用户ID
//         */
//        public void setUserId(String userId) {
//            this.userId = userId;
//        }
//
//    }
//
//    /**
//     * 设置可用的证券列表。
//     *
//     * @param activityStockList 可用的证券列表
//     */
//    public void setActivityStockList(ActivityStockList activityStockList) {
//        this.activityStockList = activityStockList;
//    }
//
//    /**
//     * 设置股票市场的实时信息。
//     *
//     * @param stockMarketInfo 股票市场的实时信息
//     */
//    public void setStockMarketInfo(StockMarketInfo stockMarketInfo) {
//        this.stockMarketInfo = stockMarketInfo;
//    }

    /**
     * 手机IMEI号生成器。
     */
    private String makeImei() {

        String imeiString="35566778898256";//前14位 
        char[] imeiChar=imeiString.toCharArray(); 
        int resultInt=0;
        
        for (int i = 0; i < imeiChar.length; i++) { 
            int a=Integer.parseInt(String.valueOf(imeiChar[i])); 
            i++; 
            final int temp=Integer.parseInt(String.valueOf(imeiChar[i]))*2; 
            final int b=temp<10?temp:temp-9; 
            resultInt+=a+b; 
        } 

        resultInt%=10; 
        resultInt=resultInt==0?0:10-resultInt; 

        return imeiString + resultInt;

    }

    /**
     * MAC地址生成器。
     */
    private class RandomMac {

        /** MAC地址公司列表长度 */
        private final static int MAX_COMPANY_LIST_LEN = 1200;

        /** 公司MAC地址列表 */
        private final static String companyListString = "00000C#00000E#000075#000095#0000F0#000102#000103#000130#000142#000143#00014A#000163#000164#000181#000196#000197#0001C7#0001C9#0001E6#0001E7#000216#000217#00024A#00024B#00025F#000278#00027D#00027E#0002A5#0002B3#0002B9#0002BA#0002DC#0002EE#0002FC#0002FD#000331#000332#000342#000347#00034B#00036B#00036C#00037F#000393#00039F#0003A0#0003E3#0003E4#0003FE#00040B#00041F#000423#000427#000428#000438#00044D#00044E#000456#00045A#00046B#00046D#00046E#000480#000496#00049A#00049B#0004BD#0004C0#0004C1#0004DC#0004DD#0004DE#0004E2#0004EA#000500#000501#000502#00051A#000531#000532#00055D#00055E#00055F#000573#000574#000585#00059A#00059B#0005B5#0005DC#0005DD#000625#000628#00062A#000652#000653#00065B#00067C#00068C#0006C1#0006D6#0006D7#00070D#00070E#00074D#00074F#000750#000772#000784#000785#0007B3#0007B4#0007E0#0007E9#0007EB#0007EC#000802#00080E#000820#000821#000874#00087C#00087D#000883#00089A#0008A3#0008A4#0008C2#0008C7#0008E2#0008E3#000911#000912#000918#000943#000944#00097B#00097C#000997#0009B6#0009B7#0009E8#0009E9#000A04#000A27#000A28#000A41#000A42#000A57#000A5E#000A8A#000A8B#000A95#000AB7#000AB8#000AD9#000AE0#000AF3#000AF4#000AF7#000B06#000B0E#000B45#000B46#000B5D#000B5F#000B60#000B85#000B86#000BAC#000BBE#000BBF#000BC5#000BCD#000BDB#000BE1#000BFC#000BFD#000C30#000C31#000C41#000C43#000C85#000C86#000CCE#000CCF#000CDB#000CE5#000CE6#000CF1#000CF7#000CF8#000D0B#000D28#000D29#000D54#000D56#000D57#000D65#000D66#000D88#000D93#000D9D#000DAE#000DBC#000DBD#000DE5#000DEC#000DED#000E07#000E08#000E0C#000E35#000E38#000E39#000E40#000E5C#000E62#000E6A#000E7F#000E83#000E84#000E86#000EB3#000EC0#000EC7#000ED6#000ED7#000EED#000F06#000F20#000F23#000F24#000F34#000F35#000F3D#000F61#000F62#000F66#000F6A#000F8F#000F90#000F9F#000FB5#000FBB#000FC3#000FCB#000FCD#000FDE#000FF7#000FF8#001007#00100B#00100D#001011#001014#001018#00101F#001029#00102F#001040#001045#00104B#001054#001055#00105A#001079#00107B#001083#00108C#0010A6#0010B3#0010DB#0010E3#0010F6#0010FA#0010FF#00110A#001111#00111A#001120#001121#001124#00113F#001143#001150#001158#00115C#00115D#001180#001185#00118B#001192#001193#001195#00119F#0011AE#0011BB#0011BC#0011F9#001200#001201#001217#00121E#001225#001237#00123F#001243#001244#001247#00124B#001262#001279#00127F#001280#001283#00128A#0012A9#0012C9#0012D1#0012D2#0012D9#0012DA#0012EE#0012F0#0012F2#0012FB#001302#00130A#001310#001315#001319#00131A#001320#001321#001346#001349#00135F#001360#001365#001370#001371#001372#001374#001377#00137F#001380#001392#0013A9#0013C3#0013C4#0013CE#0013E8#0013F7#0013FD#001404#00140D#00140E#00141B#00141C#001422#001438#00143E#001451#001469#00146A#00146C#00147C#00149A#0014A7#0014A8#0014A9#0014BF#0014C2#0014C7#0014D5#0014E8#0014F1#0014F2#0014F6#001500#001517#00152A#00152B#00152C#00152F#00153F#001540#001560#001562#001563#001570#001599#00159A#00159B#0015A0#0015A8#0015B9#0015C1#0015C5#0015C6#0015C7#0015DE#0015E8#0015E9#0015F9#0015FA#001601#001620#001626#001632#001635#001646#001647#00164D#00164E#001660#00166B#00166C#00166F#001675#001676#00169C#00169D#0016B5#0016B6#0016B8#0016BC#0016C7#0016C8#0016CA#0016CB#0016DB#0016E0#0016EA#0016EB#0016F0#001700#001708#00170E#00170F#00173F#001742#00174B#001759#00175A#001765#00177C#001783#001784#001794#001795#00179A#0017A4#0017B0#0017C9#0017CB#0017CC#0017D1#0017D5#0017DF#0017E0#0017E2#0017E3#0017E4#0017E5#0017E6#0017E7#0017E8#0017E9#0017EA#0017EB#0017EC#0017EE#0017F2#00180F#001813#001818#001819#00182F#001830#001831#001832#001833#001834#001839#001842#00184D#001868#00186E#001871#001873#001874#001882#00188B#00188D#0018A4#0018AF#0018B0#0018B9#0018BA#0018C0#0018C5#0018DE#0018F8#0018FE#001906#001907#00192C#00192D#00192F#001930#001947#00194F#001955#001956#00195B#00195E#001963#001969#001979#00198F#001992#001999#0019A6#0019A9#0019AA#0019B7#0019B9#0019BB#0019C0#0019C5#0019CB#0019D1#0019D2#0019E1#0019E2#0019E3#0019E7#0019E8#001A16#001A1B#001A1E#001A2F#001A30#001A4B#001A66#001A6C#001A6D#001A70#001A75#001A77#001A80#001A89#001A8A#001A8F#001AA0#001AA1#001AA2#001AAD#001AC1#001ADB#001ADC#001ADE#001AE2#001AE3#001AF0#001B0C#001B0D#001B11#001B21#001B25#001B2A#001B2B#001B2F#001B33#001B52#001B53#001B54#001B59#001B63#001B77#001B78#001B8F#001B90#001B98#001BAF#001BBA#001BC0#001BD4#001BD5#001BD7#001BDD#001BE9#001BED#001BEE#001C0E#001C0F#001C10#001C11#001C12#001C17#001C23#001C35#001C43#001C57#001C58#001C8E#001C9A#001C9C#001CA4#001CB0#001CB1#001CB3#001CBF#001CC0#001CC1#001CC4#001CC5#001CD4#001CD6#001CDF#001CEB#001CF0#001CF6#001CF9#001CFB#001D09#001D0D#001D25#001D28#001D2E#001D3B#001D42#001D45#001D46#001D4C#001D4F#001D6B#001D6E#001D70#001D71#001D73#001D7E#001D98#001DA1#001DA2#001DAF#001DB5#001DBA#001DBE#001DE0#001DE1#001DE5#001DE6#001DE9#001DF6#001DFD#001DFE#001E0B#001E10#001E13#001E14#001E1F#001E2A#001E3A#001E3B#001E45#001E46#001E49#001E4A#001E4F#001E52#001E58#001E5A#001E64#001E65#001E67#001E6B#001E79#001E7A#001E7D#001E7E#001E8D#001EA3#001EA4#001EA8#001EBD#001EBE#001EC1#001EC2#001EC9#001ECA#001EDC#001EE1#001EE2#001EE5#001EF6#001EF7#001F00#001F01#001F0A#001F12#001F26#001F27#001F29#001F33#001F3B#001F3C#001F41#001F46#001F5B#001F5C#001F5D#001F6C#001F6D#001F7E#001F9A#001F9D#001F9E#001FA7#001FC4#001FC9#001FCA#001FCC#001FCD#001FDA#001FDE#001FDF#001FE4#001FF3#002032#002040#002060#002075#00207B#0020A6#0020AF#0020D8#0020DA#002105#002108#002109#002119#00211B#00211C#00211E#002129#002135#002136#002143#00214C#002155#002156#002159#00215A#00215C#00215D#002162#00216A#00216B#002170#002180#002191#00219B#00219E#0021A0#0021A1#0021AA#0021AB#0021AE#0021BA#0021BE#0021D1#0021D2#0021D7#0021D8#0021E1#0021E9#0021FC#0021FE#00220C#00220D#002210#002219#00222D#00223A#00223F#002241#002255#002256#002257#002264#002265#002266#002267#00226B#002275#00227F#002283#002290#002291#002298#0022A1#0022A5#0022A6#0022B0#0022B4#0022BD#0022BE#0022CE#0022FA#0022FB#0022FC#0022FD#002304#002305#00230B#00230D#002312#002314#002315#002326#002332#002333#002334#002339#00233A#00233E#002345#00235D#00235E#002368#002369#00236C#002374#002375#00237D#002395#002399#00239C#0023A2#0023A3#0023AB#0023AC#0023AE#0023AF#0023B4#0023BE#0023C2#0023C6#0023D3#0023D4#0023D6#0023D7#0023DF#0023EA#0023EB#0023ED#0023EE#0023F1#0023F8#002400#002401#002403#002404#002413#002414#002436#002437#002438#002443#002450#002451#002454#00246C#002473#00247C#00247D#00247F#002481#002482#00248D#002490#002491#002492#002493#002495#002497#002498#0024A0#0024A1#0024A5#0024B5#0024BA#0024BE#0024C1#0024C3#0024C4#0024D6#0024D7#0024DC#0024E8#0024E9#0024EF#0024F7#0024F9#002500#00252E#002538#002545#002546#002547#002548#00254B#002564#002566#002567#002568#002583#002584#00259C#00259E#0025B3#0025B4#0025B5#0025BA#0025BC#0025BD#0025C3#0025C4#0025CF#0025D0#0025E7#0025F1#0025F2#002608#00260A#00260B#002636#002637#00263E#002641#002642#00264A#002651#002652#002654#002655#00265A#00265D#00265F#002668#002669#002688#002698#002699#0026B0#0026B9#0026BA#0026BB#0026C6#0026C7#0026CA#0026CB#0026CC#0026F3#003005#003019#00301E#003024#003040#003065#00306E#003071#003078#00307B#003080#003085#003094#003096#0030A3#0030B6#0030BD#0030C1#0030F2#004001#00400B#004027#004043#004096#005004#00500B#00500F#005014#00502A#00503E#005043#005050#005053#005054#005073#005080#00508B#005099#0050A2#0050A7#0050BA#0050BD#0050D1#0050DA#0050E2#0050E3#0050E4#0050F0#006008#006009#00602F#006038#00603E#006047#00605C#006070#006083#00608C#006097#0060B0#0060CF#008021#008039#00805F#00809F#0080A0#0080C8#009004#00900C#009021#009027#00902B#00905F#009069#00906D#00906F#009086#00908E#009092#00909C#0090A6#0090AB#0090B1#0090BF#0090CF#0090D9#0090F2#00A024#00A040#00A077#00A081#00A08E#00A0BF#00A0C5#00A0C6#00A0C9#00A0CA#00A0F8#00AA00#00AA01#00AA02#00B04A#00B064#00B08E#00B0C2#00B0D0#00C04F#00C0BE#00C0F9#00D006#00D058#00D063#00D079#00D088#00D090#00D095#00D096#00D097#00D0B7#00D0BA#00D0BB#00D0BC#00D0C0#00D0D3#00D0D8#00D0E4#00D0F6#00D0FF#00E000#00E003#00E00C#00E014#00E01E#00E02B#00E034#00E04F#00E052#00E064#00E06F#00E08F#00E0A3#00E0B0#00E0B1#00E0DA#00E0F7#00E0F9#00E0FC#00E0FE#02608C#02C08C#080007#080009#080028#080046#08004E";

        /**
         * 取得MAC地址。
         * 
         * @param split MAC地址分隔符
         * 
         * @return MAC地址
         */
        private String getFormatMacAddr(String split) {

            String mac = getCompanyMacAddrPart() + getRandomMacAddrPart();
            String outMac = "";
            for (int i = 0; i < mac.length();) {
                outMac += mac.charAt(i++);
                if (0 == i % 2 && i < mac.length()) {
                    outMac += split;
                }
            }

            return outMac;

        }

        /**
         * 获取IEEE分配的网络硬件制造商编号。
         * 
         * @return 制造商编号
         */
        private String getCompanyMacAddrPart() {

            String[] compListArray = new String[MAX_COMPANY_LIST_LEN];
            compListArray = companyListString.split("#");
            int index = (int) Math.round(Math.random()
                    * (compListArray.length - 1) + 0);
            return compListArray[index];

        }

        /**
         * 取得网卡序列号。
         * 
         * @return 网卡序列号
         */
        private String getRandomMacAddrPart() {

            String baseMacSeed = "0123456789ABCDEF";
            String wapsMacAddr = "";

            for (int i = 0; i < 3; i++) {
                wapsMacAddr += getRandomByteStr(baseMacSeed);
            }

            return wapsMacAddr;

        }

        /**
         * 获取MAC地址随机字符串。
         * 
         * @param baseMacSeed MAC地址种子
         * 
         * @return MAC地址随机字符串
         */
        private String getRandomByteStr(
                String baseMacSeed) {

            int h = (int) Math.round(Math.random() * 15 + 0);
            int l = (int) Math.round(Math.random() * 15 + 0);

            String byteStr = String.format(
                    "%c%c",
                    baseMacSeed.charAt(h),
                    baseMacSeed.charAt(l));

            return byteStr;

        }

    }

    /**
     * 生成8位随机数。
     */
    private String getUid() {  
        StringBuffer shortBuffer = new StringBuffer();  
        String uuid = UUID.randomUUID().toString().replace("-", "");  
        for (int i = 0; i < 8; i++) {  
            String str = uuid.substring(i * 4, i * 4 + 4);  
            int x = Integer.parseInt(str, 16);  
            shortBuffer.append(chars[x % 0x3E]);  
        }  
        return shortBuffer.toString();  
      
    }  
//
//    // 以下功能暂不支持
//    public List<UserAccountCommissionRec> getHistoryCommissionRecs(
//            String arg0,
//            Long arg1,
//            String arg2,
//            String arg3) throws RuntimeException {
//        return null;
//    }
//
//    public List<UserAccountTradedRec> getHistoryTradedRecs(
//            String userId,
//            Long accountNo,
//            String dateFrom,
//            String dateTo) throws RuntimeException {
//        return null;
//    }
//
//    public String buyBorrInCredit(
//            String arg0,
//            Long arg1,
//            String arg2,
//            Long arg3,
//            Integer arg4) throws RuntimeException {
//        return null;
//    }
//
//    public String buyInCredit(
//            String arg0,
//            Long arg1,
//            String arg2,
//            Long arg3,
//            Integer arg4) throws RuntimeException {
//        return null;
//    }
//
//    public String buyPayInCredit(
//            String arg0,
//            Long arg1,
//            String arg2,
//            Long arg3,
//            Integer arg4) throws RuntimeException {
//        return null;
//    }
//
//    public Long getBorrBuyingEnableAmount(
//            String arg0,
//            Long arg1,
//            String arg2,
//            Integer arg3) throws RuntimeException {
//        return null;
//    }
//
//    public Long getBorrSellingEnableAmount(
//            String arg0,
//            Long arg1,
//            String arg2,
//            Integer arg3) throws RuntimeException {
//        return null;
//    }
//
//    public UserAccountCreditInfo getCreditAssetsStatusRec(
//            String arg0,
//            Long arg1) throws RuntimeException {
//        return null;
//    }
//
//    public List<StockInventoryRec> getMarginSourceRecs(
//            String arg0,
//            Long arg1) throws RuntimeException {
//        return null;
//    }
//
//    public Long getPayBuyingEnableAmount(
//            String arg0,
//            Long arg1,
//            String arg2,
//            Integer arg3) throws RuntimeException {
//        return null;
//    }
//
//    public Long getPaySellingEnableAmount(
//            String arg0,
//            Long arg1,
//            String arg2,
//            Integer arg3) throws RuntimeException {
//        return null;
//    }
//
//    public String sellBorrInCredit(
//            String arg0,
//            Long arg1,
//            String arg2,
//            Long arg3,
//            Integer arg4) throws RuntimeException {
//        return null;
//    }
//
//    public String sellInCredit(
//            String arg0,
//            Long arg1,
//            String arg2,
//            Long arg3,
//            Integer arg4) throws RuntimeException {
//        return null;
//    }
//
//    public String sellPayInCredit(
//            String arg0,
//            Long arg1,
//            String arg2,
//            Long arg3,
//            Integer arg4) throws RuntimeException {
//        return null;
//    }
    
    public static void main(String[] args) {

        ZXZQHttpTradingAgentImpl impl = new ZXZQHttpTradingAgentImpl();
        impl.login();
        impl.getMoneyStatusRec("",0L);
    }
    
}
