import request from '@/utils/request'

// 查询当前船长船舶
export function listCaptainVessels() {
  return request({
    url: '/mobile/captain/vessel/list',
    method: 'get'
  })
}

// 查询当前船长船舶详情
export function getCaptainVessel(vesselId) {
  return request({
    url: '/mobile/captain/vessel/' + vesselId,
    method: 'get'
  })
}

// 更新当前船长船舶
export function updateCaptainVessel(data) {
  return request({
    url: '/mobile/captain/vessel',
    method: 'put',
    data: data
  })
}

// 查询当前船长文案
export function listCaptainContents() {
  return request({
    url: '/mobile/captain/content/list',
    method: 'get'
  })
}

// AI生成船长文案
export function generateCaptainContent(data) {
  return request({
    url: '/mobile/captain/content/generate',
    method: 'post',
    data: data
  })
}

// 更新船长文案草稿
export function updateCaptainContent(data) {
  return request({
    url: '/mobile/captain/content',
    method: 'put',
    data: data
  })
}

// 发布船长文案
export function publishCaptainContent(contentId) {
  return request({
    url: '/mobile/captain/content/' + contentId + '/publish',
    method: 'put'
  })
}
