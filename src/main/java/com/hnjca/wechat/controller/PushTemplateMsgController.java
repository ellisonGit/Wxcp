package com.hnjca.wechat.controller;

import com.hnjca.wechat.enums.InfoEnum;
import com.hnjca.wechat.pojo.TemplateJson;
import com.hnjca.wechat.util.TemplateMsgUtil;
import com.hnjca.wechat.vo.ResponseInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 推送模板消息
 * User: YangYong
 * Date: 2019-04-02
 * Time: 15:21
 * Modified:
 */
@RestController
@RequestMapping(value = "/",produces = "application/json;charset=utf-8")
public class PushTemplateMsgController {

    /**
     * 推送进出记录的模板消息
     * @param openId
     * @param name
     * @param action
     * @param time
     * @param address
     * @return
     */
    @GetMapping(value = "pushDoor")
    public ResponseInfo pushInAndOut(String openId,String name,String action,String time,String address){

        if(openId == null || ("").equals(openId)){
            return new ResponseInfo(InfoEnum.NO_OPENID,-1);
        }

        if(name == null || ("").equals(name)){
            return new ResponseInfo(InfoEnum.NO_STUNAME,-1);
        }

        if(action == null || ("").equals(action)){
            return new ResponseInfo(InfoEnum.NO_ACTION,-1);
        }

        if(time == null || ("").equals(time)){
            return new ResponseInfo(InfoEnum.NO_TIMESTR,-1);
        }

        if(address == null || ("").equals(address)){
            return new ResponseInfo(InfoEnum.NO_ADDRESS,-1);
        }

        TemplateJson templateJson = new TemplateJson();
        templateJson.setTouser(openId);
        templateJson.setTemplate_id("CCHmBzIIFQKXWJWxOjE_WNzVSONSHaxn20Rn7wXu6YA");
        templateJson.setUrl("http://www.130xxxx5088.com/test/info_1.html?t=1");
        templateJson.setDataFirstValue("家长您好，您的孩子有一条新的出入校园记录");
        templateJson.setDataKeyWord1Value(name);
        templateJson.setDataKeyWord2Value(time);
        templateJson.setDataKeyWord3Value(address);
        templateJson.setDataKeyWord4Value(action);
        templateJson.setDataRemarkValue("点击详情可查看更多信息");

        boolean result = TemplateMsgUtil.sendTemplateMsg(templateJson);
        if(result){
            return new ResponseInfo(InfoEnum.SUCCESS,"template message send success !");
        }else{
            return new ResponseInfo(InfoEnum.TEMPLATE_MSG_FAILED,"template message send failed !");
        }
    }

    /**
     * 推送消费的消息模板
     * @param openId
     * @param money
     * @param zdname
     * @param remainMoney
     * @param time
     * @return
     */
    @GetMapping(value = "pushConsume")
    public ResponseInfo pushConsume(String openId,String name,String money,String zdname,String remainMoney,String time){

        if(openId == null || ("").equals(openId)){
            return new ResponseInfo(InfoEnum.NO_OPENID,-1);
        }

        if(name == null || ("").equals(name)){
            return new ResponseInfo(InfoEnum.NO_STUNAME,-1);
        }

        if(money == null || ("").equals(money)){
            return new ResponseInfo(InfoEnum.NO_MONEY,-1);
        }

        if(zdname == null || ("").equals(zdname)){
            return new ResponseInfo(InfoEnum.NO_REASON,-1);
        }

        if(remainMoney == null || ("").equals(remainMoney)){
            return new ResponseInfo(InfoEnum.NO_REMAIN_MONEY,-1);
        }

        if(time == null || ("").equals(time)){
            return new ResponseInfo(InfoEnum.NO_TIMESTR,-1);
        }

        TemplateJson templateJson = new TemplateJson();
        templateJson.setTouser(openId);
        templateJson.setTemplate_id("usuiSNnSjbVXr3eqcCYC81G0bXMSYG587gE1Ri6gv5U");
        templateJson.setUrl("http://www.130xxxx5088.com/test/info_1.html?t=2");
        templateJson.setDataFirstValue("您好，"+name+"的校园卡刚进行了一笔消费");
        templateJson.setDataKeyWord1Value(money);
        templateJson.setDataKeyWord2Value(zdname);
        templateJson.setDataKeyWord3Value(time);
        templateJson.setDataKeyWord4Value(remainMoney);
        templateJson.setDataRemarkValue("点击详情可查看更多信息");

        boolean result = TemplateMsgUtil.sendTemplateMsg(templateJson);
        if(result){
            return new ResponseInfo(InfoEnum.SUCCESS,"template message send success !");
        }else{
            return new ResponseInfo(InfoEnum.TEMPLATE_MSG_FAILED,"template message send failed !");
        }
    }

    /**
     * 推送充值成功的模板消息
     * @param openId
     * @param money
     * @param remainMoney
     * @param time
     * @param account
     * @param name
     * @return
     */
    @GetMapping(value = "pushRecharge")
    public ResponseInfo pushRecharge(String openId,String money,String remainMoney,String time,String account,String name){
        if(openId == null || ("").equals(openId)){
            return new ResponseInfo(InfoEnum.NO_OPENID,-1);
        }

        if(money == null || ("").equals(money)){
            return new ResponseInfo(InfoEnum.NO_MONEY,-1);
        }

        if(account == null || ("").equals(account)){
            return new ResponseInfo(InfoEnum.NO_ACCOUNT,-1);
        }

        if(remainMoney == null || ("").equals(remainMoney)){
            return new ResponseInfo(InfoEnum.NO_REMAIN_MONEY,-1);
        }

        if(time == null || ("").equals(time)){
            return new ResponseInfo(InfoEnum.NO_TIMESTR,-1);
        }

        if(name == null || ("").equals(name)){
            return new ResponseInfo(InfoEnum.NO_STUNAME,-1);
        }

        TemplateJson templateJson = new TemplateJson();
        templateJson.setTouser(openId);
        templateJson.setTemplate_id("oyXE8Q0W79G_oFJ5o6IqGx7572wwwfqPJ4VvmIS62Nw");
        templateJson.setUrl("http://www.130xxxx5088.com/test/info_1.html?t=2");
        templateJson.setDataFirstValue("您有一条饭卡充值成功消息");
        templateJson.setDataKeyWord1Value(account);
        templateJson.setDataKeyWord2Value(name);
        templateJson.setDataKeyWord3Value(money);
        templateJson.setDataKeyWord4Value(time);
        templateJson.setDataKeyWord5Value(remainMoney);
        templateJson.setDataRemarkValue("点击详情可查看更多信息");

        boolean result = TemplateMsgUtil.sendTemplateMsg(templateJson);
        if(result){
            return new ResponseInfo(InfoEnum.SUCCESS,"template message send success !");
        }else{
            return new ResponseInfo(InfoEnum.TEMPLATE_MSG_FAILED,"template message send failed !");
        }
    }
}
