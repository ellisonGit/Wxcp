package com.hnjca.wechat.wxUtil;


import org.apache.catalina.servlet4preview.http.HttpServletRequest;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.security.MessageDigest;
import java.util.*;
import java.util.Map.Entry;

/*
 * 用户发起统一下单请求
 * 作者：cjl
 */
public class WXRequestUtil {

    


	public static String getUrl(HttpServletRequest request){/*
		HttpServletRequest httpRequest=(HttpServletRequest)request;
		String strBackUrl = "http://" + request.getServerName()
		+ httpRequest.getContextPath()
		+ httpRequest.getServletPath();
		System.out.println("strBackUrl: " + strBackUrl);*/
		String url = request.getScheme() +"://" + request.getServerName()  
                + request.getServletPath();
		if (request.getQueryString() != null){
			url += "?" + request.getQueryString();
		}
		System.out.println("微信url地址："+url);
		return url;
	}

      
    public static String NonceStr(){  
        String res = Math.random()+"::"+new Date().toString().substring(0, 30);  
        return res;  
    }  
      
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
	   * 生成签名  
	   * @param characterEncoding   编码
	   * @param parameters          集合
	   * @param key                 微信APPID
	   * @return					sign
	   */
	  public static String createSign(String characterEncoding,SortedMap<String,Object> parameters,String key){

		  StringBuffer sb = new StringBuffer();
		  Set<Entry<String,Object>> es = parameters.entrySet();  
		  Iterator<Entry<String,Object>> it = es.iterator();  
		  while(it.hasNext()) {  
           Entry<String,Object> entry = (Entry<String,Object>)it.next();
           String k = (String)entry.getKey();  
           Object v = entry.getValue();  
           if(null != v && !"".equals(v)  
                   && !"sign".equals(k) && !"key".equals(k)) {  
               sb.append(k + "=" + v + "&");  
           }  
		  }
		  sb.append("key=" + key);  
		  //String sign  = MD5Util.getMessageDigest(sb.toString().getBytes());
		  System.out.println(">>>>加密前"+sb.toString());
	      String sign = "MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase()";
	      return sign;  
	  }
       

     //Map转xml数据  
     public static String GetMapToXML(Map<String,String> param){  
         StringBuffer sb = new StringBuffer();  
         sb.append("<xml>");  
         for (Entry<String,String> entry : param.entrySet()) {
                sb.append("<"+ entry.getKey() +">");  
                sb.append(entry.getValue());  
                sb.append("</"+ entry.getKey() +">");  
        }    
         sb.append("</xml>");  
         return sb.toString();  
     }  
     
     
     /**
 	 * 在微信支付后台生成一个预支付订单
 	 * @param
 	 * @return
 	 */
 	public static SortedMap<String,Object>  initiatePay(SortedMap<String,Object> sortedMap){
 		SortedMap<String,Object> resultMap = null;
 		try {
 			String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
 			String outXml = "StringUtil.getRequestXml(sortedMap)";
 			System.out.println("发送参数："+outXml);
 			String requestXml = "";
 			/*resultMap = StringUtil.doXMLParse(requestXml);*/
 			System.out.println("返回参数："+resultMap);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		return resultMap;
 		
 	}
     
      
      
    //微信统一下单参数设置  
    public static Map<String,Object> WXParamGenerate(String description,String out_trade_no,double total_fee,String openid){
        int fee = (int)(total_fee * 0.1);  
        //String a="{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"https://pay.qq.com\",\"wap_name\": \"腾讯充值\"}}" ;
        SortedMap<String, Object> param = new TreeMap<String, Object>();
//        Map<String,String> param = new HashMap<String,String>();  
        param.put("appid", "");
        param.put("mch_id", "");
        param.put("nonce_str","");  //随机字符串
        param.put("body", description);  
        param.put("out_trade_no",out_trade_no);  
        param.put("total_fee", fee+"");  
        param.put("spbill_create_ip", GetIp());  
        param.put("notify_url", "WeixinConfig.notify_url");
        param.put("trade_type", "JSAPI"); //公众号为JSAPI
      //  param.put("product_id", product_id+"");  
      // param.put("scene_info", a);
        param.put("openid", openid);
         System.out.println("签名前数据："+param);
        //String sign = GetSign(param);  
       String sign =createSign("utf-8", param,"WeixinConfig.apiKey");
        // String sign ="310232F58E372FDDEBF7C577EEF9F508";
       //String sign =StringUtil.createSign("utf-8", parameters, key)
        param.put("sign", sign);  
        SortedMap<String, Object> result =  initiatePay(param);
        System.out.println(result.toString());
        return result;  
    }  
    

    
  //微信下单后参数设置  
    public static Map<String,Object> WXOrderParam(String packageStr,String nonceStr){
        SortedMap<String, Object> param = new TreeMap<String, Object>();
        param.put("appId", "WeixinConfig.appid");  //公众号id
        param.put("timeStamp",new Date().getTime() / 1000 );  //时间戳
       // param.put("nonceStr",StringUtil.getRandomString(32));  //随机字符串
        param.put("nonceStr",nonceStr);  //随机字符串
        param.put("package", packageStr);  //订单详情扩展字符串
        param.put("signType","MD5");  //签名方式
        String sign =createSign("utf-8", param,"WeixinConfig.apiKey");
        param.put("paySign", sign);  //签名
        String timeStamp =  param.get("timeStamp").toString();
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
	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
	public String renderString(HttpServletResponse response, String string) {
		try {
			response.reset();
	        response.setContentType("application/json");
	        response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}
}  