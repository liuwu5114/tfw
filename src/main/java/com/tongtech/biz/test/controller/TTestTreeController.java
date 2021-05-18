package com.tongtech.biz.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tongtech.biz.test.model.domain.TTestTree;
import com.tongtech.biz.test.model.dto.TTestTreeDelParam;
import com.tongtech.biz.test.model.dto.TTestTreeParam;
import com.tongtech.biz.test.service.TTestTreeService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 代码生成树结构Controller
 *
 * @author tong-framework
 * @date 2020-06-18
 */
@RestController
@RequestMapping("/test/tree")
@Api(value = "TTestTree", tags = "代码生成树结构")
@Slf4j
public class TTestTreeController extends SuperController {
  @Autowired private TTestTreeService tTestTreeService;

  /* Generated Method*/
  @ApiOperation(value = "新增 代码生成树结构", notes = "Add TTestTree")
  @PostMapping(value = "/add")
  public BaseResponse<TTestTree> addEntity(@RequestBody TTestTree addRequest) {
    BaseResponse<TTestTree> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(addRequest)) {
      // TODO 按需求添加数据去重，特殊值设定，业务异常
      // Top Level
      if (StringUtils.isEmpty(addRequest.getParentId())) {
        addRequest.setParentId(BizConstants.TOP_LEVEL);
      }
      // Query All parents
      addRequest.setParentIds(queryParentsIds(addRequest.getParentId()));
      String id = IdHelper.getId32bit();
      TTestTree data = BeanHelper.beanToBean(addRequest, TTestTree.class);
      data.setId(id);
      data.setCreateBy(getUserId());
      data.setUpdateBy(getUserId());
      data.setCreateDate(LocalDateTime.now());
      data.setUpdateDate(LocalDateTime.now());
      boolean result = tTestTreeService.save(data);
      if (result) {
        TTestTree newEntity = new TTestTree();
        newEntity.setId(id);
        baseResponse.setData(newEntity);
      }
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 代码生成树结构 ", notes = "Get TTestTree By Id")
  @GetMapping(value = "/get")
  public BaseResponse<TTestTree> getById(String id) {
    BaseResponse<TTestTree> baseResponse = new BaseResponse<>();
    if (StringUtils.isNotEmpty(id)) {
      // TODO 按需求添加业务异常判断
      TTestTree data = tTestTreeService.getById(id);
      baseResponse.setData(data);
    }
    return baseResponse;
  }

  @ApiOperation(value = "修改 代码生成树结构 ", notes = "Update TTestTree By Id")
  @PutMapping(value = "/update")
  public BaseResponse<BizGeneralResponse> updateEntity(@RequestBody TTestTree updateRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(updateRequest) && StringUtils.isNotEmpty(updateRequest.getId())) {
      // TODO 按需求添加数据去重，特殊值设定，业务异常
      TTestTree data = BeanHelper.beanToBean(updateRequest, TTestTree.class);

      TTestTree oldData = tTestTreeService.getById(updateRequest.getId());
      /** Parent Id Update */
      if (StringUtils.isNotEmpty(updateRequest.getParentId())
          && !oldData.getParentId().equals(updateRequest.getParentId())) {
        String newParentIds = queryParentsIds(updateRequest.getParentId());
        data.setParentIds(newParentIds);
        // TODO 下级parentsIds 变化
        List<TTestTree> subTTestTree = getAllSub(updateRequest.getId());
        for (TTestTree tTestTree : subTTestTree) {
          String newIds =
              replaceParentIds(tTestTree.getParentIds(), newParentIds, updateRequest.getId());
          if (StringUtils.isNotEmpty(newIds)) {
            tTestTree.setParentIds(newIds);
            tTestTree.setUpdateBy(getUserId());
            tTestTree.setUpdateDate(LocalDateTime.now());
          } else {
            log.error(
                "TTestTree Parents Id update Failed:"
                    + updateRequest.getParentId()
                    + ", related sub : "
                    + tTestTree.getId());
          }
        }
        boolean batchResult = tTestTreeService.updateBatchById(subTTestTree);
        if (!batchResult) {
          throw new ApiException(BaseStatusEnum.RELATE_UPDATE_FAILED.transform());
        }
      }
      data.setUpdateBy(getUserId());
      data.setUpdateDate(LocalDateTime.now());
      boolean result = tTestTreeService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "删除 代码生成树结构 ", notes = "delete TTestTree By Id")
  @PutMapping(value = "/delete")
  public BaseResponse<BizGeneralResponse> deleteEntity(
      @RequestBody TTestTreeDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(deleteRequest) && StringUtils.isNotEmpty(deleteRequest.getId())) {
      // TODO 按需求添加数据去重，特殊值设定，业务异常
      TTestTree data = new TTestTree();
      data.setId(deleteRequest.getId());
      data.setStatus(BizConstants.STATUS_DELETE);
      data.setUpdateBy(getUserId());
      data.setUpdateDate(LocalDateTime.now());
      /* 更新下级 */
      List<TTestTree> parentList =
          tTestTreeService.list(
              new QueryWrapper<TTestTree>().lambda().like(TTestTree::getParentIds, data.getId()));
      boolean batchResult =
          tTestTreeService.updateBatchById(
              parentList.stream()
                  .peek(tTestTree -> tTestTree.setStatus(BizConstants.STATUS_DELETE))
                  .collect(Collectors.toList()));
      if (!batchResult) {
        throw new ApiException(BaseStatusEnum.RELATE_UPDATE_FAILED.transform());
      }
      /* 更新本级 */
      boolean result = tTestTreeService.updateById(data);
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    }
    return baseResponse;
  }

  @ApiOperation(value = "批量删除代码生成树结构", notes = "delete TTestTree by batch Id")
  @PutMapping(value = "/batch_delete")
  public BaseResponse<BizGeneralResponse> deleteEntityBatch(
      @RequestBody TTestTreeDelParam deleteRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    if (Objects.nonNull(deleteRequest) && (!deleteRequest.getIds().isEmpty())) {
      /* 批量删除 */
      List<TTestTree> tTestTreeList = new ArrayList();
      for (String id : deleteRequest.getIds()) {
        TTestTree data = new TTestTree();
        data.setId(id);
        data.setStatus(BizConstants.STATUS_DELETE);
        data.setUpdateBy(getUserId());
        data.setUpdateDate(LocalDateTime.now());
        tTestTreeList.add(data);
      }
      boolean result = tTestTreeService.updateBatchById(tTestTreeList);
      // if (result) {
      baseResponse.setData(
          BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
      // }
    }
    return baseResponse;
  }

  @ApiOperation(value = "获取 代码生成树结构 分页列表", notes = "List TTestTree with page")
  @GetMapping(value = "/list")
  public BaseResponse<BaseResponseList<TTestTree>> listEntity(TTestTreeParam param) {
    BaseResponse<BaseResponseList<TTestTree>> baseResponse = new BaseResponse<>();
    Long page =
        StringUtils.isEmpty(param.getPage()) ? BizConstants.PAGE : Long.valueOf(param.getPage());
    Long limit =
        StringUtils.isEmpty(param.getLimit()) ? BizConstants.LIMIT : Long.valueOf(param.getLimit());
    Page<TTestTree> resultPage = new Page(page, limit);
    // TODO 根据需求修改查询条件及查询参数
    QueryWrapper<TTestTree> queryWrapper = createQuery(param);
    Page<TTestTree> resultList = (Page) tTestTreeService.page(resultPage, queryWrapper);
    BaseResponseList<TTestTree> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(resultList.getRecords());
    baseResponseList.setTotal(resultList.getTotal());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  @ApiOperation(value = "获取 代码生成树结构 列表", notes = "List TTestTree all")
  @GetMapping(value = "/datas")
  public BaseResponse<BaseResponseList<TTestTree>> listAllEntity(TTestTreeParam param) {
    BaseResponse<BaseResponseList<TTestTree>> baseResponse = new BaseResponse<>();
    // TODO 根据需求修改查询条件及查询参数
    QueryWrapper<TTestTree> queryWrapper = createQuery(param);
    List<TTestTree> resultList = tTestTreeService.list(queryWrapper);
    BaseResponseList<TTestTree> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(resultList);
    baseResponseList.setTotal(resultList.size());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  /* Private Methods */
  /** 列表查询条件及查询参数 */
  private QueryWrapper<TTestTree> createQuery(TTestTreeParam queryParam) {
    QueryWrapper<TTestTree> queryWrapper = new QueryWrapper<>();
    if (StringUtils.isNotEmpty(queryParam.getName())) {
      queryWrapper.like(TTestTree.NAME, queryParam.getName());
    }
    if (StringUtils.isNotEmpty(queryParam.getStatus())) {
      queryWrapper.eq(TTestTree.STATUS, queryParam.getStatus());
    } else {
      queryWrapper.ne(TTestTree.STATUS, BizConstants.STATUS_DELETE);
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
      pid = tTestTreeService.getById(pid).getParentIds();
      pids = pid + pids + FrameworkConstants.GLOBE_SPLIT_COMMA;
    }
    return pids;
  }

  /**
   * @author Created by Ivan at 2020/5/6.
   *     <p>get All sub areas
   * @param id :
   * @return java.util.List<com.tongtech.backend.apis.biz.sys.area.model.domain.SysArea>
   */
  private List<TTestTree> getAllSub(String id) {
    QueryWrapper<TTestTree> queryWrapper = new QueryWrapper<>();
    queryWrapper.like(TTestTree.PARENT_IDS, id);
    List<TTestTree> resultList = tTestTreeService.list(queryWrapper);
    return resultList;
  }

  /**
   * @author Created by Ivan at 2020/5/6.
   *     <p>Replace Parent Ids
   * @param oldIds :
   * @param newIds :
   * @param id :
   * @return java.lang.String
   */
  private String replaceParentIds(String oldIds, String newIds, String id) {
    String[] splited =
        oldIds.split(
            FrameworkConstants.GLOBE_SPLIT_COMMA + id + FrameworkConstants.GLOBE_SPLIT_COMMA);
    if (splited.length == BizConstants.PARENTS_ID_SPLIT_LENGTH) {
      newIds = newIds + id + FrameworkConstants.GLOBE_SPLIT_COMMA + splited[1];
    } else {
      newIds = "";
    }
    return newIds;
  }
}
