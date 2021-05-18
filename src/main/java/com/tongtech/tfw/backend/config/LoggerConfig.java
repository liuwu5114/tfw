package com.tongtech.tfw.backend.config;

import com.tongtech.tfw.backend.admin.apis.biz.sys.log.service.impl.SysLogServiceImpl;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.service.SysOrgService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.resource.service.SysResourceService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * LoggerConfig
 *
 * @author ivan
 * @version 1.0 Created by ivan at 1/22/21.
 */
@SpringBootConfiguration
public class LoggerConfig {
  @Autowired SysUserService sysUserService;
  @Autowired SysOrgService sysOrgService;
  @Autowired SysResourceService sysResourceService;

  @Bean("LogService")
  public SysLogServiceImpl getLogger() {
    return new SysLogServiceImpl(sysUserService, sysOrgService, sysResourceService);
  }
}
