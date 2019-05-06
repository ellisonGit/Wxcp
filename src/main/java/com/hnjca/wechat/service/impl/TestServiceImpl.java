package com.hnjca.wechat.service.impl;

import com.hnjca.wechat.dao.TestDao;
import com.hnjca.wechat.pojo.YYTest;
import com.hnjca.wechat.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * User: YangYong
 * Date: 2019-04-19
 * Time: 15:14
 * Modified:
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;

    @Override
    public Integer selectSign() {
        return testDao.selectSign();
    }

    @Override
    public void updateSign(Integer sign) {
        this.testDao.updateSign(sign);
    }

    @Override
    public List<YYTest> selectBySection(Map<String, Object> map) {
        return this.testDao.selectBySection(map);
    }

    @Override
    public Integer selectMaxId() {
        return testDao.selectMaxId();
    }
}
