package com.hnjca.wechat.controller;

import com.hnjca.wechat.util.AccessTokenUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Description: 用于本地开发时的测试
 * User: YangYong
 * Date: 2019-03-26
 * Time: 15:48
 * Modified:
 */
@RestController
@RequestMapping(value = "/localTest",produces = "application/json;charset=utf-8")
public class LocalTestController {


    @GetMapping(value = "/index")
    public String testTask(){
        return AccessTokenUtil.accessToken.getAccess_token();
    }
}
