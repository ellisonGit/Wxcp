package com.hnjca.wechat.vo;

import com.google.gson.annotations.Expose;
import lombok.Data;

/**
 * Description: 微信 access_token
 * User: YangYong
 * Date: 2019-03-20
 * Time: 15:11
 * Modified:
 */
@Data
public class WxAccessToken {

    @Expose
    private String access_token;
    @Expose
    private Integer expires_in ;
}
