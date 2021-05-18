package com.tongtech.biz.datarule.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * TestDataRuleListResponse
 *
 * @author Created by ivan on 2020/8/14 .
 * @version 1.0
 */
@Data
public class TestDataRuleListResponse implements java.io.Serializable {
  /** 业务编码 */
  @ApiModelProperty(value = "业务编码")
  private String bizId;
  /** 状态：0正常 1删除 2停用 */
  @ApiModelProperty(value = "状态：0正常 1删除 2停用")
  private String status;
  /** 备注 */
  @ApiModelProperty(value = "备注")
  private String remarks;

  @ApiModelProperty(value = "录入人")
  private String userId;

  @ApiModelProperty(value = "录入人机构")
  private String orgId;

  @ApiModelProperty(value = "科目类型")
  private String dataType;

  @ApiModelProperty(value = "成绩", example = "0.0")
  private BigDecimal score;

  @ApiModelProperty(value = "学生名称")
  private String studentName;
}
