package com.tongtech.biz.datarule.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tongtech.biz.datarule.model.domain.TestDataRule;
import com.tongtech.biz.datarule.model.dto.TestDataRuleDelParam;
import com.tongtech.biz.datarule.model.dto.TestDataRuleListResponse;
import com.tongtech.biz.datarule.model.dto.TestDataRuleParam;
import com.tongtech.biz.datarule.service.TestDataRuleService;
import com.tongtech.tfw.backend.admin.apis.biz.rule.service.SysRuleService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.service.SysOrgService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.service.SysUserService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * dataruletestController
 *
 * @author tong-framework
 * @date 2020-08-14
 */
@RestController
@RequestMapping("/dataruletest")
@Api(value = "TestDataRule", tags = "dataruletest")
@Slf4j
public class TestDataRuleController extends SuperController {
  @Autowired private TestDataRuleService testDataRuleService;
  @Autowired private SysRuleService sysRuleService;
  @Autowired private SysUserService sysUserService;
  @Autowired private SysOrgService sysOrgService;

  /* Generated Method*/
  @ApiOperation(value = "新增 dataruletest", notes = "Add TestDataRule")
  @PostMapping(value = "/add")
  public BaseResponse<TestDataRule> addEntity(@RequestBody TestDataRule addRequest) {
    BaseResponse<TestDataRule> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(addRequest)) {
      // TODO 按需求添加数据去重，特殊值设定，业务异常
      String id = IdHelper.getId32bit();
      TestDataRule data = BeanHelper.beanToBean(addRequest, TestDataRule.class);
      data.setId(id);
      data.setUserId(getUserId());
      data.setStatus(BizConstants.STATUS_ENABLE);
      data.setOrgId(sysUserService.getById(getUserId()).getOrgId());
      data.setCreateBy(getUserId());
      data.setUpdateBy(getUserId());
      data.setCreateDate(LocalDateTime.now());
      data.setUpdateDate(LocalDateTime.now());
      boolean result = testDataRuleService.save(data);
      if (result) {
        TestDataRule newEntity = new TestDataRule();
        newEntity.setId(id);
        baseResponse.setData(newEntity);
      }
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 dataruletest ", notes = "Get TestDataRule By Id")
  @GetMapping(value = "/get")
  public BaseResponse<TestDataRule> getById(String bizId) {
    BaseResponse<TestDataRule> baseResponse = new BaseResponse<>();
    if (StringUtils.isNotEmpty(bizId)) {
      // TODO 按需求添加业务异常判断
      TestDataRule data = testDataRuleService.getById(bizId);
      baseResponse.setData(data);
    }
    return baseResponse;
  }

  @ApiOperation(value = "修改 dataruletest ", notes = "Update TestDataRule By Id")
  @PutMapping(value = "/update")
  public BaseResponse<BizGeneralResponse> updateEntity(@RequestBody TestDataRule updateRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(updateRequest) && StringUtils.isNotEmpty(updateRequest.getId())) {
      // TODO 按需求添加数据去重，特殊值设定，业务异常
      TestDataRule data = BeanHelper.beanToBean(updateRequest, TestDataRule.class);
      data.setUpdateBy(getUserId());
      boolean result = testDataRuleService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "删除 dataruletest ", notes = "delete TestDataRule By Id")
  @PutMapping(value = "/delete")
  public BaseResponse<BizGeneralResponse> deleteEntity(
      @RequestBody TestDataRuleDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(deleteRequest) && StringUtils.isNotEmpty(deleteRequest.getId())) {
      // TODO 按需求添加数据去重，特殊值设定，业务异常
      TestDataRule data = new TestDataRule();
      data.setId(deleteRequest.getId());
      data.setStatus(BizConstants.STATUS_DELETE);
      data.setUpdateBy(getUserId());
      data.setUpdateDate(LocalDateTime.now());
      boolean result = testDataRuleService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "批量删除dataruletest", notes = "delete TestDataRule by batch Id")
  @PutMapping(value = "/batch_delete")
  public BaseResponse<BizGeneralResponse> deleteEntityBatch(
      @RequestBody TestDataRuleDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(deleteRequest) && (!deleteRequest.getIds().isEmpty())) {
      /* 批量删除 */
      List<TestDataRule> testDataRuleList = new ArrayList();
      for (String id : deleteRequest.getIds()) {
        TestDataRule data = new TestDataRule();
        data.setId(id);
        data.setStatus(BizConstants.STATUS_DELETE);
        data.setUpdateBy(getUserId());
        data.setUpdateDate(LocalDateTime.now());
        testDataRuleList.add(data);
      }
      boolean result = testDataRuleService.updateBatchById(testDataRuleList);
      // if (result) {
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
      // }
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 dataruletest 分页列表", notes = "List TestDataRule with page")
  @GetMapping(value = "/list")
  public BaseResponse<BaseResponseList<TestDataRuleListResponse>> listEntity(
      TestDataRuleParam param) {
    BaseResponse<BaseResponseList<TestDataRuleListResponse>> baseResponse = new BaseResponse<>();
    Long page =
        StringUtils.isEmpty(param.getPage()) ? BizConstants.PAGE : Long.valueOf(param.getPage());
    Long limit =
        StringUtils.isEmpty(param.getLimit()) ? BizConstants.LIMIT : Long.valueOf(param.getLimit());
    Page<TestDataRule> resultPage = new Page(page, limit);
    // TODO 根据需求修改查询条件及查询参数
    QueryWrapper<TestDataRule> queryWrapper = this.createQuery(param);
    Page<TestDataRule> resultList = (Page) testDataRuleService.page(resultPage, queryWrapper);
    List<TestDataRuleListResponse> dataList = new ArrayList<>(resultList.getRecords().size());
    for (TestDataRule testDataRule : resultList.getRecords()) {
      TestDataRuleListResponse tlr =
          BeanHelper.beanToBean(testDataRule, TestDataRuleListResponse.class);
      tlr.setUserId(sysUserService.getById(tlr.getUserId()).getUserName());
      tlr.setOrgId(sysOrgService.getById(tlr.getOrgId()).getOrgName());
      dataList.add(tlr);
    }
    BaseResponseList<TestDataRuleListResponse> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(dataList);
    baseResponseList.setTotal(resultList.getTotal());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  @ApiOperation(value = "获取 dataruletest 列表", notes = "List TestDataRule all")
  @GetMapping(value = "/datas")
  public BaseResponse<BaseResponseList<TestDataRuleListResponse>> listAllEntity(
      TestDataRuleParam param) {
    BaseResponse<BaseResponseList<TestDataRuleListResponse>> baseResponse = new BaseResponse<>();
    // TODO 根据需求修改查询条件及查询参数
    QueryWrapper<TestDataRule> queryWrapper = this.createQuery(param);
    List<TestDataRule> resultList = testDataRuleService.list(queryWrapper);
    List<TestDataRuleListResponse> dataList = new ArrayList<>(resultList.size());
    for (TestDataRule testDataRule : resultList) {
      TestDataRuleListResponse tlr =
          BeanHelper.beanToBean(testDataRule, TestDataRuleListResponse.class);
      tlr.setUserId(sysUserService.getById(tlr.getUserId()).getUserName());
      tlr.setOrgId(sysOrgService.getById(tlr.getOrgId()).getOrgName());
      dataList.add(tlr);
    }
    BaseResponseList<TestDataRuleListResponse> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(dataList);
    baseResponseList.setTotal(resultList.size());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  /* Private Methods */
  /** 列表查询条件及查询参数 */
  private QueryWrapper<TestDataRule> createQuery(TestDataRuleParam queryParam) {
    QueryWrapper<TestDataRule> queryWrapper = new QueryWrapper<>();
    if (StringUtils.isNotEmpty(queryParam.getBizId())) {
      queryWrapper.eq(TestDataRule.BIZ_ID, queryParam.getBizId());
    }
    if (StringUtils.isNotEmpty(queryParam.getUserId())) {
      queryWrapper.eq(TestDataRule.USER_ID, queryParam.getUserId());
    }
    if (StringUtils.isNotEmpty(queryParam.getOrgId())) {
      queryWrapper.eq(TestDataRule.ORG_ID, queryParam.getOrgId());
    }
    if (StringUtils.isNotEmpty(queryParam.getDataType())) {
      queryWrapper.eq(TestDataRule.DATA_TYPE, queryParam.getDataType());
    }
    if (StringUtils.isNotEmpty(queryParam.getStudentName())) {
      queryWrapper.eq(TestDataRule.STUDENT_NAME, queryParam.getStudentName());
    }
    if (Objects.nonNull(queryParam.getScore())) {
      queryWrapper.eq(TestDataRule.SCORE, queryParam.getScore());
    }
    if (StringUtils.isNotEmpty(queryParam.getStatus())) {
      queryWrapper.eq(TestDataRule.STATUS, queryParam.getStatus());
    } else {
      queryWrapper.ne(TestDataRule.STATUS, BizConstants.STATUS_DELETE);
    }
    // Data Rule
    if (StringUtils.isNotEmpty(queryParam.getDataRuleCode())) {
      String rule = sysRuleService.dataRuleScope(queryParam.getDataRuleCode());
      if (StringUtils.isNotEmpty(rule)) {
        queryWrapper.apply(rule);
      }
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
