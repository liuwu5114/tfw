package com.tongtech.biz.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tongtech.biz.test.model.domain.TTestTreeTable;
import com.tongtech.biz.test.model.dto.TTestTreeTableDelParam;
import com.tongtech.biz.test.model.dto.TTestTreeTableParam;
import com.tongtech.biz.test.service.TTestTreeTableService;
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
 * 树表Controller
 *
 * @author tong-framework
 * @date 2020-06-18
 */
@RestController
@RequestMapping("/test/treetable")
@Api(value = "TTestTreeTable", tags = "代码生成树表结构")
@Slf4j
public class TTestTreeTableController extends SuperController {
  @Autowired private TTestTreeTableService tTestTreeTableService;

  /* Generated Method*/
  @ApiOperation(value = "新增 树表", notes = "Add TTestTreeTable")
  @PostMapping(value = "/add")
  public BaseResponse<TTestTreeTable> addEntity(@RequestBody TTestTreeTable addRequest) {
    BaseResponse<TTestTreeTable> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(addRequest)) {
      // TODO 按需求添加数据去重，特殊值设定，业务异常
      String id = IdHelper.getId32bit();
      TTestTreeTable data = BeanHelper.beanToBean(addRequest, TTestTreeTable.class);
      data.setId(id);
      boolean result = tTestTreeTableService.save(data);
      if (result) {
        TTestTreeTable newEntity = new TTestTreeTable();
        newEntity.setId(id);
        baseResponse.setData(newEntity);
      }
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 树表 ", notes = "Get TTestTreeTable By Id")
  @GetMapping(value = "/get")
  public BaseResponse<TTestTreeTable> getById(String id) {
    BaseResponse<TTestTreeTable> baseResponse = new BaseResponse<>();
    if (StringUtils.isNotEmpty(id)) {
      // TODO 按需求添加业务异常判断
      TTestTreeTable data = tTestTreeTableService.getById(id);
      baseResponse.setData(data);
    }
    return baseResponse;
  }

  @ApiOperation(value = "修改 树表 ", notes = "Update TTestTreeTable By Id")
  @PutMapping(value = "/update")
  public BaseResponse<BizGeneralResponse> updateEntity(@RequestBody TTestTreeTable updateRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(updateRequest) && StringUtils.isNotEmpty(updateRequest.getId())) {
      // TODO 按需求添加数据去重，特殊值设定，业务异常
      TTestTreeTable data = BeanHelper.beanToBean(updateRequest, TTestTreeTable.class);
      boolean result = tTestTreeTableService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "删除 树表 ", notes = "delete TTestTreeTable By Id")
  @PutMapping(value = "/delete")
  public BaseResponse<BizGeneralResponse> deleteEntity(
      @RequestBody TTestTreeTableDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(deleteRequest) && StringUtils.isNotEmpty(deleteRequest.getId())) {
      // TODO 按需求添加数据去重，特殊值设定，业务异常
      TTestTreeTable data = new TTestTreeTable();
      data.setId(deleteRequest.getId());
      data.setStatus(BizConstants.STATUS_DELETE);
      boolean result = tTestTreeTableService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "批量删除树表", notes = "delete TTestTreeTable by batch Id")
  @PutMapping(value = "/batch_delete")
  public BaseResponse<BizGeneralResponse> deleteEntityBatch(
      @RequestBody TTestTreeTableDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(deleteRequest) && (!deleteRequest.getIds().isEmpty())) {
      /* 批量删除 */
      List<TTestTreeTable> tTestTreeTableList = new ArrayList();
      for (String id : deleteRequest.getIds()) {
        TTestTreeTable data = new TTestTreeTable();
        data.setId(id);
        data.setStatus(BizConstants.STATUS_DELETE);
        tTestTreeTableList.add(data);
      }
      boolean result = tTestTreeTableService.updateBatchById(tTestTreeTableList);
      // if (result) {
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
      // }
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 树表 分页列表", notes = "List TTestTreeTable with page")
  @GetMapping(value = "/list")
  public BaseResponse<BaseResponseList<TTestTreeTable>> listEntity(TTestTreeTableParam param) {
    BaseResponse<BaseResponseList<TTestTreeTable>> baseResponse = new BaseResponse<>();
    Long page =
        StringUtils.isEmpty(param.getPage()) ? BizConstants.PAGE : Long.valueOf(param.getPage());
    Long limit =
        StringUtils.isEmpty(param.getLimit()) ? BizConstants.LIMIT : Long.valueOf(param.getLimit());
    Page<TTestTreeTable> resultPage = new Page(page, limit);
    // TODO 根据需求修改查询条件及查询参数
    QueryWrapper<TTestTreeTable> queryWrapper = createQuery(param);
    Page<TTestTreeTable> resultList = (Page) tTestTreeTableService.page(resultPage, queryWrapper);
    BaseResponseList<TTestTreeTable> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(resultList.getRecords());
    baseResponseList.setTotal(resultList.getTotal());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  @ApiOperation(value = "获取 树表 列表", notes = "List TTestTreeTable all")
  @GetMapping(value = "/datas")
  public BaseResponse<BaseResponseList<TTestTreeTable>> listAllEntity(TTestTreeTableParam param) {
    BaseResponse<BaseResponseList<TTestTreeTable>> baseResponse = new BaseResponse<>();
    // TODO 根据需求修改查询条件及查询参数
    QueryWrapper<TTestTreeTable> queryWrapper = createQuery(param);
    List<TTestTreeTable> resultList = tTestTreeTableService.list(queryWrapper);
    BaseResponseList<TTestTreeTable> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(resultList);
    baseResponseList.setTotal(resultList.size());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  /* Private Methods */
  /** 列表查询条件及查询参数 */
  private QueryWrapper<TTestTreeTable> createQuery(TTestTreeTableParam queryParam) {
    QueryWrapper<TTestTreeTable> queryWrapper = new QueryWrapper<>();
    if (StringUtils.isNotEmpty(queryParam.getTreeId())) {
      queryWrapper.eq(TTestTreeTable.TREE_ID, queryParam.getTreeId());
    }
    if (StringUtils.isNotEmpty(queryParam.getBizData())) {
      queryWrapper.eq(TTestTreeTable.BIZ_DATA, queryParam.getBizData());
    }
    if (StringUtils.isNotEmpty(queryParam.getBizData2())) {
      queryWrapper.eq(TTestTreeTable.BIZ_DATA_2, queryParam.getBizData2());
    }
    if (StringUtils.isNotEmpty(queryParam.getStatus())) {
      queryWrapper.eq(TTestTreeTable.STATUS, queryParam.getStatus());
    } else {
      queryWrapper.ne(TTestTreeTable.STATUS, BizConstants.STATUS_DELETE);
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
