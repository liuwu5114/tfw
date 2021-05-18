package com.tongtech.biz.portal.tinymce.controller;

import com.tongtech.biz.portal.tinymce.model.dto.TinyUploadDTO;
import com.tongtech.tfw.backend.admin.apis.biz.file.FileEnumCode;
import com.tongtech.tfw.backend.common.biz.models.BaseResponse;
import com.tongtech.tfw.backend.common.models.exceptions.ApiException;
import com.tongtech.tfw.backend.common.models.supers.SuperController;
import com.tongtech.tfw.backend.core.constants.FrameworkConstants;
import com.tongtech.tfw.backend.core.helper.IdHelper;
import com.tongtech.tfw.backend.core.helper.datetime.DatetimeHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TineMCEController
 *
 * @author Created by ivan on 2020/8/31 .
 * @version 1.0
 */
@RestController
@Api(value = "TinyMCE Upload Controller", tags = "TinyMCE ")
@Slf4j
@RequestMapping("/tinyMce")
public class TinyMCEController extends SuperController {

  @Value("${tiny.file.path}")
  private String fileRootPath;

  @Value("${tiny.file.url}")
  private String fileRootUrl;

  private static final String PATH_SLASH = File.separator;
  /* Methods */
  @ApiOperation(value = "上传文件", notes = "Upload File")
  @PostMapping("/upload")
  public BaseResponse<TinyUploadDTO> upload(@RequestParam MultipartFile file) {
    BaseResponse<TinyUploadDTO> baseResponse = new BaseResponse<>();
    if (file.isEmpty()) {
      throw new ApiException(FileEnumCode.NOT_FOUND.transform());
    }
    String fileName = file.getOriginalFilename();
    String ext = fileName.substring(fileName.indexOf(FrameworkConstants.GLOBE_SPLIT_DOT) + 1);
    String filePath = DatetimeHelper.getDateTime8Length();
    if (isPic(fileName)) {
      filePath = filePath + PATH_SLASH + "pic";
    } else if (isVideo(fileName)) {
      filePath = filePath + PATH_SLASH + "video";
    } else {
      filePath = filePath + PATH_SLASH + "file";
    }

    String saveName = IdHelper.getId32bit();
    try {
      // SAVE FILE
      File path = new File(fileRootPath + filePath);
      if (!path.exists()) {
        path.mkdirs();
      }
      File dest =
          new File(
              new File(fileRootPath + filePath).getAbsolutePath()
                  + PATH_SLASH
                  + saveName
                  + FrameworkConstants.GLOBE_SPLIT_DOT
                  + ext);
      FileCopyUtils.copy(file.getBytes(), dest);
      log.info(
          "保存成功：FileId："
              + saveName
              + ", ext: "
              + ext
              + ", path:"
              + filePath
              + ", name:"
              + fileName
              + ", user: "
              + getUserId());
      TinyUploadDTO newEntity =
          TinyUploadDTO.builder()
              .fileName(fileName)
              .fileUrl(
                  fileRootUrl
                      + filePath
                      + PATH_SLASH
                      + saveName
                      + FrameworkConstants.GLOBE_SPLIT_DOT
                      + ext)
              .build();
      baseResponse.setData(newEntity);
    } catch (IOException e) {
      log.error(e.toString(), e);
      throw new ApiException(FileEnumCode.IO_FAILD.transform());
    }
    return baseResponse;
  }

  private boolean isPic(String fileName) {
    String reg = ".+(.jpeg|.jpg|.png|.gif|.tiff|.bmp|.dwg|.psd)$";
    Pattern pattern = Pattern.compile(reg);
    Matcher matcher = pattern.matcher(fileName.toLowerCase());
    return matcher.find();
  }

  private boolean isVideo(String fileName) {
    String reg = ".+(.wav|.avi|.ram|.rm|.mpg|.mov|.asf|.mp4)$";
    Pattern pattern = Pattern.compile(reg);
    Matcher matcher = pattern.matcher(fileName.toLowerCase());
    return matcher.find();
  }
}
