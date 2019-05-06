package com.hnjca.wechat.vo;

import com.hnjca.wechat.enums.InfoEnum;

/**
 * @Description: 接口返回数据封装类
 * @Create By gaoli
 * @Date: 2017/11/15 15:49
 * @Modified:
 **/
public class ResponseInfo<T> {
    private String code;
    private T data;
    private String desc;

    private String month ;

    /**
     * 适用于只返回执行状态
     * @param infoEnum
     */
    public ResponseInfo(InfoEnum infoEnum) {
        this.code = infoEnum.getCode();
        this.desc = infoEnum.getMsg();
    }


    /**
     * 适用于返回数据，以及执行状态
     * @param infoEnum
     * @param data
     */
    public ResponseInfo(InfoEnum infoEnum, T data) {
        this.code = infoEnum.getCode();
        this.desc = infoEnum.getMsg();
        this.data = data;
    }

    /**
     * 返回code desc data month
     * @param infoEnum
     * @param month
     * @param data
     */
    public ResponseInfo(InfoEnum infoEnum, String month ,T data) {
        this.code = infoEnum.getCode();
        this.desc = infoEnum.getMsg();
        this.data = data;
        this.month = month ;
    }

    /**
     * 适应于自定义状态信息
     * @param stateCode
     * @param stateInfo
     */
    public ResponseInfo(String stateCode, String stateInfo){
       this.code = stateCode;
       this.desc = stateInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String stateCode) {
        this.code = stateCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String stateInfo) {
        this.desc = stateInfo;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
