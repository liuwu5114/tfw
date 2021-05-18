package com.tongtech.biz.portal.section;

import com.tongtech.tfw.backend.common.models.response.ResponseInfo;

/**
 * Seection Enum
 *
 * <p>Created by ivan on 2020/9/1.
 */
public enum SectionEnum {
  /** 4000: 栏目英文名称已经存在 */
  EXISTED(4000, "栏目标识已经存在"),
  ;
  /** Code */
  private int code;
  /** Message */
  private String msg;

  SectionEnum(int code, String msg) {
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
