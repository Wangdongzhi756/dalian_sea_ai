<template>
  <view class="page">
    <view class="header">
      <view>
        <text class="title">业务申请</text>
        <text class="subtitle">提交、查看审核进度和通知</text>
      </view>
      <button class="primary-button" size="mini" @click="goForm">提交申请</button>
    </view>

    <view class="filter-row">
      <view
        v-for="item in statusOptions"
        :key="item.value"
        class="filter-item"
        :class="{ active: queryParams.status === item.value }"
        @click="changeStatus(item.value)"
      >
        {{ item.label }}
      </view>
    </view>

    <view v-if="applicationList.length" class="list">
      <view v-for="item in applicationList" :key="item.applicationId" class="card" @click="goDetail(item.applicationId)">
        <view class="card-top">
          <text class="application-no">{{ item.applicationNo || '待生成单号' }}</text>
          <text class="status" :class="'status-' + item.status">{{ formatStatus(item.status) }}</text>
        </view>
        <text class="card-title">{{ item.title }}</text>
        <view class="meta-row">
          <text>{{ formatType(item.applicationType) }}</text>
          <text>{{ item.vesselName || '未关联船舶' }}</text>
        </view>
        <view class="progress-box">
          <view class="progress-track">
            <view class="progress-bar" :style="{ width: (item.progressPercent || 0) + '%' }"></view>
          </view>
          <text class="progress-text">{{ item.progressPercent || 0 }}%</text>
        </view>
        <view class="card-bottom">
          <text>{{ item.currentStep || '-' }}</text>
          <text>{{ item.submittedTime || '' }}</text>
        </view>
      </view>
    </view>

    <view v-else class="empty">
      <uni-icons type="compose" size="42" color="#b7c0cc"></uni-icons>
      <text>暂无业务申请</text>
    </view>

    <uni-load-more :status="loadStatus"></uni-load-more>
  </view>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { onLoad, onPullDownRefresh, onReachBottom } from '@dcloudio/uni-app'
import { listApplication } from '@/api/business/application'

const applicationList = ref([])
const total = ref(0)
const loadStatus = ref('more')
const loading = ref(false)
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  status: undefined
})

const statusOptions = [
  { value: undefined, label: '全部' },
  { value: 'submitted', label: '待审核' },
  { value: 'approved', label: '已通过' },
  { value: 'rejected', label: '已驳回' }
]

const typeMap = {
  vessel_service: '船舶业务',
  crew_service: '船员业务',
  certificate: '证照办理',
  repair: '维修报备',
  other: '其他'
}
const statusMap = {
  submitted: '已提交',
  approved: '已通过',
  rejected: '已驳回'
}

function formatType(type) {
  return typeMap[type] || type || '-'
}

function formatStatus(status) {
  return statusMap[status] || status || '-'
}

function getList(reset = false) {
  if (loading.value) return
  loading.value = true
  loadStatus.value = 'loading'
  if (reset) {
    queryParams.pageNum = 1
    applicationList.value = []
  }
  listApplication(queryParams).then(res => {
    const rows = res.rows || []
    total.value = res.total || 0
    applicationList.value = reset ? rows : applicationList.value.concat(rows)
    loadStatus.value = applicationList.value.length >= total.value ? 'noMore' : 'more'
  }).finally(() => {
    loading.value = false
    uni.stopPullDownRefresh()
  })
}

function changeStatus(status) {
  queryParams.status = status
  getList(true)
}

function goForm() {
  uni.navigateTo({ url: '/pages/business/application/form' })
}

function goDetail(applicationId) {
  uni.navigateTo({ url: '/pages/business/application/detail?id=' + applicationId })
}

onLoad(() => {
  getList(true)
})

onPullDownRefresh(() => {
  getList(true)
})

onReachBottom(() => {
  if (loadStatus.value !== 'more') return
  queryParams.pageNum += 1
  getList()
})
</script>

<style lang="scss" scoped>
.page {
  min-height: 100vh;
  background: #f5f7fb;
  padding: 24rpx;
  box-sizing: border-box;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 28rpx;
  background: #ffffff;
  border-radius: 16rpx;
}

.title {
  display: block;
  color: #1f2d3d;
  font-size: 36rpx;
  font-weight: 700;
  line-height: 48rpx;
}

.subtitle {
  display: block;
  color: #7a8491;
  font-size: 24rpx;
  margin-top: 6rpx;
}

.primary-button {
  background: #1f6feb;
  color: #ffffff;
  border-radius: 8rpx;
  line-height: 58rpx;
}

.filter-row {
  display: flex;
  gap: 16rpx;
  margin: 24rpx 0;
  overflow-x: auto;
}

.filter-item {
  flex: 0 0 auto;
  padding: 14rpx 24rpx;
  border-radius: 8rpx;
  background: #ffffff;
  color: #5f6b7a;
  font-size: 26rpx;
}

.filter-item.active {
  background: #1f6feb;
  color: #ffffff;
}

.list {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
}

.card {
  background: #ffffff;
  border-radius: 16rpx;
  padding: 24rpx;
}

.card-top,
.card-bottom,
.meta-row,
.progress-box {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.application-no {
  color: #5f6b7a;
  font-size: 24rpx;
}

.status {
  border-radius: 8rpx;
  font-size: 24rpx;
  padding: 6rpx 14rpx;
}

.status-submitted {
  background: #fff7e6;
  color: #ad6800;
}

.status-approved {
  background: #edf7ee;
  color: #2f7d32;
}

.status-rejected {
  background: #fff1f0;
  color: #c62828;
}

.card-title {
  display: block;
  color: #17202a;
  font-size: 32rpx;
  font-weight: 600;
  line-height: 44rpx;
  margin-top: 16rpx;
}

.meta-row {
  color: #7a8491;
  font-size: 24rpx;
  margin-top: 12rpx;
}

.progress-box {
  gap: 16rpx;
  margin-top: 20rpx;
}

.progress-track {
  flex: 1;
  height: 12rpx;
  background: #e7edf5;
  border-radius: 999rpx;
  overflow: hidden;
}

.progress-bar {
  height: 100%;
  background: #1f6feb;
}

.progress-text {
  color: #5f6b7a;
  font-size: 24rpx;
}

.card-bottom {
  color: #8c96a3;
  font-size: 24rpx;
  margin-top: 16rpx;
}

.empty {
  align-items: center;
  color: #8c96a3;
  display: flex;
  flex-direction: column;
  gap: 12rpx;
  padding: 100rpx 0;
}
</style>
