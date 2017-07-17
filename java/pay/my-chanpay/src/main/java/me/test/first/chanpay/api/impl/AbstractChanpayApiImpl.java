package me.test.first.chanpay.api.impl;

import com.fasterxml.jackson.databind.*;
import me.test.first.chanpay.api.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.http.converter.json.*;
import org.springframework.util.*;
import org.springframework.web.client.*;

import java.io.*;

/**
 */
public abstract class AbstractChanpayApiImpl<Req extends BaseReq, Resp extends BaseResp>
        implements ChanpayApi<Req, Resp> {


    /**
     * 畅捷支付平台公钥
     */
    private static String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDv0rdsn5FYPn0EjsCPqDyIsYRawNWGJDRHJBcdCldodjM5bpve+XYb4Rgm36F6iDjxDbEQbp/HhVPj0XgGlCRKpbluyJJt8ga5qkqIhWoOd/Cma1fCtviMUep21hIlg1ZFcWKgHQoGoNX7xMT8/0bEsldaKdwxOlv3qGxWfqNV5QIDAQAB";
    private static String MERCHANT_PUBLIC_KEY_PROD = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPq3oXX5aFeBQGf3Ag/86zNu0VICXmkof85r+DDL46w3vHcTnkEWVbp9DaDurcF7DMctzJngO0u9OG1cb4mn+Pn/uNC1fp7S4JH4xtwST6jFgHtXcTG9uewWFYWKw/8b3zf4fXyRuI/2ekeLSstftqnMQdenVP7XCxMuEnnmM1RwIDAQAB";// 生产环境

    /**
     * 商户私钥
     */
    private static String MERCHANT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO/6rPCvyCC+IMalLzTy3cVBz/+wamCFNiq9qKEilEBDTttP7Rd/GAS51lsfCrsISbg5td/w25+wulDfuMbjjlW9Afh0p7Jscmbo1skqIOIUPYfVQEL687B0EmJufMlljfu52b2efVAyWZF9QBG1vx/AJz1EVyfskMaYVqPiTesZAgMBAAECgYEAtVnkk0bjoArOTg/KquLWQRlJDFrPKP3CP25wHsU4749t6kJuU5FSH1Ao81d0Dn9m5neGQCOOdRFi23cV9gdFKYMhwPE6+nTAloxI3vb8K9NNMe0zcFksva9c9bUaMGH2p40szMoOpO6TrSHO9Hx4GJ6UfsUUqkFFlN76XprwE+ECQQD9rXwfbr9GKh9QMNvnwo9xxyVl4kI88iq0X6G4qVXo1Tv6/DBDJNkX1mbXKFYL5NOW1waZzR+Z/XcKWAmUT8J9AkEA8i0WT/ieNsF3IuFvrIYG4WUadbUqObcYP4Y7Vt836zggRbu0qvYiqAv92Leruaq3ZN1khxp6gZKl/OJHXc5xzQJACqr1AU1i9cxnrLOhS8m+xoYdaH9vUajNavBqmJ1mY3g0IYXhcbFm/72gbYPgundQ/pLkUCt0HMGv89tn67i+8QJBALV6UgkVnsIbkkKCOyRGv2syT3S7kOv1J+eamGcOGSJcSdrXwZiHoArcCZrYcIhOxOWB/m47ymfE1Dw/+QjzxlUCQCmnGFUO9zN862mKYjEkjDN65n1IUB9Fmc1msHkIZAQaQknmxmCIOHC75u4W0PGRyVzq8KkxpNBq62ICl7xmsPM=";


    public static final String GATEWAY_TEST_URL = "https://tpay.chanpay.com/mag/gateway/receiveOrder.do";
    public static final String GATEWAY_URL = GATEWAY_TEST_URL;

    private String gatewayUrl = GATEWAY_TEST_URL;
    private boolean checkErrorCode = true;
    private String signType = "RSA";
    private String key = MERCHANT_PRIVATE_KEY;

    private ObjectMapper objectMapper;

    protected RestTemplate restTemplate;
    private MappingJackson2HttpMessageConverter jsonMsgConvertor;

    private Class<Resp> respClass;


    private Logger logger = LoggerFactory.getLogger(getClass());


    public Resp execute(
            Req req
    ) {
        HttpEntity<Req> reqEntity = new HttpEntity<>(req, null);

        ResponseEntity<Resp> respEntity = execute(reqEntity);
        if (respEntity == null) {
            return null;
        }
        return respEntity.getBody();
    }

    public ResponseEntity<Resp> execute(
            HttpEntity<Req> reqEntity
    ) {

        if ("RSA".equals(signType)) {
            ChanpayApiUtils.signWithRsa(reqEntity.getBody(), key);
        } else {
            throw new RuntimeException("不支持的签名算法 - " + signType);
        }


//        ResponseEntity<Resp> respEntity = restTemplate.exchange(
//                getGatewayUrl(),
//                HttpMethod.POST,
//                reqEntity,
//                respClass);


        ResponseEntity<String> respStrEntity = restTemplate.exchange(
                getGatewayUrl(),
                HttpMethod.POST,
                reqEntity,
                String.class);


//        jsonMsgConvertor.read(respClass)

        Assert.isTrue(HttpStatus.OK == respStrEntity.getStatusCode(),
                "调用畅捷支付API异常。响应状态码:  " + respStrEntity.getStatusCode().value());


        String respStr = respStrEntity.getBody();
        if (logger.isDebugEnabled()) {
            logger.debug("响应内容时：" + respStr);
        }

        Assert.isTrue(!StringUtils.isEmpty(respStr), "畅捷支付API响应为空");
        Resp resp = null;
        try {
            resp = objectMapper.readValue(respStr, respClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<Resp> respEntity = new ResponseEntity<>(
                resp,
                respStrEntity.getHeaders(),
                respStrEntity.getStatusCode());

        if (checkErrorCode) {
            Assert.isTrue(StringUtils.isEmpty(resp.getErrorCode()),
                    "调用畅捷支付API出错 : " + resp.getErrorCode() + " - " + resp.getErrorMessage());
        }

        return respEntity;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public boolean isCheckErrorCode() {
        return checkErrorCode;
    }

    public void setCheckErrorCode(boolean checkErrorCode) {
        this.checkErrorCode = checkErrorCode;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Class<Resp> getRespClass() {
        return respClass;
    }

    public void setRespClass(Class<Resp> respClass) {
        this.respClass = respClass;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
