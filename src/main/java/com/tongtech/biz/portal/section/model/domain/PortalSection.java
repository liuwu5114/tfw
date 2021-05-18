package com.tongtech.biz.portal.section.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * 栏目对象 t_portal_section
 *
 * @author tong-framework
 * @date 2020-09-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_portal_section")
@ApiModel(value = "PortalSection对象", description = "")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PortalSection extends SuperModel {
    private static final long serialVersionUID = 1L;
    /**
     * section id
     */
    @ApiModelProperty(value = "section id")
    @TableId("biz_id")
    private String bizId;
    /**
     * 栏目名称
     */
    @ApiModelProperty(value = "栏目名称")
    @TableField("section_name")
    private String sectionName;
    /**
     * 上级栏目
     */
    @ApiModelProperty(value = "上级栏目")
    @TableField("p_section")
    private String pSection;
    /**
     * 英文名称
     */
    @ApiModelProperty(value = "英文名称")
    @TableField("section_e_name")
    private String sectionEName;
    /**
     * 是否显示导航栏,0不显示,1显示
     */
    @ApiModelProperty(value = "是否显示导航栏,0不显示,1显示")
    @TableField("section_show")
    private String sectionShow;
    /**
     * 栏目描述
     */
    @ApiModelProperty(value = "栏目描述")
    @TableField("section_desc")
    private String sectionDesc;

    @TableField("section_order")
    private Long sectionOrder;
    /**
     * 模型
     */
    @ApiModelProperty(value = "模型")
    @TableField("section_model")
    private String sectionModel;

    /**
     * 模型
     */
    @ApiModelProperty(value = "父级ids")
    @TableField("p_ids")
    private String pIds;

    /**
     * 栏目URL
     */
    @ApiModelProperty(value = "栏目URL")
    @TableField("section_url")
    private String sectionUrl;

    /**
     * 栏目图标
     */
    @ApiModelProperty(value = "栏目图标")
    @TableField("section_icon")
    private String sectionIcon;

    public static final String BIZ_ID = "biz_id";
    public static final String SECTION_NAME = "section_name";
    public static final String P_SECTION = "p_section";
    public static final String SECTION_E_NAME = "section_e_name";
    public static final String SECTION_SHOW = "section_show";
    public static final String SECTION_DESC = "section_desc";
    public static final String SECTION_ORDER = "section_order";
    public static final String SECTION_MODEL = "section_model";
    public static final String P_IDS = "p_ids";
    public static final String SECTION_URL = "section_url";
    public static final String SECTION_ICON = "section_icon";

    @JsonIgnore
    public String getId() {
        return this.bizId;
    }

    @JsonIgnore
    public void setId(String bizId) {
        this.bizId = bizId;
    }
}
