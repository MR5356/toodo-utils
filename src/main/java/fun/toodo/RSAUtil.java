package fun.toodo;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {
    public static final String KEY_RSA = "RSA";
    public static final Integer KEY_SIZE = 1024;
    public static final String KEY_RSA_PUBLIC_KEY = "RSAPublicKey";
    public static final String KEY_RSA_PRIVATE_KEY = "RSAPrivateKey";
    private static final String KEY_RSA_SIGNATURE = "MD5withRSA";
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;

    public RSAUtil() {
    }

    public static Map<String, Key> init() {
        Map<String, Key> map = new HashMap<>();

        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_RSA);
            generator.initialize(KEY_SIZE);
            KeyPair keyPair = generator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
            map.put(KEY_RSA_PUBLIC_KEY, publicKey);
            map.put(KEY_RSA_PRIVATE_KEY, privateKey);
        } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
        }

        return map;
    }

    public static String getPublicKey(Map<String, Key> map) {
        String str = "";
        Key key = map.get("RSAPublicKey");
        str = encryptBase64(key.getEncoded());
        return str;
    }

    public static String getPrivateKey(Map<String, Key> map) {
        String str = "";
        Key key = map.get("RSAPrivateKey");
        str = encryptBase64(key.getEncoded());
        return str;
    }

    public static byte[] decryptBase64(String key) {
        return Base64.getDecoder().decode(key);
    }

    public static String encryptBase64(byte[] key) {
        return new String(Base64.getEncoder().encode(key));
    }

    public static String encryptByPublicKey(String encryptingStr, String publicKeyStr) {
        try {
            byte[] publicKeyBytes = decryptBase64(publicKeyStr);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            byte[] data = encryptingStr.getBytes(StandardCharsets.UTF_8);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = factory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(1, publicKey);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;

            for(int i = 0; inputLen - offSet > 0; offSet = i * MAX_ENCRYPT_BLOCK) {
                byte[] cache;
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }

                out.write(cache, 0, cache.length);
                ++i;
            }

            byte[] decryptedData = out.toByteArray();
            out.close();
            return encryptBase64(decryptedData);
        } catch (Exception var14) {
            var14.printStackTrace();
            return null;
        }
    }

    public static String decryptByPrivateKey(String encryptedStr, String privateKeyStr) {
        try {
            byte[] privateKeyBytes = decryptBase64(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            byte[] data = decryptBase64(encryptedStr);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(2, privateKey);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;

            for(int i = 0; inputLen - offSet > 0; offSet = i * MAX_DECRYPT_BLOCK) {
                byte[] cache;
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }

                out.write(cache, 0, cache.length);
                ++i;
            }

            out.close();
            return out.toString(StandardCharsets.UTF_8);
        } catch (Exception var14) {
            var14.printStackTrace();
            return null;
        }
    }

    public static String encryptByPrivateKey(String encryptingStr, String privateKeyStr) {
        try {
            byte[] privateKeyBytes = decryptBase64(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            byte[] data = encryptingStr.getBytes(StandardCharsets.UTF_8);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(1, privateKey);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;

            for(int i = 0; inputLen - offSet > 0; offSet = i * MAX_ENCRYPT_BLOCK) {
                byte[] cache;
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }

                out.write(cache, 0, cache.length);
                ++i;
            }

            byte[] decryptedData = out.toByteArray();
            out.close();
            return encryptBase64(decryptedData);
        } catch (Exception var14) {
            var14.printStackTrace();
            return null;
        }
    }

    public static String decryptByPublicKey(String encryptedStr, String publicKeyStr) {
        try {
            byte[] publicKeyBytes = decryptBase64(publicKeyStr);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            byte[] data = decryptBase64(encryptedStr);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = factory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(2, publicKey);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;

            for(int i = 0; inputLen - offSet > 0; offSet = i * MAX_DECRYPT_BLOCK) {
                byte[] cache;
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }

                out.write(cache, 0, cache.length);
                ++i;
            }

            out.close();
            return out.toString(StandardCharsets.UTF_8);
        } catch (Exception var14) {
            var14.printStackTrace();
            return null;
        }
    }

    public static String sign(String encryptedStr, String privateKey) {
        String str = "";

        try {
            byte[] data = encryptedStr.getBytes();
            byte[] bytes = decryptBase64(privateKey);
            PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey key = factory.generatePrivate(pkcs);
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initSign(key);
            signature.update(data);
            str = encryptBase64(signature.sign());
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return str;
    }

    public static boolean verify(String encryptedStr, String publicKey, String sign) {
        boolean flag = false;

        try {
            byte[] data = encryptedStr.getBytes();
            byte[] bytes = decryptBase64(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PublicKey key = factory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initVerify(key);
            signature.update(data);
            flag = signature.verify(decryptBase64(sign));
        } catch (Exception var10) {
            var10.printStackTrace();
        }

        return flag;
    }

    public static void main(String[] args) {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6yDgoomSay3WIU7Cus/cRaGCKDiHRBH4qZ59JMxPcsHCdyT+08aoF1DMpipBe4Tzh2sVUPYewlPR/PJRw5qwnhZDftZy2mZxoG8iGVPD4tG2AT573MWfwiMAs7waomvzzIy3YDzrgDJhhnYhL34vUrDmRTLM1NTVeTfJFSNdEjQIDAQAB";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALrIOCiiZJrLdYhTsK6z9xFoYIoOIdEEfipnn0kzE9ywcJ3JP7TxqgXUMymKkF7hPOHaxVQ9h7CU9H88lHDmrCeFkN+1nLaZnGgbyIZU8Pi0bYBPnvcxZ/CIwCzvBqia/PMjLdgPOuAMmGGdiEvfi9SsOZFMszU1NV5N8kVI10SNAgMBAAECgYEAqteHxmMC8jM3BcLG0IT7b4ixBJV4iN47KD4vi5yUhj88QpZZFwAUK9C0G0MKWj5amZqo8yx0eCdGmzql676RpuHmSs1mOewN/P4y9RLlLr2fSTf9D9ebf5U4AF2qvkylzillH3WEQpwAtw/A4Ij8JkKVtDlQinAYI2Fo/NcEyWECQQD1imuowQI16viGQQOj9ndkpUoK3/cnkWuS6zp9AgXlB/DcPKtyyRS8N3rLdbOpBqYl/yjpXh/Vx1XmtT4Wx/LrAkEAwr0L3V06V22dCkWo/+y6w/yGHaJe07S7whuCMDydje6bpfEIsTN57mmihbawXB27kFL6Rxdu84ZpdcYsvjuYZwJAYUTmhUGAiK9+qGCU+p0miv5XqJs0o74d6WO6Fc95WlOc/QIjwSMbTbyErpkABROXOjIsmNYqslt3Eh/2arvm5QJBALhZsJaxf95uJSEpStBj8XZBY1FAedaenLQnyHoTl+9Z970CzgC6Q26AT9uXU6MQfpQlCUstkto03zcgERFRZ3cCQF7tqq3mwk0rCmi/f/0NCagrOK89LzYRbPNoReZm7XnfBPje5Bawtd8MLLyoAo3bE3gn1ddqa0zsAAWA2cvz96Y=";
        String str = "qian";

        String enStr1 = encryptByPublicKey(str, publicKey);
        System.out.println("公钥加密后：" + enStr1);
        String deStr1 = decryptByPrivateKey(enStr1, privateKey);
        System.out.println("私钥解密后：" + deStr1);
        String enStr2 = encryptByPrivateKey(str, privateKey);
        System.out.println("私钥加密后：" + enStr2);
        String deStr2 = decryptByPublicKey(enStr2, publicKey);
        System.out.println("公钥解密后：" + deStr2);
        String sign = sign(enStr2, privateKey);
        System.out.println("签名:" + sign);
        boolean status = verify(enStr2, publicKey, sign);
        System.out.println("状态:" + status);
        Map<String, Key> map = init();
        System.out.println(getPrivateKey(map));
        System.out.println(getPublicKey(map));
    }
}
