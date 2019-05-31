package com.hnjca.wechat.controller;

import com.hnjca.wechat.wxUtil.WXPayUtil;
import com.hnjca.wechat.wxUtil.WXRequestUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
         int q=0;//1
        int w=0;//2
        int e=0;//3
        int r=0;//4
        int t=0;//5
        int y=0;//6

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("開始時間"+df.format(new Date()));// new Date()为获取当前系统时间
/*
        for (int i = 0; i < 1000000000; i++) {
            ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4,5,6)); //随机对象
            Random random = new Random();
            int size = list.size();
            Set<Integer> totals = new HashSet<Integer>();
            ArrayList<Integer> resultList = new ArrayList<>();
            while (totals.size() < 1) {//获取3个 //随机再集合里取出元素，添加到新哈希集
                totals.add((int) list.get(random.nextInt(size)));
            }
            Iterator iterator = totals.iterator();

            while (iterator.hasNext()) {

                int next = (int) iterator.next();
                resultList.add(next);
                if(next==1){
                    q++;
                }
                if(next==2){
                    w++;
                }
                if(next==3){
                    e++;
                }
                if(next==4){
                    r++;
                }
                if(next==5){
                    t++;
                }
                if(next==6){
                    y++;
                }

            }


        }
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("結束時間"+df2.format(new Date()));// new Date()为获取当前系统时间
        System.out.println("ellison="+q+">"+w+">"+e+">"+r+">"+t+">"+y);*/
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式


        System.out.println("結束時間"+df2.format(new Date()));// new Date()为获取当前系统时间
    }

    
}
