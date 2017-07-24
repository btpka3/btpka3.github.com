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
public class MerchantWxComPayRespConverter implements Converter<MerchantWxComPayResp, Map<String, String>> {

    @Autowired
    private ObjectProvider<ConversionService> conversionServiceProvider;

    @Override
    public Map<String, String> convert(MerchantWxComPayResp src) {

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


        String jsapiAppId = src.getJsapiAppId();
        if (!StringUtils.isEmpty(jsapiAppId)) {
            map.put("JsapiAppId", jsapiAppId);
        }

        String jsapiNonceStr = src.getJsapiNonceStr();
        if (!StringUtils.isEmpty(jsapiNonceStr)) {
            map.put("JsapiNonceStr", jsapiNonceStr);
        }

        String jsapiPackage = src.getJsapiPackage();
        if (!StringUtils.isEmpty(jsapiPackage)) {
            map.put("JsapiPackage", jsapiPackage);
        }

        String jsapiPaySign = src.getJsapiPaySign();
        if (!StringUtils.isEmpty(jsapiPaySign)) {
            map.put("JsapiPaySign", jsapiPaySign);
        }

        String jsapiSignType = src.getJsapiSignType();
        if (!StringUtils.isEmpty(jsapiSignType)) {
            map.put("JsapiSignType", jsapiSignType);
        }

        String jsapiTimeStamp = src.getJsapiTimeStamp();
        if (!StringUtils.isEmpty(jsapiTimeStamp)) {
            map.put("JsapiTimeStamp", jsapiTimeStamp);
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
