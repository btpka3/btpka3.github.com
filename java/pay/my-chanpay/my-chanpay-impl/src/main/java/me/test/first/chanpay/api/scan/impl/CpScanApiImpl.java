package me.test.first.chanpay.api.scan.impl;

import com.fasterxml.jackson.databind.*;
import me.test.first.chanpay.api.scan.*;
import me.test.first.chanpay.api.scan.dto.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.convert.*;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.client.*;

import java.io.*;
import java.util.*;

/**
 *
 */
public class CpScanApiImpl implements CpScanApi {

    public static final Logger logger = LoggerFactory.getLogger(CpScanApiImpl.class);

    private RestTemplate restTemplate;

    private String gatewayUrl;

//    private DtoUtils dtoUtils;

//    private CpScanApiUtils cpScanApiUtils;

    private ObjectMapper objectMapper;
    private String merchantPrivateKey;
    private String merchantPublicKey;
    private boolean verifyRespSign = false;

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

        byte[] reqStrToSignBytes = SignUtils.getBytes(
                SignUtils.joinStr(reqMap),
                req.getInputCharset());

        String sign = SignUtils.rsaSign(
                reqStrToSignBytes,
                SignUtils.toRsaPriKey(merchantPrivateKey));
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
        LinkedMultiValueMap<String, String> reqBody = new LinkedMultiValueMap<>();
        reqBody.setAll(conversionService.convert(req, Map.class));

        HttpEntity<MultiValueMap<String, String>> reqEntity =
                new HttpEntity<>(reqBody, null);

        ResponseEntity<String> respStrEntity = restTemplate.exchange(
                getGatewayUrl(),
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
            throw new RuntimeException(e);
        }
        return resp;
    }


    protected <R extends Req, P extends Resp> P invokeApi(R req, Class<P> respClass) {

        // 计算请求签名
        sign(req);

        // 发送请求
        String respStr = sendReq(req);

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
    public void merchantScan() {

    }

    @Override
    public OneCodePayResp oneCodePay(OneCodePayReq req) {
        return invokeApi(req, OneCodePayResp.class);
    }

    @Override
    public void chanpayWxComPay() {

    }

    @Override
    public void merchantWxComPay() {

    }

    @Override
    public void merchantAliPubPay() {

    }

    @Override
    public void aliWapPay() {

    }

    @Override
    public void confirmEnsureTrade() {

    }

    @Override
    public void queryTrade() {

    }

    @Override
    public void refund() {

    }

    @Override
    public void getDailyTradeFile() {

    }

    @Override
    public void getDailyRefundFile() {

    }
}
