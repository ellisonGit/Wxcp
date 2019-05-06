package com.hnjca.wechat.util;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Description:
 * User: by yangyong
 * Date: 2018-09-26
 * Time: 8:43
 * Modified:
 */
public class StrUtil {

    /**
     * 对字符串进行URLEncoder.encode编码
     *
     * @param str
     *            String
     * @param charset
     *            String
     * @return String
     */
    public static String UrlEncode(String str, String charset) {
        if (str == null)
            return "";

        String s = str;

        try {
            s = URLEncoder.encode(str, charset);
        } catch (Exception e) {

        }
        return s;
    }

    /**
     * 对字符串进行utf-8编码的URLEncoder.encode
     *
     * @param str
     *            String
     * @return String
     */
    public static String UrlEncode(String str) {
        return UrlEncode(str, "utf-8");
    }

    /**
     * 对字符串进行URLDecoder.decode解码
     *
     * @param str
     *            String
     * @param charset
     *            String
     * @return String
     */
    public static String UrlDecode(String str, String charset) {
        if (str == null)
            return "";

        String s = str;

        try {
            s = URLDecoder.decode(str, charset);
        } catch (Exception e) {

        }
        return s;
    }

    /**
     * 对字符串进行utf-8解码的URLDecoder.decode
     *
     * @param str
     *            String
     * @return String
     */
    public static String UrlDecode(String str) {
        return UrlDecode(str, "utf-8");
    }

    /**
     * 将ISO8859_1转换为utf-8
     *
     * @param strIn
     *            String
     * @return String
     */
    public static String UnicodeToUTF8(String strIn) {
        String strOut = "";
        if (strIn == null)
            return "";
        try {
            byte[] b = strIn.getBytes("ISO8859_1");
            // strOut = new String(b, "GB2312");//转为GB2312
            strOut = new String(b, "utf-8"); // 转为UTF-8
        } catch (Exception e) {
        }
        return strOut;
    }

    /**
     * 取得用户IP
     * @param request
     * @return
     */
    public static String getUserIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 生成随机字符串
     * @param verifySize 要生成随机字符串的长度
     * @return
     */
    public static String randomStr(int verifySize){
        Random rand = new Random(System.currentTimeMillis());
        String sources = "0123456789ABCDEFGHJKLMNOPQRSTUVWXYZ";
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
            verifyCode.append(sources.charAt(rand.nextInt(sources.length() - 1)));
        }
        return verifyCode.toString();
    }

    /**
     * 按照10.00的格式处理红包的返回金额
     * @param money
     * @return
     */
    public static String getFrontMoney(int money){
        String moneyStr = String.valueOf(money);

        int lenght = moneyStr.length();
        if(lenght == 2){
            return "0."+moneyStr;
        }else if(lenght == 3){
            return moneyStr.substring(0,1)+"."+moneyStr.substring(1,3);
        }
        return "0";
    }
}
