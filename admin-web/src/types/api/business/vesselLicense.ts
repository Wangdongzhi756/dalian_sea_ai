import type { BaseEntity, PageDomain } from "../common";

/** 船舶证照分页查询参数 */
export interface VesselLicenseQueryParams extends PageDomain {
  /** 租户ID */
  tenantId?: number;
  /** 船舶ID */
  vesselId?: number;
  /** 证照类型 */
  licenseType?: string;
  /** 证照编号 */
  licenseNo?: string;
  /** 证照状态 */
  licenseStatus?: string;
}

/** 船舶证照附件 */
export interface SeaVesselLicense extends BaseEntity {
  /** 证照ID */
  licenseId?: number;
  /** 租户ID */
  tenantId?: number;
  /** 租户名称 */
  tenantName?: string;
  /** 船舶ID */
  vesselId?: number;
  /** 船舶名称 */
  vesselName?: string;
  /** 证照类型 */
  licenseType?: string;
  /** 证照编号 */
  licenseNo?: string;
  /** 签发日期 */
  issueDate?: string;
  /** 到期日期 */
  expireDate?: string;
  /** 签发机构 */
  issuingAuthority?: string;
  /** 附件名称 */
  attachmentName?: string;
  /** 附件地址 */
  attachmentUrl?: string;
  /** 证照状态 */
  licenseStatus?: string;
}
