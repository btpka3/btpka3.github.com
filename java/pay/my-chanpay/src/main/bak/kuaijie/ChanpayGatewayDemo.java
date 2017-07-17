package com.chanpay.demo.temp;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;

import com.chanpay.demo.util.HttpProtocolHandler;
import com.chanpay.demo.util.HttpRequest;
import com.chanpay.demo.util.HttpResponse;
import com.chanpay.demo.util.HttpResultType;
import com.chanpay.demo.util.MD5;
import com.chanpay.demo.util.RSA;
import com.meidusa.fastjson.JSON;
import com.meidusa.fastjson.TypeReference;

public class ChanpayGatewayDemo {
	/**
	 * MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPq3oXX5aFeBQGf3Ag/
	 * 86zNu0VICXmkof85r+DDL46w3vHcTnkEWVbp9DaDurcF7DMctzJngO0u9OG1cb4mn+Pn/
	 * uNC1fp7S4JH4xtwST6jFgHtXcTG9uewWFYWKw/8b3zf4fXyRuI/
	 * 2ekeLSstftqnMQdenVP7XCxMuEnnmM1RwIDAQAB
	 */

	/**
	 * 畅捷支付平台公钥
	 */
	private static String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPq3oXX5aFeBQGf3Ag/86zNu0VICXmkof85r+DDL46w3vHcTnkEWVbp9DaDurcF7DMctzJngO0u9OG1cb4mn+Pn/uNC1fp7S4JH4xtwST6jFgHtXcTG9uewWFYWKw/8b3zf4fXyRuI/2ekeLSstftqnMQdenVP7XCxMuEnnmM1RwIDAQAB";//生产环境公钥
//	private static String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDONXNe9xgdWykFwTLRLKWKmGQC6ZLp66tqLRoUlvjUJnwoej8aD+KUuimcOXpIh9XuTDEO0YYh/D5xtnEN+q2wvZzK3G2l+xEirCowE7CM388t/yplGdJMw81CSaUQUeAz/5NCwbXA8i8OTv8/h0kLIdO/omMD8aJKgpmtyJ3IEQIDAQAB";//t环境公钥


	/**
	 * MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO/6rPCvyCC+IMalLzTy3cVBz
	 * /+wamCFNiq9qKEilEBDTttP7Rd/GAS51lsfCrsISbg5td/w25+
	 * wulDfuMbjjlW9Afh0p7Jscmbo1skqIOIUPYfVQEL687B0EmJufMlljfu52b2efVAyWZF9QBG1vx
	 * /AJz1EVyfskMaYVqPiTesZAgMBAAECgYEAtVnkk0bjoArOTg/
	 * KquLWQRlJDFrPKP3CP25wHsU4749t6kJuU5FSH1Ao81d0Dn9m5neGQCOOdRFi23cV9gdFKYMhwPE6
	 * +
	 * nTAloxI3vb8K9NNMe0zcFksva9c9bUaMGH2p40szMoOpO6TrSHO9Hx4GJ6UfsUUqkFFlN76XprwE
	 * +ECQQD9rXwfbr9GKh9QMNvnwo9xxyVl4kI88iq0X6G4qVXo1Tv6/
	 * DBDJNkX1mbXKFYL5NOW1waZzR+Z/XcKWAmUT8J9AkEA8i0WT/
	 * ieNsF3IuFvrIYG4WUadbUqObcYP4Y7Vt836zggRbu0qvYiqAv92Leruaq3ZN1khxp6gZKl/
	 * OJHXc5xzQJACqr1AU1i9cxnrLOhS8m+xoYdaH9vUajNavBqmJ1mY3g0IYXhcbFm/
	 * 72gbYPgundQ/pLkUCt0HMGv89tn67i+8QJBALV6UgkVnsIbkkKCOyRGv2syT3S7kOv1J+
	 * eamGcOGSJcSdrXwZiHoArcCZrYcIhOxOWB/m47ymfE1Dw/+
	 * QjzxlUCQCmnGFUO9zN862mKYjEkjDN65n1IUB9Fmc1msHkIZAQaQknmxmCIOHC75u4W0PGRyVzq8KkxpNBq62ICl7xmsPM=
	 */
	/**
	 * 商户私钥
	 */
	//生产环境 59 商户号私钥
	//private static String MERCHANT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO/6rPCvyCC+IMalLzTy3cVBz/+wamCFNiq9qKEilEBDTttP7Rd/GAS51lsfCrsISbg5td/w25+wulDfuMbjjlW9Afh0p7Jscmbo1skqIOIUPYfVQEL687B0EmJufMlljfu52b2efVAyWZF9QBG1vx/AJz1EVyfskMaYVqPiTesZAgMBAAECgYEAtVnkk0bjoArOTg/KquLWQRlJDFrPKP3CP25wHsU4749t6kJuU5FSH1Ao81d0Dn9m5neGQCOOdRFi23cV9gdFKYMhwPE6+nTAloxI3vb8K9NNMe0zcFksva9c9bUaMGH2p40szMoOpO6TrSHO9Hx4GJ6UfsUUqkFFlN76XprwE+ECQQD9rXwfbr9GKh9QMNvnwo9xxyVl4kI88iq0X6G4qVXo1Tv6/DBDJNkX1mbXKFYL5NOW1waZzR+Z/XcKWAmUT8J9AkEA8i0WT/ieNsF3IuFvrIYG4WUadbUqObcYP4Y7Vt836zggRbu0qvYiqAv92Leruaq3ZN1khxp6gZKl/OJHXc5xzQJACqr1AU1i9cxnrLOhS8m+xoYdaH9vUajNavBqmJ1mY3g0IYXhcbFm/72gbYPgundQ/pLkUCt0HMGv89tn67i+8QJBALV6UgkVnsIbkkKCOyRGv2syT3S7kOv1J+eamGcOGSJcSdrXwZiHoArcCZrYcIhOxOWB/m47ymfE1Dw/+QjzxlUCQCmnGFUO9zN862mKYjEkjDN65n1IUB9Fmc1msHkIZAQaQknmxmCIOHC75u4W0PGRyVzq8KkxpNBq62ICl7xmsPM=";//
	//生产环境 测试商户号私钥
	private static String MERCHANT_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANB5cQ5pf+QHF9Z2+DjrAXstdxQHJDHyrni1PHijKVn5VHy/+ONiEUwSd5nx1d/W+mtYKxyc6HiN+5lgWSB5DFimyYCiOInh3tGQtN+pN/AtE0dhMh4J9NXad0XEetLPRgmZ795O/sZZTnA3yo54NBquT19ijYfrvi0JVf3BY9glAgMBAAECgYBFdSCox5eXlpFnn+2lsQ6mRoiVAKgbiBp/FwsVum7NjleK1L8MqyDOMpzsinlSgaKfXxnGB7UgbVW1TTeErS/iQ06zx3r4CNMDeIG1lYwiUUuguIDMedIJxzSNXfk65Bhps37lm129AE/VnIecpKxzelaUuzyGEoFWYGevwc/lQQJBAPO0mGUxOR/0eDzqsf7ehE+Iq9tEr+aztPVacrLsEBAwqOjUEYABvEasJiBVj4tECnbgGxXeZAwyQAJ5YmgseLUCQQDa/dgviW/4UMrY+cQnzXVSZewISKg/bv+nW1rsbnk+NNwdVBxR09j7ifxg9DnQNk1Edardpu3z7ipHDTC+z7exAkAM5llOue1JKLqYlt+3GvYr85MNNzSMZKTGe/QoTmCHStwV/uuyN+VMZF5cRcskVwSqyDAG10+6aYqD1wMDep8lAkBQBoVS0cmOF5AY/CTXWrht1PsNB+gbzic0dCjkz3YU6mIpgYwbxuu69/C3SWg7EyznQIyhFRhNlJH0hvhyMhvxAkEAuf7DNrgmOJjRPcmAXfkbaZUf+F4iK+szpggOZ9XvKAhJ+JGd+3894Y/05uYYRhECmSlPv55CBAPwd8VUsSb/1w==";

	/**
	 * 编码类型
	 */
	private static String charset = "UTF-8";

	/**
	 * 建立请求，以模拟远程HTTP的POST请求方式构造并获取钱包的处理结果
	 * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
	 * "",sParaTemp)
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
	public static String buildRequest(Map<String, String> sParaTemp, String signType, String key, String inputCharset,
			String gatewayUrl) throws Exception {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp, signType, key, inputCharset);
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset(inputCharset);
		request.setMethod(HttpRequest.METHOD_POST);
		request.setParameters(generatNameValuePair(createLinkRequestParas(sPara), inputCharset));
		request.setUrl(gatewayUrl);
		System.out.println(gatewayUrl+""+httpProtocolHandler.toString(generatNameValuePair(createLinkRequestParas(sPara), inputCharset)));
		if(sParaTemp.get("Service").equalsIgnoreCase("nmg_quick_onekeypay")||sParaTemp.get("Service").equalsIgnoreCase("nmg_nquick_onekeypay")){
			return null;
		}
		
		// 返回结果处理
		HttpResponse response = httpProtocolHandler.execute(request, null, null);
		if (response == null) {
			return null;
		}
		String strResult = response.getStringResult();
		
		return strResult;
	}

	@SuppressWarnings("unused")
	private static Map convertMap(Map<?, ?> parameters, String InputCharset)
			throws ArrayIndexOutOfBoundsException, UnsupportedEncodingException, IllegalArgumentException {
		Map<String, String> formattedParameters = new HashMap<String, String>(parameters.size());
		for (Map.Entry<?, ?> entry : parameters.entrySet()) {
			if (entry.getValue() == null || Array.getLength(entry.getValue()) == 0) {
				formattedParameters.put((String) entry.getKey(), null);
			} else {
				String value = new String(((String) Array.get(entry.getValue(), 0)).getBytes(InputCharset), charset);

			}
		}
		return formattedParameters;
	}

	/**
	 * MAP类型数组转换成NameValuePair类型
	 *
	 * @param properties
	 *            MAP类型数组
	 * @return NameValuePair类型数组
	 */
	private static NameValuePair[] generatNameValuePair(Map<String, String> properties, String charset)
			throws Exception {
		NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			// nameValuePair[i++] = new NameValuePair(entry.getKey(),
			// URLEncoder.encode(entry.getValue(),charset));
			nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
		}
		return nameValuePair;
	}

	/**
	 * 生成要请求参数数组
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
	public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String signType, String key,
			String inputCharset) throws Exception {
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
		System.out.println("Sign:" + mysign);
		sPara.put("Sign", mysign);
		sPara.put("SignType", signType);

		return sPara;
	}

	/**
	 * 生成MD5签名结果
	 *
	 * @param sPara
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildRequestByMD5(Map<String, String> sPara, String key, String inputCharset)
			throws Exception {
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
	public static String buildRequestByRSA(Map<String, String> sPara, String privateKey, String inputCharset)
			throws Exception {
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
		String charset = params.get("InputCharset");
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

		params = paraFilter(params);

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		String charset = params.get("InputCharset");
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
			if (value == null || value.equals("") || key.equalsIgnoreCase("Sign") || key.equalsIgnoreCase("SignType")) {
				continue;
			}
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
	 * @throws Exception 
	 */
	public void gatewayPost(Map<String, String> origMap, String charset, String MERCHANT_PRIVATE_KEY)  {
		String result="";
		try {
			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";// 测试环境地址，上生产后需要替换该地址
			Map<String, String> sPara = buildRequestPara(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset);
				result = buildRequest(origMap, "RSA", ChanpayGatewayDemo.MERCHANT_PRIVATE_KEY, charset,
						urlStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
		
		
	}

	/**
	 * 加密，部分接口，有参数需要加密
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
	 * 异步通知验签仅供参考
	 */
	public void notifyVerify() {

		String sign = "j4XMK2uBQWTsoiVIvkP4sijhRDmnIPMdl/5ed70BEHiBFbRDr7PSN0WFFaGZzMAZyhtWJzjroAq3nhytzTA5tDo1UA8sGq2k2gTlFqdRJ4Icdo+GL+XMYtPwHGGCd6REh6AG03djyxz6/30VKGi0nISWTcJHMJhrJ8QTNN07mQ8=";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("notify_id", "785d6b9146cb40c6ac2c3e66b89ab337");
		paramMap.put("notify_type", "trade_status_sync");
		paramMap.put("notify_time", "20170629094728");
		paramMap.put("_input_charset", "UTF-8");
		paramMap.put("version", "1.0");
		paramMap.put("outer_trade_no", "2017062909460723640");
		paramMap.put("inner_trade_no", "101149870077907917824");
		paramMap.put("trade_status", "PAY_FINISHED");
		paramMap.put("trade_amount", "0.01");
		paramMap.put("gmt_create", "20170629094728");
		paramMap.put("gmt_payment", "20170629094728");
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
	 * 公共请求参数设置
	 */
	public Map<String, String> setCommonMap(Map<String, String> origMap) {
		// 2.1 基本参数
		origMap.put("Version", "1.0");
		//origMap.put("PartnerId", "200000140001"); // 测试环境商户号
		//origMap.put("PartnerId", "200000400059");//200000400059 生产参数
		origMap.put("PartnerId", "200001160097");//200000400059 生产测试参数
		
		origMap.put("InputCharset", charset);// 字符集
		origMap.put("TradeDate", "20170612");// 商户请求时间
		origMap.put("TradeTime", "141200");// 商户请求时间
		origMap.put("Memo", null);
		return origMap;
	}

	/**
	 * 2.1 鉴权绑卡  nmg_api_auth_req；商户采集方式，
	 */
	public void nmg_biz_api_auth_req() {

		Map<String, String> origMap = new HashMap<String, String>();
		origMap = setCommonMap(origMap);
		// 2.1 鉴权绑卡 api 业务参数
		origMap.put("Service", "nmg_biz_api_auth_req");// 鉴权绑卡的接口名(商户采集方式)；银行采集方式更换接口名称为：nmg_canal_api_auth_req
		String trxId = "201756796880";
		origMap.put("TrxId", trxId);// 订单号
		origMap.put("ExpiredTime", "90m");// 订单有效期
		origMap.put("MerUserId", "199312");// 用户标识
		origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡;）
		origMap.put("BkAcctNo", this.encrypt("62000000000000", MERCHANT_PUBLIC_KEY, charset));// 卡号
		System.out.println(this.encrypt("62000000000000", MERCHANT_PUBLIC_KEY, charset));
		origMap.put("IDTp", "01");// 证件类型 （目前只支持身份证 01：身份证）
		origMap.put("IDNo", this.encrypt("36000000000", MERCHANT_PUBLIC_KEY, charset));// 证件号
		System.out.println(this.encrypt("36000000000", MERCHANT_PUBLIC_KEY, charset));
		origMap.put("CstmrNm", this.encrypt("畅捷支付", MERCHANT_PUBLIC_KEY, charset));// 持卡人姓名
		origMap.put("MobNo", this.encrypt("13511111111", MERCHANT_PUBLIC_KEY, charset));// 银行预留手机号
		origMap.put("NotifyUrl", "http://dev.chanpay.com/receive.php");// 异步通知url
		origMap.put("SmsFlag", "1");
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	/**
	 * 2.2 鉴权绑卡 nmg_page_api_auth_req；前台模式
	 */
	public void nmg_page_api_auth_req() {

		Map<String, String> origMap = new HashMap<String, String>();
		origMap = setCommonMap(origMap);
		// 2.1 鉴权绑卡 api 业务参数
		origMap.put("Service", "nmg_page_api_auth_req");// 鉴权绑卡的接口名(前台模式)；
		//String trxId = "201756796880882";
		String trxId = Long.toString(System.currentTimeMillis());
		
		origMap.put("TrxId", trxId);// 订单号
		origMap.put("ExpiredTime", "90m");// 订单有效期
		origMap.put("MerUserId", "170000000");// 用户标识
		origMap.put("NotifyUrl", "http://mas.test.custom.net/atinterface/receive_notify.do");// 异步通知url
		origMap.put("ReturnUrl", "http://www.baidu.com");// 回跳地址，可空
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	/**
	 * 2.3 鉴权绑卡确认 api nmg_api_auth_sms
	 */
	public void nmg_api_auth_sms() {

		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_auth_sms");// 鉴权绑卡确认的接口名
		// 2.1 鉴权绑卡  业务参数
		String trxId = "2017031309130622";
		origMap.put("TrxId", trxId);// 订单号
		origMap.put("OriAuthTrxId", "2017567968");// 原鉴权绑卡订单号
		origMap.put("SmsCode", "112490");// 鉴权短信验证码
		origMap.put("NotifyUrl", "http://dev.chanpay.com/receive.php");// 异步通知地址
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}
	
	/**
	 * 
	 * 2.4 支付请求接口 api nmg_biz_api_quick_payment
	 */

	private void nmg_biz_api_quick_payment() {

		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_biz_api_quick_payment");// 支付的接口名
		// 2.2 业务参数
		String trxId = Long.toString(System.currentTimeMillis());
		
		origMap.put("TrxId", trxId);// 订单号

		//origMap.put("TrxId", "201703131045234230112");// 订单号
		origMap.put("OrdrName", "畅捷支付");// 商品名称
		origMap.put("MerUserId", "170010900000");// 用户标识
		origMap.put("SellerId", "200001160097");// 子账户号
		origMap.put("SubMerchantNo", "");// 子商户号
		origMap.put("ExpiredTime", "40m");// 订单有效期
		origMap.put("CardBegin", "436742");// 卡号前6位
		origMap.put("CardEnd", "4771");// 卡号后4位
		origMap.put("TrxAmt", "0.01");// 交易金额
		origMap.put("TradeType", "11");// 交易类型
		origMap.put("SmsFlag", "1");
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	
	/**
	 * 
	 * 2.5 支付确认接口： api nmg_api_quick_payment_smsconfirm
	 */

	private void nmg_api_quick_payment_smsconfirm() {

		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_quick_payment_smsconfirm");// 请求的接口名称
		// 2.2 业务参数
		String trxId = Long.toString(System.currentTimeMillis());		
		origMap.put("TrxId", trxId);// 订单号

		//origMap.put("TrxId", "101149785980144593760");// 订单号
		origMap.put("OriPayTrxId", "1497867642907");// 原有支付请求订单号
		origMap.put("SmsCode", "641496");// 短信验证码
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	/**
	 * 
	 * 2.6 支付请求接口(直付通) api nmg_zft_api_quick_payment
	 */

	private void nmg_zft_api_quick_payment() {

		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_zft_api_quick_payment");// 支付接口名称
		// 2.2 业务参数
		origMap.put("TrxId", "2017031310452344543521");// 订单号
		origMap.put("OrdrName", "畅捷支付");// 商品名称
		origMap.put("MerUserId", "20201");// 用户标识
		origMap.put("SellerId", "200001160097");// 子账户号
		origMap.put("SubMerchantNo", "200001160097");// 子商户号
		origMap.put("ExpiredTime", "40m");// 订单有效期
		origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡;）
		origMap.put("BkAcctNo", this.encrypt("6200000000000000", MERCHANT_PUBLIC_KEY, charset));// 卡号
		origMap.put("IDTp", "01");// 证件类型 （目前只支持身份证 01：身份证）
		origMap.put("IDNo", this.encrypt("360000000000000000", MERCHANT_PUBLIC_KEY, charset));// 证件号
		origMap.put("CstmrNm", this.encrypt("XXX", MERCHANT_PUBLIC_KEY, charset));// 持卡人姓名
		origMap.put("MobNo", this.encrypt("13511111111", MERCHANT_PUBLIC_KEY, charset));// 银行预留手机号
		origMap.put("EnsureAmount", "1");//担保金额
		origMap.put("TrxAmt", "30");// 交易金额
		origMap.put("TradeType", "11");// 交易类型
		origMap.put("SmsFlag", "1");//短信发送标识
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}
	
	/**
	 * 
	 * 2.2.7. 直接支付请求接口(畅捷前台)  nmg_quick_onekeypay
	 */
	private void nmg_quick_onekeypay(){
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_quick_onekeypay");// 直接支付
		// 2.2 业务参数
		//origMap.put("TrxId", "2017031310052312");// 商户网站唯一订单号
		String trxId = Long.toString(System.currentTimeMillis());		
		origMap.put("TrxId", trxId);// 订单号

		origMap.put("OrdrName", "测试商品"); // 商品名称
		origMap.put("OrdrDesc", "");// 商品描述
		origMap.put("MerUserId", "1700000001");// 用户标识

		//origMap.put("SellerId", "200000140001");// t环境  子账户号 
		//origMap.put("SellerId", "200000400059");// 生产环境 59
		origMap.put("SellerId", "200001160097");// 生产环境
		
		origMap.put("SubMerchantNo", "");// 子商户号
		origMap.put("ExpiredTime", "40m");// 订单有效期
		origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡;）
		origMap.put("BkAcctNo", this.encrypt("430000000000000000", MERCHANT_PUBLIC_KEY, charset));// 卡号
		origMap.put("IDTp", "01");// 证件类型 （目前只支持身份证 01：身份证）
		origMap.put("IDNo", this.encrypt("1300000000000000", MERCHANT_PUBLIC_KEY, charset));// 证件号
		origMap.put("CstmrNm", this.encrypt("XXX", MERCHANT_PUBLIC_KEY, charset));// 持卡人姓名
		origMap.put("MobNo", this.encrypt("1380000000", MERCHANT_PUBLIC_KEY, charset));// 银行预留手机号
		
//		origMap.put("CardCvn2", "");//cvv2
//		origMap.put("CardExprDt", "");//有效期

		//origMap.put("EnsureAmount", "1");//担保金额
		origMap.put("TrxAmt", "0.01");// 交易金额
		origMap.put("TradeType", "11");// 交易类型
		origMap.put("AccessChannel", "web");//用户终端类型  web  wap
		origMap.put("ReturnUrl", "http://www.baidu.com");//同步回调地址
		origMap.put("NotifyUrl", "");//异步回调地址
		origMap.put("Extension", "");//扩展字段
		
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	
	}
	
	/**
	 * 
	 * 2.2.8  支付请求接口(畅捷前台)  nmg_nquick_onekeypay
	 */
	private void nmg_nquick_onekeypay(){
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_nquick_onekeypay");// 支付请求接口
		// 2.2 业务参数
		//origMap.put("TrxId", "2017031310052312");// 商户网站唯一订单号
		String trxId = Long.toString(System.currentTimeMillis());		
		origMap.put("TrxId", trxId);// 订单号

		origMap.put("OrdrName", "测试商品"); // 商品名称
		origMap.put("OrdrDesc", "");// 商品描述
		origMap.put("MerUserId", "1700000000");// 用户标识 
		origMap.put("SellerId", "200001160097");// 生产环境
		origMap.put("SubMerchantNo", "");// 子商户号
		
		origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡;）	实际上应该不需要这个字段	
		origMap.put("CardBegin", "436742");// 卡号前6位
		origMap.put("CardEnd", "4771");// 卡号后4位
		origMap.put("ExpiredTime", "40m");// 订单有效期
		//origMap.put("EnsureAmount", "1");//担保金额
		origMap.put("TradeType", "11");// 交易类型
		origMap.put("TrxAmt", "0.01");// 交易金额
	
		origMap.put("AccessChannel", "web");//用户终端类型  web  wap
		origMap.put("ReturnUrl", "http://www.baidu.com");//同步回调地址
		origMap.put("NotifyUrl", "");//异步回调地址
		origMap.put("Extension", "");//扩展字段
		
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	
	}
	/**
	 * 
	 * 2.9 用户鉴权绑卡信息查询 api nmg_api_auth_info_qry
	 */

	private void nmg_api_auth_info_qry() {
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_auth_info_qry");// 用户鉴权绑卡信息查询接口名
		// 2.2 业务参数
		origMap.put("TrxId", "20170313100523156");// 商户网站唯一订单号
		origMap.put("MerUserId", "1700000001"); // 用户标识
		//origMap.put("CardBegin", "430000");// 卡号前6位
		//origMap.put("CardEnd", "4700");// 卡号后4位
		origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡）
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}
	
	/**
	 * 
	 * 用户鉴权解绑 api nmg_assign_api_auth_unbind
	 */

	private void nmg_assign_api_auth_unbind1() {
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_assign_api_auth_unbind");// 用户鉴权解绑接口名
		// 2.2 业务参数
		origMap.put("TrxId", "2017031310052312");// 商户网站唯一订单号
		origMap.put("MerUserId", "17001090000"); // 用户标识
		origMap.put("UnbindType", "1"); // 解绑模式，1：代表普通模式解绑，参数需要传递卡信息的前6后4；2：指定路由模式解绑，需要传递卡号标识（必须是指定路由模式的鉴权绑卡才可使用指定路由模式解绑
		origMap.put("CardId", "");// 卡号标识
		origMap.put("CardBegin", "430000");// 卡号前6位
		origMap.put("CardEnd", "4700");// 卡号后4位
		origMap.put("Extension", "");// 扩展字段
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	/**
	 * 
	 * 2.10 用户退款请求接口： nmg_api_refund
	 */

	private void nmg_api_refund() {

		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_refund");// 支付的接口名
		// 2.2 业务参数
		origMap.put("TrxId", "2017030915302088");// 订单号
		origMap.put("OriTrxId", "f85af8cca7584bbebb88678e8a16bf26");// 原有支付请求订单号
		origMap.put("TrxAmt", "0.09");// 退款金额
		origMap.put("RefundEnsureAmount", null);// 退担保金额
//		origMap.put("RoyaltyParameters",
//				"[{'userId':'13890009900','PID':'2','account_type':'101','amount':'100.00'},{'userId':'13890009900','PID':'2','account_type':'101','amount':'100.00'}]");// 退款分润账号集
		origMap.put("NotifyUrl", "www.baidu.com");// 异步通知地址
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	/**
	 * 
	 * 2.11 商户短信验证码重发  ：nmg_api_quick_payment_resend
	 */

	private void nmg_sms_resend() {

		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		// 2.2 业务参数
		origMap.put("Service", "nmg_api_quick_payment_resend");
		origMap.put("TrxId", "2017030915102022");// 订单号
		origMap.put("OriTrxId", "20170309131120");// 原业务请求订单号
		origMap.put("TradeType", "auth_order");// 原业务订单类型
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	

	/**
	 * 2.12 商户日退款对账单文件接口：nmg_api_refund_trade_file
	 */
	public void nmg_api_refund_trade_file() {

		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_refund_trade_file");// 请求的接口名称
		// 2.9 日退款对账单文件
		origMap.put("TransDate", "20160728");// 交易日期
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}
	/**
	 * *
	 * 
	 * 
	 * 2.13：确认收货接口：nmg_api_quick_payment_receiptconfirm
	 * 
	 */

	public void nmg_api_quick_payment_receiptconfirm() {
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("TrxId", "2334554654232342");//订单号
		origMap.put("OrderTrxId", "2334554654232342");//原支付请求订单号
		origMap.put("Service", "nmg_api_quick_payment_receiptconfirm");
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);

	}
	
	/**
	 * 
	 * 2.14 商户鉴权/支付/退款订单状态查询接口 api tzt_order_query
	 */

	private void nmg_api_query_trade() {

		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_query_trade");// 请求的接口名
		// 2.2 业务参数
		origMap.put("TrxId", "2017030915202027");// 订单号
		origMap.put("OrderTrxId", "2017030915302088");// 原业务请求订单号
		origMap.put("TradeType", "refund_order");// 原业务订单类型
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	/**
	 * 2.15 商户日交易对账单文件:nmg_api_everyday_trade_file
	 */
	public void nmg_api_everyday_trade_file() {

		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_everyday_trade_file");// 请求的接口名称
		// 2.11 日支付对账文件
		origMap.put("TransDate", "20170315");// 交易日期
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}
	
	



	public static void main(String[] args) {
		ChanpayGatewayDemo test = new ChanpayGatewayDemo();
		
//		test.nmg_biz_api_auth_req(); // 2.1 鉴权请求---API
//		test.nmg_page_api_auth_req(); //2.2 鉴权请求 ---畅捷前端
//		test.nmg_api_auth_sms(); // 2.3 鉴权请求确认---API
//		test.nmg_biz_api_quick_payment(); //2.4 支付请求---API
//		test.nmg_api_quick_payment_smsconfirm(); //2.5 支付确认---API
//		test.nmg_zft_api_quick_payment(); //2.6 支付请求（直付通）
//		test.nmg_quick_onekeypay();  //2.7 直接请求---畅捷前端
		test.nmg_nquick_onekeypay();  //2.8 支付请求---畅捷前端
//		test.nmg_api_auth_info_qry(); // 2.9 鉴权绑卡查询
//		test.nmg_assign_api_auth_unbind1();// 鉴权解绑
//		test.cjt_create_refund();//商户退款请求   //老网关退款接口
//		

//		test.nmg_sms_resend(); //2.11 短信重发
//		test.nmg_api_query_trade(); //2.14 订单状态查询
//		test.nmg_api_refund_trade_file(); //2.12 退款对账单
//		test.nmg_api_everyday_trade_file(); //2.15 交易对账单
//		test.nmg_api_quick_payment_receiptconfirm();// 2.13 确认收货接口
//		test.notifyVerify(); // 测试异步通知验签
	}

}
