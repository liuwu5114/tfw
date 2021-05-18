package com.tongtech.tfw.backend.admin.apis.biz.sys.org;

/** @author liz */
public interface OrgConstants {

  /** 顶级机构 */
  String TOP_LEVEL = "0";

  /** 状态：0正常 1删除 2停用 */
  String STATUS_ENABLE = "0";

  /** 1删除 */
  String STATUS_DELETE = "1";

  /** 2停用 */
  String STATUS_DISABLE = "2";

  /** 上级机构名称*/
  String PARENT_ORG_NAME_PARAMS = "parentOrgName";
}
