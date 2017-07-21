package me.test.first.chanpay.api.scan.impl.converter;

import me.test.first.chanpay.api.scan.*;
import me.test.first.chanpay.api.scan.dto.*;
import org.junit.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

public class UserScanRespConverterTest extends BaseTest {

    /**
     * 正常例子
     */
    @Test
    public void converer01() {


        ZonedDateTime zdt = ZonedDateTime.of(2000, 1, 2, 11, 22, 33, 444 * 1000 * 1000, ZoneId.of("Asia/Shanghai"));

        Date tradeTime = Date.from(zdt.toInstant());

        UserScanResp resp = new UserScanResp();
        resp.setAcceptStatus("S");
        resp.setPartnerId("partnerId");
        resp.setTradeDate("20001122");
        resp.setTradeTime("223344");
        resp.setInputCharset("UTF-8");
        resp.setMchId("1111");
        resp.setMemo("memo");
        resp.setSign("sign");
        resp.setSignType("RSA");
        //
        resp.setRetCode("retCode");
        resp.setRetMsg("retMsg");
        resp.setOutTradeNo("outTradeNo");
        resp.setInnerTradeNo("innerTradeNo");
        resp.setCodeUrl("codeUrl");
        resp.setStatus("status");
        resp.setExt("ext");

        Map actural = conversionService.convert(resp, Map.class);
        Map exptected = new HashMap<String, String>();
        exptected.put("AcceptStatus", "S");
        exptected.put("PartnerId", "partnerId");
        exptected.put("TradeDate", "20001122");
        exptected.put("TradeTime", "223344");
        exptected.put("InputCharset", "UTF-8");
        exptected.put("MchId", "1111");
        exptected.put("Memo", "memo");
        exptected.put("Sign", "sign");
        exptected.put("SignType", "RSA");
        //
        exptected.put("RetCode", "retCode");
        exptected.put("RetMsg", "retMsg");
        exptected.put("OutTradeNo", "outTradeNo");
        exptected.put("InnerTradeNo", "innerTradeNo");
        exptected.put("CodeUrl", "codeUrl");
        exptected.put("Status", "status");
        exptected.put("Ext", "ext");

        assertThat(actural).isEqualTo(exptected);
    }


    /**
     * 测试 空字符串
     */
    @Test
    public void converer02() {

        UserScanResp resp = new UserScanResp();
        resp.setAcceptStatus("");
        resp.setPartnerId("");
        resp.setTradeDate("");
        resp.setTradeTime("");
        resp.setInputCharset("");
        resp.setMchId("");
        resp.setMemo("");
        resp.setSign("");
        resp.setSignType("");
        //
        resp.setRetCode("");
        resp.setRetMsg("");
        resp.setOutTradeNo("");
        resp.setInnerTradeNo("");
        resp.setCodeUrl("");
        resp.setStatus("");
        resp.setExt("");

        Map actural = conversionService.convert(resp, Map.class);
        Map exptected = new HashMap<String, String>();

        assertThat(actural).isEqualTo(exptected);
    }


    /**
     * 测试 null
     */
    @Test
    public void converer03() {

        UserScanResp resp = new UserScanResp();

        Map actural = conversionService.convert(resp, Map.class);
        Map exptected = new HashMap<String, String>();

        assertThat(actural).isEqualTo(exptected);
    }

}
