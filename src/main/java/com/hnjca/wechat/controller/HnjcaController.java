package com.hnjca.wechat.controller;

import com.hnjca.wechat.constant.WXConfig;
import com.hnjca.wechat.constant.WeChatXML;
import com.hnjca.wechat.constant.WechatAccount;
import com.hnjca.wechat.pojo.WXUser;
import com.hnjca.wechat.pojo.WXWebToken;
import com.hnjca.wechat.service.WxAuthService;
import com.hnjca.wechat.util.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

/**
 * Description: 湖南捷成安微信公众号配置
 * User: YangYong
 * Date: 2019-03-20
 * Time: 14:43
 * Modified:
 */
@RestController
@RequestMapping(value = "/hnjca",produces = "application/json;charset=utf-8")
public class HnjcaController {

    @Autowired
    private WxAuthService wxAuthService;

    /**
     * 微信后台接入得接口地址
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    @GetMapping(value = "/index")
    public void get(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echsotr = request.getParameter("echostr");

        String token = WechatAccount.HNJCA.getToken();

        String[] str = {token, timestamp, nonce};

        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];

        // SHA1加密
        String digest = SHA1.Encrypt(bigStr,"SHA-1").toLowerCase();

        // 确认请求来至微信
        if (digest.equals(signature)) {
            Utils.ajaxPrint(echsotr,response);
        }else{
            Utils.ajaxPrint("are you ok!",response);
        }
    }

    @PostMapping(value = "/index")
    public void post(HttpServletRequest request,HttpServletResponse response) throws IOException, DocumentException {

        String postStr = "";

        InputStream is = request.getInputStream();
        byte b[] = new byte[1024];
        int len = 0;
        int temp = 0; // 所有读取的内容都使用temp接收
        while ((temp = is.read()) != -1) { // 当没有读取完时，继续读取
            b[len] = (byte) temp;
            len++;
        }
        is.close();
        postStr = new String(b, 0, len);

        if(null != postStr && !postStr.isEmpty()) {
            Document doc = null;
            doc = DocumentHelper.parseText(postStr);
            if (null == doc) {
                Utils.ajaxPrint("",response);
                return;
            }

            Element root = doc.getRootElement();
            String fromUsername = root.elementText("FromUserName");
            String toUsername = root.elementText("ToUserName");
            String event = root.elementTextTrim("Event");
            String time = new Date().getTime() + "";
            String eventKey = root.elementTextTrim("EventKey");

            //关注提示语
            if(event != null && "subscribe".equals(event)){
                String result = String.format(WeChatXML.text, fromUsername, toUsername, time,"hello,nihao!");
                Utils.ajaxPrint(result,response);
                return;
            }

            /**
             * key 是 click_key1的事件响应
             */
            if(event != null && "click_key1".equals(eventKey)){
                String result = String.format(WeChatXML.text, fromUsername, toUsername, time,"①首次使用一卡通微信公众号请先进行认证;\n" +
                        "②绑定时请输入正确的工号和姓名进行认证，如登记错误请到一卡通办卡点更正;\n" +
                        "③如果提示该工号已被认证过，请先确认工号和姓名是否输入正确;如果无误可凭有效证件到一卡通办卡点解除认证，再进行认证;\n" +
                        "④如果需更换认证微信号，凭有效证件到一卡通办卡点解除认证后，再进行认证;\n" +
                        "⑤一卡通微信转账成功后，钱并没有写到卡上，需要您到就近的领款机或者自助机刷卡领取到卡上;\n" +
                        "⑥使用中如遇到问题，可到一卡通办卡点进行咨询;");
                Utils.ajaxPrint(result,response);
                 return ;
            }
        }
    }

    /**
     * 微信网页授权
     * @param returnUrl
     * @param code
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/auth")
    public void auth(String returnUrl,String code,
                     HttpServletRequest request, HttpServletResponse response) throws IOException {

        if(returnUrl == null || returnUrl.equals("")){
            return;
        }

        WXUser wxUser = getWxUser(request);

        /**
         * 接收微信code的本地链接
         */
        String forWXCodeUrl = String.format(WXConfig.actAuthUrl,returnUrl);

        if(wxUser == null){//如果cookies中没有wxUser

            if(code == null || code.equals("")){
                response.sendRedirect(String.format(WXConfig.step1Url,WechatAccount.HNJCA.getAppId(),StrUtil.UrlEncode(forWXCodeUrl)));
                return;
            }

            WXWebToken wxAccessToken = wxAuthService.getWXAccessToken(WechatAccount.HNJCA.getAppId(),WechatAccount.HNJCA.getSecret(),code);
            if(wxAccessToken == null || wxAccessToken.getOpenid() == null || wxAccessToken.getOpenid().equals("")){
                response.sendRedirect(String.format(WXConfig.step1Url,WechatAccount.HNJCA.getAppId(),StrUtil.UrlEncode(forWXCodeUrl)));
                return;
            }
            wxUser = new WXUser();
            wxUser.setOpenid(wxAccessToken.getOpenid());

            String signUserInfo = TicketUtil.signWxUser(wxUser);

            Cookie ticketCookie = new Cookie(WXConfig.cookieName,StrUtil.UrlEncode(signUserInfo));
            ticketCookie.setPath("/");
            ticketCookie.setDomain("130xxxx5088.com");
            ticketCookie.setMaxAge(20*24*3600);//设置cookies保存时间为20天
            response.addCookie(ticketCookie);

            response.sendRedirect(returnUrl);
            return;
        }else{ //如果cookies中有wxUser
            String openId = wxUser.getOpenid();
            String signKey = wxUser.getSignKey();

            WXUser wxUser1 = new WXUser();
            wxUser1.setOpenid(openId);
            String signUserInfo1 = TicketUtil.signWxUser(wxUser1);

            if(!signKey.equals(wxUser1.getSignKey())){
                response.sendRedirect(String.format(WXConfig.step1Url,WechatAccount.HNJCA.getAppId(),StrUtil.UrlEncode(forWXCodeUrl)));
                return;
            }

            response.sendRedirect(returnUrl);
            return;
        }
    }

    public WXUser getWxUser(HttpServletRequest request){
        WXUser wxUser  = null;
        try{
            Cookie[] cookies = request.getCookies();
            if(cookies!=null){
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals(WXConfig.cookieName) && cookies[i].getValue() != null && !cookies[i].getValue().equals("")) {
                        wxUser =(WXUser) GsonUtil.jsonToObject(StrUtil.UrlDecode(cookies[i].getValue()),WXUser.class);
                        break;
                    }
                }
            }
        }catch(Exception e){

        }
        return wxUser;
    }


    public static void main(String[] args) {

        String responeJsonStr = "{\"button\" : [" +
                    "{\"name\":\"关于本公司\",\"sub_button\":[{\"type\":\"view\",\"name\":\"联系我们\",\"url\":\"http://www.hnjca.cn/Home/List/58/1\"},{\"type\":\"view\",\"name\":\"项目案例\",\"url\":\"http://www.hnjca.cn/Home/List/3/1\"},{\"type\":\"view\",\"name\":\"产品中心\",\"url\":\"http://www.hnjca.cn/Home/List/1/1\"},{\"type\":\"view\",\"name\":\"公司简介\",\"url\":\"http://www.hnjca.cn/Home/List/53/1\"}]}," +
                "{\"name\":\"微平台\",\"sub_button\":[{\"type\":\"view\",\"name\":\"订餐服务\",\"url\":\"http://www.130xxxx5088.com/test/inTesting.html\"},{\"type\":\"view\",\"name\":\"微信充值\",\"url\":\"http://www.130xxxx5088.com/test/inTesting.html\"},{\"type\":\"view\",\"name\":\"一卡通矩阵\",\"url\":\"http://www.130xxxx5088.com/test/juzhen.html\"},{\"type\":\"view\",\"name\":\"账户信息\",\"url\":\"http://www.130xxxx5088.com/test/info_1.html\"}]}," +
                "{\"name\":\"使用帮助\",\"sub_button\":[{\"type\":\"click\",\"name\":\"操作说明\",\"key\":\"click_key1\"},{\"type\":\"view\",\"name\":\"账户认证\",\"url\":\"http://www.130xxxx5088.com/test/banding.html\"}]}" +
                "]}";

        System.out.println(responeJsonStr);

        String token = AccessTokenUtil.accessToken.getAccess_token();
        if(token == null || "".equals(token)){
            System.out.println("----------------内存里面没有token-------------");
            token = WxServerUtil.getAccessToken(WechatAccount.HNJCA.getAppId(),WechatAccount.HNJCA.getSecret());
            AccessTokenUtil.accessToken.setAccess_token(token);
        }

        if(token == null || "".equals(token)){
            System.out.println("获取token出错，请检查后重试！");
            return;
        }

        HttpClient clientT = new HttpClient();
        PostMethod post = new PostMethod(WXConfig.createMenuUrl + token);
        post.setRequestBody(responeJsonStr);
        post.getParams().setContentCharset("utf-8");
        //发送http请求
        String respStr = "";
        try {
            clientT.executeMethod(post);
            respStr = post.getResponseBodyAsString();
            System.out.println("-----------------------");
            System.out.println(respStr);
            System.out.println("-----------------------");
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
