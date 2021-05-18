package com.tongtech.biz.portal.section.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tongtech.biz.portal.section.SectionEnum;
import com.tongtech.biz.portal.section.model.domain.PortalSection;
import com.tongtech.biz.portal.section.model.dto.PortalSectionDelParam;
import com.tongtech.biz.portal.section.model.dto.PortalSectionParam;
import com.tongtech.biz.portal.section.service.PortalSectionService;
import com.tongtech.tfw.backend.common.biz.constants.BizConstants;
import com.tongtech.tfw.backend.common.biz.models.BaseResponse;
import com.tongtech.tfw.backend.common.biz.models.BaseResponseList;
import com.tongtech.tfw.backend.common.biz.models.BizGeneralResponse;
import com.tongtech.tfw.backend.common.constants.enumeration.BaseStatusEnum;
import com.tongtech.tfw.backend.common.models.exceptions.ApiException;
import com.tongtech.tfw.backend.common.models.supers.SuperController;
import com.tongtech.tfw.backend.core.constants.FrameworkConstants;
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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 栏目Controller
 *
 * @author tong-framework
 * @date 2020-09-01
 */
@RestController
@RequestMapping("/portal/section")
@Api(value = "PortalSection", tags = "栏目")
@Slf4j
public class PortalSectionController extends SuperController {
  @Autowired private PortalSectionService portalSectionService;

  /* Generated Method*/
  @ApiOperation(value = "新增 栏目", notes = "Add PortalSection")
  @PostMapping(value = "/add")
  public BaseResponse<PortalSection> addEntity(@RequestBody PortalSection addRequest) {
    BaseResponse<PortalSection> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(addRequest)) {
      if (StringUtils.isNotBlank(addRequest.getSectionEName())) {
        int counter =
            portalSectionService.count(
                new QueryWrapper<PortalSection>()
                    .ne(PortalSection.STATUS, BizConstants.STATUS_DELETE)
                    .eq(PortalSection.SECTION_E_NAME, addRequest.getSectionEName()));
        if (counter > 0) {
          throw new ApiException(SectionEnum.EXISTED.transform());
        }
      }
      // Top Level
      if (StringUtils.isEmpty(addRequest.getPSection())) {
        addRequest.setPSection(BizConstants.TOP_LEVEL);
      }
      // Query All parents
      addRequest.setPIds(queryParentsIds(addRequest.getPSection()));
      String id = IdHelper.getId32bit();
      PortalSection data = BeanHelper.beanToBean(addRequest, PortalSection.class);
      data.setId(id);
      data.setCreateBy(getUserId());
      data.setUpdateBy(getUserId());
      data.setCreateDate(LocalDateTime.now());
      data.setUpdateDate(LocalDateTime.now());
      boolean result = portalSectionService.save(data);
      if (result) {
        PortalSection newEntity = new PortalSection();
        newEntity.setId(id);
        baseResponse.setData(newEntity);
      }
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 栏目 ", notes = "Get PortalSection By Id")
  @GetMapping(value = "/get")
  public BaseResponse<PortalSection> getById(String bizId) {
    BaseResponse<PortalSection> baseResponse = new BaseResponse<>();
    if (StringUtils.isNotEmpty(bizId)) {
      PortalSection data = portalSectionService.getById(bizId);
      baseResponse.setData(data);
    }
    return baseResponse;
  }

  @ApiOperation(value = "修改 栏目 ", notes = "Update PortalSection By Id")
  @PutMapping(value = "/update")
  public BaseResponse<BizGeneralResponse> updateEntity(@RequestBody PortalSection updateRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(updateRequest) && StringUtils.isNotEmpty(updateRequest.getId())) {
      PortalSection data = BeanHelper.beanToBean(updateRequest, PortalSection.class);
      data.setUpdateBy(getUserId());
      data.setUpdateDate(LocalDateTime.now());
      if (StringUtils.isNotBlank(updateRequest.getSectionEName())) {
        int counter =
            portalSectionService.count(
                new QueryWrapper<PortalSection>()
                    .ne(PortalSection.STATUS, BizConstants.STATUS_DELETE)
                    .ne(PortalSection.BIZ_ID, updateRequest.getId())
                    .eq(PortalSection.SECTION_E_NAME, updateRequest.getSectionEName()));
        if (counter > 0) {
          throw new ApiException(SectionEnum.EXISTED.transform());
        }
      }
      PortalSection oldData = portalSectionService.getById(updateRequest.getId());
      if (StringUtils.isNotEmpty(updateRequest.getPSection())
          && !oldData.getPSection().equals(updateRequest.getPSection())) {

        String newParentIds = queryParentsIds(updateRequest.getPSection());
        data.setPIds(newParentIds);
        // 下级parentsIds 变化
        List<PortalSection> subSectionTree = getAllSub(updateRequest.getId());
        for (PortalSection sectionTree : subSectionTree) {
          String newIds =
              replaceParentIds(sectionTree.getPIds(), newParentIds, updateRequest.getId());
          if (StringUtils.isNotEmpty(newIds)) {
            sectionTree.setPIds(newIds);
            sectionTree.setUpdateBy(getUserId());
            sectionTree.setUpdateDate(LocalDateTime.now());
          } else {
            log.error(
                "TTestTree Parents Id update Failed:"
                    + updateRequest.getPSection()
                    + ", related sub : "
                    + sectionTree.getId());
          }
        }
        boolean batchResult = portalSectionService.updateBatchById(subSectionTree);
        if (subSectionTree.size() > 0 && !batchResult) {
          throw new ApiException(BaseStatusEnum.RELATE_UPDATE_FAILED.transform());
        }
      }
      /* 更新下级 */
      if (StringUtils.isNotEmpty(updateRequest.getStatus())
          && !oldData.getStatus().equals(updateRequest.getStatus())) {
        List<PortalSection> parentResourceList =
            portalSectionService.list(
                new QueryWrapper<PortalSection>()
                    .lambda()
                    .like(PortalSection::getPIds, data.getBizId()));
        portalSectionService.updateBatchById(
            parentResourceList.stream()
                .peek(portalSection -> portalSection.setStatus(updateRequest.getStatus()))
                .collect(Collectors.toList()));
      }

      boolean result = portalSectionService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "删除 栏目 ", notes = "delete PortalSection By Id")
  @PutMapping(value = "/delete")
  public BaseResponse<BizGeneralResponse> deleteEntity(
      @RequestBody PortalSectionDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(deleteRequest) && StringUtils.isNotEmpty(deleteRequest.getId())) {
      PortalSection data = new PortalSection();
      data.setId(deleteRequest.getId());
      data.setStatus(BizConstants.STATUS_DELETE);
      data.setUpdateBy(getUserId());
      data.setUpdateDate(LocalDateTime.now());
      /* 更新下级 */
      List<PortalSection> parentResourceList =
          portalSectionService.list(
              new QueryWrapper<PortalSection>()
                  .lambda()
                  .like(PortalSection::getPIds, data.getBizId()));
      portalSectionService.updateBatchById(
          parentResourceList.stream()
              .peek(portalSection -> portalSection.setStatus(BizConstants.STATUS_DELETE))
              .collect(Collectors.toList()));
      /* 更新本级 */
      boolean result = portalSectionService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 栏目 分页列表", notes = "List PortalSection with page")
  @GetMapping(value = "/list")
  public BaseResponse<BaseResponseList<PortalSection>> listEntity(PortalSectionParam param) {
    BaseResponse<BaseResponseList<PortalSection>> baseResponse = new BaseResponse<>();
    Long page =
        StringUtils.isEmpty(param.getPage()) ? BizConstants.PAGE : Long.valueOf(param.getPage());
    Long limit =
        StringUtils.isEmpty(param.getLimit()) ? BizConstants.LIMIT : Long.valueOf(param.getLimit());
    Page<PortalSection> resultPage = new Page(page, limit);
    QueryWrapper<PortalSection> queryWrapper = this.createQuery(param);
    Page<PortalSection> resultList = (Page) portalSectionService.page(resultPage, queryWrapper);
    BaseResponseList<PortalSection> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(resultList.getRecords());
    baseResponseList.setTotal(resultList.getTotal());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  @ApiOperation(value = "获取 栏目 列表", notes = "List PortalSection all")
  @GetMapping(value = "/datas")
  public BaseResponse<BaseResponseList<PortalSection>> listAllEntity(PortalSectionParam param) {
    BaseResponse<BaseResponseList<PortalSection>> baseResponse = new BaseResponse<>();
    QueryWrapper<PortalSection> queryWrapper = this.createQuery(param);
    List<PortalSection> resultList = portalSectionService.list(queryWrapper);
    BaseResponseList<PortalSection> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(resultList);
    baseResponseList.setTotal(resultList.size());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  /* Private Methods */

  /** 列表查询条件及查询参数 */
  private QueryWrapper<PortalSection> createQuery(PortalSectionParam queryParam) {
    QueryWrapper<PortalSection> queryWrapper = new QueryWrapper<>();
    if (StringUtils.isNotEmpty(queryParam.getSectionName())) {
      queryWrapper.like(PortalSection.SECTION_NAME, queryParam.getSectionName());
    }
    if (StringUtils.isNotEmpty(queryParam.getPSection())) {
      queryWrapper.eq(PortalSection.P_SECTION, queryParam.getPSection());
    }
    if (StringUtils.isNotEmpty(queryParam.getSectionEName())) {
      queryWrapper.like(PortalSection.SECTION_E_NAME, queryParam.getSectionEName());
    }
    if (StringUtils.isNotEmpty(queryParam.getSectionShow())) {
      queryWrapper.eq(PortalSection.SECTION_SHOW, queryParam.getSectionShow());
    }
    if (StringUtils.isNotEmpty(queryParam.getSectionDesc())) {
      queryWrapper.eq(PortalSection.SECTION_DESC, queryParam.getSectionDesc());
    }
    if (Objects.nonNull(queryParam.getSectionOrder())) {
      queryWrapper.eq(PortalSection.SECTION_ORDER, queryParam.getSectionOrder());
    }
    if (StringUtils.isNotEmpty(queryParam.getSectionModel())) {
      queryWrapper.eq(PortalSection.SECTION_MODEL, queryParam.getSectionModel());
    }
    if (StringUtils.isNotEmpty(queryParam.getStatus())) {
      queryWrapper.eq(PortalSection.STATUS, queryParam.getStatus());
    } else {
      queryWrapper.ne(PortalSection.STATUS, BizConstants.STATUS_DELETE);
    }
    if (StringUtils.isNotEmpty(queryParam.getOrderBy())) {
      if (StringUtils.isNotEmpty(queryParam.getOrderType())
          && BizConstants.ASC.equals(queryParam.getOrderType())) {
        queryWrapper.orderByAsc(queryParam.getOrderBy());
      } else {
        queryWrapper.orderByDesc(queryParam.getOrderBy());
      }
    } else {
      queryWrapper.orderByDesc(PortalSection.SECTION_ORDER);
    }
    return queryWrapper;
  }

  /**
   * Build Pids
   *
   * @param pid
   * @return pids
   */
  private String queryParentsIds(String pid) {
    String pids = pid;
    if (BizConstants.TOP_LEVEL.equals(pid)) {
      return pids + FrameworkConstants.GLOBE_SPLIT_COMMA;
    } else {
      pid = portalSectionService.getById(pid).getPIds();
      pids = pid + pids + FrameworkConstants.GLOBE_SPLIT_COMMA;
    }
    return pids;
  }

  /**
   * @param id :
   * @author Created by Ivan at 2020/5/6.
   *     <p>get All sub
   */
  private List<PortalSection> getAllSub(String id) {
    QueryWrapper<PortalSection> queryWrapper = new QueryWrapper<>();
    queryWrapper.like(PortalSection.P_IDS, id);
    List<PortalSection> resultList = portalSectionService.list(queryWrapper);
    return resultList;
  }

  /**
   * @param oldIds :
   * @param newIds :
   * @param id :
   * @return java.lang.String
   * @author Created by Ivan at 2020/5/6.
   *     <p>Replace Parent Ids
   */
  private String replaceParentIds(String oldIds, String newIds, String id) {
    String[] splited =
        oldIds.split(
            FrameworkConstants.GLOBE_SPLIT_COMMA + id + FrameworkConstants.GLOBE_SPLIT_COMMA);
    if (splited.length == BizConstants.PARENTS_ID_SPLIT_END) {
      newIds = newIds + id + FrameworkConstants.GLOBE_SPLIT_COMMA;
    } else if (splited.length == BizConstants.PARENTS_ID_SPLIT_LENGTH) {
      newIds = newIds + id + FrameworkConstants.GLOBE_SPLIT_COMMA + splited[1];
    } else {
      newIds = "";
    }
    return newIds;
  }
}
