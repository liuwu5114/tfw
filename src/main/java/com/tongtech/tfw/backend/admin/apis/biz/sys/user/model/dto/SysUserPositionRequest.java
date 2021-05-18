package com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * SysUserPositionRequest
 *
 * @author Created by ivan on 2020/8/10 .
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserPositionRequest {
  /** 角色表主键 */
  @NotEmpty
  @NotNull
  @ApiModelProperty(value = "岗位表主键数组")
  private String[] positionId;

  /** 用户表主键 */
  @NotEmpty
  @NotNull
  @ApiModelProperty(value = "用户表主键")
  private String userId;
}
