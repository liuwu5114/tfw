package com.tongtech.biz.portal.context.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tongtech.biz.portal.context.ContextConstants;
import com.tongtech.biz.portal.context.model.domain.PortalContext;
import com.tongtech.biz.portal.context.model.dto.PortalContextDelParam;
import com.tongtech.biz.portal.context.model.dto.PortalContextListResponse;
import com.tongtech.biz.portal.context.model.dto.PortalContextParam;
import com.tongtech.biz.portal.context.service.PortalContextService;
import com.tongtech.biz.portal.section.model.domain.PortalSection;
import com.tongtech.biz.portal.section.service.PortalSectionService;
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
import java.util.stream.Collectors;

/**
 * 文章Controller
 *
 * @author tong-framework
 * @date 2020-09-01
 */
@RestController
@RequestMapping("/portal/context")
@Api(value = "PortalContext", tags = "文章")
@Slf4j
public class PortalContextController extends SuperController {
  @Autowired private PortalContextService portalContextService;
  @Autowired private SysUserService sysUserService;
  @Autowired private PortalSectionService portalSectionService;

  /* Generated Method*/
  @ApiOperation(value = "新增 文章", notes = "Add PortalContext")
  @PostMapping(value = "/add")
  public BaseResponse<PortalContext> addEntity(@RequestBody PortalContext addRequest) {
    BaseResponse<PortalContext> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(addRequest)) {
      String id = IdHelper.getId32bit();
      PortalContext data = BeanHelper.beanToBean(addRequest, PortalContext.class);
      if (StringUtils.isBlank(addRequest.getContextPublished())) {
        data.setContextPublished(ContextConstants.PUBLISHED_NON);
      }
      if (StringUtils.isBlank(addRequest.getContextAuthor())) {
        data.setContextAuthorId(getUserId());
        data.setContextAuthor(sysUserService.getById(getUserId()).getUserName());
      }
      data.setId(id);
      data.setStatus(BizConstants.STATUS_ENABLE);
      data.setCreateBy(getUserId());
      data.setUpdateBy(getUserId());
      data.setCreateDate(LocalDateTime.now());
      data.setUpdateDate(LocalDateTime.now());
      boolean result = portalContextService.save(data);
      if (result) {
        PortalContext newEntity = new PortalContext();
        newEntity.setId(id);
        baseResponse.setData(newEntity);
      }
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 文章 ", notes = "Get PortalContext By Id")
  @GetMapping(value = "/get")
  public BaseResponse<PortalContext> getById(String bizId) {
    BaseResponse<PortalContext> baseResponse = new BaseResponse<>();
    if (StringUtils.isNotEmpty(bizId)) {
      PortalContext data = portalContextService.getById(bizId);
      baseResponse.setData(data);
    }
    return baseResponse;
  }

  @ApiOperation(value = "修改 文章 ", notes = "Update PortalContext By Id")
  @PutMapping(value = "/update")
  public BaseResponse<BizGeneralResponse> updateEntity(@RequestBody PortalContext updateRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(updateRequest) && StringUtils.isNotEmpty(updateRequest.getId())) {
      PortalContext data = BeanHelper.beanToBean(updateRequest, PortalContext.class);
      data.setUpdateBy(getUserId());
      boolean result = portalContextService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "删除 文章 ", notes = "delete PortalContext By Id")
  @PutMapping(value = "/delete")
  public BaseResponse<BizGeneralResponse> deleteEntity(
      @RequestBody PortalContextDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(deleteRequest) && StringUtils.isNotEmpty(deleteRequest.getId())) {
      PortalContext data = new PortalContext();
      data.setId(deleteRequest.getId());
      data.setStatus(BizConstants.STATUS_DELETE);
      data.setUpdateBy(getUserId());
      data.setUpdateDate(LocalDateTime.now());
      boolean result = portalContextService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "批量删除文章", notes = "delete PortalContext by batch Id")
  @PutMapping(value = "/batch_delete")
  public BaseResponse<BizGeneralResponse> deleteEntityBatch(
      @RequestBody PortalContextDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(deleteRequest) && (!deleteRequest.getIds().isEmpty())) {
      /* 批量删除 */
      List<PortalContext> portalContextList = new ArrayList();
      for (String id : deleteRequest.getIds()) {
        PortalContext data = new PortalContext();
        data.setId(id);
        data.setStatus(BizConstants.STATUS_DELETE);
        data.setUpdateBy(getUserId());
        data.setUpdateDate(LocalDateTime.now());
        portalContextList.add(data);
      }
      boolean result = portalContextService.updateBatchById(portalContextList);
      // if (result) {
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
      // }
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 文章 分页列表", notes = "List PortalContext with page")
  @GetMapping(value = "/list")
  public BaseResponse<BaseResponseList<PortalContextListResponse>> listEntity(
      PortalContextParam param) {
    BaseResponse<BaseResponseList<PortalContextListResponse>> baseResponse = new BaseResponse<>();
    Long page =
        StringUtils.isEmpty(param.getPage()) ? BizConstants.PAGE : Long.valueOf(param.getPage());
    Long limit =
        StringUtils.isEmpty(param.getLimit()) ? BizConstants.LIMIT : Long.valueOf(param.getLimit());
    Page<PortalContext> resultPage = new Page(page, limit);
    QueryWrapper<PortalContext> queryWrapper = this.createQuery(param);
    Page<PortalContext> pageList = (Page) portalContextService.page(resultPage, queryWrapper);
    BaseResponseList<PortalContextListResponse> baseResponseList = new BaseResponseList<>();
    List<PortalContextListResponse> resultList =
        pageList.getRecords().stream()
            .map(e -> e.beanToBean(PortalContextListResponse.class))
            .collect(Collectors.toList());
    for (PortalContextListResponse portalContextListResponse : resultList) {
      PortalSection ps = portalSectionService.getById(portalContextListResponse.getSectionId());
      if (Objects.nonNull(ps)) {
        portalContextListResponse.setSectionName(ps.getSectionName());
      }
    }
    baseResponseList.setData(resultList);
    baseResponseList.setTotal(pageList.getTotal());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  @ApiOperation(value = "获取 文章 列表", notes = "List PortalContext all")
  @GetMapping(value = "/datas")
  public BaseResponse<BaseResponseList<PortalContextListResponse>> listAllEntity(
      PortalContextParam param) {
    BaseResponse<BaseResponseList<PortalContextListResponse>> baseResponse = new BaseResponse<>();
    // TODO 根据需求修改查询条件及查询参数
    QueryWrapper<PortalContext> queryWrapper = this.createQuery(param);
    List<PortalContextListResponse> resultList =
        portalContextService.list(queryWrapper).stream()
            .map(e -> e.beanToBean(PortalContextListResponse.class))
            .collect(Collectors.toList());
    for (PortalContextListResponse portalContextListResponse : resultList) {
      PortalSection ps = portalSectionService.getById(portalContextListResponse.getSectionId());
      if (Objects.nonNull(ps)) {
        portalContextListResponse.setSectionName(ps.getSectionName());
      }
    }
    BaseResponseList<PortalContextListResponse> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(resultList);
    baseResponseList.setTotal(resultList.size());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  /* Private Methods */
  /** 列表查询条件及查询参数 */
  private QueryWrapper<PortalContext> createQuery(PortalContextParam queryParam) {
    QueryWrapper<PortalContext> queryWrapper = new QueryWrapper<>();
    if (StringUtils.isNotEmpty(queryParam.getContextTitle())) {
      queryWrapper.like(PortalContext.CONTEXT_TITLE, queryParam.getContextTitle());
    }
    if (StringUtils.isNotEmpty(queryParam.getContextSubTitle())) {
      queryWrapper.like(PortalContext.CONTEXT_SUB_TITLE, queryParam.getContextSubTitle());
    }
    if (StringUtils.isNotEmpty(queryParam.getContextKeywords())) {
      queryWrapper.like(PortalContext.CONTEXT_KEYWORDS, queryParam.getContextKeywords());
    }
    if (StringUtils.isNotEmpty(queryParam.getContextSource())) {
      queryWrapper.eq(PortalContext.CONTEXT_SOURCE, queryParam.getContextSource());
    }
    if (StringUtils.isNotEmpty(queryParam.getContextSummary())) {
      queryWrapper.eq(PortalContext.CONTEXT_SUMMARY, queryParam.getContextSummary());
    }
    if (StringUtils.isNotEmpty(queryParam.getContextMain())) {
      queryWrapper.eq(PortalContext.CONTEXT_MAIN, queryParam.getContextMain());
    }
    if (Objects.nonNull(queryParam.getPublishedTime())) {
      queryWrapper.eq(PortalContext.PUBLISHED_TIME, queryParam.getPublishedTime());
    }
    if (StringUtils.isNotEmpty(queryParam.getContextReLink())) {
      queryWrapper.eq(PortalContext.CONTEXT_RE_LINK, queryParam.getContextReLink());
    }
    if (StringUtils.isNotEmpty(queryParam.getContextPicLink())) {
      queryWrapper.eq(PortalContext.CONTEXT_PIC_LINK, queryParam.getContextPicLink());
    }
    if (StringUtils.isNotEmpty(queryParam.getContextPublished())) {
      queryWrapper.eq(PortalContext.CONTEXT_PUBLISHED, queryParam.getContextPublished());
    }
    if (StringUtils.isNotEmpty(queryParam.getContextAuthor())) {
      queryWrapper.eq(PortalContext.CONTEXT_AUTHOR, queryParam.getContextAuthor());
    }
    if (StringUtils.isNotEmpty(queryParam.getContextAuthorId())) {
      queryWrapper.eq(PortalContext.CONTEXT_AUTHOR_ID, queryParam.getContextAuthorId());
    }
    if (StringUtils.isNotEmpty(queryParam.getStatus())) {
      queryWrapper.eq(PortalContext.STATUS, queryParam.getStatus());
    } else {
      queryWrapper.ne(PortalContext.STATUS, BizConstants.STATUS_DELETE);
    }
    if (StringUtils.isNotBlank(queryParam.getSectionId())) {
      queryWrapper.in(PortalContext.SECTION_ID, getAllSub(queryParam.getSectionId()));
    }
    if (Objects.nonNull(queryParam.getBizDateBetween())
        && !queryParam.getBizDateBetween().isEmpty()
        && queryParam.getBizDateBetween().size() == 2) {
      if (StringUtils.isNotEmpty(queryParam.getBizDateBetween().get(0))
          && StringUtils.isNotEmpty(queryParam.getBizDateBetween().get(1))) {
        queryWrapper.between(
            PortalContext.UPDATE_DATE,
            queryParam.getBizDateBetween().get(0),
            queryParam.getBizDateBetween().get(1));
      }
    }
    if (StringUtils.isNotBlank(queryParam.getSectionEngName())) {
      PortalSection section =
          portalSectionService.getOne(
              new QueryWrapper<PortalSection>()
                  .eq(PortalSection.SECTION_E_NAME, queryParam.getSectionEngName()));
      if (Objects.nonNull(section)) {
        queryWrapper.in(PortalContext.SECTION_ID, getAllSub(section.getId()));
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
      queryWrapper.orderByDesc(PortalContext.UPDATE_DATE);
    }
    return queryWrapper;
  }

  /**
   * @author Created by Ivan at 2020/5/6.
   *     <p>get All sub
   * @param id :
   */
  private List<String> getAllSub(String id) {
    QueryWrapper<PortalSection> queryWrapper = new QueryWrapper<>();
    queryWrapper.like(PortalSection.P_IDS, id).or().eq(PortalSection.BIZ_ID, id);
    List<String> resultList =
        portalSectionService.list(queryWrapper).stream()
            .map(e -> e.getBizId())
            .collect(Collectors.toList());
    return resultList;
  }
}
