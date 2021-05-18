package com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 角色用户表
 *
 * @author Ivan
 * @since 2020-03-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserRoleRequest {

    /**
     * 角色表主键
     */
    @NotEmpty
    @NotNull
    @ApiModelProperty(value = "角色表主键数组")
    private String[] roleId;

    /**
     * 用户表主键
     */
    @NotEmpty
    @NotNull
    @ApiModelProperty(value = "用户表主键")
    private String userId;
}
