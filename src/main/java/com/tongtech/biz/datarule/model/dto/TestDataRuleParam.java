package com.tongtech.biz.datarule.model.dto;

import com.tongtech.biz.datarule.model.domain.TestDataRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * dataruletest 删除参数对象
 *
 * @author tong-framework
 * @date 2020-08-14
 */
@Data
@ApiModel(value = "TestDataRule 列表查询参数对象")
@Accessors(chain = true)
public class TestDataRuleParam extends TestDataRule {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "每页数量")
    private String limit;

    @ApiModelProperty(value = "当前页数")
    private String page;

    @ApiModelProperty(value = "排序字段")
    private String orderBy;

    @ApiModelProperty(value = "排序方式")
    private String orderType;

    @ApiModelProperty(value = "数据权限规则编码")
    private String dataRuleCode;
}
