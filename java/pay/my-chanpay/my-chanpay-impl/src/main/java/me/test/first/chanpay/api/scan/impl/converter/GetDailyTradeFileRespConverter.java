package me.test.first.chanpay.api.scan.impl.converter;

import me.test.first.chanpay.api.scan.dto.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.*;
import org.springframework.core.convert.converter.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 *
 */
@Component
public class GetDailyTradeFileRespConverter implements Converter<GetDailyTradeFileResp, Map<String, String>> {

    @Autowired
    private ObjectProvider<ConversionService> conversionServiceProvider;

    @Override
    public Map<String, String> convert(GetDailyTradeFileResp src) {

        TypeDescriptor reqTd = TypeDescriptor.valueOf(Req.class);
        TypeDescriptor strTd = TypeDescriptor.valueOf(String.class);
        TypeDescriptor mapTd = TypeDescriptor.map(Map.class, strTd, strTd);

        ConversionService conversionService =  conversionServiceProvider.getObject();
        return (Map<String, String>) conversionService.convert(src, reqTd, mapTd);
    }
}
