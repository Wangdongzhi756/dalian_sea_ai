<template>
  <view class="page">
    <view class="form-card">
      <view class="field">
        <text class="label">租户ID</text>
        <input v-model="form.tenantId" type="number" class="input" placeholder="请输入租户ID" />
      </view>
      <view class="field">
        <text class="label">船舶ID</text>
        <input v-model="form.vesselId" type="number" class="input" placeholder="可选" />
      </view>
      <view class="field">
        <text class="label">船员ID</text>
        <input v-model="form.crewId" type="number" class="input" placeholder="可选" />
      </view>
      <view class="field">
        <text class="label">申请类型</text>
        <picker :range="applicationTypeOptions" range-key="label" @change="changeType">
          <view class="picker">{{ currentTypeLabel }}</view>
        </picker>
      </view>
      <view class="field">
        <text class="label">紧急程度</text>
        <picker :range="priorityOptions" range-key="label" @change="changePriority">
          <view class="picker">{{ currentPriorityLabel }}</view>
        </picker>
      </view>
      <view class="field">
        <text class="label">申请人</text>
        <input v-model="form.applicantName" class="input" placeholder="请输入申请人" />
      </view>
      <view class="field">
        <text class="label">联系电话</text>
        <input v-model="form.applicantPhone" class="input" placeholder="请输入联系电话" />
      </view>
      <view class="field">
        <text class="label">申请标题</text>
        <input v-model="form.title" class="input" placeholder="请输入申请标题" />
      </view>
      <view class="field">
        <text class="label">申请内容</text>
        <textarea v-model="form.content" class="textarea" placeholder="请输入申请内容" />
      </view>
      <view class="field">
        <text class="label">备注</text>
        <textarea v-model="form.remark" class="textarea small" placeholder="可选" />
      </view>
    </view>
    <button class="submit-button" :loading="submitting" @click="submit">提交申请</button>
  </view>
</template>

<script setup>
import { computed, reactive, ref } from 'vue'
import { addApplication } from '@/api/business/application'

const submitting = ref(false)
const form = reactive({
  tenantId: 1,
  vesselId: '',
  crewId: '',
  applicationType: 'vessel_service',
  priority: 'normal',
  applicantName: '',
  applicantPhone: '',
  title: '',
  content: '',
  remark: ''
})

const applicationTypeOptions = [
  { value: 'vessel_service', label: '船舶业务' },
  { value: 'crew_service', label: '船员业务' },
  { value: 'certificate', label: '证照办理' },
  { value: 'repair', label: '维修报备' },
  { value: 'other', label: '其他' }
]
const priorityOptions = [
  { value: 'normal', label: '普通' },
  { value: 'urgent', label: '紧急' },
  { value: 'critical', label: '特急' }
]

const currentTypeLabel = computed(() => applicationTypeOptions.find(item => item.value === form.applicationType)?.label || '请选择')
const currentPriorityLabel = computed(() => priorityOptions.find(item => item.value === form.priority)?.label || '请选择')

function changeType(e) {
  form.applicationType = applicationTypeOptions[e.detail.value].value
}

function changePriority(e) {
  form.priority = priorityOptions[e.detail.value].value
}

function validate() {
  if (!form.tenantId) return '租户ID不能为空'
  if (!form.applicationType) return '申请类型不能为空'
  if (!form.applicantName) return '申请人不能为空'
  if (!form.title) return '申请标题不能为空'
  return ''
}

function submit() {
  const message = validate()
  if (message) {
    uni.showToast({ title: message, icon: 'none' })
    return
  }
  submitting.value = true
  addApplication({
    ...form,
    tenantId: Number(form.tenantId),
    vesselId: form.vesselId ? Number(form.vesselId) : undefined,
    crewId: form.crewId ? Number(form.crewId) : undefined
  }).then(() => {
    uni.showToast({ title: '提交成功', icon: 'success' })
    setTimeout(() => {
      uni.redirectTo({ url: '/pages/business/application/index' })
    }, 600)
  }).finally(() => {
    submitting.value = false
  })
}
</script>

<style lang="scss" scoped>
.page {
  min-height: 100vh;
  background: #f5f7fb;
  padding: 24rpx;
  box-sizing: border-box;
}

.form-card {
  background: #ffffff;
  border-radius: 16rpx;
  padding: 8rpx 24rpx;
}

.field {
  border-bottom: 1rpx solid #edf0f5;
  padding: 24rpx 0;
}

.field:last-child {
  border-bottom: 0;
}

.label {
  color: #5f6b7a;
  display: block;
  font-size: 24rpx;
  margin-bottom: 12rpx;
}

.input,
.picker {
  color: #17202a;
  font-size: 30rpx;
  min-height: 44rpx;
}

.textarea {
  color: #17202a;
  font-size: 30rpx;
  min-height: 180rpx;
  width: 100%;
}

.textarea.small {
  min-height: 110rpx;
}

.submit-button {
  background: #1f6feb;
  border-radius: 10rpx;
  color: #ffffff;
  font-size: 30rpx;
  margin-top: 28rpx;
}
</style>
