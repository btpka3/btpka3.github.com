package me.test.first.chanpay.api.scan.impl;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import me.test.first.chanpay.api.scan.dto.*;
import org.springframework.util.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 *
 */
public class DtoUtils {


    private static final DateTimeFormatter _Req_tradeDateFmt = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter _Req_tradeTimeFmt = DateTimeFormatter.ofPattern("HHmmss");

    private static final DateTimeFormatter _UserScanReq_orderStartTimeFmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter _UserScanReq_orderEndTimeFmt = _UserScanReq_orderStartTimeFmt;


    private ZoneId zoneId = ZoneId.of("Asia/Shanghai");

    private ObjectMapper objectMapper;

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    Map<String, String> toMap(Req req) {
        Map<String, String> map = new LinkedHashMap<>();

        String service = req.getService();
        if (!StringUtils.isEmpty(service)) {
            map.put("Service", service);
        }

        String version = req.getVersion();
        if (!StringUtils.isEmpty(version)) {
            map.put("Version", version);
        }

        String partnerId = req.getPartnerId();
        if (!StringUtils.isEmpty(partnerId)) {
            map.put("PartnerId", partnerId);
        }

        String inputCharset = req.getInputCharset();
        if (!StringUtils.isEmpty(inputCharset)) {
            map.put("InputCharset", inputCharset);
        }

        String sign = req.getSign();
        if (!StringUtils.isEmpty(sign)) {
            map.put("Sign", sign);
        }

        Date tradeTime = req.getTradeTime();
        if (tradeTime != null) {
            LocalDateTime ldt = LocalDateTime.ofInstant(
                    tradeTime.toInstant(),
                    ZoneId.of("Asia/Shanghai"));
            map.put("TradeDate", _Req_tradeDateFmt.format(ldt));
            map.put("TradeTime", _Req_tradeTimeFmt.format(ldt));
        }

        String signType = req.getSignType();
        if (!StringUtils.isEmpty(signType)) {
            map.put("SignType", signType);
        }

        String returnUrl = req.getReturnUrl();
        if (!StringUtils.isEmpty(returnUrl)) {
            map.put("ReturnUrl", returnUrl);
        }

        String memo = req.getMemo();
        if (!StringUtils.isEmpty(memo)) {
            map.put("Memo", memo);
        }
        return map;
    }


    Map<String, String> toMap(UserScanReq req) {
        Map<String, String> map = new LinkedHashMap<>();
        map.putAll(toMap((Req) req));

        String outTradeNo = req.getOutTradeNo();
        if (!StringUtils.isEmpty(outTradeNo)) {
            map.put("OutTradeNo", outTradeNo);
        }

        String mchId = req.getMchId();
        if (!StringUtils.isEmpty(mchId)) {
            map.put("MchId", mchId);
        }

        String subMchId = req.getSubMchId();
        if (!StringUtils.isEmpty(subMchId)) {
            map.put("SubMchId", subMchId);
        }

        String tradeType = req.getTradeType();
        if (!StringUtils.isEmpty(tradeType)) {
            map.put("TradeType", tradeType);
        }

        String bankCode = req.getBankCode();
        if (!StringUtils.isEmpty(bankCode)) {
            map.put("BankCode", bankCode);
        }

        String appId = req.getAppId();
        if (!StringUtils.isEmpty(appId)) {
            map.put("AppId", appId);
        }

        String deviceInfo = req.getDeviceInfo();
        if (!StringUtils.isEmpty(deviceInfo)) {
            map.put("DeviceInfo", deviceInfo);
        }

        String currency = req.getCurrency();
        if (!StringUtils.isEmpty(currency)) {
            map.put("Currency", currency);
        }

        Double tradeAmount = req.getTradeAmount();
        if (tradeAmount != null) {
            map.put("TradeAmount", tradeAmount.toString());
        }

        Double ensureAmount = req.getEnsureAmount();
        if (ensureAmount != null) {
            map.put("EnsureAmount", ensureAmount.toString());
        }

        String goodsName = req.getGoodsName();
        if (!StringUtils.isEmpty(goodsName)) {
            map.put("GoodsName", goodsName);
        }

        String tradeMemo = req.getTradeMemo();
        if (!StringUtils.isEmpty(tradeMemo)) {
            map.put("TradeMemo", tradeMemo);
        }

        String subject = req.getSubject();
        if (!StringUtils.isEmpty(subject)) {
            map.put("Subject", subject);
        }

        Date orderStartTime = req.getOrderStartTime();
        if (orderStartTime != null) {
            LocalDateTime ldt = LocalDateTime.ofInstant(
                    orderStartTime.toInstant(),
                    zoneId);
            map.put("OrderStartTime", _UserScanReq_orderStartTimeFmt.format(ldt));
        }

        Date orderEndTime = req.getOrderEndTime();
        if (orderEndTime != null) {
            LocalDateTime ldt = LocalDateTime.ofInstant(
                    orderStartTime.toInstant(),
                    zoneId);
            map.put("OrderEndTime", _UserScanReq_orderEndTimeFmt.format(ldt));
        }

        String limitCreditPay = req.getLimitCreditPay();
        if (!StringUtils.isEmpty(limitCreditPay)) {
            map.put("LimitCreditPay", limitCreditPay);
        }

        String notifyUrl = req.getNotifyUrl();
        if (!StringUtils.isEmpty(notifyUrl)) {
            map.put("NotifyUrl", notifyUrl);
        }

        String spBillCreateIp = req.getSpBillCreateIp();
        if (!StringUtils.isEmpty(spBillCreateIp)) {
            map.put("SpbillCreateIp", spBillCreateIp);
        }

        List<UserScanReq.Split> splitList = req.getSplitList();
        if (splitList != null) {

            try {
                String json = objectMapper.writeValueAsString(splitList);
                map.put("SplitList", json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        String ext = req.getExt();
        if (!StringUtils.isEmpty(ext)) {
            map.put("Ext", ext);
        }

        return map;
    }

}
