import request from '@/utils/request'
import type { AjaxResult, SeaCrew, TableDataInfo, CrewQueryParams } from '@/types'

// 查询船员列表
export function listCrew(query: CrewQueryParams): Promise<TableDataInfo<SeaCrew[]>> {
  return request({
    url: '/business/crew/list',
    method: 'get',
    params: query
  })
}

// 查询船员详细
export function getCrew(crewId: number): Promise<AjaxResult<SeaCrew>> {
  return request({
    url: '/business/crew/' + crewId,
    method: 'get'
  })
}

// 新增船员
export function addCrew(data: SeaCrew): Promise<AjaxResult> {
  return request({
    url: '/business/crew',
    method: 'post',
    data: data
  })
}

// 修改船员
export function updateCrew(data: SeaCrew): Promise<AjaxResult> {
  return request({
    url: '/business/crew',
    method: 'put',
    data: data
  })
}

// 删除船员
export function delCrew(crewId: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/business/crew/' + crewId,
    method: 'delete'
  })
}
