package me.test.jdk.java.security;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.Key;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * @author dangqian.zll
 * @date 2020/9/24
 */
public class KeyStoreTest {


    /*
    keytool -genkeypair \
        -alias btpka3 \
        -keyalg RSA \
        -keysize 1024 \
        -sigalg SHA1withRSA \
        -dname "CN=test.me, OU=R & D department, O=\"BJ SOS Software Tech Co., Ltd\", L=Beijing, S=Beijing, C=CN" \
        -validity 3650 \
        -keystore sos.p12 \
        -storetype pkcs12 \
        -storepass 123456
     */

    @Test
    public void x() throws Exception {

        KeyStore p12 = KeyStore.getInstance("pkcs12");
        p12.load(new FileInputStream("/tmp/sos.p12"), "123456".toCharArray());
        Enumeration<String> e = p12.aliases();
        while (e.hasMoreElements()) {
            String alias = e.nextElement();
            Key key = p12.getKey(alias, "123456".toCharArray());
            System.out.println("------------------" + alias);
            System.out.println(toStr(key));
        }
    }


    @Test
    public void x2() throws Exception {

        KeyStore p12 = KeyStore.getInstance("pkcs12");
        p12.load(new FileInputStream("/tmp/sos.p12"), "123456".toCharArray());
        Enumeration<String> e = p12.aliases();
        while (e.hasMoreElements()) {
            String alias = e.nextElement();
            X509Certificate c = (X509Certificate) p12.getCertificate(alias);
            Principal subject = c.getSubjectDN();
            System.out.println("------------------" + alias);
            System.out.println(subject.toString());
            String keyStr = toStr(c.getPublicKey());
            System.out.println(keyStr);
            Object newKey = fromStr(keyStr);
            if (newKey instanceof SubjectPublicKeyInfo) {
                SubjectPublicKeyInfo keyInfo = (SubjectPublicKeyInfo) newKey;
                JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
                PublicKey publicKey = converter.getPublicKey(keyInfo);

                System.out.println("publicKey = " + publicKey.getClass() + ", key=" + publicKey);
            }
            System.out.println("newKey = " + newKey.getClass() + ", " + newKey);
        }
    }

    public String toStr(Key key) throws IOException {
        StringWriter sw = new StringWriter();
        JcaPEMWriter w = new JcaPEMWriter(sw);
        w.writeObject(key);
        w.flush();
        w.close();
        return sw.toString();
    }

    public Object fromStr(String str) throws IOException {
        PEMParser p = new PEMParser(new StringReader(str));
        return p.readObject();
    }


    public String toPEM(PublicKey pubKey) throws Exception {
        StringWriter sw = new StringWriter();

        PemWriter pemWriter = new PemWriter(sw);
        pemWriter.writeObject(new PemObject("RSA PUBLIC KEY", pubKey.getEncoded()));
        pemWriter.flush();
        pemWriter.close();

        return sw.toString();
    }
}
