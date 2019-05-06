package com.hnjca.wechat.controller;

import com.hnjca.wechat.constant.WXConfig;
import com.hnjca.wechat.constant.WeChatXML;
import com.hnjca.wechat.constant.WechatAccount;
import com.hnjca.wechat.pojo.TemplateJson;
import com.hnjca.wechat.util.SHA1;
import com.hnjca.wechat.util.TemplateMsgUtil;
import com.hnjca.wechat.util.Utils;
import com.hnjca.wechat.util.WxServerUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

/**
 * Description: 微信公众号测试账号的 基础配置
 * User: YangYong
 * Date: 2019-03-20
 * Time: 14:43
 * Modified:
 */
@RestController
@RequestMapping(value = "/test",produces = "application/json;charset=utf-8")
public class TestController {

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

        String token = WechatAccount.TEST_ACCOUNT.getToken();

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
                String result = String.format(WeChatXML.text, fromUsername, toUsername, time,"点我干嘛！");
                Utils.ajaxPrint(result,response);
                return;
            }

            /**
             * key 是 click_key2的事件响应
             */
            if(event != null && "click_key2".equals(eventKey)){
                TemplateJson templateJson = new TemplateJson();
                templateJson.setTouser("o-UFItwnEnikKyHLpHBdutyfJdoU");//oJQ7ajmQhHRN7AyydcYdOQQWgZnk
                templateJson.setTemplate_id("pNvitzJ4pa5fSaEa9SPkKWZx4-zOfrlybnOn8uOdBXg");
                templateJson.setUrl("https://actcdn.okii.com/okii-dhf/index.html");
                templateJson.setDataFirstValue("您好，您在小天才大黄蜂定制版抽奖活动中中奖，请回复您收货地址我们将给您快递奖品。");
                templateJson.setDataKeyWord1Value("小天才大黄蜂定制版抽奖");
                templateJson.setDataKeyWord2Value("2018年12月31日10:50:00");
                templateJson.setDataKeyWord3Value("小天才电话手表Z5大黄蜂定制版1台");
                templateJson.setDataRemarkValue("(请直接在对话框回复“姓名+联系电话+领奖地址”，奖品会在7个工作日内寄出~)");

                System.out.println(TemplateMsgUtil.sendTemplateMsg(templateJson));
                return ;
            }
        }
    }

    public static void main(String[] args) {

        String responeJsonStr = "{\"button\" : [{\"name\":\"点我点我\",\"type\":\"click\",\"key\":\"click_key1\"},{\"name\":\"别点我\",\"type\":\"click\",\"key\":\"click_key2\"}]}";

        System.out.println(responeJsonStr);

        HttpClient clientT = new HttpClient();
        PostMethod post = new PostMethod(WXConfig.createMenuUrl + WxServerUtil.getAccessToken(WechatAccount.TEST_ACCOUNT.getAppId(),WechatAccount.TEST_ACCOUNT.getSecret()));
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
