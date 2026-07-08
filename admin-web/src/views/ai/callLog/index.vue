<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="所属租户" prop="tenantId">
        <el-select v-model="queryParams.tenantId" placeholder="请选择租户" clearable filterable style="width: 220px">
          <el-option v-for="tenant in tenantOptions" :key="tenant.tenantId" :label="tenant.tenantName" :value="tenant.tenantId" />
        </el-select>
      </el-form-item>
      <el-form-item label="场景" prop="scene">
        <el-select v-model="queryParams.scene" placeholder="请选择场景" clearable style="width: 160px">
          <el-option v-for="item in sceneOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="问题" prop="question">
        <el-input v-model="queryParams.question" placeholder="请输入问题关键字" clearable style="width: 220px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="结果" prop="successFlag">
        <el-select v-model="queryParams.successFlag" placeholder="请选择结果" clearable style="width: 140px">
          <el-option v-for="item in successOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['ai:callLog:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['ai:callLog:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="callLogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="所属租户" align="center" prop="tenantName" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="场景" align="center" prop="scene" width="120">
        <template #default="scope">
          {{ formatOption(sceneOptions, scope.row.scene) }}
        </template>
      </el-table-column>
      <el-table-column label="问题" align="center" prop="question" min-width="220" :show-overflow-tooltip="true" />
      <el-table-column label="供应商" align="center" prop="providerName" width="100" />
      <el-table-column label="模型" align="center" prop="modelName" width="150" :show-overflow-tooltip="true" />
      <el-table-column label="Token" align="center" prop="totalTokens" width="90" />
      <el-table-column label="耗时" align="center" prop="latencyMs" width="100">
        <template #default="scope">{{ scope.row.latencyMs || 0 }} ms</template>
      </el-table-column>
      <el-table-column label="结果" align="center" prop="successFlag" width="90">
        <template #default="scope">
          <el-tag :type="scope.row.successFlag === '0' ? 'success' : 'danger'">{{ formatOption(successOptions, scope.row.successFlag) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="调用时间" align="center" prop="createTime" width="170">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="130" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleDetail(scope.row)" v-hasPermi="['ai:callLog:query']">详情</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['ai:callLog:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog title="调用日志详情" v-model="detailOpen" width="760px" append-to-body>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="租户">{{ detail.tenantName }}</el-descriptions-item>
        <el-descriptions-item label="场景">{{ formatOption(sceneOptions, detail.scene) }}</el-descriptions-item>
        <el-descriptions-item label="供应商">{{ detail.providerName }}</el-descriptions-item>
        <el-descriptions-item label="模型">{{ detail.modelName }}</el-descriptions-item>
        <el-descriptions-item label="Token">{{ detail.totalTokens || 0 }}</el-descriptions-item>
        <el-descriptions-item label="耗时">{{ detail.latencyMs || 0 }} ms</el-descriptions-item>
        <el-descriptions-item label="问题" :span="2">{{ detail.question }}</el-descriptions-item>
        <el-descriptions-item label="回答" :span="2">{{ detail.answer || '-' }}</el-descriptions-item>
        <el-descriptions-item label="错误信息" :span="2">{{ detail.errorMessage || '-' }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="detailOpen = false">关 闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="AiCallLog">
import { optionselectTenant } from "@/api/business/tenant"
import { delCallLog, getCallLog, listCallLog } from "@/api/ai"
import type { AiCallLogQueryParams, SeaAiCallLog, SeaTenant } from "@/types"

const { proxy } = getCurrentInstance()

const sceneOptions = [
  { value: "knowledge_qa", label: "知识问答" },
  { value: "mobile_assistant", label: "移动助手" }
]
const successOptions = [
  { value: "0", label: "成功" },
  { value: "1", label: "失败" }
]

const tenantOptions = ref<SeaTenant[]>([])
const callLogList = ref<SeaAiCallLog[]>([])
const detail = ref<SeaAiCallLog>({})
const detailOpen = ref<boolean>(false)
const loading = ref<boolean>(true)
const showSearch = ref<boolean>(true)
const ids = ref<number[]>([])
const multiple = ref<boolean>(true)
const total = ref<number>(0)

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    tenantId: undefined,
    scene: undefined,
    question: undefined,
    successFlag: undefined
  } as AiCallLogQueryParams
})

const { queryParams } = toRefs(data)

function formatOption(options: Array<{ value: string; label: string }>, value?: string) {
  return options.find(item => item.value === value)?.label || value || "-"
}

function getTenantOptions() {
  optionselectTenant().then(response => {
    tenantOptions.value = response.data || []
  })
}

function getList() {
  loading.value = true
  listCallLog(queryParams.value).then(response => {
    callLogList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleSelectionChange(selection: SeaAiCallLog[]) {
  ids.value = selection.map(item => item.logId!)
  multiple.value = !selection.length
}

function handleDetail(row: SeaAiCallLog) {
  getCallLog(row.logId!).then(response => {
    detail.value = response.data || {}
    detailOpen.value = true
  })
}

function handleDelete(row?: SeaAiCallLog) {
  const logIds = row?.logId || ids.value
  proxy.$modal.confirm('是否确认删除调用日志编号为"' + logIds + '"的数据项？').then(function() {
    return delCallLog(logIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

function handleExport() {
  proxy.download("ai/callLog/export", {
    ...queryParams.value
  }, `ai_call_log_${new Date().getTime()}.xlsx`)
}

getTenantOptions()
getList()
</script>
