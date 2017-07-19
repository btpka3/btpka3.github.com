package me.test.first.chanpay.api.scan.impl.converter;

import me.test.first.chanpay.api.scan.dto.*;
import org.springframework.core.convert.converter.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.util.*;

/**
 *
 */
@Component
public class RespConverter implements Converter<Resp, Map<String, String>> {

    @Override
    public Map<String, String> convert(Resp src) {

        Map<String, String> map = new LinkedHashMap<>();

        String acceptStatus = src.getAcceptStatus();
        if (!StringUtils.isEmpty(acceptStatus)) {
            map.put("AcceptStatus", acceptStatus);
        }

        String partnerId = src.getPartnerId();
        if (!StringUtils.isEmpty(partnerId)) {
            map.put("PartnerId", partnerId);
        }

        String tradeDate = src.getTradeDate();
        if (!StringUtils.isEmpty(tradeDate)) {
            map.put("TradeDate", tradeDate);
        }

        String tradeTime = src.getTradeTime();
        if (!StringUtils.isEmpty(tradeTime)) {
            map.put("TradeTime", tradeTime);
        }

        String inputCharset = src.getInputCharset();
        if (!StringUtils.isEmpty(inputCharset)) {
            map.put("InputCharset", inputCharset);
        }

        String mchId = src.getMchId();
        if (!StringUtils.isEmpty(mchId)) {
            map.put("MchId", mchId);
        }

        String memo = src.getMemo();
        if (!StringUtils.isEmpty(memo)) {
            map.put("Memo", memo);
        }

        String sign = src.getSign();
        if (!StringUtils.isEmpty(sign)) {
            map.put("Sign", sign);
        }

        String signType = src.getSignType();
        if (!StringUtils.isEmpty(signType)) {
            map.put("SignType", signType);
        }
        return map;
    }
}
