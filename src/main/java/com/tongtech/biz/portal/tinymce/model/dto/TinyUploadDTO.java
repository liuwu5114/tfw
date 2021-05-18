package com.tongtech.biz.portal.tinymce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * TinyUploadDTO
 *
 * @author Created by ivan on 2020/8/31 .
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
public class TinyUploadDTO implements java.io.Serializable {
  private static final long serialVersionUID = 3424455034293854111L;
  private String fileName;
  private String fileUrl;
}
