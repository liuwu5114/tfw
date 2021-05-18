package com.tongtech.biz.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tongtech.biz.test.model.domain.TTestSample;
import com.tongtech.biz.test.model.dto.TTestSampleDelParam;
import com.tongtech.biz.test.model.dto.TTestSampleParam;
import com.tongtech.biz.test.model.dto.TTestSampleWithFileParam;
import com.tongtech.biz.test.service.TTestSampleService;
import com.tongtech.tfw.backend.admin.apis.biz.file.FileEnumCode;
import com.tongtech.tfw.backend.admin.apis.biz.file.model.domain.TSysFileBiz;
import com.tongtech.tfw.backend.admin.apis.biz.file.service.TSysFileBizService;
import com.tongtech.tfw.backend.common.biz.constants.BizConstants;
import com.tongtech.tfw.backend.common.biz.models.BaseResponse;
import com.tongtech.tfw.backend.common.biz.models.BaseResponseList;
import com.tongtech.tfw.backend.common.biz.models.BizGeneralResponse;
import com.tongtech.tfw.backend.common.models.exceptions.ApiException;
import com.tongtech.tfw.backend.common.models.supers.SuperController;
import com.tongtech.tfw.backend.core.helper.CollectionHelper;
import com.tongtech.tfw.backend.core.helper.IdHelper;
import com.tongtech.tfw.backend.core.helper.TypeHelper;
import com.tongtech.tfw.backend.core.helper.bean.BeanHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 示例Controller
 *
 * @author tong-framework
 * @date 2020-06-22
 */
@RestController
@RequestMapping("/test/sample")
@Api(value = "TTestSample", tags = "示例")
@Slf4j
public class TTestSampleController extends SuperController {
  @Autowired private TTestSampleService tTestSampleService;
  @Autowired private TSysFileBizService tSysFileBizService;

  /* Generated Method*/
  @ApiOperation(value = "新增 示例", notes = "Add TTestSample")
  @PostMapping(value = "/add")
  public BaseResponse<TTestSample> addEntity(@RequestBody TTestSampleWithFileParam addRequest) {
    BaseResponse<TTestSample> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(addRequest)) {
      String id = IdHelper.getId32bit();
      // 处理附件
      if (CollectionHelper.isNotEmpty(addRequest.getFiles())) {
        List<TSysFileBiz> fileBizList = addRequest.getFiles();
        boolean fileBizResult = tSysFileBizService.saveFileBizs(fileBizList, id, getUserId());
        if (!fileBizList.isEmpty() && !fileBizResult) {
          throw new ApiException(FileEnumCode.FILE_BIZ_SAVE_FAILED.transform());
        }
      }
      // 　处理附件　end
      TTestSample data = BeanHelper.beanToBean(addRequest, TTestSample.class);
      data.setId(id);
      boolean result = tTestSampleService.save(data);
      if (result) {
        TTestSample newEntity = new TTestSample();
        newEntity.setId(id);
        baseResponse.setData(newEntity);
      }
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 示例 ", notes = "Get TTestSample By Id")
  @GetMapping(value = "/get")
  public BaseResponse<TTestSample> getById(String testId) {
    BaseResponse<TTestSample> baseResponse = new BaseResponse<>();
    if (StringUtils.isNotEmpty(testId)) {
      // TODO 按需求添加业务异常判断
      TTestSample data = tTestSampleService.getById(testId);
      baseResponse.setData(data);
    }
    return baseResponse;
  }

  @ApiOperation(value = "修改 示例 ", notes = "Update TTestSample By Id")
  @PutMapping(value = "/update")
  public BaseResponse<BizGeneralResponse> updateEntity(
      @RequestBody TTestSampleWithFileParam updateRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(updateRequest) && StringUtils.isNotEmpty(updateRequest.getId())) {
      // 处理附件
      if (!updateRequest.getFiles().isEmpty()) {
        List<TSysFileBiz> fileBizList = updateRequest.getFiles();
        boolean fileBizResult =
            tSysFileBizService.saveFileBizs(fileBizList, updateRequest.getId(), getUserId());
        if (!fileBizList.isEmpty() && !fileBizResult) {
          throw new ApiException(FileEnumCode.FILE_BIZ_SAVE_FAILED.transform());
        }
      }
      // 　处理附件　end
      TTestSample data = BeanHelper.beanToBean(updateRequest, TTestSample.class);
      data.setUpdateBy(getUserId());
      boolean result = tTestSampleService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "删除 示例 ", notes = "delete TTestSample By Id")
  @PutMapping(value = "/delete")
  public BaseResponse<BizGeneralResponse> deleteEntity(
      @RequestBody TTestSampleDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(deleteRequest) && StringUtils.isNotEmpty(deleteRequest.getId())) {
      // TODO 按需求添加数据去重，特殊值设定，业务异常
      TTestSample data = new TTestSample();
      boolean fileBizRemove =
          tSysFileBizService.remove(
              new QueryWrapper<TSysFileBiz>().eq(TSysFileBiz.BIZ_ID, deleteRequest.getId()));
      data.setId(deleteRequest.getId());
      data.setStatus(BizConstants.STATUS_DELETE);
      data.setUpdateBy(getUserId());
      data.setUpdateDate(LocalDateTime.now());
      boolean result = tTestSampleService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "批量删除示例", notes = "delete TTestSample by batch Id")
  @PutMapping(value = "/batch_delete")
  public BaseResponse<BizGeneralResponse> deleteEntityBatch(
      @RequestBody TTestSampleDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(deleteRequest) && (!deleteRequest.getIds().isEmpty())) {
      /* 批量删除 */
      List<TTestSample> tTestSampleList = new ArrayList();
      for (String id : deleteRequest.getIds()) {
        TTestSample data = new TTestSample();
        data.setId(id);
        data.setStatus(BizConstants.STATUS_DELETE);
        data.setUpdateBy(getUserId());
        data.setUpdateDate(LocalDateTime.now());
        tTestSampleList.add(data);
      }
      boolean result = tTestSampleService.updateBatchById(tTestSampleList);
      // if (result) {
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
      // }
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 示例 分页列表", notes = "List TTestSample with page")
  @GetMapping(value = "/list")
  public BaseResponse<BaseResponseList<TTestSample>> listEntity(TTestSampleParam param) {
    BaseResponse<BaseResponseList<TTestSample>> baseResponse = new BaseResponse<>();
    Long page =
        StringUtils.isEmpty(param.getPage()) ? BizConstants.PAGE : Long.valueOf(param.getPage());
    Long limit =
        StringUtils.isEmpty(param.getLimit()) ? BizConstants.LIMIT : Long.valueOf(param.getLimit());
    Page<TTestSample> resultPage = new Page(page, limit);
    // TODO 根据需求修改查询条件及查询参数
    QueryWrapper<TTestSample> queryWrapper = createQuery(param);
    Page<TTestSample> resultList = (Page) tTestSampleService.page(resultPage, queryWrapper);
    BaseResponseList<TTestSample> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(resultList.getRecords());
    baseResponseList.setTotal(resultList.getTotal());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  @ApiOperation(value = "获取 示例 列表", notes = "List TTestSample all")
  @GetMapping(value = "/datas")
  public BaseResponse<BaseResponseList<TTestSample>> listAllEntity(TTestSampleParam param) {
    BaseResponse<BaseResponseList<TTestSample>> baseResponse = new BaseResponse<>();
    // TODO 根据需求修改查询条件及查询参数
    QueryWrapper<TTestSample> queryWrapper = createQuery(param);
    List<TTestSample> resultList = tTestSampleService.list(queryWrapper);
    BaseResponseList<TTestSample> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(resultList);
    baseResponseList.setTotal(resultList.size());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  /* Private Methods */
  /** 列表查询条件及查询参数 */
  private QueryWrapper<TTestSample> createQuery(TTestSampleParam queryParam) {
    QueryWrapper<TTestSample> queryWrapper = new QueryWrapper<>();
    if (StringUtils.isNotEmpty(queryParam.getBizOne())) {
      queryWrapper.like(TTestSample.BIZ_ONE, queryParam.getBizOne());
    }
    if (Objects.nonNull(queryParam.getBizTwo())) {
      queryWrapper.eq(TTestSample.BIZ_TWO, queryParam.getBizTwo());
    }
    if (Objects.nonNull(queryParam.getBizThree())) {
      queryWrapper.eq(TTestSample.BIZ_THREE, queryParam.getBizThree());
    }
    if (Objects.nonNull(queryParam.getBizFour())) {
      queryWrapper.eq(TTestSample.BIZ_FOUR, queryParam.getBizFour());
    }
    if (StringUtils.isNotEmpty(queryParam.getBizDict())) {
      queryWrapper.eq(TTestSample.BIZ_DICT, queryParam.getBizDict());
    }
    if (Objects.nonNull(queryParam.getBizDateBetween())
        && !queryParam.getBizDateBetween().isEmpty()
        && queryParam.getBizDateBetween().size() == 2) {
      if (StringUtils.isNotEmpty(queryParam.getBizDateBetween().get(0))
          && StringUtils.isNotEmpty(queryParam.getBizDateBetween().get(1))) {
        queryWrapper.between(
            TTestSample.BIZ_DATE,
            queryParam.getBizDateBetween().get(0),
            queryParam.getBizDateBetween().get(1));
      }
    }
    if (StringUtils.isNotEmpty(queryParam.getStatus())) {
      queryWrapper.eq(TTestSample.STATUS, queryParam.getStatus());
    } else {
      queryWrapper.ne(TTestSample.STATUS, BizConstants.STATUS_DELETE);
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
