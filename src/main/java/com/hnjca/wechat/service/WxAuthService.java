package com.hnjca.wechat.service;


import com.hnjca.wechat.pojo.WXWebToken;

/**
 * Description:
 * User: by yangyong
 * Date: 2018-09-25
 * Time: 20:28
 * Modified:
 */
public interface WxAuthService {

    WXWebToken getWXAccessToken(String appId, String secret, String code);


}
