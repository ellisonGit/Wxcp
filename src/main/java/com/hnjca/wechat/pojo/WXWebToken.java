package com.hnjca.wechat.pojo;

import com.google.gson.annotations.Expose;
import lombok.Data;

/**
 * Description:
 * User: by yangyong
 * Date: 2018-09-25
 * Time: 20:30
 * Modified:
 */
@Data
public class WXWebToken {

    @Expose
    private String access_token;
    @Expose
    private Integer expires_in;
    @Expose
    private String refresh_token;
    @Expose
    private String openid;
    @Expose
    private String scope;
}
