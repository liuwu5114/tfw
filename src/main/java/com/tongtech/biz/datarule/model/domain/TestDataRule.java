package com.tongtech.biz.datarule.model.domain;

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

import java.math.BigDecimal;

/**
 * dataruletest对象 b_test_data_rule
 *
 * @author tong-framework
 * @date 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("b_test_data_rule")
@ApiModel(value = "TestDataRule对象", description = "")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TestDataRule extends SuperModel {
    private static final long serialVersionUID = 1L;
    /**
     * 业务编码
     */
    @ApiModelProperty(value = "业务编码")
    @TableField("biz_id")
    @TableId
    private String bizId;

    @ApiModelProperty(value = "录入人")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "录入人机构")
    @TableField("org_id")
    private String orgId;

    @ApiModelProperty(value = "科目类型")
    @TableField("data_type")
    private String dataType;

    @ApiModelProperty(value = "成绩", example = "0.0")
    @TableField("score")
    private BigDecimal score;

    @ApiModelProperty(value = "学生名称")
    @TableField("student_name")
    private String studentName;

    public static final String BIZ_ID = "biz_id";
    public static final String USER_ID = "user_id";
    public static final String ORG_ID = "org_id";
    public static final String DATA_TYPE = "data_type";
    public static final String SCORE = "score";
    public static final String STUDENT_NAME = "student_name";

    @JsonIgnore
    public String getId() {
        return bizId;
    }

    @JsonIgnore
    public void setId(String testId) {
        this.bizId = testId;
    }
}
