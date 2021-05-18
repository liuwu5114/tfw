package com.tongtech.biz.columns.service.impl;

import com.tongtech.biz.columns.dao.TColumnCustomDao;
import com.tongtech.biz.columns.model.domain.TColumnCustom;
import com.tongtech.biz.columns.service.TColumnCustomService;
import com.tongtech.tfw.backend.common.models.supers.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 列定制Service业务层处理
 *
 * @author tong-framework
 * @date 2020-11-06
 */
@Service
@Slf4j
public class TColumnCustomServiceImpl extends SuperServiceImpl<TColumnCustomDao, TColumnCustom>
    implements TColumnCustomService {
  @Autowired(required = false)
  private TColumnCustomDao tColumnCustomDao;
}
