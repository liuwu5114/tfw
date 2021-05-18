package com.tongtech.tfw.backend.admin.apis.biz.sys.user.service.impl;

import com.tongtech.tfw.backend.admin.apis.biz.sys.user.dao.SysUserDao;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.domain.SysUser;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.service.SysUserService;
import com.tongtech.tfw.backend.common.models.supers.SuperServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户表 服务实现类
 *
 * @author Ivan
 * @since 2020-03-31
 */
@Service
public class SysUserServiceImpl extends SuperServiceImpl<SysUserDao, SysUser>
    implements SysUserService {}
