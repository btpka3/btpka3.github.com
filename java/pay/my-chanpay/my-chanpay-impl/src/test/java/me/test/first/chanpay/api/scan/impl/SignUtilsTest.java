package me.test.first.chanpay.api.scan.impl;

import me.test.first.chanpay.api.scan.*;
import me.test.first.chanpay.api.scan.dto.*;
import org.junit.*;

import java.io.*;
import java.security.*;
import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

public class SignUtilsTest extends BaseTest {


    @Test
    public void keyTest01() throws UnsupportedEncodingException {

        byte[] bytes = "Hello World~".getBytes("UTF-8");
        PrivateKey priKey = SignUtils.toRsaPriKey(props.getScan().getMchPriKey());

        String sign = SignUtils.rsaSign(bytes, priKey);


        PublicKey pubKey = SignUtils.toRsaPubKey(props.getScan().getMchPubKey());
        boolean result = SignUtils.rsaSignVerify(bytes, sign, pubKey);
        assertThat(result).isTrue();
    }

    @Test
    public void createJoinStringForSign01() {

        Map<String, String> origMap = new HashMap<String, String>();
        // 基本参数
        origMap.put("Service", "mag_init_code_pay");
        origMap.put("Version", "1.0");
        origMap.put("PartnerId", "200000140001");//T环境
        //origMap.put("PartnerId", "200001140108");//生产环境
        //origMap.put("PartnerId", "200000400059");// 200000400059
        origMap.put("InputCharset", "UTF-8");
        origMap.put("TradeDate", "20170719");
        origMap.put("TradeTime", "121240");
        // origMap.put("SignType","RSA");
        origMap.put("ReturnUrl", "http://dev.chanpay.com/receive.php");// 前台跳转url
        origMap.put("Memo", "备注");

        // 4.2.1.1. 公众号/服务窗确认支付 api 业务参数
        origMap.put("OutTradeNo", "1500437560445");
        origMap.put("MchId", "200000140001");
        //origMap.put("MchId", "200000400059");
        origMap.put("SubMchId", "");
        origMap.put("TradeType", "11");
        origMap.put("BankCode", "WXPAY");
        //origMap.put("BankCode", "ALIPAY");
        origMap.put("AppId", "wx90192dels817xla0");
        origMap.put("DeviceInfo", "wx90192dels817xla0");
        origMap.put("Currency", "CNY");
        origMap.put("TradeAmount", "0.02");
        origMap.put("EnsureAmount", "");
        origMap.put("GoodsName", "11");
        origMap.put("TradeMemo", "1111");
        origMap.put("Subject", "0153");
        origMap.put("OrderStartTime", "20170719121240");
        origMap.put("OrderEndTime", "");
        origMap.put("NotifyUrl", "http://www.baidu.com");
        origMap.put("SpbillCreateIp", "127.0.0.1");
        origMap.put("SplitList", "");
        origMap.put("Ext", "{'ext':'ext1'}");

        // 2.11 日支付对账文件
//        origMap.put("TransDate", "20160728");// 交易日期

        String expected = "AppId=wx90192dels817xla0&BankCode=WXPAY&Currency=CNY&DeviceInfo=wx90192dels817xla0&Ext={'ext':'ext1'}&GoodsName=11&InputCharset=UTF-8&MchId=200000140001&Memo=备注&NotifyUrl=http://www.baidu.com&OrderStartTime=20170719121240&OutTradeNo=1500437560445&PartnerId=200000140001&ReturnUrl=http://dev.chanpay.com/receive.php&Service=mag_init_code_pay&SpbillCreateIp=127.0.0.1&Subject=0153&TradeAmount=0.02&TradeDate=20170719&TradeMemo=1111&TradeTime=121240&TradeType=11&Version=1.0";
        String actual = SignUtils.joinStr(origMap);
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void signWithRsa01() throws UnsupportedEncodingException {

        Map<String, String> origMap = new HashMap<String, String>();
        // 基本参数
        origMap.put("Service", "mag_init_code_pay");
        origMap.put("Version", "1.0");
        origMap.put("PartnerId", "200000140001");//T环境
        //origMap.put("PartnerId", "200001140108");//生产环境
        //origMap.put("PartnerId", "200000400059");// 200000400059
        origMap.put("InputCharset", "UTF-8");
        origMap.put("TradeDate", "20170719");
        origMap.put("TradeTime", "121240");
        // origMap.put("SignType","RSA");
        origMap.put("ReturnUrl", "http://dev.chanpay.com/receive.php");// 前台跳转url
        origMap.put("Memo", "备注");

        // 4.2.1.1. 公众号/服务窗确认支付 api 业务参数
        origMap.put("OutTradeNo", "1500437560445");
        origMap.put("MchId", "200000140001");
        //origMap.put("MchId", "200000400059");
        origMap.put("SubMchId", "");
        origMap.put("TradeType", "11");
        origMap.put("BankCode", "WXPAY");
        //origMap.put("BankCode", "ALIPAY");
        origMap.put("AppId", "wx90192dels817xla0");
        origMap.put("DeviceInfo", "wx90192dels817xla0");
        origMap.put("Currency", "CNY");
        origMap.put("TradeAmount", "0.02");
        origMap.put("EnsureAmount", "");
        origMap.put("GoodsName", "11");
        origMap.put("TradeMemo", "1111");
        origMap.put("Subject", "0153");
        origMap.put("OrderStartTime", "20170719121240");
        origMap.put("OrderEndTime", "");
        origMap.put("NotifyUrl", "http://www.baidu.com");
        origMap.put("SpbillCreateIp", "127.0.0.1");
        origMap.put("SplitList", "");
        origMap.put("Ext", "{'ext':'ext1'}");


        String joinStr = SignUtils.joinStr(origMap);
        byte[] bytes = SignUtils.getBytes(joinStr, "UTF-8");

        String expected = "cSDYyKicFjLwTR5V9ftEF+RQJLlUS1IT2nN20bgtWz9qAHXBArW8YjzuBFWE58HKFvg77EcQ83EqSa0Iqko+sHHiFP+I/DBV9CTNRJ/zQXuHr4nJHtc9kEHGQIUx92aIrnC3zJLxuOeLnHFqdUJNYVMDHXmfqKOL+HvKY8p4wFY=";


        PrivateKey priKey = SignUtils.toRsaPriKey(props.getScan().getMchPriKey());

        String actual = SignUtils.rsaSign(bytes, priKey);

        assertThat(actual).isEqualTo(expected);

        /*
RSA sign str : AppId=wx90192dels817xla0&BankCode=WXPAY&Currency=CNY&DeviceInfo=wx90192dels817xla0&Ext={'ext':'ext1'}&GoodsName=11&InputCharset=UTF-8&MchId=200000140001&Memo=备注&NotifyUrl=http://www.baidu.com&OrderStartTime=20170719121240&OutTradeNo=1500437560445&PartnerId=200000140001&ReturnUrl=http://dev.chanpay.com/receive.php&Service=mag_init_code_pay&SpbillCreateIp=127.0.0.1&Subject=0153&TradeAmount=0.02&TradeDate=20170719&TradeMemo=1111&TradeTime=121240&TradeType=11&Version=1.0
sign:cSDYyKicFjLwTR5V9ftEF+RQJLlUS1IT2nN20bgtWz9qAHXBArW8YjzuBFWE58HKFvg77EcQ83EqSa0Iqko+sHHiFP+I/DBV9CTNRJ/zQXuHr4nJHtc9kEHGQIUx92aIrnC3zJLxuOeLnHFqdUJNYVMDHXmfqKOL+HvKY8p4wFY=
https://tpay.chanpay.com/mag-unify/gateway/receiveOrder.do?AppId=wx90192dels817xla0&BankCode=WXPAY&Currency=CNY&DeviceInfo=wx90192dels817xla0&Ext=%7B%27ext%27%3A%27ext1%27%7D&GoodsName=11&InputCharset=UTF-8&MchId=200000140001&Memo=%E5%A4%87%E6%B3%A8&NotifyUrl=http%3A%2F%2Fwww.baidu.com&OrderStartTime=20170719121240&OutTradeNo=1500437560445&PartnerId=200000140001&ReturnUrl=http%3A%2F%2Fdev.chanpay.com%2Freceive.php&Service=mag_init_code_pay&Sign=cSDYyKicFjLwTR5V9ftEF%2BRQJLlUS1IT2nN20bgtWz9qAHXBArW8YjzuBFWE58HKFvg77EcQ83EqSa0Iqko%2BsHHiFP%2BI%2FDBV9CTNRJ%2FzQXuHr4nJHtc9kEHGQIUx92aIrnC3zJLxuOeLnHFqdUJNYVMDHXmfqKOL%2BHvKY8p4wFY%3D&SignType=RSA&SpbillCreateIp=127.0.0.1&Subject=0153&TradeAmount=0.02&TradeDate=20170719&TradeMemo=1111&TradeTime=121240&TradeType=11&Version=1.0
RSA sign str : AppId=wx90192dels817xla0&BankCode=WXPAY&Currency=CNY&DeviceInfo=wx90192dels817xla0&Ext={'ext':'ext1'}&GoodsName=11&InputCharset=UTF-8&MchId=200000140001&Memo=备注&NotifyUrl=http://www.baidu.com&OrderStartTime=20170719121240&OutTradeNo=1500437560445&PartnerId=200000140001&ReturnUrl=http://dev.chanpay.com/receive.php&Service=mag_init_code_pay&SpbillCreateIp=127.0.0.1&Subject=0153&TradeAmount=0.02&TradeDate=20170719&TradeMemo=1111&TradeTime=121240&TradeType=11&Version=1.0
sign:cSDYyKicFjLwTR5V9ftEF+RQJLlUS1IT2nN20bgtWz9qAHXBArW8YjzuBFWE58HKFvg77EcQ83EqSa0Iqko+sHHiFP+I/DBV9CTNRJ/zQXuHr4nJHtc9kEHGQIUx92aIrnC3zJLxuOeLnHFqdUJNYVMDHXmfqKOL+HvKY8p4wFY=
{"AcceptStatus":"F","InputCharset":"UTF-8","PartnerId":"200000140001","RetCode":"SCP_RESPONSE_FAIL","RetMsg":"请求流水号重复","Sign":"N0hJRthpkj7F0c/q91wbLYEm1PzMcVj5YFAcz2lLlDs4dUPg2vs4/Awp3WdMXZngArjNc9ToGLnN8KQGPH4GkGJzUnG3MS9drg533etWrWZWiDQNyefmlSJWLFfxAE8WsTpzdrmh2mQGmuQKvcN3TJE7d39iQWOLpZha2MUggbQ=","SignType":"RSA","TradeDate":"20170719","TradeTime":"154119"}

Process finished with exit code 0
         */

    }


    @Test
    public void signWithRsa02() {

        String expectedSign = "cSDYyKicFjLwTR5V9ftEF+RQJLlUS1IT2nN20bgtWz9qAHXBArW8YjzuBFWE58HKFvg77EcQ83EqSa0Iqko+sHHiFP+I/DBV9CTNRJ/zQXuHr4nJHtc9kEHGQIUx92aIrnC3zJLxuOeLnHFqdUJNYVMDHXmfqKOL+HvKY8p4wFY=";

        ZonedDateTime tradeTimeZdt = ZonedDateTime.of(
                2017, 7, 19,
                12, 12, 40,
                444 * 1000 * 1000,
                ZoneId.of("Asia/Shanghai"));

        Date tradeTime = Date.from(tradeTimeZdt.toInstant());

        ZonedDateTime orderStartTimeZdt = ZonedDateTime.of(
                2017, 7, 19,
                12, 12, 40,
                555 * 1000 * 1000,
                ZoneId.of("Asia/Shanghai"));

        Date orderStartTime = Date.from(orderStartTimeZdt.toInstant());

        UserScanReq req = new UserScanReq();
        req.setService("mag_init_code_pay");
        req.setVersion("1.0");
        req.setPartnerId("200000140001");
        req.setTradeDateTime(tradeTime);
        req.setSign(expectedSign);
        req.setSignType("RSA");
        req.setReturnUrl("http://dev.chanpay.com/receive.php");
        req.setMemo("备注");
        //
        req.setOutTradeNo("1500437560445");
        req.setMchId("200000140001");
        req.setSubMchId("");
        req.setTradeType("11");
        req.setBankCode("WXPAY");
        req.setAppId("wx90192dels817xla0");
        req.setDeviceInfo("wx90192dels817xla0");
        req.setCurrency("CNY");
        req.setTradeAmount(0.02);
        req.setEnsureAmount(null);
        req.setGoodsName("11");
        req.setTradeMemo("1111");
        req.setSubject("0153");
        req.setOrderStartTime(orderStartTime);
        req.setOrderEndTime(null);
        req.setLimitCreditPay("");
        req.setNotifyUrl("http://www.baidu.com");
        req.setSpBillCreateIp("127.0.0.1");
        req.setSplitList(null);
        req.setExt("{'ext':'ext1'}");

        Map map = conversionService.convert(req, Map.class);
        String actualJoinStr = SignUtils.joinStr(map);
        String expectedJoinStr = "AppId=wx90192dels817xla0&BankCode=WXPAY&Currency=CNY&DeviceInfo=wx90192dels817xla0&Ext={'ext':'ext1'}&GoodsName=11&InputCharset=UTF-8&MchId=200000140001&Memo=备注&NotifyUrl=http://www.baidu.com&OrderStartTime=20170719121240&OutTradeNo=1500437560445&PartnerId=200000140001&ReturnUrl=http://dev.chanpay.com/receive.php&Service=mag_init_code_pay&SpbillCreateIp=127.0.0.1&Subject=0153&TradeAmount=0.02&TradeDate=20170719&TradeMemo=1111&TradeTime=121240&TradeType=11&Version=1.0";
        assertThat(actualJoinStr).isEqualTo(expectedJoinStr);

        byte[] bytes = SignUtils.getBytes(actualJoinStr, "UTF-8");
        PrivateKey priKey = SignUtils.toRsaPriKey(props.getScan().getMchPriKey());

        String actualSign = SignUtils.rsaSign(bytes, priKey);
        assertThat(actualSign).isEqualTo(expectedSign);

        PublicKey pubKey = SignUtils.toRsaPubKey(props.getScan().getMchPubKey());

        boolean verifyResult = SignUtils.rsaSignVerify(bytes, expectedSign, pubKey);
        assertThat(verifyResult).isTrue();

    }


    @Test
    public void verifySignWithRsa01() {

        String sign = "uERyn9W/b8I88bAVyaXUXXpyw0Ir5D3da6WiO5qrpDrvpgBmDzrYWt2wjZsu6CZdgxZ3+VSdRszrCKJM0UxUGqqKkf0gg90DFlGPMqloZHBzemXSoU2Zz/XYc7/CXWoi3+ZYk43dMhbh/S++RQFBOq+abkiGeD6QNlm4TUiJ7os=";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("notify_id", "448b7e8b93694e958eae50e295617033");
        paramMap.put("notify_type", "trade_status_sync");
        paramMap.put("notify_time", "20170705104121");
        paramMap.put("_input_charset", "UTF-8");
        paramMap.put("version", "1.0");
        paramMap.put("outer_trade_no", "0001149922237380501132458");
        paramMap.put("inner_trade_no", "101149922237409129479");
        paramMap.put("trade_status", "TRADE_SUCCESS");
        paramMap.put("trade_amount", "0.01");
        paramMap.put("gmt_create", "20170705104121");
        paramMap.put("gmt_payment", "20170705104121");
        // paramMap.put("gmt_close", "");
        paramMap.put("extension", "{}");


        String joinStr = SignUtils.joinStr(paramMap);
        byte[] bytes = SignUtils.getBytes(joinStr, "UTF-8");

        PublicKey pubKey = SignUtils.toRsaPubKey(props.getScan().getMchPubKey());

        boolean result = SignUtils.rsaSignVerify(bytes, sign, pubKey);

        assertThat(result).isTrue();
    }


    /*

    {"AcceptStatus":"F","InputCharset":"UTF-8","PartnerId":"200000140001","RetCode":"SCP_RESPONSE_FAIL","RetMsg":"预下单失败！","Sign":"wUOC555PAbkWSMRlb3SHVl3H/Js8ZT/sRsxCgTIlkh5/1vgtPMjqOfKIFK4PMmWyywlqvnSZdet+v18al0SJcemITL8KL7QQiVrjGJ5Xv43OXkRruGUuBKmJL50eO2MqZlMdcuWqKz5IuBOiq/WDG63yvMkx9QPLUh3i9aIMp8E=","SignType":"RSA","TradeDate":"20170719","TradeTime":"211607"}

    {AcceptStatus=S,
    TradeDate=20170719, TradeTime=210358,
    InputCharset=UTF-8,
    Sign=d05j00ewo3OB8qBLQPtUU0cSuuDiUcmTZejKIuiVbttyYDry8cRYPpaLnr70eYuWNVkoOsE0Rv1SyuXctzRh71l6Fpt0KhmV3pGU0Z48DPMAWI04ZNySRynTaqY8/24vwnB9iTfUJFXgXZZpmHAAquAvudcgx2sH417jRcRngMc=,
    SignType=RSA, RetCode=SYSTEM_SUCCESS,
    RetMsg=成功, InnerTradeNo=101150046943739104250,
    CodeUrl=weixin://wxpay/bizpayurl?pr=nCmgE2P,
    Status=1}
    AcceptStatus=S&CodeUrl=weixin://wxpay/bizpayurl?pr=nCmgE2P&InnerTradeNo=101150046943739104250&InputCharset=UTF-8&RetCode=SYSTEM_SUCCESS&RetMsg=成功&Status=1&TradeDate=20170719&TradeTime=210358
    AcceptStatus=S&CodeUrl=weixin://wxpay/bizpayurl?pr=nCmgE2P&InnerTradeNo=101150046943739104250&InputCharset=UTF-8&RetCode=SYSTEM_SUCCESS&RetMsg=成功&Status=1&TradeDate=20170719&TradeTime=210358
     */
    @Test
    public void verifySignWithRsa02() {

        String sign = "d05j00ewo3OB8qBLQPtUU0cSuuDiUcmTZejKIuiVbttyYDry8cRYPpaLnr70eYuWNVkoOsE0Rv1SyuXctzRh71l6Fpt0KhmV3pGU0Z48DPMAWI04ZNySRynTaqY8/24vwnB9iTfUJFXgXZZpmHAAquAvudcgx2sH417jRcRngMc=";

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("AcceptStatus", "S");
        paramMap.put("TradeDate", "20170719");
        paramMap.put("TradeTime", "210358");
        paramMap.put("InputCharset", "UTF-8");
        paramMap.put("Sign", sign);
        paramMap.put("SignType", "RSA");
        //
        paramMap.put("RetCode", "SYSTEM_SUCCESS");
        paramMap.put("RetMsg", "成功");
        paramMap.put("InnerTradeNo", "101150046943739104250");
        paramMap.put("CodeUrl", "weixin://wxpay/bizpayurl?pr=nCmgE2P");
        paramMap.put("Status", "1");

        String joinStr = SignUtils.joinStr(paramMap);
        logger.debug("verifySignWithRsa02# joinStr = " + joinStr);

        byte[] bytes = SignUtils.getBytes(joinStr, "UTF-8");

        PublicKey pubKey = SignUtils.toRsaPubKey(props.getScan().getMchPubKey());

        boolean result = SignUtils.rsaSignVerify(bytes, sign, pubKey);

        assertThat(result).isTrue();
    }

}
