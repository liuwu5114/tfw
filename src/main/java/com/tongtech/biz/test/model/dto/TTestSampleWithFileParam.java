package com.tongtech.biz.test.model.dto;

import com.tongtech.biz.test.model.domain.TTestSample;
import com.tongtech.tfw.backend.admin.apis.biz.file.model.domain.TSysFileBiz;
import lombok.Data;

import java.util.List;

/**
 * TTestSampleWithFileParam
 *
 * @author Created by ivan on 2020/6/22 .
 * @version 1.0
 */
@Data
public class TTestSampleWithFileParam extends TTestSample {
  private static final long serialVersionUID = -8893805409792496166L;
  List<TSysFileBiz> files;
}
