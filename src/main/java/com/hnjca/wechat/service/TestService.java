package com.hnjca.wechat.service;

import com.hnjca.wechat.pojo.YYTest;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * User: YangYong
 * Date: 2019-04-19
 * Time: 15:13
 * Modified:
 */
public interface TestService {

    Integer selectSign();

    void updateSign(Integer sign);

    List<YYTest> selectBySection(Map<String,Object> map);

    Integer selectMaxId();
}
