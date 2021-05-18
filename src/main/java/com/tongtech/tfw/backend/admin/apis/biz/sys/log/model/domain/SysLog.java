package com.tongtech.tfw.backend.admin.apis.biz.sys.log.model.domain;

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

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 日志信息表
 *
 * @author Ivan
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_log")
@ApiModel(value = "SysLog对象", description = "日志信息表 ")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SysLog extends SuperModel {
  private static final long serialVersionUID = 1L;

  /** 日志主键 */
  @ApiModelProperty(value = "日志主键")
  @TableId("id")
  private String id;
  /** 系统用户名称 */
  @ApiModelProperty(value = "系统用户名称")
  @TableField("user")
  private String user;
  /** 用户所属部门 */
  @ApiModelProperty(value = "用户所属部门")
  @TableField("dept")
  private String dept;
  /** 记录时间 */
  @ApiModelProperty(value = "记录时间")
  @TableField("op_time")
  private LocalDateTime opTime;
  /** 日志类型，1登录日志，2操作日志，3异常日志 */
  @ApiModelProperty(value = "日志类型，1登录日志，2操作日志，3异常日志", example = "0")
  @TableField("type")
  private BigDecimal type;
  /** 操作IP */
  @ApiModelProperty(value = "操作IP")
  @TableField("ip")
  private String ip;
  /** 操作URI */
  @ApiModelProperty(value = "操作URI")
  @TableField("uri")
  private String uri;
  /** 操作方法 */
  @ApiModelProperty(value = "操作方法")
  @TableField("method")
  private String method;
  /** 相关信息 */
  @ApiModelProperty(value = "相关信息")
  @TableField("msg")
  private String msg;
  /** 状态：0正常 1删除 2停用 */
  @ApiModelProperty(value = "状态：0正常 1删除 2停用 ")
  private String actions;

  public static final String ID = "id";

  public static final String USER = "user";

  public static final String DEPT = "dept";

  public static final String OP_TIME = "op_time";

  public static final String TYPE = "type";

  public static final String IP = "ip";

  public static final String URI = "uri";

  public static final String METHOD = "method";

  public static final String MSG = "msg";

  public static final String ACTIONS = "actions";
}
