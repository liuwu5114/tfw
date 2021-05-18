package com.tongtech.biz.multitables;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.domain.SysUser;
import com.tongtech.tfw.backend.common.models.supers.SuperDao;
import org.apache.ibatis.annotations.Select;
/**
 * 用户 Mapper 接口
 *
 * @author Ivan
 * @since 2021-03-30
 */
public interface MultiTableDao extends SuperDao<SysUser> {
  /**
   * 自定义多表关联分页查询SQL
   *
   * @author Created by Ivan at 2021/4/1.
   * @return
   *     com.baomidou.mybatisplus.core.metadata.IPage<com.tongtech.biz.multitables.SysUserWithOrg>
   * @param page :
   */
  @Select(
      "select user.user_id as userId, user.user_name as userName, org.org_id as orgId, org.org_name as orgName "
          + "from t_sys_user user left join t_sys_org org on user.org_id = org.org_id")
  IPage<SysUserWithOrg> selectSysUserWithOrg(IPage<SysUserWithOrg> page);
}
