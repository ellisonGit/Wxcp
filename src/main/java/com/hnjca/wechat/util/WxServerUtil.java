package com.hnjca.wechat.util;

import com.hnjca.wechat.constant.WXConfig;
import com.hnjca.wechat.constant.WechatAccount;
import com.hnjca.wechat.vo.WxAccessToken;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;

public class WxServerUtil {

	/**
	 * 获取access_token
	 * @param appid
	 * @param secret
	 * @return
	 */
	public static String getAccessToken(String appid , String secret){

		HttpClient clientT = new HttpClient();
		PostMethod post = new PostMethod(String.format(WXConfig.getAccessTokenUrl,appid,secret));
		System.out.println("获取token地址：ellison"+String.format(WXConfig.getAccessTokenUrl,appid,secret));
		HttpClientParams httparams = new HttpClientParams();
		httparams.setSoTimeout(15000);
		httparams.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
		httparams.setContentCharset("utf-8");
		post.setParams(httparams);
		WxAccessToken wxAccessToken = null;
		// 发送http请求
		String respStr = "";
		try {
			int statusCode = clientT.executeMethod(post);
			if (statusCode == 200){
				respStr = post.getResponseBodyAsString();
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally
		{
			if (post != null){
				post.releaseConnection();
			}
		}
		if (respStr != null && !respStr.equals("")) {
			wxAccessToken = (WxAccessToken) GsonUtil.jsonToObject(respStr,WxAccessToken.class);
		}

		if(wxAccessToken != null){
			return wxAccessToken.getAccess_token();
		}else{
			return "";
		}
    }


	/**
	 * 获取生成带参数的二维码中 需要的ticket
	 * @return
	 */
	public static String getQrcodeTickect(){
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%1$s";
		url = String.format(url,getAccessToken(WechatAccount.TEST_ACCOUNT.getAppId(), WechatAccount.TEST_ACCOUNT.getSecret()));
		String param = "{\"expire_seconds\": 2592000, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"dahuangfeng\"}}}";
		String result = MyRequestUtil.sendPost(url,param);
		System.out.println(result);
		return result ;
	}


	public static void main(String[] args) {
		System.out.println(getAccessToken(WechatAccount.HNJCA.getAppId(), WechatAccount.HNJCA.getSecret()));
	}


}
