<template>
  <div class="assistant-page">
    <section class="assistant-main">
      <div class="assistant-panel">
        <div class="assistant-header">
          <div>
            <h2>AI助手</h2>
            <p>基于当前租户知识库回答海事业务问题</p>
          </div>
          <el-tag>{{ modelLabel }}</el-tag>
        </div>

        <el-form ref="askRef" :model="form" :rules="rules" label-width="82px">
          <el-row :gutter="16">
            <el-col :span="12">
              <el-form-item label="所属租户" prop="tenantId">
                <el-select v-model="form.tenantId" placeholder="请选择租户" filterable style="width: 100%">
                  <el-option v-for="tenant in tenantOptions" :key="tenant.tenantId" :label="tenant.tenantName" :value="tenant.tenantId" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="知识分类" prop="category">
                <el-select v-model="form.category" placeholder="不限分类" clearable style="width: 100%">
                  <el-option v-for="item in categoryOptions" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="问题" prop="question">
            <el-input v-model="form.question" type="textarea" :rows="5" placeholder="例如：办理船舶营运证需要哪些材料？" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="Promotion" :loading="loading" @click="submitAsk" v-hasPermi="['ai:assistant:ask']">发送</el-button>
            <el-button icon="Refresh" @click="resetAsk">重置</el-button>
          </el-form-item>
        </el-form>
      </div>

      <div class="answer-panel">
        <div class="answer-title">
          <span>回答</span>
          <el-tag v-if="response.matchedKnowledgeTitle" type="success">匹配：{{ response.matchedKnowledgeTitle }}</el-tag>
        </div>
        <div v-if="response.answer" class="answer-content">{{ response.answer }}</div>
        <el-empty v-else description="发送问题后显示回答" />
        <div v-if="response.logId" class="answer-meta">
          <span>日志ID：{{ response.logId }}</span>
          <span>供应商：{{ response.providerName }}</span>
          <span>模型：{{ response.modelName }}</span>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts" name="AiAssistant">
import { optionselectTenant } from "@/api/business/tenant"
import { askAi } from "@/api/ai"
import type { SeaAiChatRequest, SeaAiChatResponse, SeaTenant } from "@/types"

const { proxy } = getCurrentInstance()

const categoryOptions = [
  { value: "operation", label: "营运办理" },
  { value: "safety", label: "安全规范" },
  { value: "certificate", label: "证照材料" },
  { value: "crew", label: "船员管理" },
  { value: "other", label: "其他" }
]
const modelLabel = "local / knowledge-rule-v1"

const tenantOptions = ref<SeaTenant[]>([])
const loading = ref(false)
const response = ref<SeaAiChatResponse>({})

const data = reactive({
  form: {
    tenantId: undefined,
    scene: "knowledge_qa",
    category: undefined,
    question: ""
  } as SeaAiChatRequest,
  rules: {
    tenantId: [{ required: true, message: "所属租户不能为空", trigger: "change" }],
    question: [{ required: true, message: "问题不能为空", trigger: "blur" }]
  }
})

const { form, rules } = toRefs(data)

function getTenantOptions() {
  optionselectTenant().then(res => {
    tenantOptions.value = res.data || []
    if (!form.value.tenantId && tenantOptions.value.length) {
      form.value.tenantId = tenantOptions.value[0].tenantId
    }
  })
}

function submitAsk() {
  proxy.$refs["askRef"].validate((valid: boolean) => {
    if (!valid) return
    loading.value = true
    askAi(form.value).then(res => {
      response.value = res.data || {}
    }).finally(() => {
      loading.value = false
    })
  })
}

function resetAsk() {
  form.value.question = ""
  form.value.category = undefined
  response.value = {}
}

getTenantOptions()
</script>

<style scoped>
.assistant-page {
  background: #f5f7fb;
  min-height: calc(100vh - 84px);
  padding: 20px;
}

.assistant-main {
  display: grid;
  gap: 16px;
  grid-template-columns: minmax(420px, 520px) minmax(0, 1fr);
}

.assistant-panel,
.answer-panel {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
}

.assistant-header,
.answer-title,
.answer-meta {
  align-items: center;
  display: flex;
  justify-content: space-between;
}

.assistant-header {
  margin-bottom: 20px;
}

.assistant-header h2 {
  color: #1f2d3d;
  font-size: 20px;
  line-height: 28px;
  margin: 0;
}

.assistant-header p {
  color: #7a8491;
  font-size: 13px;
  margin: 4px 0 0;
}

.answer-title {
  color: #1f2d3d;
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 16px;
}

.answer-content {
  color: #263445;
  font-size: 14px;
  line-height: 24px;
  min-height: 220px;
  white-space: pre-wrap;
}

.answer-meta {
  border-top: 1px solid #edf0f5;
  color: #8c96a3;
  font-size: 12px;
  margin-top: 18px;
  padding-top: 12px;
}

@media (max-width: 1080px) {
  .assistant-main {
    grid-template-columns: 1fr;
  }
}
</style>
