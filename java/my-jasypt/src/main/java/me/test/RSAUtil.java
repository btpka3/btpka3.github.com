package me.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;

// http://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html
public class RSAUtil {

    public static final String ALGORITHM = "RSA";

    private int keySize = 0;

    private String transformation = null;

    private String signatureAlgorithm = null;

    private static RSAUtil instance = null;

    static {
        // for load public/private key from *.pem files

        // JCE does not work correctly! WHY?

        //        if (Security.getProvider("BC") == null) {
        //            Security.addProvider(new BouncyCastleProvider());
        //        }
    }

    private RSAUtil(int keySize, String transformation,
            String signatureAlgorithm) {
        super();
        this.keySize = keySize;
        this.transformation = transformation;
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public static synchronized RSAUtil getInstance() {
        if (instance == null) {
            instance = new RSAUtil(1024, "RSA/ECB/PKCS1Padding", "SHA512withRSA");
        }
        return instance;
    }

    public KeyPair generateRandomKeyPair() throws RuntimeException {
        try {
            KeyPairGenerator keyPairGen = null;
            keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGen.initialize(keySize, new SecureRandom());
            KeyPair keyPair = keyPairGen.genKeyPair();
            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public KeyPair generateKeyPair(byte[] seed) throws RuntimeException {
        try {
            KeyPairGenerator keyPairGen = null;
            keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGen.initialize(keySize, new SecureRandom(seed));
            KeyPair keyPair = keyPairGen.genKeyPair();
            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey loadPrivateKey(String keyPath)
            throws RuntimeException {

        try {

            byte[] data = FileUtils.readFileToByteArray(new File(keyPath));

            PKCS8EncodedKeySpec pkcs8Enc = new PKCS8EncodedKeySpec(data);

            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            return keyFactory.generatePrivate(pkcs8Enc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey loadPrivateKeyFromPem(String keyPath)
            throws RuntimeException {

        BufferedReader bufReader = null;
        byte[] data = null;

        try {
            bufReader = new BufferedReader(new FileReader(keyPath));
            StringBuffer buf = new StringBuffer();
            String next = null;

            while ((next = bufReader.readLine()) != null) {
                if (next.indexOf("RSA PRIVATE KEY") != -1) {
                    break;
                }
            }
            if (next == null) {
                throw new RuntimeException("Not exist [PRIVATE KEY] section "
                        + "in private key file.");
            }

            next = bufReader.readLine();
            if (next == null) {
                throw new RuntimeException("Invalid [PRIVATE KEY] section .");
            }
            if (next.startsWith("Proc-Type: 4,ENCRYPTED")) {
                throw new RuntimeException(
                        "Not supported encrypted private key.");
            } else {
                buf.append(next);
            }

            while ((next = bufReader.readLine()) != null) {
                if (next.startsWith("-----END")) {
                    break;
                }
                buf.append(next);
            }
            bufReader.close();
            data = Base64.decodeBase64(buf.toString());
            PKCS8EncodedKeySpec pkcs8Enc = new PKCS8EncodedKeySpec(data);

            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8Enc);
            return priKey;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey loadPublicKey(String keyPath)
            throws RuntimeException {
        try {
            File file = new File(keyPath);
            FileInputStream in = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            in.read(data);
            in.close();

            X509EncodedKeySpec x509Enc = new X509EncodedKeySpec(data);

            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            return keyFactory.generatePublic(x509Enc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey loadPublicKeyFromPemCert(String certPath)
            throws RuntimeException {
        try {
            InputStream bis = null;
            bis = new BufferedInputStream(new FileInputStream(certPath));
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate cert = cf.generateCertificate(bis);
            return cert.getPublicKey();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }

    }

    public static void savePrivateKey(PrivateKey key, String keyPath)
            throws RuntimeException {
        try {
            FileOutputStream out = new FileOutputStream(new File(keyPath));
            PKCS8EncodedKeySpec pkcs8Enc = null;
            pkcs8Enc = new PKCS8EncodedKeySpec(key.getEncoded());
            out.write(pkcs8Enc.getEncoded());
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void savePublicKey(PublicKey key, String keyPath)
            throws RuntimeException {
        try {
            FileOutputStream out = new FileOutputStream(new File(keyPath));
            X509EncodedKeySpec x509Enc = null;
            x509Enc = new X509EncodedKeySpec(key.getEncoded());
            out.write(x509Enc.getEncoded());
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generateSignature(InputStream in, PrivateKey prikey)
            throws RuntimeException {
        try {
            Signature sig = Signature.getInstance(signatureAlgorithm);
            sig.initSign(prikey);

            byte[] buffer = new byte[1024];
            int len;
            while (0 <= (len = in.read(buffer))) {
                sig.update(buffer, 0, len);
            }
            in.close();

            return sig.sign();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verifySignature(InputStream in, PublicKey pubKey,
            byte[] orgSig) throws RuntimeException {
        try {
            Signature sig = Signature.getInstance(signatureAlgorithm);

            sig.initVerify(pubKey);

            byte[] buffer = new byte[1024];
            int len;
            while (0 <= (len = in.read(buffer))) {
                sig.update(buffer, 0, len);
            }
            in.close();

            return sig.verify(orgSig);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] encode(Key key, byte[] data) throws RuntimeException {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] decode(Key key, byte[] data) throws RuntimeException {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        RSAUtil rsaUtil = RSAUtil.getInstance();

        KeyPair kp = rsaUtil.generateRandomKeyPair();
        PrivateKey priKey = kp.getPrivate();
        PublicKey pubKey = kp.getPublic();

        byte[] data = "Hello World!".getBytes();

        byte[] encData = rsaUtil.encode(priKey, data);
        byte[] decData = rsaUtil.decode(pubKey, encData);
        System.out.println(Arrays.equals(decData, data)); // true

        encData = rsaUtil.encode(pubKey, data);
        decData = rsaUtil.decode(priKey, encData);
        System.out.println(Arrays.equals(decData, data)); // true

        InputStream in = new ByteArrayInputStream(data);
        byte[] sig = rsaUtil.generateSignature(in, priKey);
        in.reset();
        boolean result = rsaUtil.verifySignature(in, pubKey, sig);
        System.out.println(result); // true

    }
}