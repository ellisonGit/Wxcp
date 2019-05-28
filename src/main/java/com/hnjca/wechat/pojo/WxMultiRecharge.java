package com.hnjca.wechat.pojo;

import lombok.Data;

import java.util.Date;

/**
 * Description: 微信充值记录 对应数据库WX_MULTI_RECHARGE表
 * User: Ellison
 * Date: 2019-05-27
 * Time: 8:56
 * Modified:
 */
@Data
public class WxMultiRecharge {

    private Integer cid ;
    private String money ;

    private String remainMoney ;

    private String state ;

    private Date createTime ;

    private String openId ;

    private String poseSequ ;

    private String cardSequ ;

    private String eCode ;

    private String JobNo ;

    private String cardNo ;

    private String outTradeNo;

    //附加
    private  String body;






}
