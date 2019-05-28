package com.hnjca.wechat.controller;

import com.hnjca.wechat.wxUtil.WXPayUtil;
import com.hnjca.wechat.wxUtil.WXRequestUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Description:
 * User: YangYong
 * Date: 2019-04-23
 * Time: 14:33
 * Modified:
 */
public class Test {
    public static void main(String[] args) {
        String a= WXRequestUtil.GetIp();
        UUID uid = UUID.randomUUID();
        int randomNum = (int)(Math.random()*(9999-1000+1))+1000;
        String sas= WXPayUtil.generateNonceStr();

        double b=10.0;
       int c= Integer.parseInt(new java.text.DecimalFormat("0").format(b));

     /*   String money="100.25";//充值金额
        BigDecimal recAmount=new BigDecimal(money);
        double fee=recAmount.doubleValue();
        System.out.println("ellison"+sas);
        System.out.println("ellison"+new Date().getTime() / 1000);
        System.out.println("ellison"+a);*/
        System.out.println("ellison"+ c);
    }
    
}
