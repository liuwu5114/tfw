// @formatter:off
package com.tongtech.tfw.backend.admin.apis.biz.sys.org.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tongtech.tfw.backend.common.models.supers.SuperModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * 组织机构表
 *
 * @author Ivan
 * @since 2020-03-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_org")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SysOrg extends SuperModel {
  private static final long serialVersionUID = 1L;
  /** 机构表主键 */
  @TableId private String orgId;
  /** 父级机构主键，如果是顶级节点值为0 */
  private String parentId;
  /** 所有父级机构主键以英文逗号隔开如：一级，二级等 */
  private String parentIds;
  /** 排序号 */
  private BigDecimal treeSort;
  /** 层次级别，1顶级依次类推 */
  private BigDecimal treeLevel;
  /** 机构名称 */
  private String orgName;
  /** 机构编码 */
  private String orgCode;
  /** 联系地址 */
  private String address;
  /** 邮政编码 */
  private String zipCode;
  /** 负责人 */
  private String leader;
  /** 电话 */
  private String phoneNo;
  /** 传真 */
  private String fax;
  /** 邮箱 */
  private String email;

  public static final String ORG_ID = "org_id";

  public static final String PARENT_ID = "parent_id";

  public static final String PARENT_IDS = "parent_ids";

  public static final String TREE_SORT = "tree_sort";

  public static final String TREE_LEVEL = "tree_level";

  public static final String ORG_NAME = "org_name";

  public static final String ORG_CODE = "org_code";

  public static final String ADDRESS = "address";

  public static final String ZIP_CODE = "zip_code";

  public static final String LEADER = "leader";

  public static final String PHONE_NO = "phone_no";

  public static final String FAX = "fax";

  public static final String EMAIL = "email";
}
