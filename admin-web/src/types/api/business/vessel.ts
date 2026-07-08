import type { BaseEntity, PageDomain } from "../common";

/** 船舶分页查询参数 */
export interface VesselQueryParams extends PageDomain {
  /** 租户ID */
  tenantId?: number;
  /** 船舶编码 */
  vesselCode?: string;
  /** 船舶名称 */
  vesselName?: string;
  /** 船舶类型 */
  vesselType?: string;
  /** 状态 */
  status?: string;
}

/** 船舶档案 */
export interface SeaVessel extends BaseEntity {
  /** 船舶ID */
  vesselId?: number;
  /** 租户ID */
  tenantId?: number;
  /** 租户名称 */
  tenantName?: string;
  /** 船舶编码 */
  vesselCode?: string;
  /** 船舶名称 */
  vesselName?: string;
  /** 船舶类型 */
  vesselType?: string;
  /** 载客人数 */
  capacity?: number;
  /** 母港 */
  homePort?: string;
  /** 船长 */
  captainName?: string;
  /** 船长电话 */
  captainPhone?: string;
  /** 服务区域 */
  serviceArea?: string;
  /** 状态（0正常 1停用） */
  status?: '0' | '1';
}
