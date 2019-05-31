package com.hnjca.wechat.util;

import com.hnjca.wechat.wxUtil.WXPayUtil;

/**
 * @author ellison
 *参数配置类
 */
public class MyConfig {

	// 微信相关
	public static final String APPID = CommonUtil.get("appId");//1.appid
	public static final String MCHID = CommonUtil.get("mchId");//2.mch_id
	public static final String SECRET = CommonUtil.get("secret");
	public static final String TOKEN = CommonUtil.get("token");
	public static final String APIKEY =CommonUtil.get("apiKey");//支付密钥Key
	public static final String notify_url = "http://llison.viphk.ngrok.org/api/weixin/wxUpdateOrder";//接收支付信息

	// 一卡通平台地址
	public static final String ICARD_URL = CommonUtil.get("url");
	//域名
	public static final String yuUrl = CommonUtil.get("yuUrl");
	//访问地址
	public static final String	comUrl=CommonUtil.get("comUrl");
	//访问本地地址
	public static final String	hostUrl=CommonUtil.get("hostUrl");


}
