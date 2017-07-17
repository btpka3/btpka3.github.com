package com.chanpay.demo.temp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;

import com.chanpay.demo.util.HttpProtocolHandler;
import com.chanpay.demo.util.HttpRequest;
import com.chanpay.demo.util.HttpResponse;
import com.chanpay.demo.util.HttpResultType;
import com.chanpay.demo.util.MD5;
import com.chanpay.demo.util.RSA;

public class ChanpayGatewayQpayDemo {

    /**
     * 商户私钥
     */
    private static String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDv0rdsn5FYPn0EjsCPqDyIsYRawNWGJDRHJBcdCldodjM5bpve+XYb4Rgm36F6iDjxDbEQbp/HhVPj0XgGlCRKpbluyJJt8ga5qkqIhWoOd/Cma1fCtviMUep21hIlg1ZFcWKgHQoGoNX7xMT8/0bEsldaKdwxOlv3qGxWfqNV5QIDAQAB";
    /**
     * 畅捷支付平台公钥
     */
    private static String MERCHANT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO/6rPCvyCC+IMalLzTy3cVBz/+wamCFNiq9qKEilEBDTttP7Rd/GAS51lsfCrsISbg5td/w25+wulDfuMbjjlW9Afh0p7Jscmbo1skqIOIUPYfVQEL687B0EmJufMlljfu52b2efVAyWZF9QBG1vx/AJz1EVyfskMaYVqPiTesZAgMBAAECgYEAtVnkk0bjoArOTg/KquLWQRlJDFrPKP3CP25wHsU4749t6kJuU5FSH1Ao81d0Dn9m5neGQCOOdRFi23cV9gdFKYMhwPE6+nTAloxI3vb8K9NNMe0zcFksva9c9bUaMGH2p40szMoOpO6TrSHO9Hx4GJ6UfsUUqkFFlN76XprwE+ECQQD9rXwfbr9GKh9QMNvnwo9xxyVl4kI88iq0X6G4qVXo1Tv6/DBDJNkX1mbXKFYL5NOW1waZzR+Z/XcKWAmUT8J9AkEA8i0WT/ieNsF3IuFvrIYG4WUadbUqObcYP4Y7Vt836zggRbu0qvYiqAv92Leruaq3ZN1khxp6gZKl/OJHXc5xzQJACqr1AU1i9cxnrLOhS8m+xoYdaH9vUajNavBqmJ1mY3g0IYXhcbFm/72gbYPgundQ/pLkUCt0HMGv89tn67i+8QJBALV6UgkVnsIbkkKCOyRGv2syT3S7kOv1J+eamGcOGSJcSdrXwZiHoArcCZrYcIhOxOWB/m47ymfE1Dw/+QjzxlUCQCmnGFUO9zN862mKYjEkjDN65n1IUB9Fmc1msHkIZAQaQknmxmCIOHC75u4W0PGRyVzq8KkxpNBq62ICl7xmsPM=";
    /**
     * 编码类型
     */
    private static String charset = "UTF-8";

    /**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取钱包的处理结果 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("", "",sParaTemp)
     *
     * @param strParaFileName
     *            文件类型的参数名
     * @param strFilePath
     *            文件路径
     * @param sParaTemp
     *            请求参数数组
     * @return 钱包处理结果
     * @throws Exception
     */
    public static String buildRequest(Map<String, String> sParaTemp, String signType, String key, String inputCharset, String gatewayUrl) throws Exception {
        // 待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp, signType, key, inputCharset);
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        // 设置编码集
        request.setCharset(inputCharset);
        request.setMethod(HttpRequest.METHOD_POST);
        request.setParameters(generatNameValuePair(createLinkRequestParas(sPara), inputCharset));
        request.setUrl(gatewayUrl);
        HttpResponse response = httpProtocolHandler.execute(request, null, null);
        if (response == null) {
            return null;
        }
        String strResult = response.getStringResult();
        return strResult;
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     *
     * @param properties
     *            MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties, String charset) throws Exception {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            // nameValuePair[i++] = new NameValuePair(entry.getKey(), URLEncoder.encode(entry.getValue(),charset));
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        return nameValuePair;
    }

    /**
     * 生成要请求给钱包的参数数组
     * 
     * @param sParaTemp
     *            请求前的参数数组
     * @param signType
     *            RSA
     * @param key
     *            商户自己生成的商户私钥
     * @param inputCharset
     *            UTF-8
     * @return 要请求的参数数组
     * @throws Exception
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String signType, String key, String inputCharset) throws Exception {
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        // 生成签名结果
        String mysign = "";
        if ("MD5".equalsIgnoreCase(signType)) {
            mysign = buildRequestByMD5(sPara, key, inputCharset);
        } else if ("RSA".equalsIgnoreCase(signType)) {
            mysign = buildRequestByRSA(sPara, key, inputCharset);
        }
        // 签名结果与签名方式加入请求提交参数组中
        System.out.println("sign:" + mysign);
        sPara.put("sign", mysign);
        sPara.put("sign_type", signType);

        return sPara;
    }

    /**
     * 生成MD5签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByMD5(Map<String, String> sPara, String key, String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = MD5.sign(prestr, key, inputCharset);
        return mysign;
    }

    /**
     * 生成RSA签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByRSA(Map<String, String> sPara, String privateKey, String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = RSA.sign(prestr, privateKey, inputCharset);
        return mysign;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @param encode
     *            是否需要urlEncode
     * @return 拼接后字符串
     */
    public static Map<String, String> createLinkRequestParas(Map<String, String> params) {
        Map<String, String> encodeParamsValueMap = new HashMap<String, String>();
        List<String> keys = new ArrayList<String>(params.keySet());
        String charset = params.get("_input_charset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value;
            try {
                value = URLEncoder.encode(params.get(key), charset);
                encodeParamsValueMap.put(key, value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return encodeParamsValueMap;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @param encode
     *            是否需要urlEncode
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, boolean encode) {

        // params = paraFilter(params);

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        String charset = params.get("_input_charset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray
     *            签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            // try {
            // value = URLEncoder.encode(value,charset);
            // } catch (UnsupportedEncodingException e) {
            // e.printStackTrace();
            // }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 向测试服务器发送post请求
     * 
     * @param origMap
     *            参数map
     * @param charset
     *            编码字符集
     * @param MERCHANT_PRIVATE_KEY
     *            私钥
     */
    public void gatewayPost(Map<String, String> origMap, String charset, String MERCHANT_PRIVATE_KEY) {
        try {
            String urlStr = "https://tpay.chanpay.com/mag/gateway/receiveOrder.do?";
            Map<String, String> sPara = buildRequestPara(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset);
            System.out.println(urlStr + createLinkString(sPara, true));
            String resultString = buildRequest(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset, urlStr);
            System.out.println(resultString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密，2.14付款到卡接口等部分接口，有参数需要加密
     * 
     * @param src
     *            原值
     * @param publicKey
     *            畅捷支付发送的平台公钥
     * @param charset
     *            UTF-8
     * @return RSA加密后的密文
     */
    private String encrypt(String src, String publicKey, String charset) {
        try {
            byte[] bytes = RSA.encryptByPublicKey(src.getBytes(charset), publicKey);
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 2.3 单笔支付cjt_create_instant_trade 手机版用cjt_wap_create_instant_trade 将调用此方法system.out打印的请求url交给浏览器访问
     */
    public void cjt_create_instant_tradeORcjt_wap_create_instant_trade() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", charset);// 字符集 UTF-8
        origMap.put("is_anonymous", "Y");// 是否匿名 必须写Y，然后buyer,seller相关的参数不要填写，因为写N的话，要求买家（商户那边，来付款的普通用户）也是畅捷的用户
        origMap.put("return_url", "http://dev.chanpay.com/receive.php");// 前台跳转url
        // 2.3 单笔支付
        origMap.put("bank_code", "BOC");// 含义看文档 跳收银台此值为空，不要再写TESTBANK了
        String out_trade_no = (UUID.randomUUID().toString()).replace("-", "");
        System.out.println(out_trade_no);
        origMap.put("out_trade_no", out_trade_no);// 订单号
        origMap.put("pay_method", "2");// 含义看文档 收银台写2 直连网银1
        origMap.put("pay_type", "C,DC");// 含义看文档 连收银台此值为空
        origMap.put("service", "cjt_create_instant_trade");// 支付的接口名
        //origMap.put("service", "cjt_wap_create_instant_trade");// 支付的接口名
        origMap.put("trade_amount", "1024.00");// 金额
        origMap.put("notify_url", "http://dev.chanpay.com/receive.php");// 前台跳转url
        /**
         * 以下俩自行选择一个看效果
         */
        // // 绑卡
        origMap.put("ext1", "[{'hasUserSign':'true','userSign':'yangyang0test'}]");
        // 绑卡 + wap收银台2.0
        // origMap.put("ext1", "[{'hasUserSign':'true','userSign':'yangyang0test','version':'2.0'}]");
        this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
    }


  /**
        * 2.4 批量订单支付接口cjt_create_batch_instant_trade
        */
       public void cjt_create_batch_instant_trade() {

		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap.put("service", "cjt_create_batch_instant_trade");// 支付的接口名
		origMap.put("version", "1.0");
		origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
		origMap.put("_input_charset", charset);// 字符集

		origMap.put("request_no", "6741334835157966");
		origMap.put("trade_amount", "409.00");
		origMap.put("prodInfo_list","[{\"action_desc\":\"\",\"expired_time\":\"\",\"notify_url\":\"http://www.govbuy.ngrok.cc/mall/interface_api/v1/banks/yy_rsa_verify\",\"order_amount\":\"409.00\",\"order_time\":\"20170420092638\",\"out_trade_no\":\"2000004000072017042084169006\",\"product_name\":\"\",\"produc_url\":\"\",\"royalty_parameters\":\"\",\"sell_id\":\"200000400007\",\"sell_id_type\":\"MEMBER_ID\",\"sell_mobile\":\"13352635263\"}]");
		origMap.put("is_anonymous", "Y");
		origMap.put("pay_method", "2");
		origMap.put("out_trade_no", "6741334835157966");
		origMap.put("sell_id_type", "200000400007");

		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
       }



	 /**
     * 2.10 查看回单接口 cjt_view_receipt
     */
    public void cjt_view_receipt() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_view_receipt");// 支付的接口名
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", charset);// 字符集
        // 2.11 日支付对账文件
        origMap.put("outer_trade_no", "20160728");

        this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
    }

   

    /**
     * 2.11 日支付对账文件cjt_everyday_trade_file 需要自行从response流里获取xls对账文件 2.12 日退款对账文件和2.13 手续费对账文件参照此接口调用
     */
    public void everyTradeFile() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_everyday_trade_file");// 支付的接口名
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", charset);// 字符集
        // 2.11 日支付对账文件
        origMap.put("transDate", "20160728");// 交易日期

        this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
    }

	 /**
     * 2.12 商户日退款对账单文件cjt_refund_trade_file
     */
    public void cjt_refund_trade_file() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_refund_trade_file");// 支付的接口名
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", charset);// 字符集
        // 2.11 日支付对账文件
        origMap.put("transDate", "20160728");// 交易日期

        this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
    }

    /**
     * 2.13 手续费对账文件cjt_fee_trade_file
     */
    public void cjt_fee_trade_file() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", charset);// 字符集
        origMap.put("service", "cjt_fee_trade_file");// 支付的接口名
        // 2.13 手续费对账文件
        origMap.put("transDate", "20160606");// 交易日期

        this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
    }

    /**
     * 异步通知验签仅供参考 2.3单笔支付，2.18，2.19快捷支付api，对应异步通知参数见2.8 2.5退款接口，对应异步通知参数见2.9 2.14付款到卡接口，对应异步通知参数见2.15 2.20订单提现接口，对应异步通知参数见2.15
     */
    public void notifyVerify() {

        String sign = "5Ji173IpLoax0pqDKNLOCp2SVOHZanTSSmESI3Y/66RtY02DxcAWvGukwRZ9/6+neP1OoDXWpAVqhFeMpdYMcHPqDImt9o5O+7MFuH5qjqM2WolgTT+54qzlnzuo3ST60eQWkS31ePmHulknJZNVsjFwmS4TB1d2lWQAW4Zo7Cg=";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("notify_id", "82daf55676574973b8f25c0154a8d2ef");
        paramMap.put("notify_type", "trade_status_sync");
        paramMap.put("notify_time", "20160715130526");
        paramMap.put("_input_charset", "UTF-8");
        paramMap.put("version", "1.0");
        paramMap.put("outer_trade_no", "NO201607062019409608");
        paramMap.put("inner_trade_no", "101146780758085591877");
        paramMap.put("trade_status", "TRADE_SUCCESS");
        paramMap.put("trade_amount", "200.00");
        paramMap.put("gmt_create", "20160706202016");
        paramMap.put("gmt_payment", "20160706202016");
        // paramMap.put("gmt_close", "");
        paramMap.put("extension", "{}");
        String text = createLinkString(paramMap, false);
        System.out.println("ori_text:" + text);
        try {
            System.out.println(RSA.verify(text, sign, MERCHANT_PUBLIC_KEY, charset));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2.7查询银行列表接口cjt_get_paychannel
     */
    public void cjt_get_paychannel() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("service", "cjt_get_paychannel");// 支付的接口名
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", charset);// 字符集
        // 2.7查询银行列表接口
        origMap.put("product_code", "20201");// 产品吗，生产环境，测试环境固定20201

        this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
    }

    /**
     * 2.5退款接口cjt_create_refund
     */
    public void refund4jc() {
        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("version", "2.0");
        origMap.put("partner_id", "200000200012"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", charset);// 字符集
        origMap.put("is_anonymous", "Y");// 是否匿名
        origMap.put("service", "cjt_create_refund");// 支付的接口名
        // 2.5退款接口
        String out_trade_no = (UUID.randomUUID().toString()).replace("-", "");
        System.out.println("out_trade_no:\r\n" + out_trade_no);
        origMap.put("out_trade_no", out_trade_no);// 订单号
        origMap.put("orig_outer_trade_no", "自行填写需要退款的原始订单号");// 原始订单号
        origMap.put("refund_amount", "0.50");// 退款金额
        origMap.put("notify_url", "https://www.baidu.com");// 后台通知的url
        this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
    }

   

    /**
     * 2.10回单下载cjt_view_receipt 测试环境订单号2016072815470375755736903945
     */
    public void cjt_view_receipt() {
        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        origMap.put("version", "1.0");
        origMap.put("partner_id", "200000400007"); // 畅捷支付分配的商户号
        origMap.put("_input_charset", charset);// 字符集
        origMap.put("service", "cjt_view_receipt");// 支付的接口名
        // 2.10回单下载
        origMap.put("outer_trade_no", "自行填写已经交易完毕的订单号");// 原始订单号
        this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
    }

    // //////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        ChanpayGatewayQpayDemo test = new ChanpayGatewayQpayDemo();
        // test.cjt_create_instant_tradeORcjt_wap_create_instant_trade();// 2.3单笔支付webORwap
        // test.notifyVerify();// 异步通知验签
        // test.everyTradeFile();// 2.11日交易对账文件
		// test.cjt_refund_trade_file();//2.12商户日退款对账单文件
        // test.cjt_get_paychannel();// 2.7 查询银行卡列表
        // test.cjt_view_receipt();// 2.10回单下载
        // test.cjt_fee_trade_file();// 2.13手续费对账文件
    }
}
