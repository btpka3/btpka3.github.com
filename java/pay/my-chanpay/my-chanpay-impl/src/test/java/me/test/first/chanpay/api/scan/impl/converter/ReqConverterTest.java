package me.test.first.chanpay.api.scan.impl.converter;

import me.test.first.chanpay.api.scan.*;
import me.test.first.chanpay.api.scan.dto.*;
import org.junit.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

public class ReqConverterTest extends BaseTest {

    /**
     * 测试正常情况
     */
    @Test
    public void converer01() {


        ZonedDateTime zdt = ZonedDateTime.of(2000, 1, 2, 11, 22, 33, 444 * 1000 * 1000, ZoneId.of("Asia/Shanghai"));

        Date tradeTime = Date.from(zdt.toInstant());

        Req req = new Req();
        req.setService("service");
        req.setVersion("version");
        req.setPartnerId("partnerId");
        req.setTradeTime(tradeTime);
        req.setSign("sign");
        req.setSignType("RSA");
        req.setReturnUrl("http://baidu.com");
        req.setMemo("memo");


        Map actural = conversionService.convert(req, Map.class);
        Map exptected = new HashMap<String, String>();
        exptected.put("Service", "service");
        exptected.put("Version", "version");
        exptected.put("InputCharset", "UTF-8");
        exptected.put("PartnerId", "partnerId");
        exptected.put("TradeDate", "20000102");
        exptected.put("TradeTime", "112233");
        exptected.put("Sign", "sign");
        exptected.put("SignType", "RSA");
        exptected.put("ReturnUrl", "http://baidu.com");
        exptected.put("Memo", "memo");

        assertThat(actural).isEqualTo(exptected);
    }


    /**
     * 测试空字符
     */
    @Test
    public void converer02() {

        Req req = new Req();
        req.setService("");
        req.setVersion("");
        req.setPartnerId("");
        //req.setTradeTime(tradeTime);
        req.setSign("");
        req.setSignType("");
        req.setReturnUrl("");
        req.setMemo("");


        Map actural = conversionService.convert(req, Map.class);
        Map exptected = new HashMap<String, String>();
        exptected.put("InputCharset", "UTF-8");

        assertThat(actural).isEqualTo(exptected);
    }

    /**
     * 测试 null
     */
    @Test
    public void converer03() {

        Req req = new Req();

        Map actural = conversionService.convert(req, Map.class);

        Map exptected = new HashMap<String, String>();
        exptected.put("InputCharset", "UTF-8");
        exptected.put("Version", "1.0");

        assertThat(actural).isEqualTo(exptected);
    }

}
