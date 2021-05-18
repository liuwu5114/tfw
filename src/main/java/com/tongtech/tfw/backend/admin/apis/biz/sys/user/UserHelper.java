package com.tongtech.tfw.backend.admin.apis.biz.sys.user;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * UserHelper
 *
 * <p>//User Helper Tool
 *
 * @author ivan
 * @version 1.0 Created by ivan at 12/28/20.
 */
public class UserHelper {

  public static String userPassword(String password) {
    // TODO User Password MD5
    return DigestUtils.md5Hex(password);
  }
}
