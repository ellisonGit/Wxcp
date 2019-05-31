package com.hnjca.wechat.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hnjca.wechat.pojo.WxMultiRecharge;
import com.hnjca.wechat.util.MyConfig;
import com.hnjca.wechat.util.MyRequestUtil;
import com.hnjca.wechat.wxUtil.WXPayUtil;
import com.hnjca.wechat.wxUtil.WXRequestUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.hnjca.wechat.util.MyConfig.hostUrl;


/**
 * Description:
 * User: Ellison
 * Date: 2019-05-23
 * Time: 16:02
 * Modified:
 */
@RestController
@RequestMapping(value = "/weixin",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
public class WeixinController {
    /**
     * 下单
     * @param
     * @return
     */
    @GetMapping(value = "/order")
    public Map<String,String> wxPayOrder(WxMultiRecharge wxMultiRecharge) throws Exception {
        String body=wxMultiRecharge.getBody();//商品描述
        int randomNum = (int)(Math.random()*(9999-1000+1))+1000;
        String mchOrder=WXRequestUtil.createTimestamp()+randomNum ;//商户单号
        String money=wxMultiRecharge.getMoney();//充值金额
        BigDecimal recAmount=new BigDecimal(money);
        double fee=recAmount.doubleValue();//转换为double类型
        String openId= wxMultiRecharge.getOpenId();//openid
        Map<String, String> data= WXRequestUtil.WXParamGenerate(body,mchOrder, fee, openId);//微信统一下单参数设置
        String packageStr ="prepay_id="+data.get("prepay_id");
        String nonceStr =  data.get("nonce_str");

        Map<String, String> resultParams=WXRequestUtil.WXOrderParam(packageStr,nonceStr);  //微信下单后参数设置
        String appid = resultParams.get("appId");
        String nonceStrs =  resultParams.get("nonceStr");
		String packageStrs = resultParams.get("package");
        String paySign = resultParams.get("paySign");
        String signType = resultParams.get("signType");
        String timeStamp =  resultParams.get("timeStamp");

        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("appid", appid);
        paraMap.put("nonce_str", nonceStrs);
        paraMap.put("package_str", packageStrs);
        paraMap.put("pay_sign", paySign);
        paraMap.put("sign_type", signType);
        paraMap.put("time_stamp", timeStamp);

        paraMap.put("success", "1");
        paraMap.put("message", "成功！");
        return paraMap;
    }

    /**
     * 支付微信回调地址
     *
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/wxUpdateOrder")
    public String wxUpdateOrder(HttpServletResponse response, HttpServletRequest request) {
        String returnxml = "<xml>" +
                "<return_code><![CDATA[SUCCESS]]></return_code>"+
                "<return_msg><![CDATA[OK]]></return_msg>"+
                "</xml>";
        try {
            String resultWX = WXRequestUtil.weixin_notify_para(request);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(resultWX);
            System.out.println("微信返回信息：" + resultMap);
            String openid=resultMap.get("openid");
            String orderId=resultMap.get("out_trade_no");
            String totalFee=resultMap.get("total_fee");
            String transactionId=resultMap.get("transaction_id");
            int i = Integer.parseInt(totalFee);
            double fee=i*0.01;
            String recAmount=fee+"";
            String startTime=resultMap.get("time_end");
            //String 转date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = sdf.parse(startTime);
            String url = MyConfig.ICARD_URL+ "/saveWxInfo";
            String result = MyRequestUtil.sendPost(url,"openId="+openid+"&outTradeNo="+orderId+"&money="+recAmount+"&uid="+transactionId+"&body="+startTime);
            System.out.println("结果:"+result);
            //todo 将充值数据返回本地数据库
            JsonParser jp = new JsonParser();
            //将json字符串转化成json对象
            JsonObject jo = jp.parse(result).getAsJsonObject();
            //获取message对应的值
           // String data = jo.get("data").getAsString();
            String data = jo.get("data").getAsJsonObject().get("jobNo").getAsString();
            System.out.println("message：" + data);
            String hostUrl = MyConfig.hostUrl+ "/saveInfo";
            String res = MyRequestUtil.sendGet(hostUrl,"deposit="+recAmount+"&empId="+data+"&cardSn="+orderId);//param金额，工号
            System.out.println("微信充值补贴结果:"+res);
            return  returnxml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回给微信的信息
        return  returnxml;
    }

}
