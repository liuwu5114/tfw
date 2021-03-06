package com.tongtech.tfw.backend.admin.apis.biz.sys.org.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tongtech.tfw.backend.admin.apis.biz.excel.ExcelEnum;
import com.tongtech.tfw.backend.admin.apis.biz.excel.model.ImportError;
import com.tongtech.tfw.backend.admin.apis.biz.excel.service.ExcelService;
import com.tongtech.tfw.backend.admin.apis.biz.excel.service.TongExcelListener;
import com.tongtech.tfw.backend.admin.apis.biz.excel.support.ExcelDtoHelper;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.OrgConstants;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.OrgEnumCode;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.model.domain.SysOrg;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.model.dto.*;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.service.OrgExcelImport;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.service.SysOrgService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.domain.SysUser;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.service.SysUserService;
import com.tongtech.tfw.backend.common.biz.constants.BizConstants;
import com.tongtech.tfw.backend.common.biz.models.BaseResponse;
import com.tongtech.tfw.backend.common.biz.models.BaseResponseList;
import com.tongtech.tfw.backend.common.biz.models.BizGeneralResponse;
import com.tongtech.tfw.backend.common.models.exceptions.ApiException;
import com.tongtech.tfw.backend.common.models.supers.SuperController;
import com.tongtech.tfw.backend.core.constants.FrameworkConstants;
import com.tongtech.tfw.backend.core.helper.IdHelper;
import com.tongtech.tfw.backend.core.helper.ObjectHelper;
import com.tongtech.tfw.backend.core.helper.StringHelper;
import com.tongtech.tfw.backend.core.helper.TypeHelper;
import com.tongtech.tfw.backend.core.helper.bean.BeanHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ??????????????? ???????????????
 *
 * @author Ivan
 * @since 2020-03-31
 */
@RestController
@Api(value = "SysOrg", tags = "SysOrg ????????????")
@Slf4j
@RequestMapping("/org")
public class SysOrgController extends SuperController {
  /** SysUserService */
  @Autowired private SysUserService sysUserService;

  /** SysOrgService */
  @Autowired private SysOrgService sysOrgService;

  /** Excel Service */
  @Autowired private ExcelService excelService;

  /** User Import Service */
  @Autowired OrgExcelImport orgExcelImport;
  /**
   * ??????????????????
   *
   * @param addRequest SysOrgCreateAndUpdateRequest
   * @return BaseResponse<SysOrg>
   */
  @ApiOperation(value = "??????????????????", notes = "?????????????????? Add new Org")
  @PostMapping(value = "/add")
  public BaseResponse<SysOrg> addEntity(
      @Valid @RequestBody SysOrgCreateAndUpdateRequest addRequest) {
    BaseResponse<SysOrg> baseResponse = new BaseResponse<>();
    if (ObjectHelper.isEmpty(addRequest)) {
      throw new ApiException(OrgEnumCode.ORG_PARAM_IS_NULL.transform());
    }
    // ????????????????????????????????????
    List<SysOrg> sysOrgList =
        sysOrgService.list(
            new QueryWrapper<SysOrg>()
                .eq(SysOrg.ORG_CODE, addRequest.getOrgCode())
                .ne(SysOrg.STATUS, BizConstants.STATUS_DELETE));
    if (!ObjectHelper.isEmpty(sysOrgList)) {
      throw new ApiException(OrgEnumCode.ORG_CODE_ALREADY_EXISTS.transform());
    }
    // ??????????????????????????????id???parentId
    String parentId = addRequest.getParentId();
    if (!StringHelper.isEmpty(parentId) && !BizConstants.TOP_LEVEL.equals(parentId)) {
      SysOrg parentOrg = sysOrgService.getById(parentId);
      addRequest.setParentIds(parentOrg.getParentIds() + "," + parentOrg.getOrgId());
    } else {
      addRequest.setParentId(BizConstants.TOP_LEVEL);
      addRequest.setParentIds(BizConstants.TOP_LEVEL);
    }
    // ?????????????????????????????????
    List<SysOrg> sysOrgs =
        sysOrgService.list(
            new QueryWrapper<SysOrg>()
                .eq(SysOrg.ORG_NAME, addRequest.getOrgName())
                .eq(SysOrg.PARENT_ID, addRequest.getParentId())
                .ne(SysOrg.STATUS, BizConstants.STATUS_DELETE));
    if (!ObjectHelper.isEmpty(sysOrgs)) {
      throw new ApiException(OrgEnumCode.ORG_NAME_ALREADY_EXISTS.transform());
    }
    SysOrg data = BeanHelper.beanToBean(addRequest, SysOrg.class);
    // ??????ID
    data.setOrgId(IdHelper.getId32bit());
    // ??????
    boolean flag = sysOrgService.save(data);
    if (flag) {
      baseResponse.setData(data);
    }
    return baseResponse;
  }

  /**
   * ????????????????????????
   *
   * @param updateRequest SysOrgCreateAndUpdateRequest
   * @return BaseResponse<SysOrg>
   */
  @ApiOperation(value = "??????????????????", notes = "?????????????????? Edit Org Info")
  @Transactional(rollbackFor = Exception.class)
  @PutMapping(value = "/update")
  public BaseResponse<SysOrg> updateEntity(
      @Valid @RequestBody SysOrgCreateAndUpdateRequest updateRequest) {
    BaseResponse<SysOrg> baseResponse = new BaseResponse<>();
    if (ObjectHelper.isEmpty(updateRequest)) {
      throw new ApiException(OrgEnumCode.ORG_PARAM_IS_NULL.transform());
    }
    if (StringHelper.isEmpty(updateRequest.getOrgId())) {
      throw new ApiException(OrgEnumCode.ORG_ID_NOT_EXISTENT.transform());
    }
    // ??????????????????????????????????????????????????????
    SysOrg oldSysOrg = sysOrgService.getById(updateRequest.getOrgId());
    if (ObjectHelper.isEmpty(oldSysOrg)) {
      throw new ApiException(OrgEnumCode.ORG_ID_NOT_EXISTENT.transform());
    }
    // ????????????
    SysOrg modifyOrg = BeanHelper.beanToBean(updateRequest, SysOrg.class);
    // ??????CODE?????????
    if (!StringHelper.isEmpty(updateRequest.getOrgCode())
        && !oldSysOrg.getOrgCode().equals(updateRequest.getOrgCode())) {
      List<SysOrg> sysOrgList =
          sysOrgService.list(
              new QueryWrapper<SysOrg>()
                  .eq(SysOrg.ORG_CODE, updateRequest.getOrgCode())
                  .ne(SysOrg.ORG_ID, updateRequest.getOrgId())
                  .ne(SysOrg.STATUS, BizConstants.STATUS_DELETE));
      if (!ObjectHelper.isEmpty(sysOrgList)) {
        throw new ApiException(OrgEnumCode.ORG_CODE_ALREADY_EXISTS.transform());
      }
    }
    // ????????????????????????????????????????????????
    boolean isStatusChanged = false;
    // ????????????newSysOrg.getStatus()?????????
    if (!modifyOrg.getStatus().equals(oldSysOrg.getStatus())) {
      isStatusChanged = true;
    }
    // ???????????????????????????????????????????????????????????????
    // ???????????????????????????
    boolean isParentOrgChanged = false;
    String newParentId = modifyOrg.getParentId();
    if (StringHelper.isEmpty(newParentId)) {
      newParentId = BizConstants.TOP_LEVEL;
    }
    String oldParentIds = oldSysOrg.getParentIds();
    String newParentIds = BizConstants.TOP_LEVEL;
    // parent id ?????????
    if (!oldSysOrg.getParentId().equals(newParentId)) {
      isParentOrgChanged = true;
      if (BizConstants.TOP_LEVEL.equals(newParentId)) {
        modifyOrg.setParentId(BizConstants.TOP_LEVEL);
        modifyOrg.setParentIds(BizConstants.TOP_LEVEL);
      } else {
        SysOrg tmp = sysOrgService.getById(newParentId);
        if (ObjectHelper.isEmpty(tmp)) {
          throw new ApiException(OrgEnumCode.ORG_ID_NOT_EXISTENT.transform());
        }
        newParentIds = tmp.getParentIds() + FrameworkConstants.GLOBE_SPLIT_COMMA + tmp.getOrgId();
        modifyOrg.setParentIds(newParentIds);
      }
    }
    if (isStatusChanged || isParentOrgChanged) {
      // ?????????????????????
      List<SysOrg> childrenOrgList = sysOrgService.getChildrenById(modifyOrg.getOrgId());
      if (!ObjectHelper.isEmpty(childrenOrgList)) {
        if (isParentOrgChanged) {
          String finalNewParentIds = newParentIds;
          childrenOrgList =
              childrenOrgList.stream()
                  .map(
                      org ->
                          org.setParentIds(
                              org.getParentIds().replaceAll(oldParentIds, finalNewParentIds)))
                  .collect(Collectors.toList());
        }
        childrenOrgList =
            childrenOrgList.stream()
                .peek(org -> org.setStatus(updateRequest.getStatus()))
                .collect(Collectors.toList());
        // ????????????
        sysOrgService.updateBatchById(childrenOrgList);
      }
    }
    // ??????
    boolean flag = sysOrgService.updateById(modifyOrg);
    if (flag) {
      baseResponse.setData(modifyOrg);
    }
    return baseResponse;
  }

  /**
   * ??????Id????????????
   *
   * @param sysOrgDelRequest del request
   * @return BaseResponse<SysOrg>
   */
  @ApiOperation(value = "????????????ID????????????", notes = "????????????ID???????????? Delete Org")
  @Transactional(rollbackFor = Exception.class)
  @PutMapping(value = "/delete")
  public BaseResponse<BizGeneralResponse> deleteEntity(
      @RequestBody SysOrgDelRequest sysOrgDelRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();

    if (StringHelper.isEmpty(sysOrgDelRequest.getOrgId())) {
      throw new ApiException(OrgEnumCode.REQUIRED_ITEMS_MISSING.transform());
    }
    /* all org ids */
    QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
    List<String> orgList =
        sysOrgService.getChildrenById(sysOrgDelRequest.getOrgId()).stream()
            .map(SysOrg::getOrgId)
            .collect(Collectors.toList());
    if (orgList.isEmpty()) {
      userQueryWrapper.eq(SysUser.ORG_ID, sysOrgDelRequest.getOrgId());
    } else {
      orgList.add(sysOrgDelRequest.getOrgId());
      userQueryWrapper.in(SysUser.ORG_ID, orgList);
    }
    userQueryWrapper.ne(SysUser.STATUS, BizConstants.STATUS_DELETE);
    /* find if user exited */
    int userCounter = sysUserService.count(userQueryWrapper);
    /* if any user, deny delete */
    if (userCounter > 0) {
      throw new ApiException(OrgEnumCode.ORG_USER_IS_NOT_NULL.transform());
    }
    SysOrg data = sysOrgService.getById(sysOrgDelRequest.getOrgId());
    if (ObjectHelper.isEmpty(data)) {
      throw new ApiException(OrgEnumCode.ORG_ID_NOT_EXISTENT.transform());
    }
    data.setStatus(BizConstants.STATUS_DELETE);
    /* ???????????? */
    List<SysOrg> parentResourceList =
        sysOrgService.list(
            new QueryWrapper<SysOrg>().lambda().like(SysOrg::getParentIds, data.getOrgId()));
    sysOrgService.updateBatchById(
        parentResourceList.stream()
            .peek(org -> org.setStatus(BizConstants.STATUS_DELETE))
            .collect(Collectors.toList()));
    boolean result = sysOrgService.updateById(data);
    baseResponse.setData(
        BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    return baseResponse;
  }

  /**
   * ??????????????????
   *
   * @return BaseResponse<List < SysOrg>>
   */
  @ApiOperation(value = "???????????????????????? TREE-INFOs", notes = "???????????????????????? Get Orgs For Tree")
  @GetMapping(value = "/list")
  public BaseResponse<List<SysOrg>> listEntity() {
    BaseResponse<List<SysOrg>> baseResponse = new BaseResponse<>();
    baseResponse.setData(
        sysOrgService.list(
            new QueryWrapper<SysOrg>()
                .select(
                    SysOrg.ORG_ID,
                    SysOrg.PARENT_ID,
                    SysOrg.PARENT_IDS,
                    SysOrg.TREE_LEVEL,
                    SysOrg.TREE_SORT,
                    SysOrg.ORG_NAME,
                    SysOrg.ORG_CODE,
                    SysOrg.STATUS)
                .ne(SysOrg.STATUS, BizConstants.STATUS_DELETE)
                .orderBy(true, true, SysOrg.TREE_SORT)));
    return baseResponse;
  }

  /**
   * ???????????????
   *
   * @return BaseResponse<List < SysOrg>>
   */
  @ApiOperation(value = "???????????????????????? ID+NAME", notes = "?????????????????????")
  @GetMapping(value = "/tree")
  public BaseResponse<List<SysOrg>> treeEntity() {
    BaseResponse<List<SysOrg>> baseResponse = new BaseResponse<>();
    baseResponse.setData(
        sysOrgService.list(
            new QueryWrapper<SysOrg>()
                .select(SysOrg.ORG_ID, SysOrg.ORG_NAME)
                .ne(SysOrg.STATUS, BizConstants.STATUS_DELETE)
                .orderBy(true, true, SysOrg.TREE_SORT)));
    return baseResponse;
  }

  /**
   * ???????????????
   *
   * @return BaseResponse<List < SysOrg>>
   */
  @ApiOperation(value = "???????????????????????? SELF+SUB", notes = "????????????ID??????????????????")
  @GetMapping(value = "/children")
  public BaseResponse<List<SysOrg>> childrenEntity(String orgId) {
    BaseResponse<List<SysOrg>> baseResponse = new BaseResponse<>();
    baseResponse.setData(sysOrgService.getChildrenById(orgId));
    return baseResponse;
  }

  /**
   * ?????????????????????Id????????????????????????
   *
   * @param param SysOrgParam
   * @return BaseResponse<BaseResponseList < SysOrg>>
   */
  @ApiOperation(value = "?????????????????????Id????????????????????????", notes = "?????????????????????Id????????????????????????")
  @GetMapping(value = "/sub_list")
  public BaseResponse<BaseResponseList<SysOrg>> childrenEntity(SysOrgParam param) {
    BaseResponse<BaseResponseList<SysOrg>> baseResponse = new BaseResponse<>();
    Long page =
        StringHelper.isEmpty(param.getPage()) ? BizConstants.PAGE : Long.valueOf(param.getPage());
    Long limit =
        StringHelper.isEmpty(param.getLimit())
            ? BizConstants.LIMIT
            : Long.valueOf(param.getLimit());
    Page<SysOrg> resultPage = new Page<>(page, limit);
    QueryWrapper<SysOrg> queryWrapper = orgIdsEntity(param);
    Page<SysOrg> resultList = sysOrgService.page(resultPage, queryWrapper);
    BaseResponseList<SysOrg> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(resultList.getRecords());
    baseResponseList.setTotal(sysOrgService.count(queryWrapper) + "");
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  /**
   * ????????????????????????ID
   *
   * @param param SysOrgParam
   * @return List<String>
   */
  public QueryWrapper<SysOrg> orgIdsEntity(SysOrgParam param) {
    QueryWrapper<SysOrg> queryWrapper = new QueryWrapper<>();
    if (!StringHelper.isEmpty(param.getOrgId())) {
      queryWrapper.and(
          i ->
              i.eq(SysOrg.ORG_ID, param.getOrgId()).or().like(SysOrg.PARENT_IDS, param.getOrgId()));
    }
    if (!StringHelper.isEmpty(param.getStatus())) {
      queryWrapper.eq(SysOrg.STATUS, param.getStatus());
    } else {
      queryWrapper.ne(SysOrg.STATUS, BizConstants.STATUS_DELETE);
    }
    if (!StringHelper.isEmpty(param.getOrgName())
        || !StringHelper.isEmpty(param.getLeader())
        || !StringHelper.isEmpty(param.getOrgCode())
        || !StringHelper.isEmpty(param.getPhoneNo())) {
      if (!StringHelper.isEmpty(param.getOrgName())) {
        queryWrapper.like(SysOrg.ORG_NAME, param.getOrgName());
      }
      if (!StringHelper.isEmpty(param.getLeader())) {
        queryWrapper.like(SysOrg.LEADER, param.getLeader());
      }
      if (!StringHelper.isEmpty(param.getOrgCode())) {
        queryWrapper.like(SysOrg.ORG_CODE, param.getOrgCode());
      }
      if (!StringHelper.isEmpty(param.getPhoneNo())) {
        queryWrapper.like(SysOrg.PHONE_NO, param.getPhoneNo());
      }
    } else if (!StringHelper.isEmpty(param.getKeyword())) {
      queryWrapper.and(
          i ->
              i.like(SysOrg.ORG_NAME, param.getKeyword())
                  .or()
                  .like(SysOrg.LEADER, param.getKeyword()));
    }
    if (!StringHelper.isEmpty(param.getOrderBy())) {
      if (!StringHelper.isEmpty(param.getOrderType())
          && BizConstants.ASC.equals(param.getOrderType())) {
        queryWrapper.orderByAsc(param.getOrderBy());
      } else {
        queryWrapper.orderByDesc(param.getOrderBy());
      }
    } else {
      queryWrapper.orderByAsc(SysOrg.PARENT_IDS, SysOrg.TREE_SORT);
    }
    return queryWrapper;
  }

  /**
   * ??????????????????Id????????????????????????
   *
   * @param orgId orgId
   * @return BaseResponse<SysOrg>
   */
  @ApiOperation(value = "????????????ID????????????", notes = "????????????ID????????????")
  @GetMapping(value = "/get")
  public BaseResponse<Map<String, Object>> getById(String orgId) {
    BaseResponse<Map<String, Object>> baseResponse = new BaseResponse<>();
    if (StringHelper.isEmpty(orgId)) {
      throw new ApiException(OrgEnumCode.REQUIRED_ITEMS_MISSING.transform());
    }
    SysOrg sysOrg = sysOrgService.getById(orgId);
    if (ObjectHelper.isEmpty(sysOrg)) {
      throw new ApiException(OrgEnumCode.ORG_ID_NOT_EXISTENT.transform());
    }
    Map<String, Object> data = BeanHelper.beanToMap(sysOrg);
    if (!StringHelper.isEmpty(sysOrg.getParentId())
        && !BizConstants.TOP_LEVEL.equals(sysOrg.getParentId())) {
      SysOrg parentOrg = sysOrgService.getById(sysOrg.getParentId());
      data.put(OrgConstants.PARENT_ORG_NAME_PARAMS, parentOrg.getOrgName());
    } else {
      data.put(OrgConstants.PARENT_ORG_NAME_PARAMS, "");
    }
    baseResponse.setData(data);
    return baseResponse;
  }

  /**
   * upload ????????????
   *
   * @author Created by ivan at ??????9:19 2020/7/29.
   * @return
   *     com.tongtech.backend.apis.common.models.BaseResponse<com.tongtech.backend.apis.biz.sys.user.model.dto.UserImportResponse>
   */
  @ApiOperation(value = "excel??????", notes = "excel??????,Upload File")
  @PostMapping("/upload")
  public BaseResponse<OrgImportResponse> upload(@RequestParam MultipartFile file) {
    BaseResponse<OrgImportResponse> baseResponse = new BaseResponse<>();
    OrgImportResponse userImportResponse = new OrgImportResponse();
    if (file.isEmpty()) {
      throw new ApiException(ExcelEnum.NO_EXPORT_FILE.transform());
    }
    String filepath = excelService.importExcel(file);
    TongExcelListener<OrgImport> tongExcelListener =
        new TongExcelListener<>(orgExcelImport, OrgImport.class);

    EasyExcel.read(filepath, OrgImport.class, tongExcelListener).sheet().doRead();
    List<ImportError<OrgImport>> errorList = tongExcelListener.getErrorList();
    userImportResponse.setErrors(errorList.size() > 19 ? errorList.subList(0, 19) : errorList);
    userImportResponse.setErrorTotal(errorList.size());
    userImportResponse.setSuccessTotal(tongExcelListener.getSuccessList().size());
    userImportResponse.setBeans(ExcelDtoHelper.getCols(new OrgImport()));
    baseResponse.setData(userImportResponse);
    return baseResponse;
  }
}
