package me.test.first.chanpay.api.scan.impl;

import org.springframework.util.*;

import java.io.*;
import java.net.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;
import java.util.stream.*;

/**
 *
 */
public class SignUtils {


    private static final List<String> ALLOWED_SIGN_TYPES = Collections.unmodifiableList(Arrays.asList(
            "MD5", "RSA"
    ));


    public static String createJoinStringForSign(
            Map<String, String> reqMap,
            String charset,
//            String inputCharset,
            boolean urlEncoding
    ) {
//        Map<String, String> reqMap = req.toSingleValueMap();
//
//        String charset = req.getInputCharset();

        List<String> dropedParams = Arrays.asList("sign", "sign_type");
        return reqMap.entrySet().stream()

                // 排除参数： "sign", "sign_type"
                .filter(entry -> dropedParams.stream()
                        .noneMatch(paramName -> paramName.equals(entry.getKey()))
                )

                // 排除值为空的参数
                .filter(entry -> !StringUtils.isEmpty(entry.getValue()))

                // 变为字符串List<String>
//                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .map(entry -> {
                    try {
                        String v = !(urlEncoding && StringUtils.isEmpty(charset))
                                ? URLEncoder.encode(entry.getValue(), charset)
                                : entry.getValue();
                        return entry.getKey() + "=" + v;
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                })

                // 字符串 List 排序
                .sorted()

                // 变成单个字符串
                .collect(Collectors.joining("&"));
    }


    public static String sign(
            Map<String, String> reqMap,
            String charset,
            String signType,
            String key
    ) {
        if ("RSA".equals(signType)) {
            return signWithRsa(reqMap, charset, key);
        } else {
            throw new RuntimeException("不支持的签名算法 - " + signType);
        }
    }


    public static String signWithRsa(
            Map<String, String> reqMap,
            String charset,
            String privateKeyInBase64
    ) {

        String strToSign = createJoinStringForSign(reqMap, charset, true);
        System.out.println(strToSign);


        byte[] priKeyBytes = Base64.getDecoder().decode(privateKeyInBase64);
        PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(priKeyBytes);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PrivateKey priKey = keyFactory.generatePrivate(priKeySpec);
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(priKey);

            byte[] bytes = StringUtils.isEmpty(charset)
                    ? strToSign.getBytes()
                    : strToSign.getBytes(charset);

            signature.update(bytes);
            byte[] result = signature.sign();
            String sign = Base64.getEncoder().encodeToString(result);
            return sign;

        } catch (NoSuchAlgorithmException
                | UnsupportedEncodingException
                | SignatureException
                | InvalidKeyException
                | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifySignWithRsa(
            Map<String, String> respMap,
            String sign,
            String charset,
            String pubKeyInBase64
    ) {
        String strToSign = createJoinStringForSign(respMap, charset, true);

        try {
            byte[] bytes = StringUtils.isEmpty(charset)
                    ? strToSign.getBytes()
                    : strToSign.getBytes(charset);


            byte[] keyBytes = Base64.getDecoder().decode(pubKeyInBase64);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(keySpec);

            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(pubKey);

            signature.update(bytes);
            return signature.verify(Base64.getDecoder().decode(sign));

        } catch (NoSuchAlgorithmException
                | UnsupportedEncodingException
                | SignatureException
                | InvalidKeyException
                | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
