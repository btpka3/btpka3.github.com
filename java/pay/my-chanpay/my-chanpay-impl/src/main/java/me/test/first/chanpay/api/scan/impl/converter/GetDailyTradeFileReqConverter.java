package me.test.first.chanpay.api.scan.impl.converter;

import com.fasterxml.jackson.databind.*;
import me.test.first.chanpay.api.scan.dto.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.*;
import org.springframework.core.convert.converter.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 *
 */
@Component
public class GetDailyTradeFileReqConverter implements Converter<GetDailyTradeFileReq, Map<String, String>> {

    private static final DateTimeFormatter _transDateFmt = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    private ObjectProvider<ConversionService> conversionServiceProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ZoneId zoneId;

    @Override
    public Map<String, String> convert(GetDailyTradeFileReq src) {

        Map<String, String> map = new LinkedHashMap<>();

        TypeDescriptor reqTd = TypeDescriptor.valueOf(Req.class);
        TypeDescriptor strTd = TypeDescriptor.valueOf(String.class);
        TypeDescriptor mapTd = TypeDescriptor.map(Map.class, strTd, strTd);

        ConversionService conversionService =  conversionServiceProvider.getObject();
        Map<String, String> reqMap = (Map<String, String>) conversionService.convert(src, reqTd, mapTd);

        map.putAll(reqMap);

        Date transDate = src.getTransDate();
        if (transDate != null) {
            LocalDateTime ldt = LocalDateTime.ofInstant(
                    transDate.toInstant(),
                    zoneId);
            map.put("TransDate", _transDateFmt.format(ldt));
        }

        return map;
    }
}
