package com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.dto;

import com.tongtech.tfw.backend.admin.apis.biz.excel.model.ColsDto;
import com.tongtech.tfw.backend.admin.apis.biz.excel.model.ImportError;
import lombok.Data;

import java.util.List;

/**
 * UserImport
 *
 * @author Created by ivan on 2020/7/22 .
 * @version 1.0
 */
@Data
public class UserImportResponse implements java.io.Serializable {
  private static final long serialVersionUID = 6826701812573993439L;
  private int successTotal;
  private int errorTotal;
  private List<ColsDto> beans;
  private List<ImportError<UserImport>> errors;
  private String fileName;
}
