package me.test.first.chanpay.controller;

import me.test.first.chanpay.api.scan.*;
import me.test.first.chanpay.api.scan.dto.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.convert.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.*;

import java.io.*;
import java.net.*;
import java.util.*;

@RequestMapping("/test/scan")
@Controller
public class TestScScanApiController {


    @Autowired
    private CpScanApi cpScanApi;

    @RequestMapping("")
    @ResponseBody
    public String home() {
        return "home " + new Date();
    }

    @Autowired
    private ConversionService conversionService;

    /**
     *
     */
    @RequestMapping("/userScan/01")
    @ResponseBody
    public Object userScan01() {

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

        System.out.println(createLinkString((Map<String,String>)conversionService.convert(req,Map.class),true));
        System.out.println("--------------");
        UserScanResp resp = cpScanApi.userScan(req);
        Map map = new LinkedHashMap();
        map.put("req", req);
        map.put("resp", resp);
        return map;
    }


    public static String createLinkString(Map<String, String> params,
                                          boolean encode) {

        // params = paraFilter(params);

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        String charset = params.get("Charset");
        if (StringUtils.isEmpty(charset)) {
            charset = params.get("InputCharset");
        }

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

}
