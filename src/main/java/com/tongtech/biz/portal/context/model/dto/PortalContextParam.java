package com.tongtech.biz.portal.context.model.dto;

import com.tongtech.biz.portal.context.model.domain.PortalContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 文章 删除参数对象
 *
 * @author tong-framework
 * @date 2020-09-01
 */
@Data
@ApiModel(value = "PortalContext 列表查询参数对象")
public class PortalContextParam extends PortalContext {
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "每页数量")
  private String limit;

  @ApiModelProperty(value = "当前页数")
  private String page;

  @ApiModelProperty(value = "排序字段")
  private String orderBy;

  @ApiModelProperty(value = "排序方式")
  private String orderType;

  @ApiModelProperty(value = "栏目英文名称")
  private String sectionEngName;

  @ApiModelProperty(value = "日期 between")
  private List<String> bizDateBetween;
}
