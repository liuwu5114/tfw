package com.tongtech.biz.portal.context.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tongtech.tfw.backend.common.models.supers.SuperModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文章对象 t_portal_context
 *
 * @author tong-framework
 * @date 2020-09-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_portal_context")
@ApiModel(value = "PortalContext对象", description = "")
public class PortalContext extends SuperModel {
    private static final long serialVersionUID = 1L;
    /**
     * 文章ID
     */
    @ApiModelProperty(value = "文章ID ")
    @TableId("biz_id")
    private String bizId;
    /**
     * 所属栏目
     */
    @ApiModelProperty(value = "所属栏目")
    @TableField("section_id")
    private String sectionId;
    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题")
    @TableField("context_title")
    private String contextTitle;
    /**
     * 副标题
     */
    @ApiModelProperty(value = "副标题")
    @TableField("context_sub_title")
    private String contextSubTitle;
    /**
     * 关键词
     */
    @ApiModelProperty(value = "关键词")
    @TableField("context_keywords")
    private String contextKeywords;
    /**
     * 来源
     */
    @ApiModelProperty(value = "来源")
    @TableField("context_source")
    private String contextSource;
    /**
     * 摘要
     */
    @ApiModelProperty(value = "摘要")
    @TableField("context_summary")
    private String contextSummary;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    @TableField("context_main")
    private String contextMain;
    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    @TableField("published_time")
    private LocalDateTime publishedTime;
    /**
     * 转向链接
     */
    @ApiModelProperty(value = "转向链接")
    @TableField("context_re_link")
    private String contextReLink;
    /**
     * 缩略图URL
     */
    @ApiModelProperty(value = "缩略图URL")
    @TableField("context_pic_link")
    private String contextPicLink;
    /**
     * 是否发布,0保存,1发布
     */
    @ApiModelProperty(value = "是否发布,0保存,1发布")
    @TableField("context_published")
    private String contextPublished;
    /**
     * 作者姓名
     */
    @ApiModelProperty(value = "作者姓名")
    @TableField("context_author")
    private String contextAuthor;
    /**
     * 作者id
     */
    @ApiModelProperty(value = "作者id")
    @TableField("context_author_id")
    private String contextAuthorId;

    public static final String BIZ_ID = "biz_id";
    public static final String SECTION_ID = "section_id";
    public static final String CONTEXT_TITLE = "context_title";
    public static final String CONTEXT_SUB_TITLE = "context_sub_title";
    public static final String CONTEXT_KEYWORDS = "context_keywords";
    public static final String CONTEXT_SOURCE = "context_source";
    public static final String CONTEXT_SUMMARY = "context_summary";
    public static final String CONTEXT_MAIN = "context_main";
    public static final String PUBLISHED_TIME = "published_time";
    public static final String CONTEXT_RE_LINK = "context_re_link";
    public static final String CONTEXT_PIC_LINK = "context_pic_link";
    public static final String CONTEXT_PUBLISHED = "context_published";
    public static final String CONTEXT_AUTHOR = "context_author";
    public static final String CONTEXT_AUTHOR_ID = "context_author_id";

    @JsonIgnore
    public String getId() {
        return this.bizId;
    }

    @JsonIgnore
    public void setId(String bizId) {
        this.bizId = bizId;
    }
}
