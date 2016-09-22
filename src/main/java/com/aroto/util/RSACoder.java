package com.aroto.util;

import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yitao on 2016/9/22.
 */

public abstract class RSACoder {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    public RSACoder() {
    }

    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = EncodeUtils.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(priKey);
        signature.update(data);
        return EncodeUtils.encodeBase64(signature.sign());
    }

    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = EncodeUtils.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(pubKey);
        signature.update(data);
        return signature.verify(EncodeUtils.decodeBase64(sign));
    }

    private static byte[] procByPrivateKey(byte[] data, String key, int mode) throws Exception {
        byte[] keyBytes = EncodeUtils.decodeBase64(key);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(mode, privateKey);
        return cipher.doFinal(data);
    }

    public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
        return procByPrivateKey(data, key, 1);
    }

    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
        return procByPrivateKey(data, key, 2);
    }

    private static byte[] procByPublicKey(byte[] data, String key, int mode) throws Exception {
        byte[] keyBytes = EncodeUtils.decodeBase64(key);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(mode, publicKey);
        return cipher.doFinal(data);
    }

    public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
        return procByPublicKey(data, key, 1);
    }

    public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
        return procByPublicKey(data, key, 2);
    }

    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key)keyMap.get("RSAPrivateKey");
        return EncodeUtils.encodeBase64(key.getEncoded());
    }

    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key)keyMap.get("RSAPublicKey");
        return EncodeUtils.encodeBase64(key.getEncoded());
    }

    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        HashMap keyMap = new HashMap(2);
        keyMap.put("RSAPublicKey", publicKey);
        keyMap.put("RSAPrivateKey", privateKey);
        return keyMap;
    }

    public static byte[] encryptLongByPublicKey(byte[] data, String key) throws Exception {
        byte[] dataReturn = new byte[0];
        byte[] keyBytes = EncodeUtils.decodeBase64(key);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, publicKey);

        for(int i = 0; i < data.length; i += 117) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 117));
            dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
        }

        return dataReturn;
    }

    public static byte[] decryptLongByPrivateKey(byte[] data, String key) throws Exception {
        byte[] dataReturn = new byte[0];
        byte[] keyBytes = EncodeUtils.decodeBase64(key);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, privateKey);

        for(int i = 0; i < data.length; i += 128) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 128));
            dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
        }

        return dataReturn;
    }

    public static byte[] encryptLongByPrivateKey(byte[] data, String key) throws Exception {
        byte[] dataReturn = new byte[0];
        byte[] keyBytes = EncodeUtils.decodeBase64(key);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, privateKey);

        for(int i = 0; i < data.length; i += 128) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 128));
            dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
        }

        return dataReturn;
    }

    public static byte[] decryptLongByPublicKey(byte[] data, String key) throws Exception {
        byte[] dataReturn = new byte[0];
        byte[] keyBytes = EncodeUtils.decodeBase64(key);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, publicKey);

        for(int i = 0; i < data.length; i += 117) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 117));
            dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
        }

        return dataReturn;
    }
}
