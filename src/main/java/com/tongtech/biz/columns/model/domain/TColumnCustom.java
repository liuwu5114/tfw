package com.tongtech.biz.columns.model.domain;

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
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 列定制对象 t_column_custom
 *
 * @author tong-framework
 * @date 2020-11-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_column_custom")
@ApiModel(value = "TColumnCustom对象", description = "")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TColumnCustom extends SuperModel {
  private static final long serialVersionUID = 1L;

  @TableId("id")
  private String id;
  /** 用户 */
  @ApiModelProperty(value = "用户")
  @TableField("user_id")
  @NotBlank(message = "用户不能为空")
  @Length(min = 0, max = 64, message = "用户长度不能超过 64 个字符")
  private String userId;
  /** 列名称:多个列以；分隔 */
  @ApiModelProperty(value = "列名称:多个列以；分隔")
  @TableField("columns")
  @NotBlank(message = "请选择要显示的列")
  @Length(min = 0, max = 1024, message = "列名称长度不能超过 1024 个字符")
  private String columns;
  /** 列名称中文 */
  @ApiModelProperty(value = "列名称中文")
  @TableField("columns_cn")
  private String columnsCn;
  /** 模块id */
  @ApiModelProperty(value = "模块id")
  @TableField("module")
  @NotBlank(message = "模块id不能为空")
  @Length(min = 0, max = 255, message = "模块id长度不能超过 255 个字符")
  private String module;
  /** 类型，0：为默认值；1：为用户定义列 */
  @ApiModelProperty(value = "类型，0：为默认值；1：为用户定义列")
  @TableField("type")
  @Length(min = 0, max = 1, message = "类型长度不能超过 1 个字符")
  private String type;

  public static final String ID = "id";
  public static final String USER_ID = "user_id";
  public static final String COLUMNS = "columns";
  public static final String COLUMNS_CN = "columnsCn";
  public static final String MODULE = "module";
  public static final String TYPE = "type";
}
