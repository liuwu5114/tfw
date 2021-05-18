package com.tongtech.biz.columns.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 列定制 删除参数对象
 *
 * @author tong-framework
 * @date 2020-11-06
 */
@Data
@ApiModel(value = "TColumnCustom 删除参数对象")
public class TColumnCustomDelParam implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "主键ID")
  private String id;

  @ApiModelProperty(value = "批量ID")
  private List<String> ids;
}
