import request from '@/utils/request'
import type { AjaxResult, SeaTenant, TableDataInfo, TenantQueryParams } from '@/types'

// 查询租户列表
export function listTenant(query: TenantQueryParams): Promise<TableDataInfo<SeaTenant[]>> {
  return request({
    url: '/business/tenant/list',
    method: 'get',
    params: query
  })
}

// 查询租户详细
export function getTenant(tenantId: number): Promise<AjaxResult<SeaTenant>> {
  return request({
    url: '/business/tenant/' + tenantId,
    method: 'get'
  })
}

// 查询租户下拉
export function optionselectTenant(): Promise<AjaxResult<SeaTenant[]>> {
  return request({
    url: '/business/tenant/optionselect',
    method: 'get'
  })
}

// 新增租户
export function addTenant(data: SeaTenant): Promise<AjaxResult> {
  return request({
    url: '/business/tenant',
    method: 'post',
    data: data
  })
}

// 修改租户
export function updateTenant(data: SeaTenant): Promise<AjaxResult> {
  return request({
    url: '/business/tenant',
    method: 'put',
    data: data
  })
}

// 删除租户
export function delTenant(tenantId: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/business/tenant/' + tenantId,
    method: 'delete'
  })
}
