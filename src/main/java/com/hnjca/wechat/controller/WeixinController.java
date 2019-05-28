package com.hnjca.wechat.controller;

import com.hnjca.wechat.pojo.WxMultiRecharge;
import com.hnjca.wechat.wxUtil.WXPayUtil;
import com.hnjca.wechat.wxUtil.WXRequestUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
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
    public String wxUpdateOrder(HttpServletResponse response,
                                HttpServletRequest request) {
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
            BigDecimal recAmount=new BigDecimal(fee);
            String startTime=(String)resultMap.get("time_end");
            //String 转date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = sdf.parse(startTime);



        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回给微信的信息
        String returnxml = "<xml>" +
                "<return_code><![CDATA[SUCCESS]]></return_code>"+
                "<return_msg><![CDATA[OK]]></return_msg>"+
                "</xml>";
        return  returnxml;
    }

}
