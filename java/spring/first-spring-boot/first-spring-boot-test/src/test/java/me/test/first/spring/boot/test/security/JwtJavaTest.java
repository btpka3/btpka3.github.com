package me.test.first.spring.boot.test.security;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

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

    @Test
    public void generateJWK() throws Exception {
        RSAKey jwk = new RSAKeyGenerator(2048)
                .keyUse(KeyUse.SIGNATURE) // indicate the intended use of the key (optional)
                .keyID(UUID.randomUUID().toString()) // give the key a unique ID (optional)
                .algorithm(JWSAlgorithm.RS256)
                .keyUse(KeyUse.SIGNATURE)
                .generate();

        /*
[
  // 私钥
  {
    "kid": "7ab14398-f4b9-4daa-8d07-4cc6bd947b58",
    "kty": "RSA",
    "use": "sig",
    "alg": "HS256",
    "e": "AQAB",
    "n": "...", // 公钥的模值
    "p": "...",
    "q": "...",
    "d": "...",
    "qi": "...",
    "dp": "...",
    "dq": "..."
  },
  // 公钥
  {
    "kid": "7ab14398-f4b9-4daa-8d07-4cc6bd947b58",
    "kty": "RSA",
    "use": "sig",
    "alg": "HS256",
    "e": "AQAB",
    "n": "..." // 公钥的模值
  }
]
         */
        System.out.println("-----------JWK(私钥、公钥）");
        String str = "[" + jwk.toString() + "," + jwk.toPublicJWK() + "]";
        System.out.println(str);
        System.out.println();
        System.out.println();

        System.out.println("-----------JWKS(公钥）");
        JWKSet jwks = new JWKSet(jwk.toPublicJWK());
        System.out.println(jwks);


    }

    /**
     * 从JSON文件中load JWK。
     */
    @Test
    public void loadJWT() throws Exception {
        RSAKey jwk = new RSAKeyGenerator(2048)
                .keyUse(KeyUse.SIGNATURE)
                .keyID(UUID.randomUUID().toString())
                .algorithm(JWSAlgorithm.RS256)
                .keyUse(KeyUse.SIGNATURE)
                .generate();
        String jwkStr = jwk.toString();

        RSAKey newJwk = RSAKey.parse(jwkStr);
        Assertions.assertEquals(jwk, newJwk);

        JWKSet jwks = new JWKSet(jwk.toPublicJWK());
        String jwksStr = jwks.toString();
        JWKSet newJwks = JWKSet.parse(jwksStr);
        Assertions.assertEquals(jwks, newJwks);

    }
}
