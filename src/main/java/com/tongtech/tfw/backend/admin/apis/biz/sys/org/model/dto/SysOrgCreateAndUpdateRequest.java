package com.tongtech.tfw.backend.admin.apis.biz.sys.org.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * 组织机构表
 *
 * @author Ivan
 * @since 2020-03-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysOrgCreateAndUpdateRequest {

    /**
     * 机构表主键
     */
    @ApiModelProperty(value = "机构表主键")
    private String orgId;

    /**
     * 父级机构主键，如果是顶级节点值为0
     */
    @ApiModelProperty(value = "父级机构主键，如果是顶级节点值为0")
    private String parentId;

    /**
     * 所有父级机构主键以英文逗号隔开如：一级，二级等
     */
    @Length(max = 2000)
    @ApiModelProperty(value = "所有父级机构主键以英文逗号隔开如：一级，二级等")
    private String parentIds;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号", example = "0.0")
    private BigDecimal treeSort;

    /**
     * 层次级别，1顶级依次类推
     */
    @ApiModelProperty(value = "层次级别，1顶级依次类推", example = "0.0")
    private BigDecimal treeLevel;

    /**
     * 机构名称
     */
    @NotNull
    @Length(min = 1, max = 100)
    @ApiModelProperty(value = "机构名称")
    private String orgName;

    /**
     * 机构编码
     */
    @Length(max = 30)
    @ApiModelProperty(value = "机构编码")
    private String orgCode;

    /**
     * 联系地址
     */
    @Length(max = 255)
    @ApiModelProperty(value = "联系地址")
    private String address;

    /**
     * 邮政编码
     */
    @Length(max = 10)
    @ApiModelProperty(value = "邮政编码")
    private String zipCode;

    /**
     * 负责人
     */
    @Length(max = 64)
    @ApiModelProperty(value = "负责人")
    private String leader;

    /**
     * 电话
     */
    @Length(max = 30)
    @ApiModelProperty(value = "电话")
    private String phoneNo;

    /**
     * 传真
     */
    @Length(max = 30)
    @ApiModelProperty(value = "传真")
    private String fax;

    /**
     * 邮箱
     */
    @Email
    @Length(max = 50)
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 状态：0正常 1删除 2停用
     */
    @NotNull
    @Pattern(regexp = "[0-2]")
    @ApiModelProperty(value = "状态：0正常 1删除 2停用")
    private String status;

    /**
     * 备注
     */
    @Length(max = 255)
    @ApiModelProperty(value = "备注")
    private String remarks;
}
