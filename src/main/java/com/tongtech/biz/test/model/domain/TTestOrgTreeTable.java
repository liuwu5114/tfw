package com.tongtech.biz.test.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tongtech.tfw.backend.common.models.supers.SuperModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 用户对象 t_test_org_tree_table
 *
 * @author tong-framework
 * @date 2020-06-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_test_org_tree_table")
@ApiModel(value = "TTestOrgTreeTable对象", description = "")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TTestOrgTreeTable extends SuperModel {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "${field.comment}")
    @TableId("id")
    private String id;

    /**
     * 所属部门
     */
    @ApiModelProperty(value = "${field.comment}")
    @TableField("org_id")
    private String orgId;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "${field.comment}")
    @TableField("biz_data")
    private String bizData;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "${field.comment}")
    @TableField("biz_data_2")
    private String bizData2;

    public static final String ID = "id";
    public static final String ORG_ID = "org_id";
    public static final String BIZ_DATA = "biz_data";
    public static final String BIZ_DATA_2 = "biz_data_2";
}
