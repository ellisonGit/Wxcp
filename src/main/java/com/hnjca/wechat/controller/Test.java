package com.hnjca.wechat.controller;

import com.hnjca.wechat.wxUtil.WXRequestUtil;

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
        System.out.println("ellison"+a);
        System.out.println("ellison"+uid);
    }
    
}
