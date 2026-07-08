import request from '@/utils/request'

// 查询业务申请列表
export function listApplication(query) {
  return request({
    url: '/business/application/list',
    method: 'get',
    params: query
  })
}

// 查询业务申请详细
export function getApplication(applicationId) {
  return request({
    url: '/business/application/' + applicationId,
    method: 'get'
  })
}

// 查询业务申请进度
export function listApplicationProgress(applicationId) {
  return request({
    url: '/business/application/' + applicationId + '/progress',
    method: 'get'
  })
}

// 查询业务通知
export function listBusinessNotice(query) {
  return request({
    url: '/business/application/notice/list',
    method: 'get',
    params: query
  })
}

// 标记业务通知已读
export function readBusinessNotice(noticeIds) {
  return request({
    url: '/business/application/notice/' + noticeIds + '/read',
    method: 'put'
  })
}

// 新增业务申请
export function addApplication(data) {
  return request({
    url: '/business/application',
    method: 'post',
    data: data
  })
}
