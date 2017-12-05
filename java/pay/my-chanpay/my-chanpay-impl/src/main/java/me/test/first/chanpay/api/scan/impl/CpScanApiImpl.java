package me.test.first.chanpay.api.scan.impl;

import com.fasterxml.jackson.databind.*;
import me.test.first.chanpay.api.*;
import me.test.first.chanpay.api.scan.*;
import me.test.first.chanpay.api.scan.dto.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.convert.*;
import org.springframework.http.*;
import org.springframework.messaging.core.*;
import org.springframework.util.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.*;

/**
 *
 */
public class CpScanApiImpl implements CpScanApi {

    public static final Logger logger = LoggerFactory.getLogger(CpScanApiImpl.class);

    private RestTemplate restTemplate;

    private String gatewayUrl;

    private ObjectMapper objectMapper;
    private String merchantPrivateKey;
    private String merchantPublicKey;
    private boolean verifyRespSign = false;

    private MessageSendingOperations msgOps;

    @Autowired
    private ConversionService conversionService;

    // ------------------------------------ getter && setter


    public boolean isVerifyRespSign() {
        return verifyRespSign;
    }

    public void setVerifyRespSign(boolean verifyRespSign) {
        this.verifyRespSign = verifyRespSign;
    }

    public String getMerchantPublicKey() {
        return merchantPublicKey;
    }

    public void setMerchantPublicKey(String merchantPublicKey) {
        this.merchantPublicKey = merchantPublicKey;
    }

    public String getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public void setMerchantPrivateKey(String merchantPrivateKey) {
        this.merchantPrivateKey = merchantPrivateKey;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    protected void sign(Req req) {
        Map<String, String> reqMap = conversionService.convert(req, Map.class);
        if (logger.isDebugEnabled()) {
            logger.debug("请求 - 请求Map : " + reqMap);
        }

        String joinStr = SignUtils.joinStr(reqMap);
        if (logger.isDebugEnabled()) {
            logger.debug("请求 - joinStr : " + joinStr);
        }

        byte[] reqStrToSignBytes = SignUtils.getBytes(
                joinStr,
                req.getInputCharset());

        String sign = SignUtils.rsaSign(
                reqStrToSignBytes,
                SignUtils.toRsaPriKey(merchantPrivateKey));

        if (logger.isDebugEnabled()) {
            logger.debug("请求 - sign : " + sign);
        }
        req.setSignType("RSA");
        req.setSign(sign);
    }

    protected void verifyRespSign(
            Resp resp,
            String respCharset,
            String sign
    ) {

        if (!verifyRespSign) {
            return;
        }

        Map<String, String> respMap = conversionService.convert(resp, Map.class);
        if (logger.isDebugEnabled()) {
            logger.debug("响应 - 响应Map : " + respMap);
        }

        String respStrToSign = SignUtils.joinStr(respMap);
        if (logger.isDebugEnabled()) {
            logger.debug("响应 - 待计算签名字符串 : " + respStrToSign);
        }

        boolean verifed = SignUtils.rsaSignVerify(
                SignUtils.getBytes(respStrToSign, respCharset),
                sign,
                SignUtils.toRsaPubKey(merchantPublicKey));

        Assert.isTrue(verifed, "响应 - 签名验证失败");
    }

    protected <T extends Req> String sendReq(T req) {
        return sendReq(req, true);
    }

    protected <T extends Req> String sendReq(T req, boolean paramInUrl) {

        if (msgOps != null) {
            msgOps.convertAndSend(req);
        }

        LinkedMultiValueMap<String, String> reqParams = new LinkedMultiValueMap<>();
        reqParams.setAll(conversionService.convert(req, Map.class));

        HttpEntity<MultiValueMap<String, String>> reqEntity = new HttpEntity<>(reqParams, null);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(getGatewayUrl());
        URI apiUri = uriBuilder.build().toUri();

        if (paramInUrl) {

            // 注意：这里使用手动 编码，因为 UriComponentsBuilder 对 URL参数的编码机制与 URLEncoder 不一致：
            // origin text              : http://dev.chanpay.com/receive.php
            // UriComponentsBuilder     : http://dev.chanpay.com/receive.php
            // URLEncoder               : http%3A%2F%2Fdev.chanpay.com%2Freceive.php
            LinkedMultiValueMap<String, String> encodedReqParams = new LinkedMultiValueMap<>();
            Map<String, String> encodedMap = reqParams.toSingleValueMap()
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            entry -> entry.getValue(),
                            entry -> {
                                try {
                                    return URLEncoder.encode(entry.getValue(), "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    throw new CpApiException(e);
                                }
                            }));
            encodedReqParams.setAll(encodedMap);

            uriBuilder.queryParams(encodedReqParams);
            apiUri = uriBuilder.build(false).toUri();

        } else {

            // 而 request body 中的则是由 FormHttpMessageConverter 调用 URLEncoder 处理的。
            reqEntity = new HttpEntity<>(reqParams, null);
        }

        ResponseEntity<String> respStrEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.POST,
                reqEntity,
                String.class);

        String respStr = respStrEntity.getBody();
        if (logger.isDebugEnabled()) {
            logger.debug("响应 - JSON 字符串 : " + respStr);
        }
        return respStr;
    }

    protected <T> T toResp(String json, Class<T> clazz) {
        T resp;
        try {
            resp = objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new CpApiException(e);
        }
        return resp;
    }


    protected <R extends Req, P extends Resp> P invokeApi(
            R req,
            Class<P> respClass
    ) {
        return invokeApi(req, respClass, false);

    }

    protected <R extends Req, P extends Resp> P invokeApi(
            R req,
            Class<P> respClass,
            boolean paramInUrl
    ) {

        // 计算请求签名
        sign(req);

        // 发送请求
        String respStr = sendReq(req, paramInUrl);

        // JSON -> Resp
        P resp = toResp(respStr, respClass);

        // 验证响应签名
        verifyRespSign(resp, resp.getInputCharset(), resp.getSign());

        return resp;
    }


    @Override
    public UserScanResp userScan(UserScanReq req) {
        return invokeApi(req, UserScanResp.class);
    }

    @Override
    public MerchantScanResp merchantScan(MerchantScanReq req) {
        return invokeApi(req, MerchantScanResp.class);
    }

    @Override
    public OneCodePayResp oneCodePay(OneCodePayReq req) {
        return invokeApi(req, OneCodePayResp.class);
    }

    @Override
    public ChanpayWxComPayResp chanpayWxComPay(ChanpayWxComPayReq req) {
        return invokeApi(req, ChanpayWxComPayResp.class);
    }

    @Override
    public MerchantWxComPayResp merchantWxComPay(MerchantWxComPayReq req) {
        return invokeApi(req, MerchantWxComPayResp.class);
    }

    @Override
    public MerchantAliPubPayResp merchantAliPubPay(MerchantAliPubPayReq req) {
        return invokeApi(req, MerchantAliPubPayResp.class);
    }

    @Override
    public AliWapPayResp aliWapPay(AliWapPayReq req) {
        return invokeApi(req, AliWapPayResp.class);
    }

    @Override
    public ConfirmWarrantTradeResp confirmWarrantTrade(ConfirmWarrantTradeReq req) {
        return invokeApi(req, ConfirmWarrantTradeResp.class);
    }

    @Override
    public QueryTradeResp queryTrade(QueryTradeReq req) {
        return invokeApi(req, QueryTradeResp.class);
    }

    @Override
    public RefundResp refund(RefundReq req) {
        return invokeApi(req, RefundResp.class);
    }

    @Override
    public GetDailyTradeFileResp getDailyTradeFile(GetDailyTradeFileReq req) {
        return invokeApi(req, GetDailyTradeFileResp.class);
    }

    @Override
    public GetDailyRefundFileResp getDailyRefundFile(GetDailyRefundFileReq req) {
        return invokeApi(req, GetDailyRefundFileResp.class);
    }
}
