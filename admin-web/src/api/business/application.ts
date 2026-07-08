import request from '@/utils/request'
import type {
  AjaxResult,
  ApplicationQueryParams,
  BusinessNoticeQueryParams,
  SeaBusinessApplication,
  SeaBusinessNotice,
  SeaBusinessProgress,
  TableDataInfo
} from '@/types'

// 查询业务申请列表
export function listApplication(query: ApplicationQueryParams): Promise<TableDataInfo<SeaBusinessApplication[]>> {
  return request({
    url: '/business/application/list',
    method: 'get',
    params: query
  })
}

// 查询业务申请详细
export function getApplication(applicationId: number): Promise<AjaxResult<SeaBusinessApplication>> {
  return request({
    url: '/business/application/' + applicationId,
    method: 'get'
  })
}

// 查询业务申请进度
export function listApplicationProgress(applicationId: number): Promise<AjaxResult<SeaBusinessProgress[]>> {
  return request({
    url: '/business/application/' + applicationId + '/progress',
    method: 'get'
  })
}

// 查询业务通知列表
export function listBusinessNotice(query: BusinessNoticeQueryParams): Promise<TableDataInfo<SeaBusinessNotice[]>> {
  return request({
    url: '/business/application/notice/list',
    method: 'get',
    params: query
  })
}

// 标记业务通知已读
export function readBusinessNotice(noticeIds: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/business/application/notice/' + noticeIds + '/read',
    method: 'put'
  })
}

// 新增业务申请
export function addApplication(data: SeaBusinessApplication): Promise<AjaxResult> {
  return request({
    url: '/business/application',
    method: 'post',
    data: data
  })
}

// 修改业务申请
export function updateApplication(data: SeaBusinessApplication): Promise<AjaxResult> {
  return request({
    url: '/business/application',
    method: 'put',
    data: data
  })
}

// 审核业务申请
export function auditApplication(data: SeaBusinessApplication): Promise<AjaxResult> {
  return request({
    url: '/business/application/audit',
    method: 'put',
    data: data
  })
}

// 删除业务申请
export function delApplication(applicationId: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/business/application/' + applicationId,
    method: 'delete'
  })
}
