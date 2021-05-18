package com.tongtech.tfw.backend.admin.apis.biz.sys.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongtech.tfw.backend.admin.apis.biz.excel.model.ImportCheckResult;
import com.tongtech.tfw.backend.admin.apis.biz.excel.model.ImportError;
import com.tongtech.tfw.backend.admin.apis.biz.excel.service.ImportCheckService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.model.domain.SysOrg;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.service.SysOrgService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.UserConstants;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.UserHelper;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.domain.SysUser;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.dto.UserImport;
import com.tongtech.tfw.backend.common.biz.constants.BizConstants;
import com.tongtech.tfw.backend.common.constants.ApiConstants;
import com.tongtech.tfw.backend.common.request.RequestUtil;
import com.tongtech.tfw.backend.core.helper.ObjectHelper;
import com.tongtech.tfw.backend.core.helper.StringHelper;
import com.tongtech.tfw.backend.core.helper.bean.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * UserExcelImportService
 *
 * @author Created by ivan on 2020/7/27 .
 * @version 1.0
 */
@Service
public class UserExcelImportService implements ImportCheckService<UserImport> {
  @Autowired private SysUserService sysUserService;
  @Autowired private SysOrgService sysOrgService;
  private final Map<String, String> orgMap = new HashMap<>();

  @Override
  public ImportCheckResult<UserImport> doImportData(
      Map<String, UserImport> insertMap, boolean insertTrigger) {
    List<UserImport> successData = new ArrayList<>();
    List<ImportError<UserImport>> errorData = new ArrayList<>();
    List<SysUser> insertData = new ArrayList<>();
    for (String dataId : insertMap.keySet()) {
      UserImport userImport = insertMap.get(dataId);
      String checkResult = checkImportData(userImport);
      if (StringHelper.isNotEmpty(checkResult)) {
        ImportError<UserImport> importError =
            ImportError.<UserImport>builder()
                .errorMsg(checkResult)
                .lineNumber(Integer.parseInt(dataId))
                .data(userImport)
                .build();
        errorData.add(importError);
      } else {
        successData.add(userImport);
        insertData.add(converter(userImport));
      }
    }
    if (!insertData.isEmpty() && insertTrigger) {
      sysUserService.saveBatch(insertData);
    }
    return new ImportCheckResult<>(successData, errorData);
  }

  private SysUser converter(UserImport userImport) {
    SysUser sysUser = BeanHelper.beanToBean(userImport, SysUser.class);
    String orgName = userImport.getOrgName();
    String orgId = orgMap.get(orgName);
    sysUser.setOrgId(orgId);
    String userId =
        Optional.ofNullable((String) RequestUtil.getRequest().getAttribute(ApiConstants.CLAIM_KEY))
            .orElse("test");
    sysUser.setCreateBy(userId);
    sysUser.setUpdateBy(userId);
    sysUser.setCreateDate(LocalDateTime.now());
    sysUser.setUpdateDate(LocalDateTime.now());
    sysUser.setStatus(BizConstants.STATUS_ENABLE);
    sysUser.setUserType("0");
    if (ObjectHelper.isNotEmpty(sysUser.getSex()) && "男".equals(sysUser.getSex().trim())) {
      sysUser.setSex("0");
    } else if (ObjectHelper.isNotEmpty(sysUser.getSex()) && "女".equals(sysUser.getSex().trim())) {
      sysUser.setSex("1");
    }
    sysUser.setStatus(BizConstants.STATUS_ENABLE);
    sysUser.setLoginPwd(UserHelper.userPassword(UserConstants.USER_PASSWORD));
    return sysUser;
  }

  private String checkImportData(UserImport data) {
    StringBuilder result = new StringBuilder();
    // 登录名唯一
    SysUser existsSysUser =
        sysUserService.getOne(
            new QueryWrapper<SysUser>()
                .eq(SysUser.LOGIN_NAME, data.getLoginName())
                .ne(SysUser.STATUS, BizConstants.STATUS_DELETE)
                .last("LIMIT 1"));
    if (ObjectHelper.isNotEmpty(existsSysUser)) {
      result.append("登录名已存在").append(";");
    }
    // 机构部门
    if (ObjectHelper.isEmpty(orgMap.get(data.getOrgName()))
        || StringHelper.isEmpty(orgMap.get(data.getOrgName()))) {
      String orgMessage = checkOrg(data.getOrgName());
      if (!StringHelper.isEmpty(orgMessage)) {
        result.append(orgMessage);
      }
    }
    return result.toString();
  }

  private String checkOrg(String orgNames) {
    StringBuilder result = new StringBuilder();
    // 机构部门存在
    String[] orgs = orgNames.split("/");
    String pOrgId = BizConstants.TOP_LEVEL;
    String cOrgId = "";
    int counter = 0;
    for (String orgName : orgs) {
      SysOrg sysOrg =
          sysOrgService.getOne(
              new QueryWrapper<SysOrg>()
                  .lambda()
                  .eq(SysOrg::getOrgName, orgName)
                  .eq(SysOrg::getParentId, pOrgId)
                  .ne(SysOrg::getStatus, BizConstants.STATUS_DELETE)
                  .last("LIMIT 1"));
      if (ObjectHelper.isNotEmpty(sysOrg)) {
        pOrgId = sysOrg.getOrgId();
        cOrgId = sysOrg.getOrgId();
        counter++;
      } else {
        result.append("所属部门,").append(orgName).append(" 不存在");
        break;
      }
    }
    if (counter == orgs.length) {
      orgMap.put(orgNames, cOrgId);
    }
    return result.toString();
  }
}
