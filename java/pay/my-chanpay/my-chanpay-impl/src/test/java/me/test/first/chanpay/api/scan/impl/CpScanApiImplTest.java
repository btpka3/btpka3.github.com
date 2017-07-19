package me.test.first.chanpay.api.scan.impl;

import com.fasterxml.jackson.core.*;
import me.test.first.chanpay.api.scan.*;
import me.test.first.chanpay.api.scan.dto.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

public class CpScanApiImplTest extends BaseTest {

    @Value("${ut.scan.mchPubKey}")
    String a;

    @Test
    public void userScan01() throws JsonProcessingException {


        UserScanReq req = new UserScanReq();
        req.setService(CpScanApi.S_userScan);
        req.setVersion("1.0");
        req.setPartnerId("200000140001");
        req.setTradeTime(new Date());
        req.setReturnUrl("http://dev.chanpay.com/receive.php");
        req.setMemo("备注");


        req.setOutTradeNo(Long.toString(System.currentTimeMillis()));
        req.setMchId("200000140001");
        req.setSubMchId("");
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
        Map map = new LinkedHashMap();
        map.put("req", req);
        map.put("resp", resp);


        System.out.println(objectMapper.writeValueAsString(map));
    }
}
