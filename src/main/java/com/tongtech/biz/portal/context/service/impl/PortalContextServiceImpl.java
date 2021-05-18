package com.tongtech.biz.portal.context.service.impl;

import com.tongtech.biz.portal.context.dao.PortalContextDao;
import com.tongtech.biz.portal.context.model.domain.PortalContext;
import com.tongtech.biz.portal.context.service.PortalContextService;
import com.tongtech.tfw.backend.common.models.supers.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文章Service业务层处理
 *
 * @author tong-framework
 * @date 2020-09-01
 */
@Service
public class PortalContextServiceImpl extends SuperServiceImpl<PortalContextDao, PortalContext>
    implements PortalContextService {
  @Autowired(required = false)
  private PortalContextDao portalContextDao;
}
