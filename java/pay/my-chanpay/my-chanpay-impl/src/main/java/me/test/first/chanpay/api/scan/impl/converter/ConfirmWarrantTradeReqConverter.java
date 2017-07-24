package me.test.first.chanpay.api.scan.impl.converter;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import me.test.first.chanpay.api.scan.dto.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.*;
import org.springframework.core.convert.converter.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.time.*;
import java.util.*;

/**
 *
 */
@Component
public class ConfirmWarrantTradeReqConverter implements Converter<ConfirmWarrantTradeReq, Map<String, String>> {


    @Autowired
    private ObjectProvider<ConversionService> conversionServiceProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Map<String, String> convert(ConfirmWarrantTradeReq src) {

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

        String outTradeNo = src.getOutTradeNo();
        if (!StringUtils.isEmpty(outTradeNo)) {
            map.put("OutTradeNo", outTradeNo);
        }

        List<ConfirmWarrantTradeReq.Split> splitList = src.getRoyaltyParameters();
        if (splitList != null) {
            try {
                String json = objectMapper.writeValueAsString(splitList);
                map.put("RoyaltyParameters", json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        String ext = src.getExt();
        if (!StringUtils.isEmpty(ext)) {
            map.put("Ext", ext);
        }
        return map;
    }
}
