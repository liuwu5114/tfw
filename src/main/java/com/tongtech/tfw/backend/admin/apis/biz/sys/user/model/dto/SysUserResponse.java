package com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.dto;

import com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.domain.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SysUserResponse
 *
 * @author Ivan
 * @version 1.0 Created by Ivan at 2020/6/3.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserResponse extends SysUser {
  private static final long serialVersionUID = -6926573232286525332L;
  private String orgName;
}
