package me.test.first.chanpay.api.scan.impl;

import com.fasterxml.jackson.core.*;
import me.test.first.chanpay.api.scan.*;
import me.test.first.chanpay.api.scan.dto.*;
import org.junit.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

public class CpScanApiImplTest extends BaseTest {

    /**
     * 使用固定的参数，以方便调试签名过程。
     */

    @Test
    public void userScan00() throws JsonProcessingException {
        ZonedDateTime tradeTimeZdt = ZonedDateTime.of(
                2017, 7, 24,
                12, 12, 40,
                444 * 1000 * 1000,
                ZoneId.of("Asia/Shanghai"));

        Date tradeDateTime = Date.from(tradeTimeZdt.toInstant());

        UserScanReq req = new UserScanReq();
        req.setService(CpScanApi.S_userScan);
        req.setVersion("1.0");
        req.setPartnerId("200000140001");
        req.setTradeDateTime(tradeDateTime);
        req.setReturnUrl("http://dev.chanpay.com/receive.php");
        req.setMemo("备注");


//        req.setOutTradeNo(Long.toString(System.currentTimeMillis()));
        req.setOutTradeNo("11223344556633");
        req.setMchId("200000140001");
//        req.setSubMchId("");
        req.setTradeType("11");
        req.setBankCode("WXPAY");
        req.setAppId("wx90192dels817xla0");
        req.setDeviceInfo("wx90192dels817xla0");
        req.setCurrency("CNY");
        req.setTradeAmount(0.02);
//        req.setEnsureAmount(0.02);
        req.setGoodsName("11");

        req.setTradeMemo("1111");
        req.setOrderStartTime(tradeDateTime);
//        req.setOrderEndTime();
        req.setNotifyUrl("http://www.baidu.com");
        req.setSpBillCreateIp("127.0.0.1");
//        req.setSplitList();
        req.setExt("{'ext':'ext1'}");
        req.setSignType("RSA");
        req.setSubject("subject");


        UserScanResp resp = cpScanApi.userScan(req);


        String acceptStatus = resp.getAcceptStatus();
        assertThat("S".equals(acceptStatus) || "R".equals(acceptStatus)).isTrue();
    }

    @Test
    public void userScan01() throws JsonProcessingException {


        UserScanReq req = new UserScanReq();
        req.setService(CpScanApi.S_userScan);
        req.setVersion("1.0");
        req.setPartnerId("200000140001");
        req.setTradeDateTime(new Date());
        req.setReturnUrl("http://dev.chanpay.com/receive.php");
        req.setMemo("备注");


        req.setOutTradeNo(Long.toString(System.currentTimeMillis()));
        req.setMchId("200000140001");
//        req.setSubMchId("");
        req.setTradeType("11");
        req.setBankCode("WXPAY");
        req.setAppId("wx90192dels817xla0");
        req.setDeviceInfo("wx90192dels817xla0");
        req.setCurrency("CNY");
        req.setTradeAmount(0.02);
//        req.setEnsureAmount(0.02);
        req.setGoodsName("11");

        req.setTradeMemo("1111");
        req.setOrderStartTime(new Date());
//        req.setOrderEndTime();
        req.setNotifyUrl("http://www.baidu.com");
        req.setSpBillCreateIp("127.0.0.1");
//        req.setSplitList();
        req.setExt("{'ext':'ext1'}");
        req.setSignType("RSA");
        req.setSubject("subject");


        UserScanResp resp = cpScanApi.userScan(req);


        String acceptStatus = resp.getAcceptStatus();
        assertThat("S".equals(acceptStatus) || "R".equals(acceptStatus)).isTrue();
    }


    @Test
    public void oneCodePay01() throws JsonProcessingException {

        OneCodePayReq req = new OneCodePayReq();
        req.setService(CpScanApi.S_oneCodePay);
        req.setVersion("1.0");
        req.setPartnerId("200000140001");
        req.setTradeDateTime(new Date());
        req.setReturnUrl("http://dev.chanpay.com/receive.php");
        req.setMemo("备注");
        //
        req.setOutTradeNo(Long.toString(System.currentTimeMillis()));
        req.setMchId("200000140001");
//        req.setSubMchId("");
        req.setTradeType("11");
        req.setBusiType("1");
        req.setDeviceInfo("wx90192dels817xla0");
        req.setCurrency("CNY");
        req.setTradeAmount(0.02);
//        req.setEnsureAmount(0.02);
        req.setGoodsName("11");
        req.setTradeMemo("1111");
        req.setSubject("0153");
        req.setOrderStartTime(new Date());
//        req.setOrderEndTime();
        req.setNotifyUrl("http://www.baidu.com");
//        req.setSplitList();
        req.setExt("{'ext':'ext1'}");
        req.setSignType("RSA");

        OneCodePayResp resp = cpScanApi.oneCodePay(req);

        String acceptStatus = resp.getAcceptStatus();
        assertThat("S".equals(acceptStatus) || "R".equals(acceptStatus)).isTrue();
    }

    @Test
    public void chanpayWxComPay01() {
        ChanpayWxComPayReq req = new ChanpayWxComPayReq();
        req.setService(CpScanApi.S_chanpayWxComPay);
        req.setVersion("1.0");
        req.setPartnerId("200000140001");
        req.setTradeDateTime(new Date());
        req.setReturnUrl("http://dev.chanpay.com/receive.php");
        req.setMemo("备注");
        //
        req.setOutTradeNo(Long.toString(System.currentTimeMillis()));
        req.setMchId("200000140001");
//        req.setMchId("200000400059");
        req.setSubMchId("");
        req.setTradeType("11");
        req.setBankCode("WXPAY");
        req.setDeviceInfo("wx90192dels817xla0");
        req.setCurrency("CNY");
        req.setTradeAmount(0.02);
        req.setEnsureAmount(null);
        req.setGoodsName("zll'sItem");
        req.setTradeMemo("1111");
        req.setSubject("0153");
        req.setOrderStartTime(new Date());
        req.setOrderEndTime(null);
        req.setNotifyUrl("http://www.baidu.com");
        req.setSpBillCreateIp("127.0.0.1");
        req.setSplitList(null);
        req.setExt("{'ext':'ext1'}");

        ChanpayWxComPayResp resp = cpScanApi.chanpayWxComPay(req);
        String status = resp.getStatus();
        assertThat("0".equals(status) || "1".equals(status)).isTrue();

    }


    // NOTICE: 测试环境不支持该API。
    @Test
    public void merchantWxComPay01() {


        MerchantWxComPayReq req = new MerchantWxComPayReq();

        req.setService(CpScanApi.S_merchantWxComPay);
        req.setVersion("1.0");
        req.setPartnerId("200000140001");
        req.setTradeDateTime(new Date());
        req.setReturnUrl("http://dev.chanpay.com/receive.php");
        req.setMemo("备注");
        //
        req.setOutTradeNo(Long.toString(System.currentTimeMillis()));
//        req.setMchId("200000400059");
        req.setMchId("200000140001");
//        req.setSubMchId("");
        req.setTradeType("11");
        req.setAppId("wx90192dels817xla0");
        req.setDeviceInfo("013467007045764");
        req.setBuyerPayCode("wx013467007045764");
        req.setCurrency("CNY");
        req.setTradeAmount(0.02);
//        req.setEnsureAmount(0.02);
        req.setGoodsName("11");
        req.setTradeMemo("1111");
        req.setSubject("0153");
        req.setOrderStartTime(new Date());
//        req.setOrderEndTime();
        req.setNotifyUrl("http://www.baidu.com");
        req.setSpBillCreateIp("127.0.0.1");
//        req.setSplitList();
        req.setExt("{'ext':'ext1'}");
        req.setSignType("RSA");

        MerchantWxComPayResp resp = cpScanApi.merchantWxComPay(req);

        String acceptStatus = resp.getAcceptStatus();
        assertThat("S".equals(acceptStatus) || "R".equals(acceptStatus)).isTrue();
    }
}
