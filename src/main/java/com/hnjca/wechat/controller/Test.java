package com.hnjca.wechat.controller;


import sun.misc.GC;

import java.text.DateFormat;
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
    public static void main(String[] args) throws InterruptedException {
       StringBuilder b=new StringBuilder();

       b.append(2);
        StringBuffer a=new StringBuffer();
        a.append(1);
        System.out.println("ad；"+a+"bb:"+GC.currentLatencyTarget());
        for (int i = 10; i>=0; i--) {
            Thread.sleep(100);  //1000毫秒就是1秒
            Thread s=new Thread();
            s.start();
            s.getId();
            System.out.println(i+"id:"+ s.getId()+"name:"+s.getName());
        }

        }


    
}
