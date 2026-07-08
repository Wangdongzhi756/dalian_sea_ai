import type { BaseEntity, PageDomain } from "../common";

/** 船员证书分页查询参数 */
export interface CrewCertificateQueryParams extends PageDomain {
  /** 租户ID */
  tenantId?: number;
  /** 船员ID */
  crewId?: number;
  /** 证书类型 */
  certificateType?: string;
  /** 证书编号 */
  certificateNo?: string;
  /** 证书状态 */
  certificateStatus?: string;
}

/** 船员证书档案 */
export interface SeaCrewCertificate extends BaseEntity {
  /** 证书ID */
  certificateId?: number;
  /** 租户ID */
  tenantId?: number;
  /** 租户名称 */
  tenantName?: string;
  /** 船员ID */
  crewId?: number;
  /** 船员姓名 */
  crewName?: string;
  /** 证书类型 */
  certificateType?: string;
  /** 证书编号 */
  certificateNo?: string;
  /** 签发日期 */
  issueDate?: string;
  /** 到期日期 */
  expireDate?: string;
  /** 签发机构 */
  issuingAuthority?: string;
  /** 证书状态 */
  certificateStatus?: string;
}
