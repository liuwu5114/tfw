package com.tongtech.biz.test.service.impl;

import com.tongtech.biz.test.dao.TTestOrgTreeTableDao;
import com.tongtech.biz.test.model.domain.TTestOrgTreeTable;
import com.tongtech.biz.test.service.TTestOrgTreeTableService;
import com.tongtech.tfw.backend.common.models.supers.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户Service业务层处理
 *
 * @author tong-framework
 * @date 2020-06-22
 */
@Service
public class TTestOrgTreeTableServiceImpl
    extends SuperServiceImpl<TTestOrgTreeTableDao, TTestOrgTreeTable>
    implements TTestOrgTreeTableService {
  @Autowired(required = false) private TTestOrgTreeTableDao tTestOrgTreeTableDao;
}
