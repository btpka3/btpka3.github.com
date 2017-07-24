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

import java.util.*;

/**
 *
 */
@Component
public class RefundReqConverter implements Converter<RefundReq, Map<String, String>> {


    @Autowired
    private ObjectProvider<ConversionService> conversionServiceProvider;


    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public Map<String, String> convert(RefundReq src) {

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

        String oriTrxId = src.getOriTrxId();
        if (!StringUtils.isEmpty(oriTrxId)) {
            map.put("OriTrxId", oriTrxId);
        }

        Double refundEnsureAmount = src.getRefundEnsureAmount();
        if (refundEnsureAmount != null) {
            map.put("RefundEnsureAmount", refundEnsureAmount.toString());
        }

        List<RefundReq.Split> splitList = src.getRoyaltyParameters();
        if (splitList != null) {
            try {
                String json = objectMapper.writeValueAsString(splitList);
                map.put("RoyaltyParameters", json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        String notifyUrl = src.getNotifyUrl();
        if (!StringUtils.isEmpty(notifyUrl)) {
            map.put("NotifyUrl", notifyUrl);
        }

        String extension = src.getExtension();
        if (!StringUtils.isEmpty(extension)) {
            map.put("Extension", extension);
        }

        return map;
    }
}
