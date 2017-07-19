package me.test.first.chanpay.api.scan.impl.converter;

import me.test.first.chanpay.api.scan.*;
import me.test.first.chanpay.api.scan.dto.*;
import org.junit.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

public class UserScanReqConverterTest extends BaseTest {

    @Test
    public void converer01() {

        String sign = "cSDYyKicFjLwTR5V9ftEF+RQJLlUS1IT2nN20bgtWz9qAHXBArW8YjzuBFWE58HKFvg77EcQ83EqSa0Iqko+sHHiFP+I/DBV9CTNRJ/zQXuHr4nJHtc9kEHGQIUx92aIrnC3zJLxuOeLnHFqdUJNYVMDHXmfqKOL+HvKY8p4wFY=";


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
        req.setTradeTime(tradeTime);
        req.setSign(sign);
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

        Map actural = conversionService.convert(req, Map.class);

        Map<String, String> exptected = new HashMap<>();
        // 基本参数
        exptected.put("Service", "mag_init_code_pay");
        exptected.put("Version", "1.0");
        exptected.put("PartnerId", "200000140001");
        exptected.put("InputCharset", "UTF-8");
        exptected.put("TradeDate", "20170719");
        exptected.put("TradeTime", "121240");
        exptected.put("Sign", sign);
        exptected.put("SignType", "RSA");
        exptected.put("ReturnUrl", "http://dev.chanpay.com/receive.php");// 前台跳转url
        exptected.put("Memo", "备注");
        //
        exptected.put("OutTradeNo", "1500437560445");
        exptected.put("MchId", "200000140001");
        //exptected.put("SubMchId", "");
        exptected.put("TradeType", "11");
        exptected.put("BankCode", "WXPAY");
        exptected.put("AppId", "wx90192dels817xla0");
        exptected.put("DeviceInfo", "wx90192dels817xla0");
        exptected.put("Currency", "CNY");
        exptected.put("TradeAmount", "0.02");
        //exptected.put("EnsureAmount", "");
        exptected.put("GoodsName", "11");
        exptected.put("TradeMemo", "1111");
        exptected.put("Subject", "0153");
        exptected.put("OrderStartTime", "20170719121240");
        //exptected.put("OrderEndTime", "");
        //exptected.put("LimitCreditPay", "");
        exptected.put("NotifyUrl", "http://www.baidu.com");
        exptected.put("SpbillCreateIp", "127.0.0.1");
        //exptected.put("SplitList", "");
        exptected.put("Ext", "{'ext':'ext1'}");


        assertThat(actural).isEqualTo(exptected);
    }

}
