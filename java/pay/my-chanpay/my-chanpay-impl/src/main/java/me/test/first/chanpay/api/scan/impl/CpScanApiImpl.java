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

    private void verifyRespSign(
            Map<String, String> respMap,
            String respCharset,
            String sign
    ) {

        if (verifyRespSign) {

            String respStrToSign = SignUtils.joinStr(respMap);
            if (logger.isDebugEnabled()) {
                logger.debug("RESPONSE 签名验证字符串 : " + respStrToSign);
            }

            boolean verifed = SignUtils.rsaSignVerify(
                    SignUtils.getBytes(respStrToSign, respCharset),
                    sign,
                    SignUtils.toRsaPubKey(merchantPublicKey));

            Assert.isTrue(verifed, "财付通响应签名验证失败");
        }
    }


    @Override
    public UserScanResp userScan(UserScanReq req) {

        // 计算签名
        Map<String, String> reqMap = conversionService.convert(req, Map.class);
        byte[] reqStrToSignBytes = SignUtils.getBytes(
                SignUtils.joinStr(reqMap),
                req.getInputCharset());

        String sign = SignUtils.rsaSign(
                reqStrToSignBytes,
                SignUtils.toRsaPriKey(merchantPrivateKey));
        req.setSign(sign);
        req.setSignType("RSA");


        // 发送请求
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
            logger.debug("respStr : " + respStr);
        }
        UserScanResp resp;
        try {
            resp = objectMapper.readValue(respStr, UserScanResp.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 验证签名
        if (verifyRespSign) {
            Map<String, String> respMap = conversionService.convert(resp, Map.class);
            if (logger.isDebugEnabled()) {
                logger.debug("respMap : " + respMap);
            }

            verifyRespSign(respMap, resp.getInputCharset(), resp.getSign());
        }


        return resp;
    }

    @Override
    public void merchantScan() {

    }

    @Override
    public void oneCodePay() {

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
