package com.tongtech.biz.test.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 树表 删除参数对象
 *
 * @author tong-framework
 * @date 2020-06-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "TTestTreeTable 删除参数对象")
public class TTestTreeTableDelParam implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "主键ID")
  private String id;

  @ApiModelProperty(value = "批量ID")
  private List<String> ids;
}
