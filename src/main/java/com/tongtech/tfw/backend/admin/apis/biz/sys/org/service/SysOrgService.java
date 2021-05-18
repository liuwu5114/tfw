package com.tongtech.tfw.backend.admin.apis.biz.sys.org.service;

import com.tongtech.tfw.backend.admin.apis.biz.sys.org.model.domain.SysOrg;
import com.tongtech.tfw.backend.common.models.supers.SuperService;

import java.util.List;

/**
 * 组织机构表 服务类
 *
 * @author Ivan
 * @since 2020-03-31
 */
public interface SysOrgService extends SuperService<SysOrg> {

  /**
   * 根据机构ID获取该机构下的所有子机构
   *
   * @param orgId orgId
   * @return List<SysOrg>
   */
  List<SysOrg> getChildrenById(String orgId);

  /**
   * 获取机构全路径名称
   *
   * @param orgId
   * @return
   */
  String getOrgFullPathName(String orgId);
}
