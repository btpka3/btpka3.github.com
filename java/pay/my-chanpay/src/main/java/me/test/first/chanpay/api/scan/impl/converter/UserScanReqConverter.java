package me.test.first.chanpay.api.scan.impl.converter;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import me.test.first.chanpay.api.scan.dto.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.convert.*;
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
public class UserScanReqConverter implements Converter<UserScanReq, Map<String, String>> {

    private static final DateTimeFormatter _orderStartTimeFmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter _orderEndTimeFmt = _orderStartTimeFmt;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ZoneId zoneId;


    @Override
    public Map<String, String> convert(UserScanReq src) {

        Map<String, String> map = new LinkedHashMap<>();


        TypeDescriptor reqTd = TypeDescriptor.valueOf(Req.class);
        TypeDescriptor strTd = TypeDescriptor.valueOf(String.class);
        TypeDescriptor mapTd = TypeDescriptor.map(Map.class, strTd, strTd);

        Map<String, String> reqMap = (Map<String, String>) conversionService.convert(src, reqTd, mapTd);

        map.putAll(reqMap);


        String outTradeNo = src.getOutTradeNo();
        if (!StringUtils.isEmpty(outTradeNo)) {
            map.put("OutTradeNo", outTradeNo);
        }

        String mchId = src.getMchId();
        if (!StringUtils.isEmpty(mchId)) {
            map.put("MchId", mchId);
        }

        String subMchId = src.getSubMchId();
        if (!StringUtils.isEmpty(subMchId)) {
            map.put("SubMchId", subMchId);
        }

        String tradeType = src.getTradeType();
        if (!StringUtils.isEmpty(tradeType)) {
            map.put("TradeType", tradeType);
        }

        String bankCode = src.getBankCode();
        if (!StringUtils.isEmpty(bankCode)) {
            map.put("BankCode", bankCode);
        }

        String appId = src.getAppId();
        if (!StringUtils.isEmpty(appId)) {
            map.put("AppId", appId);
        }

        String deviceInfo = src.getDeviceInfo();
        if (!StringUtils.isEmpty(deviceInfo)) {
            map.put("DeviceInfo", deviceInfo);
        }

        String currency = src.getCurrency();
        if (!StringUtils.isEmpty(currency)) {
            map.put("Currency", currency);
        }


        Double tradeAmount = src.getTradeAmount();
        if (tradeAmount != null) {
            map.put("TradeAmount", tradeAmount.toString());
        }

        Double ensureAmount = src.getEnsureAmount();
        if (ensureAmount != null) {
            map.put("EnsureAmount", ensureAmount.toString());
        }

        String goodsName = src.getGoodsName();
        if (!StringUtils.isEmpty(goodsName)) {
            map.put("GoodsName", goodsName);
        }

        String tradeMemo = src.getTradeMemo();
        if (!StringUtils.isEmpty(tradeMemo)) {
            map.put("TradeMemo", tradeMemo);
        }

        String subject = src.getSubject();
        if (!StringUtils.isEmpty(subject)) {
            map.put("Subject", subject);
        }
        Date orderStartTime = src.getOrderStartTime();
        if (orderStartTime != null) {
            LocalDateTime ldt = LocalDateTime.ofInstant(
                    orderStartTime.toInstant(),
                    zoneId);
            map.put("OrderStartTime", _orderStartTimeFmt.format(ldt));
        }

        Date orderEndTime = src.getOrderEndTime();
        if (orderEndTime != null) {
            LocalDateTime ldt = LocalDateTime.ofInstant(
                    orderStartTime.toInstant(),
                    zoneId);
            map.put("OrderEndTime", _orderEndTimeFmt.format(ldt));
        }

        String limitCreditPay = src.getLimitCreditPay();
        if (!StringUtils.isEmpty(limitCreditPay)) {
            map.put("LimitCreditPay", limitCreditPay);
        }

        String notifyUrl = src.getNotifyUrl();
        if (!StringUtils.isEmpty(notifyUrl)) {
            map.put("NotifyUrl", notifyUrl);
        }

        String spBillCreateIp = src.getSpBillCreateIp();
        if (!StringUtils.isEmpty(spBillCreateIp)) {
            map.put("SpbillCreateIp", spBillCreateIp);
        }

        List<UserScanReq.Split> splitList = src.getSplitList();
        if (splitList != null) {

            try {
                String json = objectMapper.writeValueAsString(splitList);
                map.put("SplitList", json);
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
