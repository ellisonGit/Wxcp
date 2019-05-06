package com.hnjca.wechat.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

/**
 * Description:
 * User: by yangyong
 * Date: 2018-07-11
 * Time: 20:24
 * Modified:
 */
public class Utils {

    /**
     * 获取当前用户IP地址
     */
    public static String getIp(HttpServletRequest request) {

        String unknow="unknown";

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || unknow.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknow.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknow.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        try{
            if(ip!=null){
                int index = ip.indexOf(",");
                if(index!=-1){
                    ip = ip.substring(0, index);
                }
            }
        }catch(Exception e){
        }
        return ip;
    }


    /**
     * ISPOST
     * @param request
     * @return
     */
    public static boolean ispost (HttpServletRequest request){
        return request.getMethod().equalsIgnoreCase("POST");
    }

    /**
     * 关键字包含
     * @author du
     * @return
     */
    public static boolean isContainKeyWord(String resource ,String spl , String keyWord){

        String rs [] = resource.split(spl);
        boolean idc = false ;
        for(int i = 0 ; i < rs.length ; i++){
            if(keyWord.contains(rs[i])){
                idc = true ;
                break ;
            }
        }
        return idc ;

    }

    /**
     * response返回内容
     * @param rs
     * @param response
     */
    public static void ajaxPrint(String rs, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control",
                "no-store, max-age=0, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        try {
            PrintWriter out = response.getWriter();
            out.write(rs);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否是在工作时间以外
     * @return
     */
    public static boolean isOutWorkTime(){
        Calendar calendar = Calendar.getInstance();
        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        boolean flag = nowHour < 8 || nowHour >= 22;
        return  flag;
    }

    /**
     * 是否是在工作时间以外 过年期间
     * @return
     */
    public static boolean isOutSpringFestivalWorkTime(){
        Calendar calendar = Calendar.getInstance();
        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        boolean flag = nowHour < 8 || nowHour >= 18;
        return  flag;
    }

    /**
     * 是否在小天才大黄蜂活动期间
     * @return
     */
    public static boolean inBigYellowBeeActTime(){
        Calendar calendar = Calendar.getInstance();
        int nowDay = calendar.get(Calendar.DATE);
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH);

        if(nowYear == 2018 && nowMonth == 11 && (nowDay >= 27 && nowDay <= 30 )){
            return true;
        }
        return false;
    }

    /**
     * 替换数字成字母
     * @param num
     * @return
     */
    public static String numToLetter(String num){
        num = num.replaceAll("1","a");
        num = num.replaceAll("2","B");
        num = num.replaceAll("3","c");
        num = num.replaceAll("4","d");
        num = num.replaceAll("5","E");
        num = num.replaceAll("6","F");
        num = num.replaceAll("7","g");
        num = num.replaceAll("8","h");
        num = num.replaceAll("9","i");
        num = num.replaceAll("0","o");

        return  num;
    }

    /**
     * 获取随机的幸运抽奖码
     * @param openId
     * @return
     */
    public static String getLuckCode(String openId){
        String nowTime = String.valueOf(new Date().getTime());
        nowTime = nowTime.substring(nowTime.length()-4);
        nowTime = numToLetter(nowTime);
        openId = openId.substring(openId.length()-6);

        return nowTime + openId;
    }

    /**
     * 判断当前时间是否在春节期间
     * @return
     */
    public static boolean inSpringFestival(){

        Calendar calendar = Calendar.getInstance();
        int nowDay = calendar.get(Calendar.DATE);
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH);

        if(nowYear == 2019 && nowMonth == 1 && (nowDay >= 4 && nowDay <= 7 )){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        int nowMonth = calendar.get(Calendar.MONTH);
        int nowDay = calendar.get(Calendar.DATE);
        int nowYear = calendar.get(Calendar.YEAR);
        System.out.println("month:"+nowMonth);
        System.out.println("day:"+nowDay);
        System.out.println("nowYear:"+nowYear);
    }
}
