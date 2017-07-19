package me.test.first.chanpay.controller;

import me.test.first.chanpay.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/scan")
public class MyScanQrCodeController {



    @Autowired
    TradeApi tradeApi;

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "home " + new Date();
    }


    /**
     *
     */
    @RequestMapping("/qpay")
    @ResponseBody
    public Object qpay() {


        TradeApiReq req = new TradeApiReq();
        req.setService("cjt_wap_create_instant_trade");
        req.setVersion("1.0");

        req.setPartnerId("200000400007");
        req.setInputCharset("UTF-8");

        String outTradeNo = (UUID.randomUUID().toString()).replace("-", "");
        req.setOutTradeNo(outTradeNo);
        req.setTradeAmount("0.01");
        req.setProductName("般若的测试商品");
        req.setNotifyUrl("http://dev.chanpay.com/receive.php");

        req.setPayMethod("1");
        req.setPayType("C,GC");
        req.setBankCode("WXPAY");
        req.setIsAnonymous("Y");
        req.setReturnUrl("Y");


        // 以下俩自行选择一个看效果
        // 绑卡
        //req.setExt1("[{'hasUserSign':'true','userSign':'yangyang0test'}]");

        // 绑卡 + wap收银台2.0
        // origMap.put("ext1", "[{'hasUserSign':'true','userSign':'yangyang0test','version':'2.0'}]");


        TradeApiResp resp = tradeApi.execute(req);
        Map map = new LinkedHashMap();
        map.put("req", req);
        map.put("resp", resp);
        return map;
    }

}
