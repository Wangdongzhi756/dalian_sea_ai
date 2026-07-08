import request from '@/utils/request'
import type { AjaxResult, ContentQueryParams, SeaContentPublish, TableDataInfo } from '@/types'

// 查询内容发布列表
export function listContent(query: ContentQueryParams): Promise<TableDataInfo<SeaContentPublish[]>> {
  return request({
    url: '/business/content/list',
    method: 'get',
    params: query
  })
}

// 查询内容发布详细
export function getContent(contentId: number): Promise<AjaxResult<SeaContentPublish>> {
  return request({
    url: '/business/content/' + contentId,
    method: 'get'
  })
}

// 新增内容发布
export function addContent(data: SeaContentPublish): Promise<AjaxResult> {
  return request({
    url: '/business/content',
    method: 'post',
    data: data
  })
}

// 修改内容发布
export function updateContent(data: SeaContentPublish): Promise<AjaxResult> {
  return request({
    url: '/business/content',
    method: 'put',
    data: data
  })
}

// AI生成内容
export function generateContent(data: SeaContentPublish): Promise<AjaxResult<SeaContentPublish>> {
  return request({
    url: '/business/content/generate',
    method: 'post',
    data: data
  })
}

// 推流发布内容
export function publishContent(contentId: number): Promise<AjaxResult> {
  return request({
    url: '/business/content/' + contentId + '/publish',
    method: 'put'
  })
}

// 删除内容发布
export function delContent(contentId: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/business/content/' + contentId,
    method: 'delete'
  })
}
