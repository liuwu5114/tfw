package com.tongtech.biz.test.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户 删除参数对象
 *
 * @author tong-framework
 * @date 2020-06-22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "TTestOrgTreeTable 删除参数对象")
public class TTestOrgTreeTableDelParam implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "主键ID")
  private String id;

  @ApiModelProperty(value = "批量ID")
  private List<String> ids;
}
