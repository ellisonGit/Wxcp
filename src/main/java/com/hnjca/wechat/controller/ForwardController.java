package com.hnjca.wechat.controller;

import com.hnjca.wechat.enums.InfoEnum;
import com.hnjca.wechat.util.DateUtil;
import com.hnjca.wechat.util.MyConfig;
import com.hnjca.wechat.util.MyRequestUtil;
import com.hnjca.wechat.vo.ResponseInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 转发请求到一卡通平台
 * User: YangYong
 * Date: 2019-04-02
 * Time: 14:39
 * Modified:
 */
@RestController
@RequestMapping(value = "/forward",produces = "application/json;charset=utf-8")
public class ForwardController {

    /**
     * 绑定
     * @param openid
     * @param stuno
     * @param stuname
     * @return
     */
    @GetMapping(value = "/banding")
    public ResponseInfo banding(String openid,String stuno,String stuname){

        if(openid == null || "".equals(openid)){
            System.out.println("没有获取到openId");
            return new ResponseInfo(InfoEnum.NO_OPENID,-1);
        }

        if(stuno == null || ("").equals(stuno)){
            return new ResponseInfo(InfoEnum.NO_STUNO,-1);
        }

        if(stuname == null || ("").equals(stuname)){
            return new ResponseInfo(InfoEnum.NO_STUNAME_lowercase,-1);
        }

        String url = MyConfig.ICARD_URL+ "/openIdBanding";
        String result = MyRequestUtil.sendPost(url,"openid="+openid+"&stuno="+stuno+"&stuname="+stuname);

        System.out.println("绑定结果:"+result);

        return new ResponseInfo(InfoEnum.SUCCESS,result);
    }

    /**
     * 验证是否该openid已经有绑定
     * @param openId
     * @return
     */
    @GetMapping(value = "/validateBanding")
    public ResponseInfo validateBanding(String openId){

        if(openId == null || "".equals(openId)){
            return new ResponseInfo(InfoEnum.NO_OPENID,-1);
        }
        String url = MyConfig.ICARD_URL+ "/verificationBanding";
        String result = MyRequestUtil.sendPost(url,"fromuser="+openId);

        return new ResponseInfo(InfoEnum.SUCCESS,result);
    }

    /**
     * 通过openid 获取员工基本信息
     * @param openId
     * @return
     */
    @GetMapping(value = "/queryCardInfo")
    public ResponseInfo queryCardInfo(String openId){

        if(openId == null || "".equals(openId)){
            return new ResponseInfo(InfoEnum.NO_OPENID,-1);
        }

        String url = MyConfig.ICARD_URL+ "/userInfo";
        String result = MyRequestUtil.sendPost(url,"openId="+openId);

        return new ResponseInfo(InfoEnum.SUCCESS,result);
    }


    /**
     * 消费记录分页
     * @param fromuser
     * @param start
     * @param pageSize
     * @param month
     * @return
     */
    @GetMapping(value = "/queryConsumeByPage")
    public ResponseInfo queryConsumeByPage(String fromuser,String start,String pageSize,String month,String type){

        if(fromuser == null || "".equals(fromuser)){
            return new ResponseInfo(InfoEnum.NO_OPENID,-1);
        }

        if(start == null || "".equals(start)){
            return new ResponseInfo(InfoEnum.NO_START,-1);
        }

        if(pageSize == null){
            pageSize = "20";
        }

        if(month == null || month.equals("now")){
            month = DateUtil.getMonthStr();
        }

        if(type == null || ("").equals(type)){
            type = "0";
        }

        if(!type.equals("1") || !type.equals("2")){
            type = "0";
        }

        String url = MyConfig.ICARD_URL+ "/queryConsumeByPage.action";
        String result = MyRequestUtil.sendGet(url,"fromuser="+fromuser+"&start="+start+"&pageSize="+pageSize+"&month="+month+"&type="+type);

        if(result.equals("baocuo")){
            return new ResponseInfo(InfoEnum.NET_ERROR,"网络异常，请检查网络后重试！");
        }else{
            return new ResponseInfo(InfoEnum.SUCCESS,month,result);
        }
    }


    /**
     * 出入记录分页查询
     * @param fromuser
     * @param start
     * @param pageSize
     * @param month
     * @return
     */
    @GetMapping(value = "/queryDoorByPage")
    public ResponseInfo queryDoorByPage(String fromuser,String start,String pageSize,String month){

        if(fromuser == null || "".equals(fromuser)){
            return new ResponseInfo(InfoEnum.NO_OPENID,-1);
        }

        if(start == null || "".equals(start)){
            return new ResponseInfo(InfoEnum.NO_START,-1);
        }

        if(pageSize == null){
            pageSize = "20";
        }

        if(month == null || month.equals("now")){
            month = DateUtil.getMonthStr();
        }

        String url = MyConfig.ICARD_URL+ "/queryDoorByPage.action";
        String result = MyRequestUtil.sendGet(url,"fromuser="+fromuser+"&start="+start+"&pageSize="+pageSize+"&month="+month);

        if(result.equals("baocuo")){
            return new ResponseInfo(InfoEnum.NET_ERROR,"网络异常，请检查网络后重试！");
        }else{
            return new ResponseInfo(InfoEnum.SUCCESS,month,result);
        }
    }
}
