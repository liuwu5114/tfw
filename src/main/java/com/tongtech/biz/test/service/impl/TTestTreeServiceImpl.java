package com.tongtech.biz.test.service.impl;

import com.tongtech.biz.test.dao.TTestTreeDao;
import com.tongtech.biz.test.model.domain.TTestTree;
import com.tongtech.biz.test.service.TTestTreeService;
import com.tongtech.tfw.backend.common.models.supers.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 代码生成树结构Service业务层处理
 *
 * @author tong-framework
 * @date 2020-06-18
 */
@Service
public class TTestTreeServiceImpl extends SuperServiceImpl<TTestTreeDao, TTestTree>
    implements TTestTreeService {
  @Autowired(required = false) private TTestTreeDao tTestTreeDao;
}
