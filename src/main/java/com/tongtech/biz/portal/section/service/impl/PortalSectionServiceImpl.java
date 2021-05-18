package com.tongtech.biz.portal.section.service.impl;

import com.tongtech.biz.portal.section.dao.PortalSectionDao;
import com.tongtech.biz.portal.section.model.domain.PortalSection;
import com.tongtech.biz.portal.section.service.PortalSectionService;
import com.tongtech.tfw.backend.common.models.supers.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 栏目Service业务层处理
 *
 * @author tong-framework
 * @date 2020-09-01
 */
@Service
public class PortalSectionServiceImpl extends SuperServiceImpl<PortalSectionDao, PortalSection>
    implements PortalSectionService {
  @Autowired(required = false)
  private PortalSectionDao portalSectionDao;
}
