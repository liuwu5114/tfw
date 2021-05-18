package com.tongtech.biz.multitables;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tongtech.tfw.backend.admin.apis.biz.sys.org.service.SysOrgService;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.model.domain.SysUser;
import com.tongtech.tfw.backend.admin.apis.biz.sys.user.service.SysUserService;
import com.tongtech.tfw.backend.common.biz.constants.BizConstants;
import com.tongtech.tfw.backend.core.helper.bean.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MultiTabelService {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysOrgService sysOrgService;
    @Autowired
    MultiTableDao multiTableDao;

    /**
     * USER作为主表进行分页，ORG作为扩展信息表，org_id作为关联主键作关联查询
     * <p>
     * 方法一，先查询分页结果，然后查询关联数据
     */
    public List<SysUser> selectUserWithOrg1() {
        Long page = BizConstants.PAGE;
        Long limit = BizConstants.LIMIT;
        Page<SysUser> resultPage = new Page(page, limit);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        Page<SysUser> resultList = (Page) sysUserService.page(resultPage, queryWrapper);
        List<SysUser> dataList = new ArrayList<>(resultList.getRecords().size());
        for (SysUser sysUser : resultList.getRecords()) {
            SysUser sysUserResult =
                    BeanHelper.beanToBean(sysUser, SysUser.class);
            sysUserResult.setOrgId(sysOrgService.getById(sysUserResult.getOrgId()).getOrgName());
            dataList.add(sysUserResult);
        }
        return dataList;
    }
    /**
     * USER作为主表进行分页，ORG作为扩展信息表，org_id作为关联主键作关联查询
     * <p>
     * 方法二，通过dao层增加sql，传入IPAGE参数进行分页
     */
    public List<SysUserWithOrg> selectUserWithOrg2() {
        Long page = BizConstants.PAGE;
        Long limit = BizConstants.LIMIT;
        Page<SysUserWithOrg> resultPage = new Page(page, limit);
        Page<SysUserWithOrg> resultList = (Page) multiTableDao.selectSysUserWithOrg(resultPage);
        return resultList.getRecords();
    }


}
