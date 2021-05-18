package com.tongtech.tfw.backend.admin.apis.biz.sys.log.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tongtech.tfw.backend.admin.apis.biz.sys.log.LogEnumCode;
import com.tongtech.tfw.backend.admin.apis.biz.sys.log.model.domain.SysLog;
import com.tongtech.tfw.backend.admin.apis.biz.sys.log.model.dto.SysLogDelParam;
import com.tongtech.tfw.backend.admin.apis.biz.sys.log.model.dto.SysLogParam;
import com.tongtech.tfw.backend.admin.apis.biz.sys.log.service.SysLogService;
import com.tongtech.tfw.backend.common.biz.constants.BizConstants;
import com.tongtech.tfw.backend.common.biz.models.BaseResponse;
import com.tongtech.tfw.backend.common.biz.models.BaseResponseList;
import com.tongtech.tfw.backend.common.biz.models.BizGeneralResponse;
import com.tongtech.tfw.backend.common.models.supers.SuperController;
import com.tongtech.tfw.backend.core.helper.ObjectHelper;
import com.tongtech.tfw.backend.core.helper.StringHelper;
import com.tongtech.tfw.backend.core.helper.TypeHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日志信息表 前端控制器
 *
 * @author Ivan
 * @since 2020-05-27
 */
@RestController
@Api(value = "SysLog", tags = "SysLog 日志信息表")
@Slf4j
@RequestMapping("/monitor/log")
public class SysLogController extends SuperController {
  /* Constants Field */

  /* Auto Wire Field  */
  @Autowired private SysLogService sysLogService;
  /* Methods */

  @ApiOperation(value = "删除 SysLog ", notes = "删除 SysLog delete SysLog By Id")
  @PutMapping(value = "/batch_delete")
  public BaseResponse<BizGeneralResponse> deleteEntity(@RequestBody SysLogDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (ObjectHelper.isNotEmpty(deleteRequest) && !deleteRequest.getIds().isEmpty()) {
      List<SysLog> sysLogArrayList;
      sysLogArrayList =
          deleteRequest.getIds().stream()
              .map(
                  sysLogId ->
                      SysLog.builder().id(sysLogId).status(BizConstants.STATUS_DELETE).build())
              .collect(Collectors.toList());
      boolean result = sysLogService.updateBatchById(sysLogArrayList);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 SysLog-Logging 分页列表", notes = "List SysLog with page")
  @GetMapping(value = "/logging")
  public BaseResponse<BaseResponseList<SysLog>> listEntityLogging(SysLogParam param) {
    return listEntity(param, new BigDecimal(LogEnumCode.LOGGING.code()));
  }

  @ApiOperation(value = "获取 SysLog-Operation 分页列表", notes = "List SysLog with page")
  @GetMapping(value = "/operation")
  public BaseResponse<BaseResponseList<SysLog>> listEntityOperation(SysLogParam param) {
    return listEntity(param, new BigDecimal(LogEnumCode.OPERATION.code()));
  }

  @ApiOperation(value = "获取 SysLog-Other 分页列表", notes = "List SysLog with page")
  @GetMapping(value = "/error")
  public BaseResponse<BaseResponseList<SysLog>> listEntityOther(SysLogParam param) {
    return listEntity(param, new BigDecimal(LogEnumCode.OTHER.code()));
  }

  @ApiOperation(value = "清空登录日志 SysLog ", notes = "delete SysLog By Id")
  @PutMapping(value = "/logging/clear")
  public BaseResponse<BizGeneralResponse> deleteLogging(@RequestBody SysLogDelParam deleteRequest) {
    return emptyEntity(deleteRequest, new BigDecimal(LogEnumCode.LOGGING.code()));
  }

  @ApiOperation(value = "清空操作日志 SysLog ", notes = "delete SysLog By Id")
  @PutMapping(value = "/option/clear")
  public BaseResponse<BizGeneralResponse> deleteOption(@RequestBody SysLogDelParam deleteRequest) {
    return emptyEntity(deleteRequest, new BigDecimal(LogEnumCode.OPERATION.code()));
  }

  @ApiOperation(value = "清空异常日志 SysLog ", notes = "delete SysLog By Id")
  @PutMapping(value = "/error/clear")
  public BaseResponse<BizGeneralResponse> deleteError(@RequestBody SysLogDelParam deleteRequest) {
    return emptyEntity(deleteRequest, new BigDecimal(LogEnumCode.OTHER.code()));
  }

  /* Private Methods */
  /**
   * emptyEntity
   *
   * <p>清空
   *
   * @author Created by ivan at 下午3:09 2020/5/29.
   * @return
   *     com.tongtech.backend.apis.common.models.BaseResponse<com.tongtech.backend.apis.common.models.BizGeneralResponse>
   */
  private BaseResponse<BizGeneralResponse> emptyEntity(SysLogDelParam param, BigDecimal type) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();

    // 根据需求修改查询条件及查询参数
    QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
    if (ObjectHelper.isNotEmpty(param.getStartTime())
        && ObjectHelper.isNotEmpty(param.getEndTime())) {
      queryWrapper.between(SysLog.OP_TIME, param.getStartTime(), param.getEndTime());
    } else if (ObjectHelper.isNotEmpty(param.getStartTime())) {
      queryWrapper.ge(SysLog.OP_TIME, param.getStartTime());
    } else if (ObjectHelper.isNotEmpty(param.getEndTime())) {
      queryWrapper.le(SysLog.OP_TIME, param.getEndTime());
    }
    if (ObjectHelper.isNotEmpty(type)) {
      queryWrapper.eq(SysLog.TYPE, type);
    }
    queryWrapper.ne(SysLog.STATUS, BizConstants.STATUS_DELETE);
    // query
    List<SysLog> resultList = sysLogService.list(queryWrapper);
    for (SysLog sysLog : resultList) {
      sysLog.setStatus(BizConstants.STATUS_DELETE);
    }
    boolean result = sysLogService.updateBatchById(resultList);
    baseResponse.setData(
        BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    return baseResponse;
  }

  /**
   * listEntity
   *
   * @author Created by ivan at 下午2:17 2020/5/27.
   * @return
   *     com.tongtech.backend.apis.common.models.BaseResponse<com.tongtech.backend.apis.common.models.BaseResponseList<com.tongtech.backend.apis.biz.sys.log.model.domain.SysLog>>
   */
  private BaseResponse<BaseResponseList<SysLog>> listEntity(SysLogParam param, BigDecimal type) {
    BaseResponse<BaseResponseList<SysLog>> baseResponse = new BaseResponse<>();
    Long page =
        StringHelper.isEmpty(param.getPage()) ? BizConstants.PAGE : Long.valueOf(param.getPage());
    Long limit =
        StringHelper.isEmpty(param.getLimit())
            ? BizConstants.LIMIT
            : Long.valueOf(param.getLimit());
    Page<SysLog> resultPage = new Page<>(page, limit);
    // 根据需求修改查询条件及查询参数
    param.setType(type);
    QueryWrapper<SysLog> queryWrapper = createQuery(param);
    Page<SysLog> resultList = sysLogService.page(resultPage, queryWrapper);
    BaseResponseList<SysLog> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(resultList.getRecords());
    baseResponseList.setTotal(resultList.getTotal());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  /** 列表查询条件及查询参数 */
  private QueryWrapper<SysLog> createQuery(SysLogParam queryParam) {
    QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
    if (ObjectHelper.isNotEmpty(queryParam.getType())) {
      queryWrapper.eq(SysLog.TYPE, queryParam.getType());
    }
    if (StringHelper.isNotEmpty(queryParam.getActions())) {
      queryWrapper.like(SysLog.ACTIONS, queryParam.getActions());
    }
    if (StringHelper.isNotEmpty(queryParam.getUser())) {
      queryWrapper.like(SysLog.USER, queryParam.getUser());
    }
    if (ObjectHelper.isNotEmpty(queryParam.getOpTimeStart())
        && ObjectHelper.isNotEmpty(queryParam.getOpTimeEnd())) {
      queryWrapper.between(SysLog.OP_TIME, queryParam.getOpTimeStart(), queryParam.getOpTimeEnd());
    } else if (ObjectHelper.isNotEmpty(queryParam.getOpTimeStart())) {
      queryWrapper.ge(SysLog.OP_TIME, queryParam.getOpTimeStart());
    } else if (ObjectHelper.isNotEmpty(queryParam.getOpTimeEnd())) {
      queryWrapper.le(SysLog.OP_TIME, queryParam.getOpTimeEnd());
    }
    if (StringHelper.isNotEmpty(queryParam.getStatus())) {
      queryWrapper.eq(SysLog.STATUS, queryParam.getStatus());
    } else {
      queryWrapper.ne(SysLog.STATUS, BizConstants.STATUS_DELETE);
    }
    if (StringHelper.isNotEmpty(queryParam.getOrderBy())) {
      if (StringHelper.isNotEmpty(queryParam.getOrderType())
          && BizConstants.ASC.equals(queryParam.getOrderType())) {
        queryWrapper.orderByAsc(queryParam.getOrderBy());
      } else {
        queryWrapper.orderByDesc(queryParam.getOrderBy());
      }
    } else {
      queryWrapper.orderByDesc(SysLog.UPDATE_DATE);
    }
    return queryWrapper;
  }
}
