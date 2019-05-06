package com.hnjca.wechat.constant;

/**
 * Description:
 * User: by yangyong
 * Date: 2018-09-25
 * Time: 20:42
 * Modified:
 */
public class WXConfig {

    /**
     * 发送模板消息url
     */
    public static final String templateUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%1$s";

    /**
     * 模版消息请求的json字符串
     */
    public static final String templateJsonStr = "{\"touser\":\"%1$s\"," +
            "\"template_id\":\"%2$s\"," +
            "\"url\":\"%3$s\"," +
            "\"data\":{" +
            "\"first\":{\"value\":\"%4$s\",\"color\":\"#173177\"}," +
            "\"keyword1\":{\"value\":\"%5$s\",\"color\":\"#173177\"}," +
            "\"keyword2\":{\"value\":\"%6$s\",\"color\":\"#173177\"}," +
            "\"keyword3\":{\"value\":\"%7$s\",\"color\":\"#173177\"}," +
            "\"keyword4\":{\"value\":\"%8$s\",\"color\":\"#173177\"}," +
            "\"keyword5\":{\"value\":\"%9$s\",\"color\":\"#173177\"}," +
            "\"remark\":{\"value\":\"%10$s\",\"color\":\"#173177\"}" +
            "}}";


    public static final String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=%1$s&openid=%2$s&lang=zh_CN";

    public static final String cookieName = "wx_user_info";

    public static final String step1Url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%1$s&redirect_uri=%2$s&response_type=code&scope=snsapi_userinfo&state=abc#wechat_redirect";

    public static final String actAuthUrl = "http://www.130xxxx5088.com/api/hnjca/auth?returnUrl=%1$s";

    /**
     * 创建菜单的链接
     */
    public static final String createMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

    /**
     * 获取基础token的链接
     */
    public static final String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%1$s&secret=%2$s" ;

    /**
     * 获取网页授权token的链接
     */
    public static final String getWebTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%1$s&secret=%2$s&code=%3$s&grant_type=authorization_code" ;
}
