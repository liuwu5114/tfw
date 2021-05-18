package com.tongtech.biz.test.model.dto;

import com.tongtech.biz.test.model.domain.TTestTree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 代码生成树结构 删除参数对象
 *
 * @author tong-framework
 * @date 2020-06-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "TTestTree 列表查询参数对象")
public class TTestTreeParam extends TTestTree {
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
