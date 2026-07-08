import type { BaseEntity, PageDomain } from "../common";

/** 船员分页查询参数 */
export interface CrewQueryParams extends PageDomain {
  /** 租户ID */
  tenantId?: number;
  /** 船舶ID */
  vesselId?: number;
  /** 船员编码 */
  crewCode?: string;
  /** 船员姓名 */
  crewName?: string;
  /** 在船状态 */
  onboardStatus?: string;
  /** 状态 */
  status?: string;
}

/** 船员档案 */
export interface SeaCrew extends BaseEntity {
  /** 船员ID */
  crewId?: number;
  /** 租户ID */
  tenantId?: number;
  /** 租户名称 */
  tenantName?: string;
  /** 船舶ID */
  vesselId?: number;
  /** 船舶名称 */
  vesselName?: string;
  /** 船员编码 */
  crewCode?: string;
  /** 船员姓名 */
  crewName?: string;
  /** 性别 */
  gender?: '0' | '1' | '2';
  /** 出生日期 */
  birthDate?: string;
  /** 证件号码 */
  idCard?: string;
  /** 联系电话 */
  phone?: string;
  /** 岗位职务 */
  positionName?: string;
  /** 在船状态 */
  onboardStatus?: string;
  /** 健康状态 */
  healthStatus?: string;
  /** 状态（0正常 1停用） */
  status?: '0' | '1';
}
