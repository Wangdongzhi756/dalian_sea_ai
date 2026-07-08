import request from '@/utils/request'
import type { AjaxResult, SeaVessel, TableDataInfo, VesselQueryParams } from '@/types'

// 查询船舶列表
export function listVessel(query: VesselQueryParams): Promise<TableDataInfo<SeaVessel[]>> {
  return request({
    url: '/business/vessel/list',
    method: 'get',
    params: query
  })
}

// 查询船舶详细
export function getVessel(vesselId: number): Promise<AjaxResult<SeaVessel>> {
  return request({
    url: '/business/vessel/' + vesselId,
    method: 'get'
  })
}

// 新增船舶
export function addVessel(data: SeaVessel): Promise<AjaxResult> {
  return request({
    url: '/business/vessel',
    method: 'post',
    data: data
  })
}

// 修改船舶
export function updateVessel(data: SeaVessel): Promise<AjaxResult> {
  return request({
    url: '/business/vessel',
    method: 'put',
    data: data
  })
}

// 删除船舶
export function delVessel(vesselId: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/business/vessel/' + vesselId,
    method: 'delete'
  })
}
