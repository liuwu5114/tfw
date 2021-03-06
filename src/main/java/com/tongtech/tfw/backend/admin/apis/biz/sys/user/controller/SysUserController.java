package com.tongtech.tfw.backend.admin.apis.biz.sys.user.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tongtech.tfw.backend.admin.apis.biz.excel.ExcelEnum;
import com.tongtech.tfw.backend.admin.apis.biz.excel.model.ImportError;
import com.tongtech.tfw.backend.admin.apis.biz.excel.service.ExcelService;
import com.tongtech.tfw.backend.admin.apis.biz.excel.service.TongExcelListener;
import com.tongtech.tfw.backend.admin.apis.biz.excel.support.ExcelDtoHelper;
import com.tongtech.tfw.backend.admin.apis.biz.file.FileEnumCode;
import com.tongtech.tfw.backend.admin.apis.biz.file.model.domain.TSysFileBiz;
import com.tongtech.tfw.backend.admin.apis.biz.file.service.TSysFileBizService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.model.domain.SysOrg;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.service.SysOrgService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.position.model.domain.TSysJobPosition;
import com.tongtech.tfw.backend.admin.apis.biz.sys.position.service.TSysJobPositionService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.role.model.domain.SysRole;
import com.tongtech.tfw.backend.admin.apis.biz.sys.role.service.SysRoleService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.UserConstants;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.UserEnumCode;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.UserHelper;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.domain.SysUser;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.dto.*;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.service.SysUserService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.service.UserExcelImportService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.userRole.model.domain.SysUserRole;
import com.tongtech.tfw.backend.admin.apis.biz.sys.userRole.service.SysUserRoleService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.userposition.model.domain.TSysUserPosition;
import com.tongtech.tfw.backend.admin.apis.biz.sys.userposition.service.TSysUserPositionService;
import com.tongtech.tfw.backend.common.biz.constants.BizConstants;
import com.tongtech.tfw.backend.common.biz.models.BaseResponse;
import com.tongtech.tfw.backend.common.biz.models.BaseResponseList;
import com.tongtech.tfw.backend.common.biz.models.BizGeneralResponse;
import com.tongtech.tfw.backend.common.models.exceptions.ApiException;
import com.tongtech.tfw.backend.common.models.supers.SuperController;
import com.tongtech.tfw.backend.core.helper.IdHelper;
import com.tongtech.tfw.backend.core.helper.ObjectHelper;
import com.tongtech.tfw.backend.core.helper.StringHelper;
import com.tongtech.tfw.backend.core.helper.TypeHelper;
import com.tongtech.tfw.backend.core.helper.bean.BeanHelper;
import com.tongtech.tfw.backend.core.helper.datetime.DatetimeHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ????????? ???????????????
 *
 * @author Ivan
 * @since 2020-03-31
 */
@RestController
@Api(value = "SysUser", tags = "SysUser ??????")
@Slf4j
@RequestMapping("/user")
public class SysUserController extends SuperController {

  /** SysUserService */
  @Autowired private SysUserService sysUserService;

  /** SysOrgService */
  @Autowired private SysOrgService sysOrgService;

  /** SysUserRoleService */
  @Autowired private SysUserRoleService sysUserRoleService;

  /** SysRoleService */
  @Autowired private SysRoleService sysRoleService;

  /** SysUserPosition */
  @Autowired private TSysUserPositionService tSysUserPositionService;

  /** SysJobPosition */
  @Autowired private TSysJobPositionService tSysJobPositionService;

  /** TSysFileBizService */
  @Autowired private TSysFileBizService tSysFileBizService;

  /** Excel Service */
  @Autowired private ExcelService excelService;

  /** User Import Service */
  @Autowired UserExcelImportService userExcelImportService;

  /**
   * ????????????????????????
   *
   * @param param SysUserParam
   * @return BaseResponse<BaseResponseList < SysUserRequest>>
   */
  @ApiOperation(value = "????????????????????????", notes = "???????????????????????? List User")
  @GetMapping(value = "/list")
  public BaseResponse<BaseResponseList<SysUserListResponse>> listEntity(@Valid SysUserParam param) {
    BaseResponse<BaseResponseList<SysUserListResponse>> baseResponse = new BaseResponse<>();
    // ????????????ID????????????
    List<SysOrg> sysOrgList;
    if (StringHelper.isEmpty(param.getOrgId())) {
      sysOrgList =
          sysOrgService.list(new QueryWrapper<SysOrg>().select(SysOrg.ORG_ID, SysOrg.ORG_NAME));
    } else {
      sysOrgList = sysOrgService.getChildrenById(param.getOrgId());
    }
    // ????????????????????????
    Long page =
        StringHelper.isEmpty(param.getPage()) ? BizConstants.PAGE : Long.valueOf(param.getPage());
    Long limit =
        StringHelper.isEmpty(param.getLimit())
            ? BizConstants.LIMIT
            : Long.valueOf(param.getLimit());
    Page<SysUser> resultPage = new Page<>(page, limit);
    QueryWrapper<SysUser> queryWrapper = createQuery(param);
    Page<SysUser> resultList = sysUserService.page(resultPage, queryWrapper);
    // ???????????????
    List<SysOrg> finalSysOrgList = sysOrgList;
    List<SysUserListResponse> records =
        resultList.getRecords().stream()
            .map(bean -> bean.beanToBean(SysUserListResponse.class))
            .peek(
                bean ->
                    finalSysOrgList.stream()
                        .filter(sysOrg -> bean.getOrgId().equals(sysOrg.getOrgId()))
                        .forEach(sysOrg -> bean.setOrgName(sysOrg.getOrgName())))
            .collect(Collectors.toList());
    BaseResponseList<SysUserListResponse> baseResponseList = new BaseResponseList<>();
    baseResponseList.setData(records);
    baseResponseList.setTotal(resultList.getTotal());
    baseResponse.setData(baseResponseList);
    return baseResponse;
  }

  /**
   * ????????????????????????
   *
   * @param addRequest SysUserCreateAndUpdateRequest
   * @return BaseResponse<SysUser>
   */
  @ApiOperation(value = "????????????????????????", notes = "???????????????????????? Add user")
  @PostMapping(value = "/add")
  public BaseResponse<SysUser> addEntity(
      @Valid @RequestBody SysUserCreateAndUpdateRequest addRequest) {
    BaseResponse<SysUser> baseResponse = new BaseResponse<>();
    if (ObjectHelper.isEmpty(addRequest)) {
      throw new ApiException(UserEnumCode.USER_PARAM_IS_NULL.transform());
    }
    if (StringHelper.isEmpty(addRequest.getLoginPwd())) {
      throw new ApiException(UserEnumCode.REQUIRED_ITEMS_MISSING.transform());
    }
    if (!StringHelper.isEmpty(addRequest.getOrgId())
        && !ObjectHelper.isEmpty(sysOrgService.getById(addRequest.getOrgId()))) {
      SysUser existsSysUser =
          sysUserService.getOne(
              new QueryWrapper<SysUser>()
                  .eq(SysUser.LOGIN_NAME, addRequest.getLoginName())
                  .ne(SysUser.STATUS, BizConstants.STATUS_DELETE)
                  .last("LIMIT 1"));
      if (ObjectHelper.isNotEmpty(existsSysUser)) {
        throw new ApiException(UserEnumCode.LOGIN_NAME_ALREADY_EXISTS.transform());
      }
      String userId = IdHelper.getId32bit();
      // ????????????
      if (ObjectHelper.isNotEmpty(addRequest.getFiles()) && !addRequest.getFiles().isEmpty()) {
        List<TSysFileBiz> fileBizList = addRequest.getFiles();
        boolean fileBizResult = tSysFileBizService.saveFileBizs(fileBizList, userId, getUserId());
        if (!fileBizList.isEmpty() && !fileBizResult) {
          throw new ApiException(FileEnumCode.FILE_BIZ_SAVE_FAILED.transform());
        }
      }
      // ??????????????????end
      SysUser data = BeanHelper.beanToBean(addRequest, SysUser.class);
      if (StringHelper.isEmpty(data.getLoginPwd())) {
        data.setLoginPwd(UserConstants.USER_PASSWORD);
      }

      data.setLoginPwd(UserHelper.userPassword(data.getLoginPwd()));
      data.setUserId(userId);
      // ??????????????????
      sysUserService.save(data);
    } else {
      throw new ApiException(UserEnumCode.USER_ORG_ID_ERROR.transform());
    }
    return baseResponse;
  }

  /**
   * ????????????????????????
   *
   * @param updateRequest SysUserCreateAndUpdateRequest
   * @return BaseResponse<SysUser>
   */
  @ApiOperation(value = "????????????????????????", notes = "???????????????????????? edit user")
  @PutMapping(value = "/update")
  public BaseResponse<SysUser> updateEntity(
      @Valid @RequestBody SysUserCreateAndUpdateRequest updateRequest) {
    BaseResponse<SysUser> baseResponse = new BaseResponse<>();
    if (ObjectHelper.isEmpty(updateRequest)) {
      throw new ApiException(UserEnumCode.USER_PARAM_IS_NULL.transform());
    }
    if (StringHelper.isEmpty(updateRequest.getUserId())) {
      throw new ApiException(UserEnumCode.USER_ID_ERROR.transform());
    }
    if (!StringHelper.isEmpty(updateRequest.getOrgId())
        && ObjectHelper.isNotEmpty(sysOrgService.getById(updateRequest.getOrgId()))) {
      SysUser newUser = BeanHelper.beanToBean(updateRequest, SysUser.class);
      SysUser oldUser = sysUserService.getById(newUser.getUserId());
      if (!newUser.getLoginName().equals(oldUser.getLoginName())) {
        SysUser temp =
            sysUserService.getOne(
                new QueryWrapper<SysUser>()
                    .eq(SysUser.LOGIN_NAME, newUser.getLoginName())
                    .ne(SysUser.STATUS, BizConstants.STATUS_DELETE)
                    .last("LIMIT 1"));
        if (ObjectHelper.isNotEmpty(temp)) {
          throw new ApiException(UserEnumCode.LOGIN_NAME_ALREADY_EXISTS.transform());
        }
      }
      // ????????????????????????????????????????????????
      if (StringHelper.isEmpty(newUser.getLoginPwd())) {
        newUser.setLoginPwd(oldUser.getLoginPwd());
      } else {

        newUser.setLoginPwd(UserHelper.userPassword(newUser.getLoginPwd()));
      }
      // ????????????
      if (ObjectHelper.isNotEmpty(updateRequest.getFiles())
          && !updateRequest.getFiles().isEmpty()) {
        List<TSysFileBiz> fileBizList = updateRequest.getFiles();
        boolean fileBizResult =
            tSysFileBizService.saveFileBizs(fileBizList, updateRequest.getUserId(), getUserId());
        if (!fileBizList.isEmpty() && !fileBizResult) {
          throw new ApiException(FileEnumCode.FILE_BIZ_SAVE_FAILED.transform());
        }
      }
      // ??????????????????end
      // ??????????????????
      sysUserService.updateById(newUser);
    } else {
      throw new ApiException(UserEnumCode.USER_ORG_ID_ERROR.transform());
    }
    return baseResponse;
  }

  /**
   * ????????????ID??????????????????
   *
   * @param userId userId
   * @return BaseResponse<SysUserParam>
   */
  @ApiOperation(value = "????????????ID??????????????????", notes = "????????????ID?????????????????? get User By Id")
  @GetMapping(value = "/get")
  public BaseResponse<SysUserResponse> getEntity(String userId) {
    BaseResponse<SysUserResponse> baseResponse = new BaseResponse<>();
    if (StringHelper.isEmpty(userId)) {
      throw new ApiException(UserEnumCode.USER_ID_ERROR.transform());
    }
    SysUser user = sysUserService.getById(userId);
    if (ObjectHelper.isEmpty(user)) {
      throw new ApiException(UserEnumCode.USER_ID_ERROR.transform());
    }
    // ???????????????
    SysUserResponse sysUserResponse = BeanHelper.beanToBean(user, SysUserResponse.class);
    // Map<String, Object> data = BeanHelper.beanToMap(user);
    SysOrg org = sysOrgService.getById(user.getOrgId());
    if (!ObjectHelper.isEmpty(org)) {
      sysUserResponse.setOrgName(org.getOrgName());
    }
    baseResponse.setData(sysUserResponse);
    return baseResponse;
  }

  /**
   * ????????????ID??????????????????
   *
   * @param deleteRequest SysUserDeleteRequest
   * @return BaseResponse<SysUser>
   */
  @ApiOperation(value = "????????????ID??????????????????", notes = "????????????ID?????????????????? delete user by id")
  @Transactional(rollbackFor = Exception.class)
  @PutMapping(value = "/delete")
  public BaseResponse<SysUser> deleteEntity(
      @Valid @RequestBody SysUserDeleteRequest deleteRequest) {
    BaseResponse<SysUser> baseResponse = new BaseResponse<>();
    if (ObjectHelper.isEmpty(deleteRequest)) {
      throw new ApiException(UserEnumCode.REQUIRED_ITEMS_MISSING.transform());
    }
    SysUser sysUser = sysUserService.getById(deleteRequest.getUserId());
    if (ObjectHelper.isEmpty(sysUser)) {
      throw new ApiException(UserEnumCode.USER_ID_ERROR.transform());
    }
    // ????????????
    tSysFileBizService.remove(
        new QueryWrapper<TSysFileBiz>().eq(TSysFileBiz.BIZ_ID, deleteRequest.getUserId()));
    // ????????????end
    // ??????????????????
    sysUser.setStatus(BizConstants.STATUS_DELETE);
    sysUserService.updateById(sysUser);
    // ????????????????????????
    sysUserRoleService.remove(
        new QueryWrapper<SysUserRole>().eq(SysUserRole.USER_ID, sysUser.getUserId()));
    // ????????????????????????
    tSysUserPositionService.remove(
        new QueryWrapper<TSysUserPosition>().eq(TSysUserPosition.USER_ID, sysUser.getUserId()));
    return baseResponse;
  }

  /**
   * ????????????ID??????????????????????????????
   *
   * @param deleteBatchRequest deleteBatchRequest
   * @return BaseResponse<SysUser>
   */
  @ApiOperation(value = "????????????ID??????????????????????????????", notes = "????????????ID??????????????????????????????delete user by ids")
  @Transactional(rollbackFor = Exception.class)
  @PutMapping(value = "/batch_delete")
  public BaseResponse<SysUser> deleteEntities(
      @Valid @RequestBody SysUserDeleteBatchRequest deleteBatchRequest) {
    BaseResponse<SysUser> baseResponse = new BaseResponse<>();
    if (ObjectHelper.isEmpty(deleteBatchRequest)) {
      throw new ApiException(UserEnumCode.USER_ID_ERROR.transform());
    }
    List<String> ids = Arrays.stream(deleteBatchRequest.getUserId()).collect(Collectors.toList());
    Collection<SysUser> users =
        sysUserService.listByIds(ids).stream()
            .map(
                user ->
                    SysUser.builder()
                        .userId(user.getUserId())
                        .status(BizConstants.STATUS_DELETE)
                        .build())
            .collect(Collectors.toList());
    // ????????????????????????
    sysUserRoleService.remove(new QueryWrapper<SysUserRole>().in(SysUserRole.USER_ID, ids));
    // ????????????????????????
    tSysUserPositionService.remove(
        new QueryWrapper<TSysUserPosition>().in(TSysUserPosition.USER_ID, ids));
    // ??????????????????
    sysUserService.updateBatchById(users);
    return baseResponse;
  }

  /**
   * ????????????ID???????????????????????????????????????
   *
   * @param userId userId
   * @return BaseResponse<List < Map < String, Object>>>
   */
  @ApiOperation(value = "????????????ID???????????????????????????????????????", notes = "????????????ID??????????????????????????????????????? get roles by user")
  @GetMapping(value = "/show")
  public BaseResponse<List<Map<String, Object>>> showEntities(String userId) {
    BaseResponse<List<Map<String, Object>>> baseResponse = new BaseResponse<>();
    if (StringHelper.isEmpty(userId)) {
      throw new ApiException(UserEnumCode.USER_ID_ERROR.transform());
    }
    // ???????????????
    List<SysRole> roles =
        sysRoleService.list(
            new QueryWrapper<SysRole>().ne(true, SysRole.STATUS, BizConstants.STATUS_DELETE));
    // ????????????????????????
    List<SysUserRole> checkedRoles =
        sysUserRoleService.list(new QueryWrapper<SysUserRole>().eq(SysUserRole.USER_ID, userId));
    // ????????????????????????ID
    List<String> checkedRoleIds =
        checkedRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
    // ??????????????????
    List<Map<String, Object>> data = BeanHelper.beansToMaps(roles);
    for (Map<String, Object> role : data) {
      if (checkedRoleIds.contains(String.valueOf(role.get("roleId")))) {
        role.put("isChecked", true);
      } else {
        role.put("isChecked", false);
      }
    }
    baseResponse.setData(data);
    return baseResponse;
  }

  @ApiOperation(value = "?????????????????????", notes = "????????????????????? assign role to user")
  @Transactional(rollbackFor = Exception.class)
  @PostMapping(value = "/assign")
  public BaseResponse<SysUser> assignEntity(@RequestBody SysUserRoleRequest assignRequest) {
    BaseResponse<SysUser> baseResponse = new BaseResponse<>();
    // ?????????????????????????????????????????????
    sysUserRoleService.remove(
        new QueryWrapper<SysUserRole>().eq(SysUserRole.USER_ID, assignRequest.getUserId()));
    // ?????????????????????????????????
    if (ObjectHelper.isNotEmpty(assignRequest.getRoleId())) {
      ArrayList<SysUserRole> userRoles = new ArrayList<>(16);
      for (String roleId : assignRequest.getRoleId()) {
        userRoles.add(new SysUserRole().setUserId(assignRequest.getUserId()).setRoleId(roleId));
      }
      sysUserRoleService.saveBatch(userRoles);
    }
    return baseResponse;
  }

  @ApiOperation(value = "????????????ID???????????????????????????????????????", notes = "????????????ID??????????????????????????????????????? get positions by user")
  @GetMapping(value = "/showPosition")
  public BaseResponse<List<Map<String, Object>>> showPosition(String userId) {
    BaseResponse<List<Map<String, Object>>> baseResponse = new BaseResponse<>();
    if (StringHelper.isEmpty(userId)) {
      throw new ApiException(UserEnumCode.USER_ID_ERROR.transform());
    }
    // ???????????????
    List<TSysJobPosition> positions =
        tSysJobPositionService.list(
            new QueryWrapper<TSysJobPosition>()
                .ne(true, TSysJobPosition.STATUS, BizConstants.STATUS_DELETE));
    // ?????????????????????
    List<TSysUserPosition> checkedPositions =
        tSysUserPositionService.list(
            new QueryWrapper<TSysUserPosition>().eq(TSysUserPosition.USER_ID, userId));
    // ????????????????????????ID
    List<String> checkedRoleIds =
        checkedPositions.stream().map(TSysUserPosition::getPositionId).collect(Collectors.toList());
    // ??????????????????
    List<Map<String, Object>> data = BeanHelper.beansToMaps(positions);
    for (Map<String, Object> position : data) {
      if (checkedRoleIds.contains(String.valueOf(position.get("positionId")))) {
        position.put("isChecked", true);
      } else {
        position.put("isChecked", false);
      }
    }
    baseResponse.setData(data);
    return baseResponse;
  }

  /**
   * ?????????????????????
   *
   * @param assignRequest SysUserRoleRequest
   * @return BaseResponse<SysUser>
   */
  @ApiOperation(value = "?????????????????????", notes = "????????????????????? assign position to user")
  @Transactional(rollbackFor = Exception.class)
  @PostMapping(value = "/assignPosition")
  public BaseResponse<SysUser> assignPosition(@RequestBody SysUserPositionRequest assignRequest) {
    BaseResponse<SysUser> baseResponse = new BaseResponse<>();
    // ?????????????????????????????????????????????
    tSysUserPositionService.remove(
        new QueryWrapper<TSysUserPosition>()
            .eq(TSysUserPosition.USER_ID, assignRequest.getUserId()));
    // ?????????????????????????????????
    ArrayList<TSysUserPosition> userPositions = new ArrayList<>(16);
    if (ObjectHelper.isNotEmpty(assignRequest.getPositionId())) {
      for (String positionId : assignRequest.getPositionId()) {
        userPositions.add(
            TSysUserPosition.builder()
                .id(IdHelper.getId32bit())
                .positionId(positionId)
                .userId(assignRequest.getUserId())
                .build());
      }
      tSysUserPositionService.saveBatch(userPositions);
    }

    return baseResponse;
  }

  /**
   * ??????????????????
   *
   * @param pwdRequest SysUserChangePwdRequest
   * @return BaseResponse<SysUser>
   */
  @ApiOperation(value = "??????????????????", notes = "?????????????????? edit password")
  @PutMapping(value = "/password/update")
  public BaseResponse<SysUser> passwordEntity(
      @Valid @RequestBody SysUserChangePwdRequest pwdRequest) {
    BaseResponse<SysUser> baseResponse = new BaseResponse<>();
    SysUser user = sysUserService.getById(pwdRequest.getUserId());

    String oldPassword = UserHelper.userPassword(pwdRequest.getOldLoginPwd());
    String newPassword = UserHelper.userPassword(pwdRequest.getNewLoginPwd());
    if (ObjectHelper.isEmpty(user)) {
      throw new ApiException(UserEnumCode.USER_ID_ERROR.transform());
    }
    if (!oldPassword.equals(user.getLoginPwd())) {
      throw new ApiException(UserEnumCode.USER_PWD_MISMATCH.transform());
    }
    user.setLoginPwd(newPassword);
    sysUserService.updateById(user);
    return baseResponse;
  }

  /**
   * ??????????????????
   *
   * @param pwdRequest SysUserChangePwdRequest
   * @return BaseResponse<SysUser>
   */
  @ApiOperation(value = "??????????????????", notes = "?????????????????? reset password")
  @PutMapping(value = "/password/reset")
  public BaseResponse<BizGeneralResponse> passwordResetEntity(
      @RequestBody SysUserChangePwdRequest pwdRequest) {
    BaseResponse<BizGeneralResponse> baseResponse = new BaseResponse<>();
    SysUser user = sysUserService.getById(pwdRequest.getUserId());
    boolean result = false;
    // md5
    if (ObjectHelper.isNotEmpty(user)) {
      user.setLoginPwd(UserHelper.userPassword(UserConstants.USER_PASSWORD));
      result = sysUserService.updateById(user);
    }
    baseResponse.setData(
        BizGeneralResponse.builder().result(TypeHelper.castToString(result)).build());
    return baseResponse;
  }

  @ApiOperation(value = "????????????", notes = "???????????? export user data")
  @GetMapping(value = "/export")
  public void exportAllEntity(
      SysUserParam params,
      @RequestParam(value = "cols", required = false) List<String> cols,
      @RequestParam(value = "ids", required = false) List<String> ids,
      HttpServletResponse response) {
    SysUserParam param = ObjectHelper.defaultIfNull(params, new SysUserParam());

    if (StringHelper.isNotBlank(param.getOrgId())) {
      param.setOrgId(sysUserService.getById(this.getUserId()).getOrgId());
    }
    QueryWrapper<SysUser> queryWrapper = createQuery(param);
    if (ObjectHelper.isNotEmpty(ids) && !ids.isEmpty()) {
      queryWrapper.in(SysUser.USER_ID, ids);
    }
    List<SysUser> resultList = sysUserService.list(queryWrapper);
    // ???????????????
    List<SysUserListResponse> records =
        resultList.stream()
            .map(bean -> bean.beanToBean(SysUserListResponse.class))
            .collect(Collectors.toList());
    List<UserExport> userExports =
        records.stream()
            .map(e -> BeanHelper.beanToBean(e, UserExport.class))
            .peek(UserExport::convertSex)
            // .peek(UserExport::setOrgName)
            .collect(Collectors.toList());
    for (UserExport userExport : userExports) {
      userExport.setOrgName(sysOrgService.getOrgFullPathName(userExport.getOrgId()));
    }
    try {
      excelService.export(
          "??????????????????-" + DatetimeHelper.getDateTime8Length(),
          userExports,
          UserExport.class,
          response,
          cols);
    } catch (IOException e) {
      log.error("Export Error ", e);
      throw new ApiException(ExcelEnum.EXPORT_ERROR.transform());
    }
  }

  /**
   * upload ????????????
   *
   * @author Created by ivan at ??????9:19 2020/7/29.
   * @return
   *     com.tongtech.backend.apis.common.models.BaseResponse<com.tongtech.backend.apis.biz.sys.user.model.dto.UserImportResponse>
   */
  @ApiOperation(value = "????????????", notes = "???????????? Upload EXCEL File")
  @PostMapping("/upload")
  public BaseResponse<UserImportResponse> upload(@RequestParam MultipartFile file) {
    BaseResponse<UserImportResponse> baseResponse = new BaseResponse<>();
    UserImportResponse userImportResponse = new UserImportResponse();
    if (file.isEmpty()) {
      throw new ApiException(ExcelEnum.NO_EXPORT_FILE.transform());
    }
    String filepath = excelService.importExcel(file);
    TongExcelListener<UserImport> tongExcelListener =
        new TongExcelListener<>(userExcelImportService, UserImport.class);

    EasyExcel.read(filepath, UserImport.class, tongExcelListener).sheet().doRead();
    List<ImportError<UserImport>> errorList = tongExcelListener.getErrorList();
    String fileName = saveErrorHistory(errorList);
    userImportResponse.setErrors(errorList.size() > 19 ? errorList.subList(0, 19) : errorList);
    userImportResponse.setErrorTotal(errorList.size());
    userImportResponse.setSuccessTotal(tongExcelListener.getSuccessList().size());
    userImportResponse.setBeans(ExcelDtoHelper.getCols(new UserImport()));
    userImportResponse.setFileName(fileName);
    baseResponse.setData(userImportResponse);
    return baseResponse;
  }

  /**
   * saveErrorHistory ????????????????????????
   *
   * @author Created by ivan at ??????9:19 2020/7/29.
   * @return java.lang.String
   */
  private String saveErrorHistory(List<ImportError<UserImport>> data) {
    String fileName = "";
    if (!data.isEmpty()) {
      List<UserImportError> errors = new ArrayList<>(data.size());
      data.forEach(
          importError -> {
            UserImportError userImportError =
                BeanHelper.beanToBean(importError.getData(), UserImportError.class);
            userImportError.setErrMsg(importError.getErrorMsg());
            errors.add(userImportError);
          });
      try {
        fileName = excelService.errorHistory(errors, UserImportError.class);
      } catch (FileNotFoundException e) {
        throw new ApiException(UserEnumCode.USER_IMPORT_ERROR_SAVE_FAIL.transform());
      }
    }
    return fileName;
  }

  /**
   * ??????
   *
   * @param queryParam queryParam
   * @return QueryWrapper<SysUser>
   */
  private QueryWrapper<SysUser> createQuery(SysUserParam queryParam) {
    QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
    // ????????????????????????????????????
    if (!StringHelper.isEmpty(queryParam.getLoginName())) {
      queryWrapper.like(SysUser.LOGIN_NAME, queryParam.getLoginName());
    }
    // ????????????????????????????????????
    if (!StringHelper.isEmpty(queryParam.getUserName())) {
      queryWrapper.like(SysUser.USER_NAME, queryParam.getUserName());
    }
    // ?????????????????????
    if (!StringHelper.isEmpty(queryParam.getPhoneNo())) {
      queryWrapper.like(SysUser.PHONE_NO, queryParam.getPhoneNo());
    }
    // ????????????
    if (!StringHelper.isEmpty(queryParam.getUserType())) {
      queryWrapper.eq(SysUser.USER_TYPE, queryParam.getUserType());
    }
    // ????????????ID???????????????
    if (!StringHelper.isEmpty(queryParam.getOrgId())) {
      List<String> orgList =
          sysOrgService.getChildrenById(queryParam.getOrgId()).stream()
              .map(SysOrg::getOrgId)
              .collect(Collectors.toList());
      if (orgList.isEmpty()) {
        queryWrapper.eq(SysUser.ORG_ID, queryParam.getOrgId());
      } else {
        orgList.add(queryParam.getOrgId());
        queryWrapper.in(SysUser.ORG_ID, orgList);
      }
    }
    // ?????????????????????????????????????????????????????????
    if (StringHelper.isEmpty(queryParam.getStatus())) {
      queryWrapper.in(
          SysUser.STATUS, Arrays.asList(BizConstants.STATUS_ENABLE, BizConstants.STATUS_DISABLE));
    } else {
      if (!BizConstants.STATUS_DELETE.equals(queryParam.getStatus())) {
        queryWrapper.eq(SysUser.STATUS, queryParam.getStatus());
      }
    }
    // ???????????????????????????????????????
    if (!StringHelper.isEmpty(queryParam.getOrderBy())) {
      if (!StringHelper.isEmpty(queryParam.getOrderType())
          && BizConstants.ASC.equals(queryParam.getOrderType())) {
        queryWrapper.orderByAsc(queryParam.getOrderBy());
      } else {
        queryWrapper.orderByDesc(queryParam.getOrderBy());
      }
    } else {
      queryWrapper.orderByDesc(SysUser.USER_NAME);
    }
    return queryWrapper;
  }
}
