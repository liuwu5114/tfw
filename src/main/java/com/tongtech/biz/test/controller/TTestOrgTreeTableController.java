package com.tongtech.biz.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tongtech.biz.test.model.domain.TTestOrgTreeTable;
import com.tongtech.biz.test.model.dto.TTestOrgTreeTableDelParam;
import com.tongtech.biz.test.model.dto.TTestOrgTreeTableParam;
import com.tongtech.biz.test.service.TTestOrgTreeTableService;
import com.tongtech.tfw.backend.common.biz.constants.BizConstants;
import com.tongtech.tfw.backend.common.biz.models.BaseResponse;
import com.tongtech.tfw.backend.common.biz.models.BaseResponseList;
import com.tongtech.tfw.backend.common.biz.models.BizGeneralResponse;
import com.tongtech.tfw.backend.common.models.supers.SuperController;
import com.tongtech.tfw.backend.core.helper.IdHelper;
import com.tongtech.tfw.backend.core.helper.TypeHelper;
import com.tongtech.tfw.backend.core.helper.bean.BeanHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户Controller
 *
 * @author tong-framework
 * @date 2020-06-22
 */
@RestController
@RequestMapping("/test/orgtreetable")
@Api(value = "TTestOrgTreeTable", tags = "用户")
@Slf4j
public class TTestOrgTreeTableController extends SuperController {
  @Autowired private TTestOrgTreeTableService tTestOrgTreeTableService;

  /* Generated Method*/
  @ApiOperation(value = "新增 用户", notes = "Add TTestOrgTreeTable")
  @PostMapping(value = "/add")
  public BaseResponse<TTestOrgTreeTable> addEntity(@RequestBody TTestOrgTreeTable addRequest) {
    BaseResponse<TTestOrgTreeTable> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(addRequest)) {
      // TODO 按需求添加数据去重，特殊值设定，业务异常
      String id = IdHelper.getId32bit();
      TTestOrgTreeTable data = BeanHelper.beanToBean(addRequest, TTestOrgTreeTable.class);
      data.setId(id);
      boolean result = tTestOrgTreeTableService.save(data);
      if (result) {
        TTestOrgTreeTable newEntity = new TTestOrgTreeTable();
        newEntity.setId(id);
        baseResponse.setData(newEntity);
      }
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 用户 ", notes = "Get TTestOrgTreeTable By Id")
  @GetMapping(value = "/get")
  public BaseResponse<TTestOrgTreeTable> getById(String id) {
    BaseResponse<TTestOrgTreeTable> baseResponse = new BaseResponse<>();
    if (StringUtils.isNotEmpty(id)) {
      // TODO 按需求添加业务异常判断
      TTestOrgTreeTable data = tTestOrgTreeTableService.getById(id);
      baseResponse.setData(data);
    }
    return baseResponse;
  }

  @ApiOperation(value = "修改 用户 ", notes = "Update TTestOrgTreeTable By Id")
  @PutMapping(value = "/update")
  public BaseResponse<BizGeneralResponse> updateEntity(
      @RequestBody TTestOrgTreeTable updateRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(updateRequest) && StringUtils.isNotEmpty(updateRequest.getId())) {
      // TODO 按需求添加数据去重，特殊值设定，业务异常
      TTestOrgTreeTable data = BeanHelper.beanToBean(updateRequest, TTestOrgTreeTable.class);
      boolean result = tTestOrgTreeTableService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "删除 用户 ", notes = "delete TTestOrgTreeTable By Id")
  @PutMapping(value = "/delete")
  public BaseResponse<BizGeneralResponse> deleteEntity(
      @RequestBody TTestOrgTreeTableDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(deleteRequest) && StringUtils.isNotEmpty(deleteRequest.getId())) {
      // TODO 按需求添加数据去重，特殊值设定，业务异常
      TTestOrgTreeTable data = new TTestOrgTreeTable();
      data.setId(deleteRequest.getId());
      data.setStatus(BizConstants.STATUS_DELETE);
      boolean result = tTestOrgTreeTableService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "批量删除用户", notes = "delete TTestOrgTreeTable by batch Id")
  @PutMapping(value = "/batch_delete")
  public BaseResponse<BizGeneralResponse> deleteEntityBatch(
      @RequestBody TTestOrgTreeTableDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(deleteRequest) && (!deleteRequest.getIds().isEmpty())) {
      /* 批量删除 */
      List<TTestOrgTreeTable> tTestOrgTreeTableList = new ArrayList();
      for (String id : deleteRequest.getIds()) {
        TTestOrgTreeTable data = new TTestOrgTreeTable();
        data.setId(id);
        data.setStatus(BizConstants.STATUS_DELETE);
        tTestOrgTreeTableList.add(data);
      }
      boolean result = tTestOrgTreeTableService.updateBatchById(tTestOrgTreeTableList);
      // if (result) {
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
      // }
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 用户 分页列表", notes = "List TTestOrgTreeTable with page")
  @GetMapping(value = "/list")
  public BaseResponse<BaseResponseList<TTestOrgTreeTable>> listEntity(
      TTestOrgTreeTableParam param) {
    BaseResponse<BaseResponseList<TTestOrgTreeTable>> baseResponse = new BaseResponse<>();
    Long page =
        StringUtils.isEmpty(param.getPage()) ? BizConstants.PAGE : Long.valueOf(param.getPage());
    Long limit =
        StringUtils.isEmpty(param.getLimit()) ? BizConstants.LIMIT : Long.valueOf(param.getLimit());
    Page<TTestOrgTreeTable> resultPage = new Page(page, limit);
    // TODO 根据需求修改查询条件及查询参数
    QueryWrapper<TTestOrgTreeTable> queryWrapper = createQuery(param);
    Page<TTestOrgTreeTable> resultList =
        (Page) tTestOrgTreeTableService.page(resultPage, queryWrapper);
    BaseResponseList<TTestOrgTreeTable> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(resultList.getRecords());
    baseResponseList.setTotal(resultList.getTotal());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  @ApiOperation(value = "获取 用户 列表", notes = "List TTestOrgTreeTable all")
  @GetMapping(value = "/datas")
  public BaseResponse<BaseResponseList<TTestOrgTreeTable>> listAllEntity(
      TTestOrgTreeTableParam param) {
    BaseResponse<BaseResponseList<TTestOrgTreeTable>> baseResponse = new BaseResponse<>();
    // TODO 根据需求修改查询条件及查询参数
    QueryWrapper<TTestOrgTreeTable> queryWrapper = createQuery(param);
    List<TTestOrgTreeTable> resultList = tTestOrgTreeTableService.list(queryWrapper);
    BaseResponseList<TTestOrgTreeTable> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(resultList);
    baseResponseList.setTotal(resultList.size());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  /* Private Methods */
  /** 列表查询条件及查询参数 */
  private QueryWrapper<TTestOrgTreeTable> createQuery(TTestOrgTreeTableParam queryParam) {
    QueryWrapper<TTestOrgTreeTable> queryWrapper = new QueryWrapper<>();
    if (StringUtils.isNotEmpty(queryParam.getOrgId())) {
      queryWrapper.eq(TTestOrgTreeTable.ORG_ID, queryParam.getOrgId());
    }
    if (StringUtils.isNotEmpty(queryParam.getBizData())) {
      queryWrapper.eq(TTestOrgTreeTable.BIZ_DATA, queryParam.getBizData());
    }
    if (StringUtils.isNotEmpty(queryParam.getBizData2())) {
      queryWrapper.eq(TTestOrgTreeTable.BIZ_DATA_2, queryParam.getBizData2());
    }
    if (StringUtils.isNotEmpty(queryParam.getStatus())) {
      queryWrapper.eq(TTestOrgTreeTable.STATUS, queryParam.getStatus());
    } else {
      queryWrapper.ne(TTestOrgTreeTable.STATUS, BizConstants.STATUS_DELETE);
    }
    if (StringUtils.isNotEmpty(queryParam.getOrderBy())) {
      if (StringUtils.isNotEmpty(queryParam.getOrderType())
          && BizConstants.ASC.equals(queryParam.getOrderType())) {
        queryWrapper.orderByAsc(queryParam.getOrderBy());
      } else {
        queryWrapper.orderByDesc(queryParam.getOrderBy());
      }
    } else {
      queryWrapper.orderByDesc("update_date");
    }
    return queryWrapper;
  }
}
