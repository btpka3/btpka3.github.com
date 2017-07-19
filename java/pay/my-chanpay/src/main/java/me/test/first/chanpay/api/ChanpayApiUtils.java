package me.test.first.chanpay.api;

import org.springframework.util.*;

import java.io.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;
import java.util.stream.*;

/**
 *
 */
public class ChanpayApiUtils {


    private static final List<String> ALLOWED_SIGN_TYPES = Collections.unmodifiableList(Arrays.asList(
            "MD5", "RSA"
    ));


    public static String createJoinStringForSign(BaseReq req, boolean urlEncoding) {
        Map<String, String> reqMap = req.toSingleValueMap();

        String charset = req.getInputCharset();

        List<String> dropedParams = Arrays.asList("sign", "sign_type");
        return reqMap.entrySet().stream()

                // 排除参数： "sign", "sign_type"
                .filter(entry -> dropedParams.stream()
                        .noneMatch(paramName -> paramName.equals(entry.getKey()))
                )

                // 排除值为空的参数
                .filter(entry -> !StringUtils.isEmpty(entry.getValue()))

                // 变为字符串List<String>
                .map(entry -> entry.getKey() + "=" + entry.getValue())
//                .map(entry -> {
////                    try {
////                        String v = !(urlEncoding && StringUtils.isEmpty(charset))
////                                ? URLEncoder.encode(entry.getValue(), charset)
////                                : entry.getValue();
//                        return entry.getKey() + "=" + entry.getValue();
////                    } catch (UnsupportedEncodingException e) {
////                        throw new RuntimeException(e);
////                    }
//                })

                // 字符串 List 排序
                .sorted()

                // 变成单个字符串
                .collect(Collectors.joining("&"));
    }


//    public static void signWithMd5(BaseReq req, String key) {
//        String strToSign = createJoinStringForSign(req, true);
//
//        // 将私钥直接拼接到待签名字符串之后
//        strToSign += key;
//
//        try {
//            byte[] signBytes = MessageDigest.getInstance("MD5")
//                    .digest(strToSign.getBytes("UTF-8"));
//            String sign = DatatypeConverter.printHexBinary(signBytes);
//            req.setSign(sign);
//            req.setSignType("MD5");
//        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public static void signWithRsa(BaseReq req, String privateKeyInBase64) {
        String charset = req.getInputCharset();

        String strToSign = createJoinStringForSign(req, false);
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
            req.setSign(sign);
            req.setSignType("RSA");

        } catch (NoSuchAlgorithmException
                | UnsupportedEncodingException
                | SignatureException
                | InvalidKeyException
                | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

}
