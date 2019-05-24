package com.hnjca.wechat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Description:
 * User: Ellison
 * Date: 2019-05-23
 * Time: 16:02
 * Modified:
 */
@RestController
@RequestMapping(value = "/weixin",produces = "application/json;charset=utf-8")
public class WeixinController {

    public Map<String,String> wxPayFunction() throws Exception {

     return null;
    }


}
