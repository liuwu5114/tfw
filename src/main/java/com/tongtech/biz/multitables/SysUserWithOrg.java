package com.tongtech.biz.multitables;

import lombok.Data;

@Data
public class SysUserWithOrg {
    private String userId;
    private String userName;
    private String orgId;
    private String orgName;
}
