package com.hnjca.wechat.pojo;

import com.google.gson.annotations.Expose;
import lombok.Data;

/**
 * Description:
 * User: by yangyong
 * Date: 2018-09-25
 * Time: 20:33
 * Modified:
 */
@Data
public class WXUser {

    @Expose
    private String openid;

    @Expose
    private String nickname;

    @Expose
    private String sex;

    @Expose
    private String language;

    @Expose
    private String province;

    @Expose
    private String city;

    @Expose
    private String country;

    @Expose
    private String headimgurl;

    @Expose
    private String [] privilege;

    @Expose
    private String unionid;

    @Expose
    private String signKey;

}
