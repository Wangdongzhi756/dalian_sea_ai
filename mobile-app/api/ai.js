import request from '@/utils/request'

// AI助手问答
export function askAi(data) {
  return request({
    url: '/ai/assistant/ask',
    method: 'post',
    data: data
  })
}
