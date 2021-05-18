package com.tongtech.biz.columns.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tongtech.biz.columns.model.domain.TColumnCustom;
import com.tongtech.biz.columns.model.dto.TColumnCustomDelParam;
import com.tongtech.biz.columns.model.dto.TColumnCustomParam;
import com.tongtech.biz.columns.service.TColumnCustomService;
import com.tongtech.tfw.backend.common.biz.constants.BizConstants;
import com.tongtech.tfw.backend.common.biz.models.BaseResponse;
import com.tongtech.tfw.backend.common.biz.models.BaseResponseList;
import com.tongtech.tfw.backend.common.biz.models.BizGeneralResponse;
import com.tongtech.tfw.backend.common.models.supers.SuperController;
import com.tongtech.tfw.backend.core.helper.IdHelper;
import com.tongtech.tfw.backend.core.helper.StringHelper;
import com.tongtech.tfw.backend.core.helper.TypeHelper;
import com.tongtech.tfw.backend.core.helper.bean.BeanHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 列定制Controller
 *
 * @author tong-framework
 * @date 2020-11-06
 */
@RestController
@RequestMapping("/columns/custom")
@Api(value = "TColumnCustom", tags = "列定制")
@Slf4j
public class TColumnCustomController extends SuperController {
  @Autowired private TColumnCustomService tColumnCustomService;

  /* Generated Method*/
  @ApiOperation(value = "新增 列定制", notes = "Add TColumnCustom")
  @PostMapping(value = "/add")
  public BaseResponse<TColumnCustom> addEntity(@RequestBody TColumnCustom addRequest) {
    BaseResponse<TColumnCustom> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(addRequest)) {
      String id = IdHelper.getId32bit();
      TColumnCustom data = BeanHelper.beanToBean(addRequest, TColumnCustom.class);
      data.setId(id);
      data.setUserId(getUserId());
      data.setCreateBy(getUserId());
      data.setUpdateBy(getUserId());
      data.setCreateDate(LocalDateTime.now());
      data.setUpdateDate(LocalDateTime.now());
      boolean result = tColumnCustomService.save(data);
      if (result) {
        TColumnCustom newEntity = new TColumnCustom();
        newEntity.setId(id);
        baseResponse.setData(newEntity);
      }
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 列定制 ", notes = "Get TColumnCustom By Id")
  @GetMapping(value = "/get")
  public BaseResponse<TColumnCustom> getById(String id) {
    BaseResponse<TColumnCustom> baseResponse = new BaseResponse<>();
    if (StringHelper.isNotEmpty(id)) {
      TColumnCustom data = tColumnCustomService.getById(id);
      baseResponse.setData(data);
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取用户的列定制 ", notes = "query user cols config")
  @GetMapping(value = "/getUserCols")
  public BaseResponse<TColumnCustom> getUserCols(TColumnCustomParam queryParams) {
    BaseResponse<TColumnCustom> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(queryParams)
        && StringHelper.isNotEmpty(queryParams.getUserId())
        && StringHelper.isNotEmpty(queryParams.getModule())) {
      queryParams.setStatus(BizConstants.STATUS_ENABLE);
      QueryWrapper<TColumnCustom> queryWrapper = this.createQuery(queryParams);
      List<TColumnCustom> list = tColumnCustomService.list(queryWrapper);
      if (list != null && !list.isEmpty()) baseResponse.setData(list.get(0));
    }
    return baseResponse;
  }

  @ApiOperation(value = "修改 列定制 ", notes = "Update TColumnCustom By Id")
  @PutMapping(value = "/update")
  public BaseResponse<BizGeneralResponse> updateEntity(@RequestBody TColumnCustom updateRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(updateRequest) && StringHelper.isNotEmpty(updateRequest.getId())) {
      TColumnCustom data = BeanHelper.beanToBean(updateRequest, TColumnCustom.class);
      data.setUpdateBy(getUserId());
      boolean result = tColumnCustomService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "删除 列定制 ", notes = "delete TColumnCustom By Id")
  @PutMapping(value = "/delete")
  public BaseResponse<BizGeneralResponse> deleteEntity(
      @RequestBody TColumnCustomDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(deleteRequest) && StringHelper.isNotEmpty(deleteRequest.getId())) {
      // TODO 按需求添加数据去重，特殊值设定，业务异常
      TColumnCustom data = new TColumnCustom();
      data.setId(deleteRequest.getId());
      data.setStatus(BizConstants.STATUS_DELETE);
      data.setUpdateBy(getUserId());
      data.setUpdateDate(LocalDateTime.now());
      boolean result = tColumnCustomService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "批量删除列定制", notes = "delete TColumnCustom by batch Id")
  @PutMapping(value = "/batch_delete")
  public BaseResponse<BizGeneralResponse> deleteEntityBatch(
      @RequestBody TColumnCustomDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(deleteRequest) && (!deleteRequest.getIds().isEmpty())) {
      /* 批量删除 */
      List<TColumnCustom> tColumnCustomList = new ArrayList();
      for (String id : deleteRequest.getIds()) {
        TColumnCustom data = new TColumnCustom();
        data.setId(id);
        data.setStatus(BizConstants.STATUS_DELETE);
        data.setUpdateBy(getUserId());
        data.setUpdateDate(LocalDateTime.now());
        tColumnCustomList.add(data);
      }
      boolean result = tColumnCustomService.updateBatchById(tColumnCustomList);
      // if (result) {
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
      // }
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 列定制 分页列表", notes = "List TColumnCustom with page")
  @GetMapping(value = "/list")
  public BaseResponse<BaseResponseList<TColumnCustom>> listEntity(TColumnCustomParam param) {
    BaseResponse<BaseResponseList<TColumnCustom>> baseResponse = new BaseResponse<>();
    Long page =
        StringHelper.isEmpty(param.getPage()) ? BizConstants.PAGE : Long.valueOf(param.getPage());
    Long limit =
        StringHelper.isEmpty(param.getLimit())
            ? BizConstants.LIMIT
            : Long.valueOf(param.getLimit());
    Page<TColumnCustom> resultPage = new Page(page, limit);
    QueryWrapper<TColumnCustom> queryWrapper = this.createQuery(param);
    Page<TColumnCustom> resultList = tColumnCustomService.page(resultPage, queryWrapper);
    BaseResponseList<TColumnCustom> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(resultList.getRecords());
    baseResponseList.setTotal(resultList.getTotal());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  @ApiOperation(value = "获取 列定制 列表", notes = "List TColumnCustom all")
  @GetMapping(value = "/datas")
  public BaseResponse<BaseResponseList<TColumnCustom>> listAllEntity(TColumnCustomParam param) {
    BaseResponse<BaseResponseList<TColumnCustom>> baseResponse = new BaseResponse<>();
    QueryWrapper<TColumnCustom> queryWrapper = this.createQuery(param);
    List<TColumnCustom> resultList = tColumnCustomService.list(queryWrapper);
    BaseResponseList<TColumnCustom> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(resultList);
    baseResponseList.setTotal(resultList.size());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  /* Private Methods */

  /** 列表查询条件及查询参数 */
  private QueryWrapper<TColumnCustom> createQuery(TColumnCustomParam queryParam) {
    QueryWrapper<TColumnCustom> queryWrapper = new QueryWrapper<>();
    if (StringHelper.isNotEmpty(queryParam.getUserId())) {
      queryWrapper.eq(TColumnCustom.USER_ID, queryParam.getUserId());
    }
    if (StringHelper.isNotEmpty(queryParam.getColumns())) {
      queryWrapper.eq(TColumnCustom.COLUMNS, queryParam.getColumns());
    }
    if (StringHelper.isNotEmpty(queryParam.getColumnsCn())) {
      queryWrapper.eq(TColumnCustom.COLUMNS_CN, queryParam.getColumnsCn());
    }
    if (StringHelper.isNotEmpty(queryParam.getModule())) {
      queryWrapper.eq(TColumnCustom.MODULE, queryParam.getModule());
    }
    if (StringHelper.isNotEmpty(queryParam.getType())) {
      queryWrapper.eq(TColumnCustom.TYPE, queryParam.getType());
    }
    if (StringHelper.isNotEmpty(queryParam.getCreateBy())) {
      queryWrapper.eq(TColumnCustom.CREATE_BY, queryParam.getCreateBy());
    }
    if (Objects.nonNull(queryParam.getCreateDate())) {
      queryWrapper.eq(TColumnCustom.CREATE_DATE, queryParam.getCreateDate());
    }
    if (StringHelper.isNotEmpty(queryParam.getUpdateBy())) {
      queryWrapper.eq(TColumnCustom.UPDATE_BY, queryParam.getUpdateBy());
    }
    if (Objects.nonNull(queryParam.getUpdateDate())) {
      queryWrapper.eq(TColumnCustom.UPDATE_DATE, queryParam.getUpdateDate());
    }
    if (StringHelper.isNotEmpty(queryParam.getRemarks())) {
      queryWrapper.eq(TColumnCustom.REMARKS, queryParam.getRemarks());
    }
    if (StringHelper.isNotEmpty(queryParam.getStatus())) {
      queryWrapper.eq(TColumnCustom.STATUS, queryParam.getStatus());
    } else {
      queryWrapper.ne(TColumnCustom.STATUS, BizConstants.STATUS_DELETE);
    }
    if (StringHelper.isNotEmpty(queryParam.getOrderBy())) {
      if (StringHelper.isNotEmpty(queryParam.getOrderType())
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
