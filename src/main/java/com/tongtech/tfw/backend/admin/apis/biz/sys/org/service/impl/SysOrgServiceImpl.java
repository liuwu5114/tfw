package com.tongtech.tfw.backend.admin.apis.biz.sys.org.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.OrgConstants;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.dao.SysOrgDao;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.model.domain.SysOrg;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.service.SysOrgService;
import com.tongtech.tfw.backend.common.biz.constants.BizConstants;
import com.tongtech.tfw.backend.common.models.supers.SuperServiceImpl;
import com.tongtech.tfw.backend.core.constants.FrameworkConstants;
import com.tongtech.tfw.backend.core.helper.ObjectHelper;
import com.tongtech.tfw.backend.core.helper.StringHelper;
import io.jsonwebtoken.lang.Collections;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组织机构表 服务实现类
 *
 * @author Ivan
 * @since 2020-03-31
 */
@Service
public class SysOrgServiceImpl extends SuperServiceImpl<SysOrgDao, SysOrg>
    implements SysOrgService {

  @Override
  public List<SysOrg> getChildrenById(String orgId) {
    QueryWrapper<SysOrg> queryWrapper = new QueryWrapper<>();
    queryWrapper
        .eq(SysOrg.STATUS, BizConstants.STATUS_ENABLE)
        .and(
            i ->
                i.eq(SysOrg.ORG_ID, orgId)
                    .or()
                    .like(SysOrg.PARENT_IDS, FrameworkConstants.GLOBE_SPLIT_COMMA + orgId))
        .orderByAsc(SysOrg.TREE_LEVEL, SysOrg.TREE_SORT);
    // return baseMapper.getChildrenById(orgId);
    return list(queryWrapper);
  }

  @Override
  // TODO 缓存
  public String getOrgFullPathName(String orgId) {
    StringBuilder fullName = new StringBuilder();
    SysOrg sysOrg = getById(orgId);
    List<String> pIds =
        Collections.arrayToList(
            StringHelper.split(sysOrg.getParentIds(), FrameworkConstants.GLOBE_SPLIT_COMMA));
    int size = pIds.size();
    if (2 <= size) {
      for (String pid : pIds) {
        if (OrgConstants.TOP_LEVEL.equals(pid)) {
          continue;
        }
        SysOrg pOrg = getById(pid);
        if (ObjectHelper.isNotEmpty(pOrg)) {
          fullName.append(pOrg.getOrgName()).append("/");
        }
      }
    }
    fullName.append(sysOrg.getOrgName());
    return fullName.toString();
  }
}
