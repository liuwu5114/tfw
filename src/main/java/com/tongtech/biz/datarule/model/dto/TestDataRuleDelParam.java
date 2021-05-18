package com.tongtech.biz.datarule.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * dataruletest 删除参数对象
 *
 * @author tong-framework
 * @date 2020-08-14
 */
@Data
@ApiModel(value = "TestDataRule 删除参数对象")
public class TestDataRuleDelParam implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "主键ID")
  private String id;

  @ApiModelProperty(value = "批量ID")
  private List<String> ids;
}
