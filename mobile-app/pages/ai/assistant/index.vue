<template>
  <view class="page">
    <view class="panel">
      <view class="header">
        <text class="title">AI助手</text>
        <text class="subtitle">基于海事知识库回答业务问题</text>
      </view>

      <view class="field">
        <text class="label">租户ID</text>
        <input v-model="form.tenantId" type="number" class="input" placeholder="请输入租户ID" />
      </view>

      <view class="field">
        <text class="label">知识分类</text>
        <picker :range="categoryOptions" range-key="label" @change="changeCategory">
          <view class="picker">{{ currentCategoryLabel }}</view>
        </picker>
      </view>

      <view class="field">
        <text class="label">问题</text>
        <textarea v-model="form.question" class="textarea" placeholder="例如：办理船舶营运证需要哪些材料？" />
      </view>

      <button class="submit-button" :loading="loading" @click="submitAsk">发送</button>
    </view>

    <view class="answer-card">
      <view class="answer-head">
        <text>回答</text>
        <text v-if="response.matchedKnowledgeTitle" class="match">匹配：{{ response.matchedKnowledgeTitle }}</text>
      </view>
      <text v-if="response.answer" class="answer">{{ response.answer }}</text>
      <view v-else class="empty">
        <uni-icons type="chat" size="40" color="#b7c0cc"></uni-icons>
        <text>发送问题后显示回答</text>
      </view>
      <view v-if="response.logId" class="meta">
        <text>日志ID：{{ response.logId }}</text>
        <text>{{ response.providerName }} / {{ response.modelName }}</text>
      </view>
    </view>
  </view>
</template>

<script setup>
import { computed, reactive, ref } from 'vue'
import { askAi } from '@/api/ai'

const loading = ref(false)
const response = ref({})
const form = reactive({
  tenantId: 1,
  scene: 'mobile_assistant',
  category: '',
  question: ''
})

const categoryOptions = [
  { value: '', label: '不限分类' },
  { value: 'operation', label: '营运办理' },
  { value: 'safety', label: '安全规范' },
  { value: 'certificate', label: '证照材料' },
  { value: 'crew', label: '船员管理' },
  { value: 'other', label: '其他' }
]

const currentCategoryLabel = computed(() => categoryOptions.find(item => item.value === form.category)?.label || '不限分类')

function changeCategory(e) {
  form.category = categoryOptions[e.detail.value].value
}

function submitAsk() {
  if (!form.tenantId) {
    uni.showToast({ title: '租户ID不能为空', icon: 'none' })
    return
  }
  if (!form.question) {
    uni.showToast({ title: '问题不能为空', icon: 'none' })
    return
  }
  loading.value = true
  askAi({
    tenantId: Number(form.tenantId),
    scene: form.scene,
    category: form.category || undefined,
    question: form.question
  }).then(res => {
    response.value = res.data || {}
  }).finally(() => {
    loading.value = false
  })
}
</script>

<style lang="scss" scoped>
.page {
  background: #f5f7fb;
  box-sizing: border-box;
  min-height: 100vh;
  padding: 24rpx;
}

.panel,
.answer-card {
  background: #ffffff;
  border-radius: 16rpx;
  padding: 24rpx;
}

.header {
  margin-bottom: 18rpx;
}

.title {
  color: #17202a;
  display: block;
  font-size: 36rpx;
  font-weight: 700;
  line-height: 48rpx;
}

.subtitle {
  color: #7a8491;
  display: block;
  font-size: 24rpx;
  margin-top: 6rpx;
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
.picker {
  color: #17202a;
  font-size: 30rpx;
  min-height: 44rpx;
}

.textarea {
  color: #17202a;
  font-size: 30rpx;
  min-height: 190rpx;
  width: 100%;
}

.submit-button {
  background: #1f6feb;
  border-radius: 10rpx;
  color: #ffffff;
  font-size: 30rpx;
  margin-top: 24rpx;
}

.answer-card {
  margin-top: 20rpx;
}

.answer-head {
  align-items: center;
  color: #17202a;
  display: flex;
  font-size: 30rpx;
  font-weight: 700;
  justify-content: space-between;
}

.match {
  color: #2f7d32;
  font-size: 22rpx;
  font-weight: 400;
  max-width: 420rpx;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.answer {
  color: #263445;
  display: block;
  font-size: 28rpx;
  line-height: 44rpx;
  margin-top: 18rpx;
  white-space: pre-wrap;
}

.empty {
  align-items: center;
  color: #8c96a3;
  display: flex;
  flex-direction: column;
  gap: 12rpx;
  padding: 70rpx 0;
}

.meta {
  border-top: 1rpx solid #edf0f5;
  color: #8c96a3;
  display: flex;
  font-size: 22rpx;
  justify-content: space-between;
  margin-top: 22rpx;
  padding-top: 18rpx;
}
</style>
