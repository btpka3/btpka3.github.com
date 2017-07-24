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
public class MerchantAliPubPayRespConverter implements Converter<MerchantAliPubPayResp, Map<String, String>> {

    @Autowired
    private ObjectProvider<ConversionService> conversionServiceProvider;

    @Override
    public Map<String, String> convert(MerchantAliPubPayResp src) {

        Map<String, String> map = new LinkedHashMap<>();

        TypeDescriptor respTd = TypeDescriptor.valueOf(Resp.class);
        TypeDescriptor strTd = TypeDescriptor.valueOf(String.class);
        TypeDescriptor mapTd = TypeDescriptor.map(Map.class, strTd, strTd);

        ConversionService conversionService =  conversionServiceProvider.getObject();
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

        String payData = src.getPayData();
        if (!StringUtils.isEmpty(payData)) {
            map.put("PayData", payData);
        }

        String mchId = src.getMchId();
        if (!StringUtils.isEmpty(mchId)) {
            map.put("MchId", mchId);
        }

        String subMchId = src.getSubMchId();
        if (!StringUtils.isEmpty(subMchId)) {
            map.put("SubMchId", subMchId);
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
