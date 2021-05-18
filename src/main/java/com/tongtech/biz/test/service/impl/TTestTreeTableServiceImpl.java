package com.tongtech.biz.test.service.impl;

import com.tongtech.biz.test.dao.TTestTreeTableDao;
import com.tongtech.biz.test.model.domain.TTestTreeTable;
import com.tongtech.biz.test.service.TTestTreeTableService;
import com.tongtech.tfw.backend.common.models.supers.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 树表Service业务层处理
 *
 * @author tong-framework
 * @date 2020-06-18
 */
@Service
public class TTestTreeTableServiceImpl extends SuperServiceImpl<TTestTreeTableDao, TTestTreeTable>
    implements TTestTreeTableService {
  @Autowired(required = false)
  private TTestTreeTableDao tTestTreeTableDao;
}
