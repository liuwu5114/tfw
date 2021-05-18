package com.tongtech.biz.test.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tongtech.tfw.backend.common.models.supers.SuperModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 示例对象 t_test_sample
 *
 * @author tong-framework
 * @date 2020-06-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_test_sample")
@ApiModel(value = "TTestSample对象", description = "")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TTestSample extends SuperModel {
    private static final long serialVersionUID = 1L;
    /**
     * ID必须64位
     */
    @ApiModelProperty(value = "${field.comment}")
    @TableId("test_id")
    private String testId;
    /**
     * 业务字段1
     */
    @ApiModelProperty(value = "${field.comment}")
    @TableField("biz_one")
    private String bizOne;
    /**
     * 业务字段2-decimal
     */
    @ApiModelProperty(value = "业务字段2-decimal", example = "0")
    @TableField("biz_two")
    private Long bizTwo;
    /**
     * 业务字段3-int
     */
    @ApiModelProperty(value = "业务字段3-int", example = "0")
    @TableField("biz_three")
    private Integer bizThree;
    /**
     * 业务字段4-tinyint
     */
    @ApiModelProperty(value = "业务字段4-tinyint", example = "0")
    @TableField("biz_four")
    private Integer bizFour;
    /**
     * 机构ID
     */
    @ApiModelProperty(value = "${field.comment}")
    @TableField("create_by_org")
    private String createByOrg;
    /**
     * 代码表
     */
    @ApiModelProperty(value = "${field.comment}")
    @TableField("biz_dict")
    private String bizDict;
    /**
     * 测试日期
     */
    @ApiModelProperty(value = "${field.comment}")
    @TableField("biz_date")
    private String bizDate;

    public static final String TEST_ID = "test_id";
    public static final String BIZ_ONE = "biz_one";
    public static final String BIZ_TWO = "biz_two";
    public static final String BIZ_THREE = "biz_three";
    public static final String BIZ_FOUR = "biz_four";
    public static final String CREATE_BY_ORG = "create_by_org";
    public static final String BIZ_DICT = "biz_dict";
    public static final String BIZ_DATE = "biz_date";

    @JsonIgnore
    public String getId() {
        return testId;
    }

    @JsonIgnore
    public void setId(String testId) {
        this.testId = testId;
    }
}
