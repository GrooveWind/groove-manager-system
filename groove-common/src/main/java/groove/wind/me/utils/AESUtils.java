package groove.wind.me.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class AESUtils {

    /**
     * AES加密
     * @param keyStr	加密密钥串
     * @param sSrc		加密的内容
     * @return
     * @throws Exception
     */
    public static String encrypt(String keyStr, String sSrc) throws Exception {
        if (keyStr == null) {
            throw new Exception("Key为空null");
        }

        // 判断Key是否为16位
        if (keyStr.length() != 16) {
            throw new Exception("Key长度不是16位");
        }

        byte[] raw = keyStr.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");// "算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(keyStr.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] srawt = sSrc.getBytes();

        int len = srawt.length;

        /* 计算补0后的长度 */
        while (len % 16 != 0)
            len++;

        byte[] sraw = new byte[len];
        /* 在最后补0 */

        for (int i = 0; i < len; ++i) {
            if (i < srawt.length) {
                sraw[i] = srawt[i];
            } else {
                sraw[i] = 0;
            }
        }

        byte[] encrypted = cipher.doFinal(sraw);

        return new String(Base64.encodeBase64(encrypted));
    }

}
