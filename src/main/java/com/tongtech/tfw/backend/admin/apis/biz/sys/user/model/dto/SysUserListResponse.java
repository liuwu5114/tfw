package com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * SysUserListResponse
 *
 * @author Created by ivan on 2020/5/29 .
 * @version 1.0
 */
@Data
public class SysUserListResponse {
  private static final long serialVersionUID = -369757436549277018L;
  /** 用户表主键 */
  @ApiModelProperty(value = "用户表主键")
  private String userId;
  /** 机构表主键 */
  @ApiModelProperty(value = "机构表主键")
  private String orgId;
  /** 登录账号 */
  @ApiModelProperty(value = "登录账号")
  private String loginName;
  /** 用户昵称 */
  @ApiModelProperty(value = "用户昵称")
  private String userName;
  /** 用户类型，在系统内部使用0表示系统管理员，1表示不是系统内部的人员 */
  @ApiModelProperty(value = "用户类型，在系统内部使用0表示系统管理员，1表示不是系统内部的人员")
  private String userType;
  /** 用户头像 */
  @ApiModelProperty(value = "用户头像")
  private String photoUrl;
  /** 用户性别0男1女 */
  @ApiModelProperty(value = "用户性别0男1女")
  private String sex;
  /** 联系地址 */
  @ApiModelProperty(value = "联系地址")
  private String address;
  /** 邮政编码 */
  @ApiModelProperty(value = "邮政编码")
  private String zipCode;
  /** 电话 */
  @ApiModelProperty(value = "电话")
  private String phoneNo;
  /** 传真 */
  @ApiModelProperty(value = "传真")
  private String fax;
  /** 邮箱 */
  @ApiModelProperty(value = "邮箱")
  private String email;
  /** 状态：0正常 1删除 2停用 */
  @ApiModelProperty(value = "状态：0正常 1删除 2停用")
  private String status;
  /** 机构名称 */
  @ApiModelProperty(value = "机构名称")
  private String orgName;
}
