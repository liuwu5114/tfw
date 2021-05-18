// @formatter:off
package com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tongtech.tfw.backend.common.models.supers.SuperModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author Ivan
 * @since 2020-03-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_user")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SysUser extends SuperModel {
  private static final long serialVersionUID = 1L;
  /** 用户表主键 */
  @TableId
  @ApiModelProperty(value = "用户表主键")
  private String userId;
  /** 机构表主键 */
  @ApiModelProperty(value = "机构表主键")
  private String orgId;
  /** 登录账号 */
  @ApiModelProperty(value = "登录账号")
  private String loginName;
  /** 登录密码 */
  @ApiModelProperty(value = "登录密码")
  private String loginPwd;
  /** 用户昵称 */
  @ApiModelProperty(value = "用户昵称")
  private String userName;
  /** 用户类型，在系统内部使用0表示系统管理员，1表示不是系统内部的人员 */
  @ApiModelProperty(value = "用户类型，在系统内部使用0表示系统管理员，1表示不是系统内部的人员")
  private String userType;
  /** 用户头像 */
  @ApiModelProperty(value = "用户头像")
  private String photoUrl;
  /** 最后登陆IP */
  @ApiModelProperty(value = "最后登陆IP")
  private String loginIp;
  /** 最后登陆时间 */
  @ApiModelProperty(value = "最后登陆时间")
  private LocalDateTime loginDate;
  /** 密码安全级别，密码安全级别（ 1弱 2一般 3安全） */
  @ApiModelProperty(value = "密码安全级别，密码安全级别（ 1弱 2一般 3安全）")
  private String pwdSecurityLevel;
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
  /** 备注 */
  @ApiModelProperty(value = "UKEY-USER-PID")
  private String userKeyPid;

  public static final String USER_ID = "user_id";

  public static final String ORG_ID = "org_id";

  public static final String LOGIN_NAME = "login_name";

  public static final String LOGIN_PWD = "login_pwd";

  public static final String USER_NAME = "user_name";

  public static final String USER_TYPE = "user_type";

  public static final String PHOTO_URL = "photo_url";

  public static final String LOGIN_IP = "login_ip";

  public static final String LOGIN_DATE = "login_date";

  public static final String PWD_SECURITY_LEVEL = "pwd_security_level";

  public static final String SEX = "sex";

  public static final String ADDRESS = "address";

  public static final String ZIP_CODE = "zip_code";

  public static final String PHONE_NO = "phone_no";

  public static final String FAX = "fax";

  public static final String EMAIL = "email";

  public static final String USER_KEY_PID = "user_key_pid";
}
