package com.aroto.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by yitao on 2016/9/22.
 */

public abstract class EncodeUtils {
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    public EncodeUtils() {
    }

    public static final String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }

    public static final byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException var2) {
            throw new IllegalStateException("Hex Decoder exception", var2);
        }
    }

    public static final String encodeBase64(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    public static final String encodeUrlSafeBase64(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    public static final byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input);
    }

    public static final String encodeBase62(long num) {
        return alphabetEncode(num, 62);
    }

    public static final long decodeBase62(String str) {
        return alphabetDecode(str, 62);
    }

    private static final String alphabetEncode(long num, int base) {
        num = Math.abs(num);

        StringBuilder sb;
        for(sb = new StringBuilder(); num > 0L; num /= (long)base) {
            sb.append("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt((int)(num % (long)base)));
        }

        return sb.toString();
    }

    private static final long alphabetDecode(String str, int base) {
        AssertUtils.hasText(str);
        long result = 0L;

        for(int i = 0; i < str.length(); ++i) {
            result = (long)((double)result + (double)"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".indexOf(str.charAt(i)) * Math.pow((double)base, (double)i));
        }

        return result;
    }

    public static final String urlEncode(String part) {
        try {
            return URLEncoder.encode(part, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw ExceptionUtils.unchecked(var2);
        }
    }

    public static final String urlDecode(String part) {
        try {
            return URLDecoder.decode(part, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw ExceptionUtils.unchecked(var2);
        }
    }

    public static final String htmlEscape(String html) {
        return StringEscapeUtils.escapeHtml4(html);
    }

    public static final String htmlUnescape(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

    public static final String xmlEscape(String xml) {
        return StringEscapeUtils.escapeXml11(xml);
    }

    public static final String xmlUnescape(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }
}

