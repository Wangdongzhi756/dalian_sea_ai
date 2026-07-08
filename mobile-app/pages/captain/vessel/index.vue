<template>
  <view class="page">
    <view v-if="vessels.length === 0" class="empty">暂无匹配到当前手机号关联的船舶</view>

    <view v-for="item in vessels" :key="item.vesselId" class="card" @click="edit(item)">
      <view class="card-title">{{ item.vesselName }}</view>
      <view class="row"><text>船舶编码</text><text>{{ item.vesselCode }}</text></view>
      <view class="row"><text>母港</text><text>{{ item.homePort || '-' }}</text></view>
      <view class="row"><text>载客人数</text><text>{{ item.capacity || 0 }}人</text></view>
      <view class="row"><text>服务区域</text><text>{{ item.serviceArea || '-' }}</text></view>
    </view>

    <view v-if="editing" class="form-card">
      <view class="section-title">编辑船舶资料</view>
      <view class="field">
        <text class="label">船舶名称</text>
        <input v-model="form.vesselName" class="input" />
      </view>
      <view class="field">
        <text class="label">船舶类型</text>
        <picker :range="vesselTypeOptions" range-key="label" @change="changeType">
          <view class="picker">{{ currentTypeLabel }}</view>
        </picker>
      </view>
      <view class="field">
        <text class="label">载客人数</text>
        <input v-model="form.capacity" type="number" class="input" />
      </view>
      <view class="field">
        <text class="label">母港</text>
        <input v-model="form.homePort" class="input" />
      </view>
      <view class="field">
        <text class="label">船长</text>
        <input v-model="form.captainName" class="input" />
      </view>
      <view class="field">
        <text class="label">服务区域</text>
        <textarea v-model="form.serviceArea" class="textarea small" />
      </view>
      <button class="submit-button" :loading="submitting" @click="submit">保存船舶资料</button>
    </view>
  </view>
</template>

<script setup>
import { computed, reactive, ref } from 'vue'
import { onLoad, onPullDownRefresh } from '@dcloudio/uni-app'
import { listCaptainVessels, updateCaptainVessel } from '@/api/captain'

const vessels = ref([])
const editing = ref(false)
const submitting = ref(false)
const form = reactive({})

const vesselTypeOptions = [
  { value: 'sightseeing', label: '观光船' },
  { value: 'fishing', label: '渔船' },
  { value: 'transport', label: '交通船' },
  { value: 'service', label: '服务船' }
]

const currentTypeLabel = computed(() => vesselTypeOptions.find(item => item.value === form.vesselType)?.label || '请选择')

function load() {
  listCaptainVessels().then(res => {
    vessels.value = res.data || []
  }).finally(() => {
    uni.stopPullDownRefresh()
  })
}

function edit(item) {
  Object.assign(form, item)
  editing.value = true
}

function changeType(e) {
  form.vesselType = vesselTypeOptions[e.detail.value].value
}

function submit() {
  if (!form.vesselName) {
    uni.showToast({ title: '船舶名称不能为空', icon: 'none' })
    return
  }
  submitting.value = true
  updateCaptainVessel({
    ...form,
    capacity: form.capacity ? Number(form.capacity) : 0
  }).then(() => {
    uni.showToast({ title: '保存成功', icon: 'success' })
    editing.value = false
    load()
  }).finally(() => {
    submitting.value = false
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

.empty,
.card,
.form-card {
  background: #fff;
  border-radius: 16rpx;
  box-sizing: border-box;
  margin-bottom: 24rpx;
  padding: 24rpx;
}

.empty {
  color: #7a8699;
  text-align: center;
}

.card-title,
.section-title {
  color: #17202a;
  font-size: 34rpx;
  font-weight: 600;
  margin-bottom: 18rpx;
}

.row {
  color: #4d5b6a;
  display: flex;
  font-size: 26rpx;
  justify-content: space-between;
  line-height: 1.9;
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

.submit-button {
  background: #1f6feb;
  border-radius: 10rpx;
  color: #fff;
  font-size: 30rpx;
  margin-top: 28rpx;
}
</style>
