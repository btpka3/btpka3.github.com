package me.test;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class JwtJavaTest {

    @Test
    public void testHs256() throws Exception {

        // linux 命令行生成: tr -dc A-Za-z0-9 < /dev/urandom | head -c 13 ; echo ''
        String secretString = "NjGEkRGtCmBA2qshryghPMjw3W0K7VlL";
        String jwtStr = null;

        // 生成 JWT
        {
            JWSSigner signer = new MACSigner(secretString);

            JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256)
                    .keyID("keyId001")
                    .build();

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .issuer("urn:example:issuer")
                    .audience("urn:example:audience")
                    // 2022-04-04 04:04:04.000
                    .issueTime(new Date(1617480244L * 1000))
                    // 2022-02-02 02:02:02.000
                    .expirationTime(new Date(1643738522L * 1000))

                    // 自定义额外的 playload
                    .claim("foo", "bar")
                    .build();


            SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet);

            // 计算签名
            signedJWT.sign(signer);

            jwtStr = signedJWT.serialize();
            System.out.println("jwtStr = " + jwtStr);
        }

        // 验证 jwt
        {
            SignedJWT signedJWT = SignedJWT.parse(jwtStr);

            JWSVerifier verifier = new MACVerifier(secretString);
            Assertions.assertTrue(signedJWT.verify(verifier));

            // TODO: 额外验证业务上相关的值
            Assertions.assertEquals("urn:example:issuer", signedJWT.getJWTClaimsSet().getIssuer());
        }
    }
}
