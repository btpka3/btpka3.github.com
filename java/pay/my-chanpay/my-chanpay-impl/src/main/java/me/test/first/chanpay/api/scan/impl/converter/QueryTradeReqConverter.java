package me.test.first.chanpay.api.scan.impl.converter;

import me.test.first.chanpay.api.scan.dto.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.*;
import org.springframework.core.convert.converter.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.util.*;

/**
 *
 */
@Component
public class QueryTradeReqConverter implements Converter<QueryTradeReq, Map<String, String>> {


    @Autowired
    private ObjectProvider<ConversionService> conversionServiceProvider;


    @Override
    public Map<String, String> convert(QueryTradeReq src) {

        Map<String, String> map = new LinkedHashMap<>();

        TypeDescriptor reqTd = TypeDescriptor.valueOf(Req.class);
        TypeDescriptor strTd = TypeDescriptor.valueOf(String.class);
        TypeDescriptor mapTd = TypeDescriptor.map(Map.class, strTd, strTd);

        ConversionService conversionService =  conversionServiceProvider.getObject();
        Map<String, String> reqMap = (Map<String, String>) conversionService.convert(src, reqTd, mapTd);

        map.putAll(reqMap);

        String trxId = src.getTrxId();
        if (!StringUtils.isEmpty(trxId)) {
            map.put("TrxId", trxId);
        }

        String orderTrxId = src.getOrderTrxId();
        if (!StringUtils.isEmpty(orderTrxId)) {
            map.put("OrderTrxId", orderTrxId);
        }

        String tradeType = src.getTradeType();
        if (!StringUtils.isEmpty(tradeType)) {
            map.put("TradeType", tradeType);
        }

        String extension = src.getExtension();
        if (!StringUtils.isEmpty(extension)) {
            map.put("Extension", extension);
        }
        return map;
    }
}
