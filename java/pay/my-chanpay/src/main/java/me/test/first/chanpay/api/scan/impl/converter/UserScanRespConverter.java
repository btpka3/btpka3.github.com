package me.test.first.chanpay.api.scan.impl.converter;

import com.fasterxml.jackson.databind.*;
import me.test.first.chanpay.api.scan.dto.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.convert.*;
import org.springframework.core.convert.converter.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 *
 */
@Component
public class UserScanRespConverter implements Converter<UserScanResp, Map<String, String>> {

    private static final DateTimeFormatter _orderStartTimeFmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter _orderEndTimeFmt = _orderStartTimeFmt;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ZoneId zoneId;

    @Override
    public Map<String, String> convert(UserScanResp src) {

        Map<String, String> map = new LinkedHashMap<>();


        TypeDescriptor respTd = TypeDescriptor.valueOf(Resp.class);
        TypeDescriptor strTd = TypeDescriptor.valueOf(String.class);
        TypeDescriptor mapTd = TypeDescriptor.map(Map.class, strTd, strTd);

        Map<String, String> respMap = (Map<String, String>) conversionService.convert(src, respTd, mapTd);

        map.putAll(respMap);

        String retCode = src.getRetCode();
        if (!StringUtils.isEmpty(retCode)) {
            map.put("RetCode", retCode);
        }

        String retMsg = src.getRetMsg();
        if (!StringUtils.isEmpty(retMsg)) {
            map.put("RetMsg", retMsg);
        }

        String outTradeNo = src.getOutTradeNo();
        if (!StringUtils.isEmpty(outTradeNo)) {
            map.put("OutTradeNo", outTradeNo);
        }

        String innerTradeNo = src.getInnerTradeNo();
        if (!StringUtils.isEmpty(innerTradeNo)) {
            map.put("InnerTradeNo", innerTradeNo);
        }

        String codeUrl = src.getCodeUrl();
        if (!StringUtils.isEmpty(codeUrl)) {
            map.put("CodeUrl", codeUrl);
        }

        String status = src.getStatus();
        if (!StringUtils.isEmpty(status)) {
            map.put("Status", status);
        }

        String ext = src.getExt();
        if (!StringUtils.isEmpty(ext)) {
            map.put("Ext", ext);
        }

        return map;
    }
}
