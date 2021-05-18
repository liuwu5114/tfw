package com.tongtech.tfw.backend.admin.apis.biz.sys.log.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * SysLogDelParam
 *
 * @author Created by ivan on 2020/5/29 .
 * @version 1.0
 */
@Data
public class SysLogDelParam {
  List<String> ids;
  LocalDateTime startTime;
  LocalDateTime endTime;
}
