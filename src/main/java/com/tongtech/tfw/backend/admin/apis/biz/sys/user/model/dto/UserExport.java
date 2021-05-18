package com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * UserExport
 *
 * @author Created by ivan on 2020/7/17 .
 * @version 1.0
 */
@Service(value = "userExport")
@Data
public class UserExport {

  /** 登录账号 */
  @ApiModelProperty(value = "登录账号")
  @ExcelProperty("登录账号")
  private String loginName;
  /** 用户昵称 */
  @ApiModelProperty(value = "用户昵称")
  @ExcelProperty("用户昵称")
  private String userName;
  /** 用户性别0男1女 */
  @ApiModelProperty(value = "用户性别")
  @ExcelProperty("用户性别")
  private String sex;
  /** 电话 */
  @ApiModelProperty(value = "电话")
  @ExcelProperty("电话")
  private String phoneNo;
  /** 机构表主键 */
  @ApiModelProperty(value = "机构表主键")
  private String orgId;
  /** 机构名称 */
  @ApiModelProperty(value = "机构名称")
  @ExcelProperty("机构名称")
  private String orgName;
  /** 邮箱 */
  @ApiModelProperty(value = "邮箱")
  @ExcelProperty("邮箱")
  private String email;
  /** 联系地址 */
  @ApiModelProperty(value = "联系地址")
  @ExcelProperty("联系地址")
  private String address;

  public void convertSex() {
    if ("0".equals(getSex())) {
      setSex("男");
    } else if ("1".equals(getSex())) {
      setSex("女");
    }
  }
}
