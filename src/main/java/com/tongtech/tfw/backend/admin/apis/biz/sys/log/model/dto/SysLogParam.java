package com.tongtech.tfw.backend.admin.apis.biz.sys.log.model.dto;

import com.tongtech.tfw.backend.admin.apis.biz.sys.log.model.domain.SysLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 日志信息表
 *
 * @author Ivan
 * @since 2020-05-27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysLogParam extends SysLog {
  private static final long serialVersionUID = 1L;

  private LocalDateTime opTimeStart;
  private LocalDateTime opTimeEnd;

  @ApiModelProperty(value = "每页数量")
  private String limit;

  @ApiModelProperty(value = "当前页数")
  private String page;

  @ApiModelProperty(value = "排序字段")
  private String orderBy;

  @ApiModelProperty(value = "排序方式")
  private String orderType;
}
