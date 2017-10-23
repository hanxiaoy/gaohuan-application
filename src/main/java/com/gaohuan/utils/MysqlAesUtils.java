package com.gaohuan.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * 等同于MySQL AES加密解密
 *
 * to_base64( AES_ENCRYPT(str,secretKey))
 *
 * AES_DECRYPT(from_base64(encryptStr),secretKey)
 *
 * Created by gaohuan on 2017/10/20.
 */
public class MysqlAesUtils {

    /**
     * 根据明文生成秘钥
     *
     * @param key
     * @return
     */
    private static SecretKeySpec generateMysqlAesKey(final String key) {
        try {
            final byte[] finalKey = new byte[16];
            int i = 0;
            for (byte b : key.getBytes("UTF-8"))
                finalKey[i++ % 16] ^= b;
            return new SecretKeySpec(finalKey, "AES");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密
     *
     * @param content 待加密内容
     * @return
     */
    public static String encrypt(String content, String secretKey) {
        try {
            final Cipher encryptCipher = Cipher.getInstance("AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, generateMysqlAesKey(secretKey));
            byte[] bytes = encryptCipher.doFinal(content.getBytes("UTF-8"));
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            throw new RuntimeException("AES加密失败");
        }
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @return
     */
    public static String decrypt(String content, String secretKey) {
        try {
            final Cipher decryptCipher = Cipher.getInstance("AES");
            decryptCipher.init(Cipher.DECRYPT_MODE, generateMysqlAesKey(secretKey));
            byte[] bytes = decryptCipher.doFinal(Base64.decodeBase64(content));
            return new String(bytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("AES解密失败");
        }
    }


    public static void main(String... args) throws Exception {
        System.out.println(encrypt("15811056271", "1234"));
        System.out.println(decrypt("7xTOIRNO0lmycY12eFN2sw==", "1234"));
    }
}