package com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 用户表 新增修改参数
 *
 * @author Ivan
 * @since 2020-03-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserChangePwdRequest {
    /**
     * 用户表主键
     */
    @NotNull
    @ApiModelProperty(value = "用户表主键")
    private String userId;

    /**
     * 原始密码
     */
    @NotNull
    @ApiModelProperty(value = "原始密码")
    private String oldLoginPwd;

    /**
     * 新密码
     */
    @NotNull
    @ApiModelProperty(value = "新密码")
    private String newLoginPwd;
}
