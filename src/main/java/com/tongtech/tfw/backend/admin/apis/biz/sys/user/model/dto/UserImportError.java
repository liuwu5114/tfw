package com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * UserImportError
 *
 * @author Created by ivan on 2020/7/28 .
 * @version 1.0
 */
@Data
public class UserImportError {
  /** 登录账号 */
  @ApiModelProperty(value = "登录账号")
  @ExcelProperty("登录账号")
  private String loginName;
  /** 用户昵称 */
  @ApiModelProperty(value = "姓名")
  @ExcelProperty("姓名")
  private String userName;
  /** 用户性别0男1女 */
  @ApiModelProperty(value = "性别")
  @ExcelProperty("性别")
  private String sex;
  /** 手机号 */
  @ApiModelProperty(value = "手机号")
  @ExcelProperty("手机号")
  private String phoneNo;
  /** 所属部门 */
  @ApiModelProperty(value = "所属部门")
  @ExcelProperty("所属部门")
  private String orgName;
  /** 邮箱 */
  @ApiModelProperty(value = "邮箱")
  @ExcelProperty("邮箱")
  private String email;
  /** 地址 */
  @ApiModelProperty(value = "地址")
  @ExcelProperty("地址")
  private String address;
  /** 错误信息 */
  @ApiModelProperty(value = "错误信息")
  @ExcelProperty("错误信息")
  private String errMsg;
}
