package com.hnjca.wechat.wxUtil;
import com.hnjca.wechat.util.MyConfig;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;
/*
 * 用户发起统一下单请求
 * 作者：cjl
 */
public class WXRequestUtil {

     public static String GetIp() {
        InetAddress ia=null;
        try {
            ia=InetAddress.getLocalHost();
            String localip=ia.getHostAddress();
            return localip;
        } catch (Exception e) {
            return null;
        }
    }

     /**
 	 * 在微信支付后台生成一个预支付订单
 	 * @param
 	 * @return
 	 */
 	public static Map<String,String>  initiatePay(Map<String,String> sortedMap){
        Map<String,String> resultMap = null;
 		try {
 			String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
 			String outXml =WXPayUtil.mapToXml(sortedMap);
 			System.out.println("发送参数："+outXml);
 			String requestXml = httpsRequest(url, "POST", outXml);
 			resultMap =WXPayUtil.xmlToMap(requestXml);
 			System.out.println("返回参数："+resultMap);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		return resultMap;
 		
 	}
     
    //微信统一下单参数设置
    public static Map<String,String> WXParamGenerate(String body,String out_trade_no,double total_fee,String openid) throws Exception {
        int fee = (int)(total_fee * 0.1);
        Map<String, String> param = new TreeMap<String, String>();
        param.put("appid", MyConfig.APPID);//appid
        param.put("mch_id",MyConfig.MCHID);//商户ID
        param.put("nonce_str",WXPayUtil.generateNonceStr());  //随机字符串
        param.put("body", body);//商品描述
        param.put("out_trade_no",out_trade_no);//商户订单号
        param.put("total_fee", fee+"");//金额
        param.put("spbill_create_ip", GetIp());//终端IP
        param.put("notify_url", MyConfig.notify_url);//回调接收支付信息地址
        param.put("trade_type", "JSAPI"); //公众号为JSAPI
        param.put("openid", openid);
        System.out.println("签名前数据："+param);
        //String sign =createSign("utf-8", param,MyConfig.APIKEY);//apiKey
        String sign =WXPayUtil.generateSignature(param,MyConfig.APIKEY);//apiKey
        param.put("sign", sign);  //签名
        Map<String, String> result =  initiatePay(param);
        return result;
    }  

  //微信下单后参数设置  
    public static Map<String,String> WXOrderParam(String packageStr,String nonceStr) throws Exception {
        Map<String, String> param = new TreeMap<String, String>();
        param.put("appId",MyConfig.APPID);  //公众号id
        param.put("timeStamp", createTimestamp());  //时间戳
        param.put("nonceStr",nonceStr);  //随机字符串
        param.put("package", packageStr);  //订单详情扩展字符串
        param.put("signType","MD5");  //签名方式
        System.out.println("签名前数据2："+param);
        String sign =WXPayUtil.generateSignature(param,MyConfig.APIKEY);//apiKey
        param.put("paySign", sign);  //签名
        String timeStamp =  param.get("timeStamp");
        System.out.println("微信下单后参数设置随机字符串："+nonceStr);
        System.out.println("微信下单后参数设置时间："+timeStamp);
        System.out.println("微信下单后参数设置签名："+sign);
        return param;
    }  
    
    //发起微信支付请求  
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {    
      try {    
          URL url = new URL(requestUrl);    
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();    
            
          conn.setDoOutput(true);    
          conn.setDoInput(true);    
          conn.setUseCaches(false);    
          // 设置请求方式（GET/POST）    
          conn.setRequestMethod(requestMethod);    
          conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");    
          // 当outputStr不为null时向输出流写数据    
          if (null != outputStr) {    
              OutputStream outputStream = conn.getOutputStream();    
              // 注意编码格式    
              outputStream.write(outputStr.getBytes("UTF-8"));    
              outputStream.close();    
          }    
          // 从输入流读取返回内容    
          InputStream inputStream = conn.getInputStream();    
          InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");    
          BufferedReader bufferedReader = new BufferedReader(inputStreamReader);    
          String str = null;  
          StringBuffer buffer = new StringBuffer();    
          while ((str = bufferedReader.readLine()) != null) {    
              buffer.append(str);    
          }    
          // 释放资源    
          bufferedReader.close();    
          inputStreamReader.close();    
          inputStream.close();    
          inputStream = null;    
          conn.disconnect();    
          return buffer.toString();    
      } catch (ConnectException ce) {    
          System.out.println("连接超时：{}"+ ce);    
      } catch (Exception e) {    
          System.out.println("https请求异常：{}"+ e);    
      }    
      return null;    
    }    
        
    /**
	 * 微信支付回调获取返回参数
	 * 
	 * @param request
	 * @return String
	 */
	public static String weixin_notify_para(HttpServletRequest request) {

		String result = null;

		try {
			BufferedReader reader = request.getReader();
			// 获取参数
			String line = "";
			StringBuffer inputString = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				inputString.append(line);
			}
			if (reader != null) {
				reader.close();
			}
			result = inputString.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


    public static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    //生成随机字符串
    public static String createNonceStr() {
        return UUID.randomUUID().toString();
    }
    public static  String convertStreamToString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));


            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}