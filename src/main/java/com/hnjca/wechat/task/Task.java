package com.hnjca.wechat.task;

import com.hnjca.wechat.constant.WechatAccount;
import com.hnjca.wechat.pojo.YYTest;
import com.hnjca.wechat.service.TestService;
import com.hnjca.wechat.util.AccessTokenUtil;
import com.hnjca.wechat.util.DateUtil;
import com.hnjca.wechat.util.WxServerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * User: YangYong
 * Date: 2019-03-26
 * Time: 15:40
 * Modified:
 */
@Component
public class Task {

     @Autowired
     private TestService testService;

//    @Scheduled(cron="0 1 * * * ?")   //测试每1分钟执行一次
//    public void getAccessTokenLocal() {
//        String token = WxServerUtil.getAccessToken(WechatAccount.TEST_ACCOUNT.getAppId(), WechatAccount.TEST_ACCOUNT.getSecret());
//        AccessTokenUtil.accessToken.setAccess_token(token);
//        System.out.println("---------------------");
//        System.out.println("获取到的token是："+token);
//        System.out.println("---------------------");
//    }

    //@Scheduled(cron="0 1 * * * ?")   //每一小时的第一分钟执行一次
    public void getAccessTokenProduction() {
        String token = WxServerUtil.getAccessToken(WechatAccount.HNJCA.getAppId(), WechatAccount.HNJCA.getSecret());
        AccessTokenUtil.accessToken.setAccess_token(token);
        System.out.println("---------------------");
        System.out.println("获取到的token是："+token);
        System.out.println("---------------------");
    }

   //todo 暂停 @Scheduled(cron="0 */1 * * * ?")   //每1分钟执行一次
    public void getTestDataBySection(){

        Integer start = testService.selectSign();
        Integer end = testService.selectMaxId();

        //表示没有最新的数据
        if(end <= start){
            System.out.println("-----没有最新的数据-----");
            return ;
        }

        Map<String,Object> map = new HashMap<>();
        map.put("start",start+1);
        map.put("end",end);
        List<YYTest>  list = testService.selectBySection(map);

        for(int i=0;i<list.size();i++){
            YYTest yyTest = list.get(i);

            System.out.println("-------------------------");
            System.out.println("获取到第"+(i+1)+"条数据：消费用户："+yyTest.getUserName()+"；消费终端："+yyTest.getMachineName()+
                        "；消费时间："+ DateUtil.dateToStr(yyTest.getCreateTime(),null)+"；余额："+yyTest.getRemainMoney()+"。");
            System.out.println("-------------------------");
        }

        try {
            Thread.sleep(1000);
            this.testService.updateSign(end);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
