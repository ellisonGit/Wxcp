package com.hnjca.wechat.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hnjca.wechat.constant.WXConfig;
import com.hnjca.wechat.util.HttpUtils;
import com.hnjca.wechat.util.MyConfig;
import com.hnjca.wechat.util.MyRequestUtil;
import com.hnjca.wechat.util.StrUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Description:
 * User: Ellison
 * Date: 2019-06-06
 * Time: 9:12
 * Modified:
 */
@RestController
@RequestMapping(value = "/wxCp",produces = "application/json;charset=utf-8")
public class WxCpController {

    /**
     * 2构造网页授权链接
     *
     * @param response
     * @throws IOException
     */
    @GetMapping(value = {"/authOutUser"})
    public void auth(HttpServletResponse response) throws IOException {
        String appid = MyConfig.APPID;//微信企业号里面的appid ;
        // 微信回调地址
        String redirect_uri = "";
        //经redirect_uri的回调地址
        try {
            redirect_uri = java.net.URLEncoder.encode(MyConfig.comUrl + "/wxCp/getUser", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("ecdoe error: " + e.getMessage());
        }
        String oauth2Url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + redirect_uri + "&agentid=1000004" +
                "&response_type=code&scope=snsapi_privateinfo&state=STATE#wechat_redirect";//scope=snsapi_privateinfo弹出授权页面，并加上agentid参数
        System.out.println(oauth2Url);
        response.sendRedirect(oauth2Url);
        return;
    }

    /***
     * 3获取访问用户身份
     * @param response
     * @param code
     * @throws IOException
     */
    @GetMapping(value = "/getUser")
    public void getUser(HttpServletResponse response, String code) throws IOException, WxErrorException, ParseException {
        System.out.println("code>>>>>>>>>>>>" + code);
        if (code != null && !code.equals("")) {
            String yuUrl = MyConfig.ICARD_URL + "/selectToken";
            String res = MyRequestUtil.sendGet(yuUrl, "id=" + 1);
            System.out.println("获取本地库Token>>>>>>>>>>>>>" + res);
            if (res.equals("baocuo")) {
                response.sendRedirect(MyConfig.comUrl + "/banding.html");
            } else {
                JsonParser jp = new JsonParser();
                JsonObject jo = jp.parse(res).getAsJsonObject();
                String dstr = jo.get("data").getAsJsonObject().get("tokenTime").getAsString();//获取时间
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = df.parse(dstr);
                long currentTime = 120 * 60 * 1000;//当前时间加2小时
                Date datenow = new Date();//获取现在时间
                if (date.getTime() + currentTime > datenow.getTime()) {
                    String accessToken = jo.get("data").getAsJsonObject().get("accessToken").getAsString();//获取token
                    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
                    String result = MyRequestUtil.sendGet(url, "access_token=" + accessToken + "&code=" + code);
                    System.out.println("获取旧Token用户信息>>>>>>>>>>>>>>>" + result);
                    response.sendRedirect(MyConfig.comUrl + "/banding.html");
                } else {
                    //获取accessToken
                    String accessToken = getAccessToken();
                    String updateUrl = MyConfig.ICARD_URL + "/updateToken";
                    String rest = MyRequestUtil.sendGet(updateUrl, "accessToken=" + accessToken);//将获取到的accessToken保存到数据库
                    System.out.println("重新更新token到数据库>>>>>>>>>>>>>>" + rest);
                    //调用接口凭证
                    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
                    String result = MyRequestUtil.sendGet(url, "access_token=" + accessToken + "&code=" + code);
                    //设置Cookie
                    Cookie ticketCookie = new Cookie(WXConfig.cookieName, StrUtil.UrlEncode(result));
                    ticketCookie.setPath("/");
                    ticketCookie.setDomain(MyConfig.yuUrl);//设置域名
                    ticketCookie.setMaxAge(20 * 24 * 3600);//设置cookies保存时间为20天
                    response.addCookie(ticketCookie);
                    System.out.println("获取用户信息重新请求token>>>>>>>>>>>>>>" + result);
                    response.sendRedirect(MyConfig.comUrl + "/banding.html");
                }

            }
        }
    }


    /***
     * 1获取getAccessToken
     * @return
     */
    @GetMapping(value = "/getAccessToken")
    public String getAccessToken() {

        String corpid = MyConfig.APPID;
        String corpsecret = MyConfig.SECRET;
        try {
            String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpid + "&corpsecret=" + corpsecret;
            URL u = new URL(url);
            HttpsURLConnection huconn = (HttpsURLConnection) u.openConnection();
            StringBuilder result = new StringBuilder();
            huconn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(huconn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            if (in != null) {
                in.close();
            }
            // 打印返回结果
            System.out.println("获取token----" + result);
            JsonParser jp = new JsonParser();
            //将json字符串转化成json对象
            JsonObject jo = jp.parse(result.toString()).getAsJsonObject();
            //获取message对应的值
            String token = jo.get("access_token").getAsString();//获取token
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /***
     * 发送消息
     * @return
     */
    @GetMapping(value = "/sendMessage")
    public String sendMessage( String userId) throws IOException, ParseException, JSONException {
        String agentid = "1000004";//是应用的agenid
        String yuUrl = MyConfig.ICARD_URL + "/selectToken";
        String res = MyRequestUtil.sendGet(yuUrl, "id=" + 1);
        if(res.equals("baocuo")){
            return "请求超时";
        }
        JsonParser jp = new JsonParser();
        JsonObject jo = jp.parse(res).getAsJsonObject();
        String dstr = jo.get("data").getAsJsonObject().get("tokenTime").getAsString();//获取时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(dstr);
        long currentTime = 120 * 60 * 1000;//当前时间加2小时
        Date datenow = new Date();//获取现在时间
        if (date.getTime() + currentTime > datenow.getTime()) {
            String accessToken = jo.get("data").getAsJsonObject().get("accessToken").getAsString();//获取token
            String content = "测试消息推送：old你的快递已到，请携带工卡前往邮件中心领取。\n出发前可查看<a href=\"http://work.weixin.qq.com\">邮件中心视频实况</a>，聪明避开排队。";
            JSONObject params = new JSONObject();
            params.put("touser", userId);
            params.put("agentid", agentid);
            JSONObject mytext = new JSONObject();
            mytext.put("content", content);
            params.put("msgtype", "text");
            params.put("text", mytext);

            String result = HttpUtils.httpPostMethod("https://qyapi.weixin.qq.com/cgi-bin/message/send" + "?access_token=" + accessToken, new HashMap<>(), params.toString());
            System.out.println("发送消息响应数据>>>>>>>>>" + result);
            return result;
        } else {
            //获取accessToken
            String accessToken = getAccessToken();
            String content = "测试消息推送：new你的快递已到，请携带工卡前往邮件中心领取。\n出发前可查看<a href=\"http://work.weixin.qq.com\">邮件中心视频实况</a>，聪明避开排队。";
            JSONObject params = new JSONObject();
            params.put("touser", userId);
            params.put("agentid", agentid);
            JSONObject mytext = new JSONObject();
            mytext.put("content", content);
            params.put("msgtype", "text");
            params.put("text", mytext);

            String aa = HttpUtils.httpPostMethod("https://qyapi.weixin.qq.com/cgi-bin/message/send" + "?access_token=" + accessToken, new HashMap<>(), params.toString());
            System.out.println("发送消息响应数据>>>>>>>>>" + aa);

            return aa;
        }
    }

}
