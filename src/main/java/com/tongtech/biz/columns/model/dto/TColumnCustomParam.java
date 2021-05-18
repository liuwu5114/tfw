package com.tongtech.biz.columns.model.dto;

import com.tongtech.biz.columns.model.domain.TColumnCustom;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 列定制 删除参数对象
 *
 * @author tong-framework
 * @date 2020-11-06
 */
@Data
@ApiModel(value = "TColumnCustom 列表查询参数对象")
public class TColumnCustomParam extends TColumnCustom {
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "每页数量")
  private String limit;

  @ApiModelProperty(value = "当前页数")
  private String page;

  @ApiModelProperty(value = "排序字段")
  private String orderBy;

  @ApiModelProperty(value = "排序方式")
  private String orderType;
}
