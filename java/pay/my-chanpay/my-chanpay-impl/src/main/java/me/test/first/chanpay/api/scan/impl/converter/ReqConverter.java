package me.test.first.chanpay.api.scan.impl.converter;

import me.test.first.chanpay.api.scan.dto.*;
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
public class ReqConverter implements Converter<Req, Map<String, String>> {

    private static final DateTimeFormatter tradeDateFmt = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter tradeTimeFmt = DateTimeFormatter.ofPattern("HHmmss");

    @Override
    public Map<String, String> convert(Req src) {
        Map<String, String> map = new LinkedHashMap<>();

        String service = src.getService();
        if (!StringUtils.isEmpty(service)) {
            map.put("Service", service);
        }

        String version = src.getVersion();
        if (!StringUtils.isEmpty(version)) {
            map.put("Version", version);
        }

        String partnerId = src.getPartnerId();
        if (!StringUtils.isEmpty(partnerId)) {
            map.put("PartnerId", partnerId);
        }

        String inputCharset = src.getInputCharset();
        if (!StringUtils.isEmpty(inputCharset)) {
            map.put("InputCharset", inputCharset);
        }

        String sign = src.getSign();
        if (!StringUtils.isEmpty(sign)) {
            map.put("Sign", sign);
        }

        Date tradeDateTime = src.getTradeDateTime();
        if (tradeDateTime != null) {
            LocalDateTime ldt = LocalDateTime.ofInstant(
                    tradeDateTime.toInstant(),
                    ZoneId.of("Asia/Shanghai"));
            map.put("TradeDate", tradeDateFmt.format(ldt));
            map.put("TradeTime", tradeTimeFmt.format(ldt));
        }

        String signType = src.getSignType();
        if (!StringUtils.isEmpty(signType)) {
            map.put("SignType", signType);
        }

        String returnUrl = src.getReturnUrl();
        if (!StringUtils.isEmpty(returnUrl)) {
            map.put("ReturnUrl", returnUrl);
        }

        String memo = src.getMemo();
        if (!StringUtils.isEmpty(memo)) {
            map.put("Memo", memo);
        }
        return map;
    }
}
