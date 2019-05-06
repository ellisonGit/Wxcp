package com.hnjca.wechat.vo;

import com.google.gson.annotations.Expose;

/**
 * Description:
 * User: by yangyong
 * Date: 2018-10-17
 * Time: 17:10
 * Modified:
 */
public class TemplateMsgResult {

    @Expose
    private String errcode;

    @Expose
    private String errmsg;

    @Expose
    private String msgid;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }
}
