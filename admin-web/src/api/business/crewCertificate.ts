import request from '@/utils/request'
import type { AjaxResult, SeaCrewCertificate, TableDataInfo, CrewCertificateQueryParams } from '@/types'

// 查询船员证书列表
export function listCrewCertificate(query: CrewCertificateQueryParams): Promise<TableDataInfo<SeaCrewCertificate[]>> {
  return request({
    url: '/business/crewCertificate/list',
    method: 'get',
    params: query
  })
}

// 查询船员证书详细
export function getCrewCertificate(certificateId: number): Promise<AjaxResult<SeaCrewCertificate>> {
  return request({
    url: '/business/crewCertificate/' + certificateId,
    method: 'get'
  })
}

// 新增船员证书
export function addCrewCertificate(data: SeaCrewCertificate): Promise<AjaxResult> {
  return request({
    url: '/business/crewCertificate',
    method: 'post',
    data: data
  })
}

// 修改船员证书
export function updateCrewCertificate(data: SeaCrewCertificate): Promise<AjaxResult> {
  return request({
    url: '/business/crewCertificate',
    method: 'put',
    data: data
  })
}

// 删除船员证书
export function delCrewCertificate(certificateId: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/business/crewCertificate/' + certificateId,
    method: 'delete'
  })
}
