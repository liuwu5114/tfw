package com.tongtech.tfw.backend.admin.apis.biz.sys.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongtech.tfw.backend.admin.apis.biz.sys.log.LogEnumCode;
import com.tongtech.tfw.backend.admin.apis.biz.sys.log.dao.SysLogDao;
import com.tongtech.tfw.backend.admin.apis.biz.sys.log.model.domain.SysLog;
import com.tongtech.tfw.backend.admin.apis.biz.sys.log.service.SysLogService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.model.domain.SysOrg;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.service.SysOrgService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.resource.model.domain.SysResource;
import com.tongtech.tfw.backend.admin.apis.biz.sys.resource.service.SysResourceService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.domain.SysUser;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.service.SysUserService;
import com.tongtech.tfw.backend.common.biz.constants.BizConstants;
import com.tongtech.tfw.backend.common.constants.ApiConstants;
import com.tongtech.tfw.backend.common.constants.enumeration.HttpStatusCodeEnum;
import com.tongtech.tfw.backend.common.models.response.FailedResponse;
import com.tongtech.tfw.backend.common.models.supers.SuperServiceImpl;
import com.tongtech.tfw.backend.common.request.RequestUtil;
import com.tongtech.tfw.backend.common.response.ResponseWrapper;
import com.tongtech.tfw.backend.core.constants.FrameworkConstants;
import com.tongtech.tfw.backend.core.helper.IdHelper;
import com.tongtech.tfw.backend.core.helper.ObjectHelper;
import com.tongtech.tfw.backend.core.helper.StringHelper;
import com.tongtech.tfw.backend.core.helper.json.JacksonHelper;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 日志信息表 服务实现类
 *
 * @author Ivan
 * @since 2020-05-27
 */
public class SysLogServiceImpl extends SuperServiceImpl<SysLogDao, SysLog>
    implements SysLogService {
  private final SysUserService sysUserService;
  private final SysOrgService sysOrgService;
  private final SysResourceService sysResourceService;

  public SysLogServiceImpl(
      SysUserService sysUserService,
      SysOrgService sysOrgService,
      SysResourceService sysResourceService) {
    this.sysUserService = sysUserService;
    this.sysOrgService = sysOrgService;
    this.sysResourceService = sysResourceService;
  }

  /**
   * saveLog
   *
   * <p>//日志入库实现
   *
   * @author Created by ivan at 上午10:45 2020/6/16.
   */
  public void saveLog(HttpServletRequest request, ResponseWrapper response, Object result) {
    SysLog sysLog = new SysLog();
    // ID
    sysLog.setId(IdHelper.getId32bit());
    // URI
    sysLog.setUri((String) request.getAttribute(ApiConstants.REQUEST_URL));
    /* action */
    String contentPath = request.getContextPath();
    String url = StringHelper.removePrefix(sysLog.getUri(), contentPath);
    SysResource sysResource =
        sysResourceService.getOne(
            new QueryWrapper<SysResource>()
                .lambda()
                .select(
                    SysResource::getResourceId,
                    SysResource::getResourceName,
                    SysResource::getParentIds)
                .eq(SysResource::getResourceUrl, url)
                .eq(SysResource::getStatus, BizConstants.STATUS_ENABLE)
                .last("LIMIT 1"),
            false);
    if (ObjectHelper.isNotEmpty(sysResource)) {
      StringBuilder actionName = new StringBuilder();
      String[] parents =
          StringHelper.split(sysResource.getParentIds(), FrameworkConstants.GLOBE_SPLIT_COMMA);
      for (String pid : parents) {
        if (BizConstants.TOP_LEVEL.equals(pid)) {
          continue;
        }
        actionName
            .append(sysResourceService.getById(pid).getResourceName())
            .append(FrameworkConstants.GLOBE_JOIN_CROSS_BAR);
      }
      actionName.append(sysResource.getResourceName());
      sysLog.setActions(actionName.toString());
    } else {
      sysLog.setActions(url);
    }
    // IP
    sysLog.setIp(RequestUtil.getIpAddr(request));
    // METHOD
    sysLog.setMethod((String) request.getAttribute(ApiConstants.REQUEST_METHOD));
    // STATUS
    sysLog.setStatus(BizConstants.STATUS_ENABLE);
    // OP TIME
    sysLog.setOpTime(LocalDateTime.now());
    sysLog.setUser("Unknown");
    sysLog.setDept("Unknown");
    sysLog.setCreateBy("Unknown");
    sysLog.setCreateDate(LocalDateTime.now());
    if (ObjectHelper.isNotEmpty(request.getAttribute(ApiConstants.CLAIM_KEY))) {
      String userId = (String) request.getAttribute(ApiConstants.CLAIM_KEY);
      sysLog.setCreateBy(userId);
      SysUser sysUser = sysUserService.getById(userId);
      if (ObjectHelper.isNotEmpty(sysUser)) {
        sysLog.setUser(sysUser.getUserName());
        SysOrg sysOrg = sysOrgService.getById(sysUser.getOrgId());
        // 拼接机构名称
        StringBuilder orgName = new StringBuilder();
        if (ObjectHelper.isNotEmpty(sysOrg)) {
          String[] parents =
              StringHelper.split(sysOrg.getParentIds(), FrameworkConstants.GLOBE_SPLIT_COMMA);
          for (String pid : parents) {
            if (BizConstants.TOP_LEVEL.equals(pid)) {
              continue;
            }
            orgName
                .append(sysOrgService.getById(pid).getOrgName())
                .append(FrameworkConstants.GLOBE_JOIN_CROSS_BAR);
          }
          orgName.append(sysOrg.getOrgName());
          sysLog.setDept(orgName.toString());
        } else {
          sysLog.setDept(sysUser.getOrgId());
        }
      } else {
        sysLog.setUser(userId);
      }
    }
    /* 类型 */
    if (result instanceof FailedResponse) {
      FailedResponse failedResponse = (FailedResponse) result;
      if (HttpStatusCodeEnum.OK.code() == failedResponse.getCode()) {
        sysLog.setType(new BigDecimal(LogEnumCode.OPERATION.code()));
      } else {
        sysLog.setType(new BigDecimal(LogEnumCode.OTHER.code()));
      }
      sysLog.setMsg(JacksonHelper.toJson(result));
    } else {
      sysLog.setType(new BigDecimal(LogEnumCode.OPERATION.code()));
      if (sysLog.getUri().contains("/auth/login")) {
        sysLog.setType(new BigDecimal(LogEnumCode.LOGGING.code()));
      }
    }
    save(sysLog);
  }
}
