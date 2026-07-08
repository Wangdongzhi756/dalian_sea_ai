import request from '@/utils/request'
import type { AjaxResult, SeaVesselLicense, TableDataInfo, VesselLicenseQueryParams } from '@/types'

// 查询船舶证照列表
export function listVesselLicense(query: VesselLicenseQueryParams): Promise<TableDataInfo<SeaVesselLicense[]>> {
  return request({
    url: '/business/vesselLicense/list',
    method: 'get',
    params: query
  })
}

// 查询船舶证照详细
export function getVesselLicense(licenseId: number): Promise<AjaxResult<SeaVesselLicense>> {
  return request({
    url: '/business/vesselLicense/' + licenseId,
    method: 'get'
  })
}

// 新增船舶证照
export function addVesselLicense(data: SeaVesselLicense): Promise<AjaxResult> {
  return request({
    url: '/business/vesselLicense',
    method: 'post',
    data: data
  })
}

// 修改船舶证照
export function updateVesselLicense(data: SeaVesselLicense): Promise<AjaxResult> {
  return request({
    url: '/business/vesselLicense',
    method: 'put',
    data: data
  })
}

// 删除船舶证照
export function delVesselLicense(licenseId: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/business/vesselLicense/' + licenseId,
    method: 'delete'
  })
}
