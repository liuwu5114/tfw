package com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 用户表
 *
 * @author Ivan
 * @since 2020-03-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysUserParam extends SysUserRequest {

    /**
     * 每页数量
     */
    @Pattern(regexp = "^[1-9]+[0-9]*$")
    @NotNull
    @ApiModelProperty(value = "每页数量")
    private String limit;

    /**
     * 当前页数
     */
    @Pattern(regexp = "^[1-9]+[0-9]*$")
    @NotNull
    @ApiModelProperty(value = "当前页数")
    private String page;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String orderBy;

    /**
     * 排序方式
     */
    @ApiModelProperty(value = "排序方式")
    private String orderType;
}
