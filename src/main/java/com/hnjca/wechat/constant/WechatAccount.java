package com.hnjca.wechat.constant;

/**
 * Description: 微信账号 appId secret token
 * User: by yangyong
 * Date: 2018-05-31
 * Time: 15:41
 * Modified:
 */
public enum WechatAccount {

    TEST_ACCOUNT("wx42a3f104c41fe2ea","97dd18649d642c00c7b66253deb3e354","test_account"),
    HNJCA("wxbdcb4a18a86e232e","8d420ccfd7488efd8621e03f0f98ba1d","hunanJIECHENGAN");
   //appid
    private String appId ;

    private String secret;

    private String token ;

    WechatAccount(String appId, String secret,String token) {
        this.appId = appId;
        this.secret = secret;
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
