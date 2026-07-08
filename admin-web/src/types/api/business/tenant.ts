import type { BaseEntity, PageDomain } from "../common";

/** 租户分页查询参数 */
export interface TenantQueryParams extends PageDomain {
  /** 租户编码 */
  tenantCode?: string;
  /** 租户名称 */
  tenantName?: string;
  /** 联系电话 */
  contactPhone?: string;
  /** 套餐类型 */
  planType?: string;
  /** 状态 */
  status?: string;
}

/** 租户主体 */
export interface SeaTenant extends BaseEntity {
  /** 租户ID */
  tenantId?: number;
  /** 租户编码 */
  tenantCode?: string;
  /** 租户名称 */
  tenantName?: string;
  /** 联系人 */
  contactName?: string;
  /** 联系电话 */
  contactPhone?: string;
  /** 联系邮箱 */
  contactEmail?: string;
  /** 套餐类型 */
  planType?: string;
  /** 船舶额度 */
  maxVessels?: number;
  /** 到期日期 */
  expireTime?: string;
  /** 状态（0正常 1停用） */
  status?: '0' | '1';
}
