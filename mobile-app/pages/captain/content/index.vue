<template>
  <view class="page">
    <view class="form-card">
      <view class="section-title">AI生成公众号文案</view>
      <view class="field">
        <text class="label">关联船舶</text>
        <picker :range="vessels" range-key="vesselName" @change="changeVessel">
          <view class="picker">{{ currentVesselName }}</view>
        </picker>
      </view>
      <view class="field">
        <text class="label">内容类型</text>
        <picker :range="contentTypeOptions" range-key="label" @change="changeType">
          <view class="picker">{{ currentTypeLabel }}</view>
        </picker>
      </view>
      <view class="field">
        <text class="label">发布渠道</text>
        <picker :range="channelOptions" range-key="label" @change="changeChannel">
          <view class="picker">{{ currentChannelLabel }}</view>
        </picker>
      </view>
      <view class="field">
        <text class="label">主题</text>
        <input v-model="form.topic" class="input" placeholder="例如：周末近海观光预约" />
      </view>
      <view class="field">
        <text class="label">亮点</text>
        <textarea v-model="form.highlights" class="textarea small" placeholder="船舶服务、活动、海况、安全提示等" />
      </view>
      <button class="submit-button" :loading="generating" @click="generate">生成文案</button>
    </view>

    <view v-if="generated.contentId" class="card">
      <view class="card-title">{{ generated.title }}</view>
      <textarea v-model="generated.content" class="content-textarea" />
      <view class="actions">
        <button class="secondary-button" @click="copyGenerated">复制</button>
        <button class="submit-button inline" @click="publish(generated)">发布记录</button>
      </view>
    </view>

    <view class="section-title list-title">我的文案</view>
    <view v-if="contents.length === 0" class="empty">暂无文案记录</view>
    <view v-for="item in contents" :key="item.contentId" class="card">
      <view class="card-title">{{ item.title || item.topic }}</view>
      <view class="meta">{{ typeLabel(item.contentType) }} · {{ item.publishStatus === 'published' ? '已发布' : '草稿' }}</view>
      <view class="preview">{{ item.content }}</view>
      <view class="actions">
        <button class="secondary-button" @click="copy(item.content)">复制</button>
        <button v-if="item.publishStatus !== 'published'" class="submit-button inline" @click="publish(item)">发布记录</button>
      </view>
    </view>
  </view>
</template>

<script setup>
import { computed, reactive, ref } from 'vue'
import { onLoad, onPullDownRefresh } from '@dcloudio/uni-app'
import { generateCaptainContent, listCaptainContents, listCaptainVessels, publishCaptainContent, updateCaptainContent } from '@/api/captain'

const vessels = ref([])
const contents = ref([])
const generated = reactive({})
const generating = ref(false)

const form = reactive({
  tenantId: 1,
  vesselId: undefined,
  contentType: 'wechat_article',
  publishChannel: 'captain_wechat',
  topic: '',
  tone: '专业可信',
  highlights: ''
})

const contentTypeOptions = [
  { value: 'wechat_article', label: '公众号文章' },
  { value: 'moments', label: '朋友圈文案' },
  { value: 'short_video_script', label: '短视频脚本' },
  { value: 'live_stream_script', label: '直播推流脚本' }
]
const channelOptions = [
  { value: 'captain_wechat', label: '船长公众号' },
  { value: 'wechat_public', label: '平台公众号' },
  { value: 'video_account', label: '视频号' },
  { value: 'live_stream', label: '直播推流' }
]

const currentVesselName = computed(() => vessels.value.find(item => item.vesselId === form.vesselId)?.vesselName || '请选择船舶')
const currentTypeLabel = computed(() => typeLabel(form.contentType))
const currentChannelLabel = computed(() => channelOptions.find(item => item.value === form.publishChannel)?.label || '请选择')

function typeLabel(value) {
  return contentTypeOptions.find(item => item.value === value)?.label || value || '-'
}

function load() {
  Promise.all([listCaptainVessels(), listCaptainContents()]).then(([vesselRes, contentRes]) => {
    vessels.value = vesselRes.data || []
    contents.value = contentRes.data || []
    if (!form.vesselId && vessels.value.length) {
      form.vesselId = vessels.value[0].vesselId
    }
  }).finally(() => {
    uni.stopPullDownRefresh()
  })
}

function changeVessel(e) {
  const vessel = vessels.value[e.detail.value]
  form.vesselId = vessel && vessel.vesselId
}

function changeType(e) {
  form.contentType = contentTypeOptions[e.detail.value].value
}

function changeChannel(e) {
  form.publishChannel = channelOptions[e.detail.value].value
}

function generate() {
  if (!form.topic) {
    uni.showToast({ title: '主题不能为空', icon: 'none' })
    return
  }
  generating.value = true
  generateCaptainContent(form).then(res => {
    Object.assign(generated, res.data || {})
    uni.showToast({ title: '生成成功', icon: 'success' })
    load()
  }).finally(() => {
    generating.value = false
  })
}

function publish(item) {
  const saveDraft = item === generated ? updateCaptainContent({ ...item }) : Promise.resolve()
  saveDraft.then(() => publishCaptainContent(item.contentId)).then(() => {
    uni.showToast({ title: '发布记录已更新', icon: 'success' })
    load()
  })
}

function copyGenerated() {
  copy(generated.content)
}

function copy(content) {
  uni.setClipboardData({
    data: content || '',
    success: () => uni.showToast({ title: '已复制', icon: 'success' })
  })
}

onLoad(load)
onPullDownRefresh(load)
</script>

<style lang="scss" scoped>
.page {
  min-height: 100vh;
  background: #f5f7fb;
  box-sizing: border-box;
  padding: 24rpx;
}

.form-card,
.card,
.empty {
  background: #fff;
  border-radius: 16rpx;
  box-sizing: border-box;
  margin-bottom: 24rpx;
  padding: 24rpx;
}

.section-title,
.card-title {
  color: #17202a;
  font-size: 32rpx;
  font-weight: 600;
  margin-bottom: 18rpx;
}

.list-title {
  margin: 30rpx 0 18rpx;
}

.field {
  border-bottom: 1rpx solid #edf0f5;
  padding: 22rpx 0;
}

.label {
  color: #5f6b7a;
  display: block;
  font-size: 24rpx;
  margin-bottom: 12rpx;
}

.input,
.picker,
.textarea {
  color: #17202a;
  font-size: 30rpx;
  width: 100%;
}

.textarea.small {
  min-height: 120rpx;
}

.content-textarea {
  background: #f7f9fc;
  border-radius: 10rpx;
  box-sizing: border-box;
  color: #17202a;
  font-size: 28rpx;
  min-height: 420rpx;
  padding: 18rpx;
  width: 100%;
}

.meta,
.preview,
.empty {
  color: #6b7788;
  font-size: 26rpx;
}

.preview {
  display: -webkit-box;
  line-height: 1.6;
  margin-top: 12rpx;
  overflow: hidden;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 4;
}

.actions {
  display: flex;
  gap: 16rpx;
  margin-top: 20rpx;
}

.submit-button,
.secondary-button {
  border-radius: 10rpx;
  font-size: 28rpx;
}

.submit-button {
  background: #1f6feb;
  color: #fff;
  margin-top: 28rpx;
}

.submit-button.inline,
.secondary-button {
  flex: 1;
  margin-top: 0;
}

.secondary-button {
  background: #eef2f7;
  color: #24364b;
}
</style>
