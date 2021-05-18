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
 * 代码生成树结构对象 t_test_tree
 *
 * @author tong-framework
 * @date 2020-06-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_test_tree")
@ApiModel(value = "TTestTree对象", description = "")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TTestTree extends SuperModel {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;
    /**
     * 上级节点
     */
    @ApiModelProperty(value = "${field.comment}")
    @TableField("parent_id")
    private String parentId;

    @TableField("name")
    private String name;

    @TableField("parent_ids")
    private String parentIds;


    public static final String ID = "id";
    public static final String PARENT_ID = "parent_id";
    public static final String NAME = "name";
    public static final String PARENT_IDS = "parent_ids";
}
