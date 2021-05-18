package com.tongtech.biz.datarule.service.impl;

import com.tongtech.biz.datarule.dao.TestDataRuleDao;
import com.tongtech.biz.datarule.model.domain.TestDataRule;
import com.tongtech.biz.datarule.service.TestDataRuleService;
import com.tongtech.tfw.backend.common.models.supers.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * dataruletestService业务层处理
 * 
 * @author tong-framework
 * @date 2020-08-14
 */
@Service
public class TestDataRuleServiceImpl extends SuperServiceImpl<TestDataRuleDao, TestDataRule> implements TestDataRuleService
{
    @Autowired(required = false)
    private TestDataRuleDao testDataRuleDao;

}
