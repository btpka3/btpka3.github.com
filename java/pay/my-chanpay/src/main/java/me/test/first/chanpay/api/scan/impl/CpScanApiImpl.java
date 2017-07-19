package me.test.first.chanpay.api.scan.impl;

import com.fasterxml.jackson.databind.*;
import me.test.first.chanpay.api.scan.*;
import me.test.first.chanpay.api.scan.dto.*;
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

    private RestTemplate restTemplate;

    private String gatewayUrl;

//    private DtoUtils dtoUtils;

//    private CpScanApiUtils cpScanApiUtils;

    private ObjectMapper objectMapper;
    private String merchantPrivateKey;
    private String merchantPublicKey;
    @Autowired
    private ConversionService conversionService;

    // ------------------------------------ getter && setter


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

    @Override
    public UserScanResp userScan(UserScanReq req) {
        String charset = req.getInputCharset();

        // 计算签名
        Map<String, String> reqMap = conversionService.convert(req, Map.class);
        String sign = SignUtils.sign(reqMap,
                charset,
                "RSA",
                merchantPrivateKey);
        req.setSign(sign);
        req.setSignType("RSA");

        LinkedMultiValueMap<String, String> reqBody = new LinkedMultiValueMap<>();
        reqBody.setAll(conversionService.convert(req, Map.class));


        // 发送请求
        HttpEntity<MultiValueMap<String, String>> reqEntity =
                new HttpEntity<>(reqBody, null);

        ResponseEntity<String> respStrEntity = restTemplate.exchange(
                getGatewayUrl(),
                HttpMethod.POST,
                reqEntity,
                String.class);


        String respStr = respStrEntity.getBody();
        UserScanResp resp;
        try {
            resp = objectMapper.readValue(respStr, UserScanResp.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 验证签名
        String respCharset = resp.getInputCharset();
        Map<String, String> respMap = conversionService.convert(resp, Map.class);
        boolean verifed = SignUtils.verifySignWithRsa(
                respMap,
                resp.getSign(),
                respCharset,
                merchantPublicKey);

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
