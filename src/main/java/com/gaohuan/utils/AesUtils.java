package com.gaohuan.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by gaohuan on 2017/10/19.
 */
public class AesUtils {

    public static final String SECRET_KEY = "SlEfTJgMiJiWoVWA+vftnA==";

    /**
     * 加密
     *
     * @param content 待加密内容
     * @return
     */
    public static String encrypt(String content) {
        try {
            SecretKey key = new SecretKeySpec(Base64.decodeBase64(SECRET_KEY), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.encodeBase64String(cipher.doFinal(content.getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
            //do nothing
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @return
     */
    public static String decrypt(String content) {
        try {
            SecretKey key = new SecretKeySpec(Base64.decodeBase64(SECRET_KEY), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(Base64.decodeBase64(content));
            return new String(bytes, "utf-8");
        } catch (Exception e) {
            //do nothing
        }
        return null;
    }

    /**
     * 生成秘钥
     *
     * @return
     */
    public static String secretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128, new SecureRandom());
            return Base64.encodeBase64String(keyGen.generateKey().getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such AES algorithm");
        }
    }

    public static void main(String[] args) {
        String s = encrypt("15811056271");
        System.out.println(s);
    }
}
