package com.tongtech.tfw.backend.admin.apis.biz.sys.org.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongtech.tfw.backend.admin.apis.biz.excel.model.ImportCheckResult;
import com.tongtech.tfw.backend.admin.apis.biz.excel.model.ImportError;
import com.tongtech.tfw.backend.admin.apis.biz.excel.service.ImportCheckService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.OrgEnumCode;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.model.domain.SysOrg;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.model.dto.OrgImport;
import com.tongtech.tfw.backend.common.biz.constants.BizConstants;
import com.tongtech.tfw.backend.common.constants.ApiConstants;
import com.tongtech.tfw.backend.common.request.RequestUtil;
import com.tongtech.tfw.backend.core.helper.ObjectHelper;
import com.tongtech.tfw.backend.core.helper.StringHelper;
import com.tongtech.tfw.backend.core.helper.bean.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * OrgExcelImport
 *
 * @author Created by ivan on 2020/9/11 .
 * @version 1.0
 */
@Service
public class OrgExcelImport implements ImportCheckService<OrgImport> {

  @Autowired private SysOrgService sysOrgService;

  @Override
  public ImportCheckResult<OrgImport> doImportData(
      Map<String, OrgImport> insertMap, boolean insertTrigger) {
    List<OrgImport> successData = new ArrayList<>();
    List<ImportError<OrgImport>> errorData = new ArrayList<>();
    TreeMap<String, OrgImport> orderMap = new TreeMap<>(insertMap);
    List<SysOrg> insertData = new ArrayList<>();
    for (int i = 0; i < insertMap.keySet().size() + 1; i++) {
      OrgImport orgImport = orderMap.get(String.valueOf(i));
      if (ObjectHelper.isEmpty(orgImport)) {
        continue;
      }
      orgImport.setOrgName(orgImport.getOrgName().trim());
      String checkResult = checkImportData(orgImport);
      if (StringHelper.isNotEmpty(checkResult)) {
        ImportError<OrgImport> importError =
            ImportError.<OrgImport>builder()
                .errorMsg(checkResult)
                .lineNumber(i)
                .data(orgImport)
                .build();
        errorData.add(importError);
      } else {
        successData.add(orgImport);
        // insertData.add(converter(orgImport));
        sysOrgService.save(converter(orgImport));
      }
    }
    return new ImportCheckResult<>(successData, errorData);
  }

  private String checkImportData(OrgImport data) {
    StringBuilder result = new StringBuilder();
    // 判断机构名称是否已经存在
    List<SysOrg> sysOrgList =
        sysOrgService.list(
            new QueryWrapper<SysOrg>()
                .eq(SysOrg.ORG_CODE, data.getOrgCode())
                .ne(SysOrg.STATUS, BizConstants.STATUS_DELETE));
    if (!ObjectUtils.isEmpty(sysOrgList)) {
      result.append(OrgEnumCode.ORG_CODE_ALREADY_EXISTS.msg());
    }
    // 判断同一父级下不能同名
    List<SysOrg> sysOrgs =
        sysOrgService.list(
            new QueryWrapper<SysOrg>()
                .eq(SysOrg.ORG_NAME, data.getOrgName())
                .eq(SysOrg.PARENT_ID, data.getParentId())
                .ne(SysOrg.STATUS, BizConstants.STATUS_DELETE));
    if (!ObjectUtils.isEmpty(sysOrgs)) {
      result.append(OrgEnumCode.ORG_NAME_ALREADY_EXISTS.msg());
    }
    return result.toString();
  }

  private SysOrg converter(OrgImport orgImport) {
    SysOrg sysOrg = BeanHelper.beanToBean(orgImport, SysOrg.class);
    // 是否传入了上级机构的id：parentId
    String parentId = orgImport.getParentId();
    if (!StringHelper.isEmpty(parentId) && !BizConstants.TOP_LEVEL.equals(parentId)) {
      SysOrg parentOrg = sysOrgService.getById(parentId);
      sysOrg.setParentIds(parentOrg.getParentIds() + "," + parentOrg.getOrgId());
    } else {
      sysOrg.setParentId(BizConstants.TOP_LEVEL);
      sysOrg.setParentIds(BizConstants.TOP_LEVEL);
    }
    String userId =
        Optional.ofNullable((String) RequestUtil.getRequest().getAttribute(ApiConstants.CLAIM_KEY))
            .orElse("test");
    sysOrg.setCreateBy(userId);
    sysOrg.setUpdateBy(userId);
    sysOrg.setCreateDate(LocalDateTime.now());
    sysOrg.setUpdateDate(LocalDateTime.now());
    sysOrg.setStatus(BizConstants.STATUS_ENABLE);
    return sysOrg;
  }
}
