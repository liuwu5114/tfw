package com.tongtech.biz.portal.section.model.dto;

import com.tongtech.biz.portal.section.model.domain.PortalSection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 栏目 删除参数对象
 *
 * @author tong-framework
 * @date 2020-09-01
 */

@Data
@ApiModel(value = "PortalSection 列表查询参数对象")
public class PortalSectionParam extends PortalSection {
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
