package com.hnjca.wechat.util;

import com.hnjca.wechat.constant.WXConfig;
import com.hnjca.wechat.constant.WechatAccount;
import com.hnjca.wechat.pojo.TemplateJson;
import com.hnjca.wechat.vo.TemplateMsgResult;

/**
 * Description:发送模版消息
 * User: by yangyong
 * Date: 2018-10-17
 * Time: 16:54
 * Modified:
 */
public class TemplateMsgUtil {

    /**
     *
     * @param templateJson 发送的template json 数据
     * @return
     */
    public static boolean sendTemplateMsg(TemplateJson templateJson){

        String token = AccessTokenUtil.accessToken.getAccess_token();
        if(token == null || "".equals(token)){
            token = WxServerUtil.getAccessToken(WechatAccount.HNJCA.getAppId(),WechatAccount.HNJCA.getSecret());
            AccessTokenUtil.accessToken.setAccess_token(token);
            System.out.println("打印token:"+token);
        }
        if(token == null || "".equals(token)){
            System.out.println("-----------------------------");
            System.out.println("获取token出错，请检查后重试！");
            System.out.println("-----------------------------");
            return false;
        }
        String url = String.format(WXConfig.templateUrl,token);

        String param = String.format(
                        WXConfig.templateJsonStr,
                        templateJson.getTouser(),
                        templateJson.getTemplate_id(),
                        templateJson.getUrl(),
                        templateJson.getDataFirstValue(),
                        templateJson.getDataKeyWord1Value(),
                        templateJson.getDataKeyWord2Value(),
                        templateJson.getDataKeyWord3Value(),
                        templateJson.getDataKeyWord4Value(),
                        templateJson.getDataKeyWord5Value(),
                        templateJson.getDataRemarkValue());

        String postResult = MyRequestUtil.sendPost(url,param);

        TemplateMsgResult templateMsgResult = (TemplateMsgResult) GsonUtil.jsonToObject(postResult, TemplateMsgResult.class);
        if(templateMsgResult.getErrcode() != null && templateMsgResult.getErrcode().equals("0")){
            return true;
        }
        return false;
    }


    public static void main(String[] args) {

        TemplateJson templateJson = new TemplateJson();
        templateJson.setTouser("o5hcr6ILjwyqby99TYbgTFzzGxRE");//oJQ7ajmQhHRN7AyydcYdOQQWgZnk
        templateJson.setTemplate_id("usuiSNnSjbVXr3eqcCYC81G0bXMSYG587gE1Ri6gv5U");
        templateJson.setUrl("https://actcdn.okii.com/okii-dhf/index.html");
        templateJson.setDataFirstValue("您好，您在小天才大黄蜂定制版抽奖活动中中奖，请回复您收货地址我们将给您快递奖品。");
        templateJson.setDataKeyWord1Value("小天才大黄蜂定制版抽奖");
        templateJson.setDataKeyWord2Value("2018年12月31日10:50:00");
        templateJson.setDataKeyWord3Value("小天才电话手表Z5大黄蜂定制版1台");
        templateJson.setDataRemarkValue("(请直接在对话框回复“姓名+联系电话+领奖地址”，奖品会在7个工作日内寄出~)");

        System.out.println(TemplateMsgUtil.sendTemplateMsg(templateJson));
    }
}
