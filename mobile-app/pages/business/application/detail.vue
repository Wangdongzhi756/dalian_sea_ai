<template>
  <view class="page">
    <view class="summary-card">
      <view class="summary-top">
        <text class="application-no">{{ detail.applicationNo }}</text>
        <text class="status" :class="'status-' + detail.status">{{ formatStatus(detail.status) }}</text>
      </view>
      <text class="title">{{ detail.title }}</text>
      <view class="progress-box">
        <view class="progress-track">
          <view class="progress-bar" :style="{ width: (detail.progressPercent || 0) + '%' }"></view>
        </view>
        <text class="progress-text">{{ detail.progressPercent || 0 }}%</text>
      </view>
    </view>

    <view class="info-card">
      <view class="row"><text>申请类型</text><text>{{ formatType(detail.applicationType) }}</text></view>
      <view class="row"><text>所属租户</text><text>{{ detail.tenantName || '-' }}</text></view>
      <view class="row"><text>所属船舶</text><text>{{ detail.vesselName || '-' }}</text></view>
      <view class="row"><text>所属船员</text><text>{{ detail.crewName || '-' }}</text></view>
      <view class="row"><text>申请人</text><text>{{ detail.applicantName || '-' }}</text></view>
      <view class="row"><text>当前节点</text><text>{{ detail.currentStep || '-' }}</text></view>
      <view class="content-row">
        <text>申请内容</text>
        <text>{{ detail.content || '-' }}</text>
      </view>
      <view class="content-row">
        <text>审核意见</text>
        <text>{{ detail.auditOpinion || '-' }}</text>
      </view>
    </view>

    <view class="section-title">办理进度</view>
    <view class="timeline">
      <view v-for="item in progressList" :key="item.progressId" class="timeline-item">
        <view class="dot"></view>
        <view class="timeline-content">
          <view class="timeline-head">
            <text>{{ item.stepName }}</text>
            <text>{{ item.handleTime || '' }}</text>
          </view>
          <text class="timeline-text">{{ item.handleOpinion || '-' }}</text>
        </view>
      </view>
    </view>

    <view class="section-title">业务通知</view>
    <view class="notice-list">
      <view v-for="item in noticeList" :key="item.noticeId" class="notice-card">
        <view class="notice-top">
          <text>{{ item.noticeTitle }}</text>
          <text class="read-flag">{{ item.readFlag === '1' ? '已读' : '未读' }}</text>
        </view>
        <text class="notice-content">{{ item.noticeContent }}</text>
        <button v-if="item.readFlag !== '1'" class="read-button" size="mini" @click="markRead(item.noticeId)">标记已读</button>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { onLoad } from '@dcloudio/uni-app'
import { getApplication, listApplicationProgress, listBusinessNotice, readBusinessNotice } from '@/api/business/application'

const detail = ref({})
const progressList = ref([])
const noticeList = ref([])
const applicationId = ref()

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

function loadDetail() {
  if (!applicationId.value) return
  getApplication(applicationId.value).then(res => {
    detail.value = res.data || {}
  })
  listApplicationProgress(applicationId.value).then(res => {
    progressList.value = res.data || []
  })
  listBusinessNotice({ pageNum: 1, pageSize: 1000, applicationId: applicationId.value }).then(res => {
    noticeList.value = res.rows || []
  })
}

function markRead(noticeId) {
  readBusinessNotice(noticeId).then(() => {
    uni.showToast({ title: '已标记', icon: 'success' })
    loadDetail()
  })
}

onLoad(options => {
  applicationId.value = options.id
  loadDetail()
})
</script>

<style lang="scss" scoped>
.page {
  min-height: 100vh;
  background: #f5f7fb;
  padding: 24rpx;
  box-sizing: border-box;
}

.summary-card,
.info-card,
.notice-card {
  background: #ffffff;
  border-radius: 16rpx;
  padding: 24rpx;
}

.summary-top,
.progress-box,
.row,
.notice-top,
.timeline-head {
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

.title {
  color: #17202a;
  display: block;
  font-size: 34rpx;
  font-weight: 700;
  line-height: 46rpx;
  margin-top: 18rpx;
}

.progress-box {
  gap: 16rpx;
  margin-top: 24rpx;
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

.info-card {
  margin-top: 20rpx;
}

.row {
  border-bottom: 1rpx solid #edf0f5;
  color: #17202a;
  font-size: 28rpx;
  padding: 20rpx 0;
}

.row text:first-child,
.content-row text:first-child {
  color: #7a8491;
}

.content-row {
  border-bottom: 1rpx solid #edf0f5;
  display: flex;
  flex-direction: column;
  gap: 12rpx;
  padding: 20rpx 0;
}

.content-row text {
  color: #17202a;
  font-size: 28rpx;
  line-height: 40rpx;
}

.section-title {
  color: #17202a;
  font-size: 30rpx;
  font-weight: 700;
  margin: 28rpx 0 16rpx;
}

.timeline-item {
  display: flex;
  gap: 16rpx;
  margin-bottom: 18rpx;
}

.dot {
  background: #1f6feb;
  border-radius: 50%;
  flex: 0 0 18rpx;
  height: 18rpx;
  margin-top: 20rpx;
  width: 18rpx;
}

.timeline-content {
  background: #ffffff;
  border-radius: 14rpx;
  flex: 1;
  padding: 20rpx;
}

.timeline-head {
  color: #17202a;
  font-size: 28rpx;
  font-weight: 600;
}

.timeline-head text:last-child {
  color: #8c96a3;
  font-size: 22rpx;
  font-weight: 400;
}

.timeline-text,
.notice-content {
  color: #5f6b7a;
  display: block;
  font-size: 26rpx;
  line-height: 38rpx;
  margin-top: 10rpx;
}

.notice-list {
  display: flex;
  flex-direction: column;
  gap: 16rpx;
}

.notice-top {
  color: #17202a;
  font-size: 28rpx;
  font-weight: 600;
}

.read-flag {
  color: #8c96a3;
  font-size: 24rpx;
  font-weight: 400;
}

.read-button {
  background: #1f6feb;
  color: #ffffff;
  margin: 18rpx 0 0;
}
</style>
