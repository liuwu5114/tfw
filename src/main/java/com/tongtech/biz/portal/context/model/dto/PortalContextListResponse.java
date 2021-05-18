package com.tongtech.biz.portal.context.model.dto;

import com.tongtech.biz.portal.context.model.domain.PortalContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * PortalContextListResponse
 *
 * @author Created by ivan on 2020/9/4 .
 * @version 1.0
 */
@Data
public class PortalContextListResponse extends PortalContext {
  private static final long serialVersionUID = -601658646941131666L;
  @ApiModelProperty(value = "栏目名称")
  private String sectionName;
}
