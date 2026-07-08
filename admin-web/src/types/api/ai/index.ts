import type { BaseEntity, PageDomain } from "../common";

/** AI知识库分页查询参数 */
export interface AiKnowledgeQueryParams extends PageDomain {
  tenantId?: number;
  title?: string;
  category?: string;
  sourceType?: string;
  tags?: string;
  status?: string;
}

/** AI调用日志分页查询参数 */
export interface AiCallLogQueryParams extends PageDomain {
  tenantId?: number;
  scene?: string;
  question?: string;
  providerName?: string;
  modelName?: string;
  successFlag?: string;
}

/** AI文案模板分页查询参数 */
export interface ContentTemplateQueryParams extends PageDomain {
  tenantId?: number;
  templateName?: string;
  contentType?: string;
  publishChannel?: string;
  status?: string;
}

/** AI知识库 */
export interface SeaAiKnowledge extends BaseEntity {
  knowledgeId?: number;
  tenantId?: number;
  tenantName?: string;
  title?: string;
  category?: string;
  sourceType?: string;
  content?: string;
  tags?: string;
  status?: string;
}

/** AI调用日志 */
export interface SeaAiCallLog extends BaseEntity {
  logId?: number;
  tenantId?: number;
  tenantName?: string;
  scene?: string;
  question?: string;
  answer?: string;
  providerName?: string;
  modelName?: string;
  requestTokens?: number;
  responseTokens?: number;
  totalTokens?: number;
  latencyMs?: number;
  successFlag?: string;
  errorMessage?: string;
}

/** AI文案模板 */
export interface SeaContentTemplate extends BaseEntity {
  templateId?: number;
  tenantId?: number;
  tenantName?: string;
  templateName?: string;
  contentType?: string;
  publishChannel?: string;
  promptTemplate?: string;
  status?: string;
  sortOrder?: number;
}

/** AI问答请求 */
export interface SeaAiChatRequest {
  tenantId?: number;
  scene?: string;
  category?: string;
  question?: string;
}

/** AI问答响应 */
export interface SeaAiChatResponse {
  answer?: string;
  matchedKnowledgeId?: number;
  matchedKnowledgeTitle?: string;
  logId?: number;
  providerName?: string;
  modelName?: string;
}
