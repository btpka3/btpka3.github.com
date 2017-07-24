package me.test.first.chanpay.api.scan.impl.converter;

import groovy.lang.*;
import me.test.first.chanpay.api.scan.dto.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.convert.*;
import org.springframework.core.convert.converter.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.util.*;

/**
 *
 */
@Component
public class QueryTradeRespConverter implements Converter<QueryTradeResp, Map<String, String>> {

    @Autowired
    private ObjectProvider<ConversionService> conversionServiceProvider;

    @Override
    public Map<String, String> convert(QueryTradeResp src) {

        Map<String, String> map = new LinkedHashMap<>();

        TypeDescriptor respTd = TypeDescriptor.valueOf(Resp.class);
        TypeDescriptor strTd = TypeDescriptor.valueOf(String.class);
        TypeDescriptor mapTd = TypeDescriptor.map(Map.class, strTd, strTd);

        ConversionService conversionService =  conversionServiceProvider.getObject();
        Map<String, String> respMap = (Map<String, String>) conversionService.convert(src, respTd, mapTd);

        map.putAll(respMap);

        String trxId = src.getTrxId();
        if (!StringUtils.isEmpty(trxId)) {
            map.put("TrxId", trxId);
        }

        String orderTrxId = src.getOrderTrxId();
        if (!StringUtils.isEmpty(orderTrxId)) {
            map.put("OrderTrxId", orderTrxId);
        }

        String status = src.getStatus();
        if (!StringUtils.isEmpty(status)) {
            map.put("Status", status);
        }

        String retCode = src.getRetCode();
        if (!StringUtils.isEmpty(retCode)) {
            map.put("RetCode", retCode);
        }

        String retMsg = src.getRetMsg();
        if (!StringUtils.isEmpty(retMsg)) {
            map.put("RetMsg", retMsg);
        }

        String extension = src.getExtension();
        if (!StringUtils.isEmpty(extension)) {
            map.put("Extension", extension);
        }

        return map;
    }
}
