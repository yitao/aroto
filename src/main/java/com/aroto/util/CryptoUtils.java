package com.aroto.util;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/**
 * Created by yitao on 2016/9/22.
 */

public abstract class CryptoUtils {
    private static final String DES = "DES";
    private static final String AES = "AES";
    private static final String HMACSHA1 = "HmacSHA1";
    private static final int DEFAULT_HMACSHA1_KEYSIZE = 160;
    private static final int DEFAULT_AES_KEYSIZE = 128;

    public CryptoUtils() {
    }

    public static byte[] hmacSha1(String input, byte[] keyBytes) {
        try {
            SecretKeySpec e = new SecretKeySpec(keyBytes, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(e);
            return mac.doFinal(input.getBytes());
        } catch (GeneralSecurityException var4) {
            throw ExceptionUtils.unchecked(var4);
        }
    }

    public static String hmacSha1ToHex(String input, byte[] keyBytes) {
        byte[] macResult = hmacSha1(input, keyBytes);
        return EncodeUtils.encodeHex(macResult);
    }

    public static String hmacSha1ToBase64(String input, byte[] keyBytes) {
        byte[] macResult = hmacSha1(input, keyBytes);
        return EncodeUtils.encodeBase64(macResult);
    }

    public static String hmacSha1ToBase64UrlSafe(String input, byte[] keyBytes) {
        byte[] macResult = hmacSha1(input, keyBytes);
        return EncodeUtils.encodeUrlSafeBase64(macResult);
    }

    public static boolean isHexMacValid(String hexMac, String input, byte[] keyBytes) {
        byte[] expected = EncodeUtils.decodeHex(hexMac);
        byte[] actual = hmacSha1(input, keyBytes);
        return Arrays.equals(expected, actual);
    }

    public static boolean isBase64MacValid(String base64Mac, String input, byte[] keyBytes) {
        byte[] expected = EncodeUtils.decodeBase64(base64Mac);
        byte[] actual = hmacSha1(input, keyBytes);
        return Arrays.equals(expected, actual);
    }

    public static byte[] generateMacSha1Key() {
        try {
            KeyGenerator e = KeyGenerator.getInstance("HmacSHA1");
            e.init(160);
            SecretKey secretKey = e.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException var2) {
            throw ExceptionUtils.unchecked(var2);
        }
    }

    public static String generateMacSha1HexKey() {
        return EncodeUtils.encodeHex(generateMacSha1Key());
    }

    public static String desEncryptToHex(String input, byte[] keyBytes) {
        byte[] encryptResult = des(input.getBytes(), keyBytes, 1);
        return EncodeUtils.encodeHex(encryptResult);
    }

    public static String desEncryptToBase64(String input, byte[] keyBytes) {
        byte[] encryptResult = des(input.getBytes(), keyBytes, 1);
        return EncodeUtils.encodeBase64(encryptResult);
    }

    public static String desDecryptFromHex(String input, byte[] keyBytes) {
        byte[] decryptResult = des(EncodeUtils.decodeHex(input), keyBytes, 2);
        return new String(decryptResult);
    }

    public static String desDecryptFromBase64(String input, byte[] keyBytes) {
        byte[] decryptResult = des(EncodeUtils.decodeBase64(input), keyBytes, 2);
        return new String(decryptResult);
    }

    private static byte[] des(byte[] inputBytes, byte[] keyBytes, int mode) {
        try {
            DESKeySpec e = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(e);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(mode, secretKey);
            return cipher.doFinal(inputBytes);
        } catch (GeneralSecurityException var7) {
            throw ExceptionUtils.unchecked(var7);
        }
    }

    public static byte[] generateDesKey() {
        try {
            KeyGenerator e = KeyGenerator.getInstance("DES");
            SecretKey secretKey = e.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException var2) {
            throw ExceptionUtils.unchecked(var2);
        }
    }

    public static String generateDesHexKey() {
        return EncodeUtils.encodeHex(generateDesKey());
    }

    public static String aesEncryptToHex(String input, byte[] keyBytes) {
        byte[] encryptResult = aes(input.getBytes(), keyBytes, 1);
        return EncodeUtils.encodeHex(encryptResult);
    }

    public static String aesEncryptToBase64(String input, byte[] keyBytes) {
        byte[] encryptResult = aes(input.getBytes(), keyBytes, 1);
        return EncodeUtils.encodeBase64(encryptResult);
    }

    public static String aesDecryptFromHex(String input, byte[] keyBytes) {
        byte[] decryptResult = aes(EncodeUtils.decodeHex(input), keyBytes, 2);
        return new String(decryptResult);
    }

    public static String aesDecryptFromBase64(String input, byte[] keyBytes) {
        byte[] decryptResult = aes(EncodeUtils.decodeBase64(input), keyBytes, 2);
        return new String(decryptResult);
    }

    private static byte[] aes(byte[] inputBytes, byte[] keyBytes, int mode) {
        try {
            SecretKeySpec e = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(mode, e);
            return cipher.doFinal(inputBytes);
        } catch (GeneralSecurityException var5) {
            throw ExceptionUtils.unchecked(var5);
        }
    }

    public static byte[] generateAesKey() {
        try {
            KeyGenerator e = KeyGenerator.getInstance("AES");
            e.init(128);
            SecretKey secretKey = e.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException var2) {
            throw ExceptionUtils.unchecked(var2);
        }
    }

    public static String generateAesHexKey() {
        return EncodeUtils.encodeHex(generateAesKey());
    }
}
