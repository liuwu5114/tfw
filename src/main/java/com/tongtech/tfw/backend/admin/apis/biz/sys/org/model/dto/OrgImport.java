package com.tongtech.tfw.backend.admin.apis.biz.sys.org.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * OrgImport
 *
 * @author Created by ivan on 2020/9/11 .
 * @version 1.0
 */
@Data
public class OrgImport {

  @ApiModelProperty(value = "orgName")
  @ExcelProperty("orgName")
  private String orgName;

  @ApiModelProperty(value = "ORG_ID")
  @ExcelProperty("ORG_ID")
  private String orgId;

  @ApiModelProperty(value = "parentId")
  @ExcelProperty("parentId")
  private String parentId;

  @ApiModelProperty(value = "orgCode")
  @ExcelProperty("orgCode")
  private String orgCode;

  @ApiModelProperty(value = "treeSort")
  @ExcelProperty("treeSort")
  private String treeSort;
}
