package com.tongtech.tfw.backend.admin.apis.biz.sys.user;

import com.tongtech.tfw.backend.common.models.response.ResponseInfo;

/** @author liz */
public enum UserEnumCode {

  /** 1000: 登录账号已被占用 */
  LOGIN_NAME_ALREADY_EXISTS(1000, "登录名已被占用"),

  /** 1001: 必填项缺失 */
  REQUIRED_ITEMS_MISSING(1001, "必填项缺失"),

  /** 1003: 未填写机构信息，或该机构不存在 */
  USER_ORG_ID_ERROR(1003, "未填写机构信息，或该机构不存在"),

  /** 1004: 用户ID为空，或用户不存在 */
  USER_ID_ERROR(1004, "用户ID为空，或用户不存在"),

  /** 1005: 原始密码不匹配 */
  USER_PWD_MISMATCH(1005, "原密码输入错误"),

  /** 1100: 请求参数为空 */
  USER_PARAM_IS_NULL(1100, "请求参数为空"),

  /** 1101: 用户导入错误信息写入失败 */
  USER_IMPORT_ERROR_SAVE_FAIL(1101, "用户导入错误信息写入失败"),
  ;

  /** Code */
  private final int code;
  /** Message */
  private final String msg;

  UserEnumCode(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int code() {
    return code;
  }

  public String msg() {
    return msg;
  }

  public ResponseInfo transform() {
    return ResponseInfo.builder().code(code()).msg(msg()).build();
  }
}
