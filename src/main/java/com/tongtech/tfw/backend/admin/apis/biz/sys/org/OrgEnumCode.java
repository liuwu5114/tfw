package com.tongtech.tfw.backend.admin.apis.biz.sys.org;

import com.tongtech.tfw.backend.common.models.response.ResponseInfo;

/** @author liz */
public enum OrgEnumCode {

  /** 2000: 机构编码已经存在 */
  ORG_CODE_ALREADY_EXISTS(2000, "机构编码已经存在"),

  /** 2001: 机构信息必填项缺失 */
  REQUIRED_ITEMS_MISSING(2001, "必填项缺失"),

  /** 2002: 机构信息输入值格式错误 */
  REQUIRED_ITEMS_FORMAT_ERROR(2002, "机构信息输入值格式错误"),

  /** 2003: 机构Id不存在 */
  ORG_ID_NOT_EXISTENT(2003, "机构Id不存在"),

  /** 2004: 机构Id不存在 */
  ORG_PARENT_ID_ERROR(2004, "不能父节点设置在子节点下"),

  /** 2005: 同级机构名称已经存在 */
  ORG_NAME_ALREADY_EXISTS(2005, "同级机构名称已经存在"),
  /** 2100 */
  ORG_PARAM_IS_NULL(2100, "请求参数为空"),

  /** 2006 */
  ORG_USER_IS_NOT_NULL(2006, "该机构或子机构下已有用户，需要删除用户后再删除机构"),
  ;

  /** Code */
  private final int code;
  /** Message */
  private final String msg;

  OrgEnumCode(int code, String msg) {
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
