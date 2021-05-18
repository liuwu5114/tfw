package com.tongtech.tfw.backend.admin.apis.biz.sys.log;

import com.tongtech.tfw.backend.common.models.response.ResponseInfo;

/**
 * Log Enum
 *
 * <p>Created by ivan on 2020/5/27.
 */
public enum LogEnumCode {
  /* LOGGING Log*/
  LOGGING(1, "用户登录"),
  /* OPERATION Log*/
  OPERATION(2, "用户操作"),

  /* OTHER Log*/
  OTHER(3, "其他日志"),
  ;
  /** Code */
  private final int code;
  /** Message */
  private final String msg;

  LogEnumCode(int code, String msg) {
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
