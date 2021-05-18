package com.tongtech.tfw.backend.admin.apis.biz.sys.org.model.dto;

import com.tongtech.tfw.backend.admin.apis.biz.excel.model.ColsDto;
import com.tongtech.tfw.backend.admin.apis.biz.excel.model.ImportError;
import lombok.Data;

import java.util.List;

/**
 * SysImportResponse
 *
 * @author Created by ivan on 2020/9/11 .
 * @version 1.0
 */
@Data
public class OrgImportResponse implements java.io.Serializable {
  private static final long serialVersionUID = -6104367799164400016L;
  private int successTotal;
  private int errorTotal;
  private List<ColsDto> beans;
  private List<ImportError<OrgImport>> errors;
  private String fileName;
}
