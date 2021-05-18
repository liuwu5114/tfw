package com.tongtech.tfw.backend.admin.apis.biz.sys.org.dao;

import com.tongtech.tfw.backend.admin.apis.biz.sys.org.model.domain.SysOrg;
import com.tongtech.tfw.backend.common.models.supers.SuperDao;

/**
 * 组织机构表 Mapper 接口
 *
 * @author Ivan
 * @since 2020-03-31
 */
public interface SysOrgDao extends SuperDao<SysOrg> {

  /**
   * 根据机构ID获取该机构下的所有子机构
   *
   * @param orgId orgId
   * @return List<SysOrg>
   */
  /*
  @Select(
      "SELECT * FROM T_SYS_ORG where FIND_IN_SET(ORG_ID, FUN_GET_ORG_CHILD(#{orgId})) "
          + "and status = '0' ORDER BY tree_level,tree_sort ASC;")
  List<SysOrg> getChildrenById(@Param("orgId") String orgId);

  @Select("")
  List<SysOrg> getChildrenByName(@Param("orgName") String orgName);*/
}
