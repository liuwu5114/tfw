package com.tongtech.biz.portal.context.model.dto;

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
@ApiModel(value = "PortalContext 删除参数对象")
public class PortalContextDelParam implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "主键ID")
  private String id;

  @ApiModelProperty(value = "批量ID")
  private List<String> ids;
}
