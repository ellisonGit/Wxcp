package com.hnjca.wechat.dao;

import com.hnjca.wechat.pojo.YYTest;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * User: YangYong
 * Date: 2019-04-19
 * Time: 10:49
 * Modified:
 */
public interface TestDao {

    Integer selectSign();

    void updateSign(Integer sign);

    List<YYTest> selectBySection(Map<String,Object> map);

    Integer selectMaxId();
}
