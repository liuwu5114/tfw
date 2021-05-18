package com.tongtech.biz.test.service.impl;

import com.tongtech.biz.test.dao.TTestSampleDao;
import com.tongtech.biz.test.model.domain.TTestSample;
import com.tongtech.biz.test.service.TTestSampleService;
import com.tongtech.tfw.backend.common.models.supers.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 示例Service业务层处理
 *
 * @author tong-framework
 * @date 2020-06-09
 */
@Service
public class TTestSampleServiceImpl extends SuperServiceImpl<TTestSampleDao, TTestSample>
    implements TTestSampleService {
  @Autowired(required = false) private TTestSampleDao tTestSampleDao;
}
