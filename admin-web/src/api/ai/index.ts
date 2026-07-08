import request from '@/utils/request'
import type {
  AiCallLogQueryParams,
  AiKnowledgeQueryParams,
  AjaxResult,
  ContentTemplateQueryParams,
  SeaAiCallLog,
  SeaAiChatRequest,
  SeaAiChatResponse,
  SeaAiKnowledge,
  SeaContentTemplate,
  TableDataInfo
} from '@/types'

// 查询AI知识库列表
export function listKnowledge(query: AiKnowledgeQueryParams): Promise<TableDataInfo<SeaAiKnowledge[]>> {
  return request({
    url: '/ai/knowledge/list',
    method: 'get',
    params: query
  })
}

// 查询AI知识详细
export function getKnowledge(knowledgeId: number): Promise<AjaxResult<SeaAiKnowledge>> {
  return request({
    url: '/ai/knowledge/' + knowledgeId,
    method: 'get'
  })
}

// 新增AI知识
export function addKnowledge(data: SeaAiKnowledge): Promise<AjaxResult> {
  return request({
    url: '/ai/knowledge',
    method: 'post',
    data: data
  })
}

// 修改AI知识
export function updateKnowledge(data: SeaAiKnowledge): Promise<AjaxResult> {
  return request({
    url: '/ai/knowledge',
    method: 'put',
    data: data
  })
}

// 删除AI知识
export function delKnowledge(knowledgeId: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/ai/knowledge/' + knowledgeId,
    method: 'delete'
  })
}

// 查询AI文案模板列表
export function listContentTemplate(query: ContentTemplateQueryParams): Promise<TableDataInfo<SeaContentTemplate[]>> {
  return request({
    url: '/ai/contentTemplate/list',
    method: 'get',
    params: query
  })
}

// 查询AI文案模板详细
export function getContentTemplate(templateId: number): Promise<AjaxResult<SeaContentTemplate>> {
  return request({
    url: '/ai/contentTemplate/' + templateId,
    method: 'get'
  })
}

// 新增AI文案模板
export function addContentTemplate(data: SeaContentTemplate): Promise<AjaxResult> {
  return request({
    url: '/ai/contentTemplate',
    method: 'post',
    data: data
  })
}

// 修改AI文案模板
export function updateContentTemplate(data: SeaContentTemplate): Promise<AjaxResult> {
  return request({
    url: '/ai/contentTemplate',
    method: 'put',
    data: data
  })
}

// 删除AI文案模板
export function delContentTemplate(templateId: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/ai/contentTemplate/' + templateId,
    method: 'delete'
  })
}

// 查询AI调用日志列表
export function listCallLog(query: AiCallLogQueryParams): Promise<TableDataInfo<SeaAiCallLog[]>> {
  return request({
    url: '/ai/callLog/list',
    method: 'get',
    params: query
  })
}

// 查询AI调用日志详细
export function getCallLog(logId: number): Promise<AjaxResult<SeaAiCallLog>> {
  return request({
    url: '/ai/callLog/' + logId,
    method: 'get'
  })
}

// 删除AI调用日志
export function delCallLog(logId: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/ai/callLog/' + logId,
    method: 'delete'
  })
}

// AI助手问答
export function askAi(data: SeaAiChatRequest): Promise<AjaxResult<SeaAiChatResponse>> {
  return request({
    url: '/ai/assistant/ask',
    method: 'post',
    data: data
  })
}
