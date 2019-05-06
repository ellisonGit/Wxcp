package com.hnjca.wechat.constant;

/**
 * Description:
 * User: by yangyong
 * Date: 2018-09-11
 * Time: 10:10
 * Modified:
 */
public class WeChatXML {

    /**
     * 转发多客服消息用到的xml
     */
    public static final String transfer_customer_service = "<xml>"+
            "<ToUserName><![CDATA[%1$s]]></ToUserName>"+
            "<FromUserName><![CDATA[%2$s]]></FromUserName>"+
            "<CreateTime>%3$s</CreateTime>"+
            "<MsgType><![CDATA[transfer_customer_service]]></MsgType>"+
            "</xml>";


    /**
     * 普通文本消息用的xml
     */
    public static final String text = "<xml>"+
            "<ToUserName><![CDATA[%1$s]]></ToUserName>"+
            "<FromUserName><![CDATA[%2$s]]></FromUserName>"+
            "<CreateTime>%3$s</CreateTime>"+
            "<MsgType><![CDATA[text]]></MsgType>"+
            "<Content><![CDATA[%4$s]]></Content>"+
            "<FuncFlag>0</FuncFlag>"+
            "</xml>";


    /**
     * 多图文的头部
     */
    public static final String news_head = "<xml>"+
            "<ToUserName><![CDATA[%1$s]]></ToUserName>"+
            "<FromUserName><![CDATA[%2$s]]></FromUserName>"+
            "<CreateTime>%3$s</CreateTime>"+
            "<MsgType><![CDATA[news]]></MsgType>"+

            "<ArticleCount><![CDATA[%4$s]]></ArticleCount>"+
            "<Articles>";

    /**
     * 多图文的尾部
     */
    public static final String news_foot = "</Articles>"+
            "<FuncFlag>0</FuncFlag>"+
            "</xml>";
}
