import type { BaseEntity, PageDomain } from "../common";

/** 内容发布查询参数 */
export interface ContentQueryParams extends PageDomain {
  tenantId?: number;
  vesselId?: number;
  contentType?: string;
  publishChannel?: string;
  topic?: string;
  title?: string;
  publishStatus?: string;
  pushStatus?: string;
}

/** 内容发布 */
export interface SeaContentPublish extends BaseEntity {
  contentId?: number;
  tenantId?: number;
  tenantName?: string;
  vesselId?: number;
  vesselName?: string;
  captainName?: string;
  captainPhone?: string;
  contentType?: string;
  publishChannel?: string;
  topic?: string;
  tone?: string;
  highlights?: string;
  title?: string;
  content?: string;
  publishStatus?: string;
  pushStatus?: string;
  pushMessage?: string;
  publishTime?: string;
  aiProvider?: string;
  aiModel?: string;
}
