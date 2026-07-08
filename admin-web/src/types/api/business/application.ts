import type { BaseEntity, PageDomain } from "../common";

/** 业务申请分页查询参数 */
export interface ApplicationQueryParams extends PageDomain {
  /** 租户ID */
  tenantId?: number;
  /** 船舶ID */
  vesselId?: number;
  /** 船员ID */
  crewId?: number;
  /** 申请单号 */
  applicationNo?: string;
  /** 申请类型 */
  applicationType?: string;
  /** 申请标题 */
  title?: string;
  /** 申请人 */
  applicantName?: string;
  /** 紧急程度 */
  priority?: string;
  /** 申请状态 */
  status?: string;
}

/** 业务通知分页查询参数 */
export interface BusinessNoticeQueryParams extends PageDomain {
  /** 申请ID */
  applicationId?: number;
  /** 申请单号 */
  applicationNo?: string;
  /** 通知类型 */
  noticeType?: string;
  /** 是否已读 */
  readFlag?: string;
  /** 接收人 */
  receiverName?: string;
}

/** 业务申请 */
export interface SeaBusinessApplication extends BaseEntity {
  /** 申请ID */
  applicationId?: number;
  /** 租户ID */
  tenantId?: number;
  /** 租户名称 */
  tenantName?: string;
  /** 船舶ID */
  vesselId?: number;
  /** 船舶名称 */
  vesselName?: string;
  /** 船员ID */
  crewId?: number;
  /** 船员姓名 */
  crewName?: string;
  /** 申请单号 */
  applicationNo?: string;
  /** 申请类型 */
  applicationType?: string;
  /** 申请标题 */
  title?: string;
  /** 申请人 */
  applicantName?: string;
  /** 联系电话 */
  applicantPhone?: string;
  /** 紧急程度 */
  priority?: string;
  /** 申请内容 */
  content?: string;
  /** 当前节点 */
  currentStep?: string;
  /** 进度百分比 */
  progressPercent?: number;
  /** 申请状态 */
  status?: string;
  /** 提交时间 */
  submittedTime?: string;
  /** 审核人 */
  auditBy?: string;
  /** 审核时间 */
  auditTime?: string;
  /** 审核意见 */
  auditOpinion?: string;
}

/** 业务申请进度 */
export interface SeaBusinessProgress extends BaseEntity {
  /** 进度ID */
  progressId?: number;
  /** 申请ID */
  applicationId?: number;
  /** 申请单号 */
  applicationNo?: string;
  /** 节点名称 */
  stepName?: string;
  /** 节点状态 */
  stepStatus?: string;
  /** 处理人 */
  handlerName?: string;
  /** 处理时间 */
  handleTime?: string;
  /** 处理意见 */
  handleOpinion?: string;
}

/** 业务通知 */
export interface SeaBusinessNotice extends BaseEntity {
  /** 通知ID */
  noticeId?: number;
  /** 申请ID */
  applicationId?: number;
  /** 申请单号 */
  applicationNo?: string;
  /** 通知标题 */
  noticeTitle?: string;
  /** 通知内容 */
  noticeContent?: string;
  /** 通知类型 */
  noticeType?: string;
  /** 是否已读 */
  readFlag?: string;
  /** 接收人 */
  receiverName?: string;
}
