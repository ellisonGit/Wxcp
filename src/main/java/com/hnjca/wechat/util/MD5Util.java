package com.hnjca.wechat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description:
 * User: by yangyong
 * Date: 2018-09-26
 * Time: 21:24
 * Modified:
 */
public class MD5Util {

    private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /**
     * Transform the byte array to hex string.
     *
     * @param b
     * @return
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /** */
    /**
     * Transform a byte to hex string.
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;

        // get the first four bit
        int d1 = n / 16;

        // get the second four bit
        int d2 = n % 16;

        return hexDigits[d1] + hexDigits[d2];
    }

    /** */
    /**
     * Get the MD5 encrypt hex string of the origin string. <br/>
     * The origin string won't validate here, so who use the API should validate
     * by himself.
     *
     * @param origin
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String MD5Encode(String origin)
    {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");

            resultString = byteArrayToHexString(md.digest(origin.getBytes("GB2312"))).substring(8, 24).toLowerCase();
        } catch (Exception exception) {
        }
        return resultString;

    }
}
