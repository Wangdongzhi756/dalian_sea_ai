<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="所属租户" prop="tenantId">
        <el-select v-model="queryParams.tenantId" placeholder="请选择租户" clearable filterable style="width: 220px">
          <el-option v-for="tenant in tenantOptions" :key="tenant.tenantId" :label="tenant.tenantName" :value="tenant.tenantId" />
        </el-select>
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input v-model="queryParams.title" placeholder="请输入标题" clearable style="width: 220px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="分类" prop="category">
        <el-select v-model="queryParams.category" placeholder="请选择分类" clearable style="width: 160px">
          <el-option v-for="item in categoryOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 140px">
          <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['ai:knowledge:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate" v-hasPermi="['ai:knowledge:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['ai:knowledge:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['ai:knowledge:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="knowledgeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="标题" align="center" prop="title" min-width="190" :show-overflow-tooltip="true" />
      <el-table-column label="所属租户" align="center" prop="tenantName" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="分类" align="center" prop="category" width="120">
        <template #default="scope">
          <el-tag>{{ formatOption(categoryOptions, scope.row.category) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="来源" align="center" prop="sourceType" width="100">
        <template #default="scope">
          {{ formatOption(sourceTypeOptions, scope.row.sourceType) }}
        </template>
      </el-table-column>
      <el-table-column label="标签" align="center" prop="tags" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" prop="status" width="90">
        <template #default="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'info'">{{ formatOption(statusOptions, scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="170">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['ai:knowledge:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['ai:knowledge:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="760px" append-to-body>
      <el-form ref="knowledgeRef" :model="form" :rules="rules" label-width="92px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="所属租户" prop="tenantId">
              <el-select v-model="form.tenantId" placeholder="请选择租户" filterable style="width: 100%">
                <el-option v-for="tenant in tenantOptions" :key="tenant.tenantId" :label="tenant.tenantName" :value="tenant.tenantId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分类" prop="category">
              <el-select v-model="form.category" placeholder="请选择分类" style="width: 100%">
                <el-option v-for="item in categoryOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="来源" prop="sourceType">
              <el-select v-model="form.sourceType" placeholder="请选择来源" style="width: 100%">
                <el-option v-for="item in sourceTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio label="0">启用</el-radio>
                <el-radio label="1">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入知识标题" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="标签" prop="tags">
              <el-input v-model="form.tags" placeholder="多个标签用逗号分隔" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="内容" prop="content">
              <el-input v-model="form.content" type="textarea" :rows="8" placeholder="请输入知识内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="AiKnowledge">
import { optionselectTenant } from "@/api/business/tenant"
import { addKnowledge, delKnowledge, getKnowledge, listKnowledge, updateKnowledge } from "@/api/ai"
import type { AiKnowledgeQueryParams, SeaAiKnowledge, SeaTenant } from "@/types"

const { proxy } = getCurrentInstance()

const categoryOptions = [
  { value: "operation", label: "营运办理" },
  { value: "safety", label: "安全规范" },
  { value: "certificate", label: "证照材料" },
  { value: "crew", label: "船员管理" },
  { value: "other", label: "其他" }
]
const sourceTypeOptions = [
  { value: "manual", label: "手工录入" },
  { value: "document", label: "文档导入" },
  { value: "system", label: "系统生成" }
]
const statusOptions = [
  { value: "0", label: "启用" },
  { value: "1", label: "停用" }
]

const tenantOptions = ref<SeaTenant[]>([])
const knowledgeList = ref<SeaAiKnowledge[]>([])
const open = ref<boolean>(false)
const loading = ref<boolean>(true)
const showSearch = ref<boolean>(true)
const ids = ref<number[]>([])
const single = ref<boolean>(true)
const multiple = ref<boolean>(true)
const total = ref<number>(0)
const title = ref<string>("")

const data = reactive({
  form: {} as SeaAiKnowledge,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    tenantId: undefined,
    title: undefined,
    category: undefined,
    status: undefined
  } as AiKnowledgeQueryParams,
  rules: {
    tenantId: [{ required: true, message: "所属租户不能为空", trigger: "change" }],
    title: [{ required: true, message: "知识标题不能为空", trigger: "blur" }],
    category: [{ required: true, message: "知识分类不能为空", trigger: "change" }],
    content: [{ required: true, message: "知识内容不能为空", trigger: "blur" }]
  }
})

const { queryParams, form, rules } = toRefs(data)

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
  listKnowledge(queryParams.value).then(response => {
    knowledgeList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

function cancel() {
  open.value = false
  reset()
}

function reset() {
  form.value = {
    knowledgeId: undefined,
    tenantId: undefined,
    title: undefined,
    category: "operation",
    sourceType: "manual",
    content: undefined,
    tags: undefined,
    status: "0",
    remark: undefined
  }
  proxy.resetForm("knowledgeRef")
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleSelectionChange(selection: SeaAiKnowledge[]) {
  ids.value = selection.map(item => item.knowledgeId!)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

function handleAdd() {
  reset()
  open.value = true
  title.value = "添加知识"
}

function handleUpdate(row?: SeaAiKnowledge) {
  reset()
  const knowledgeId = row?.knowledgeId || ids.value[0]
  getKnowledge(knowledgeId).then(response => {
    form.value = response.data!
    open.value = true
    title.value = "修改知识"
  })
}

function submitForm() {
  proxy.$refs["knowledgeRef"].validate((valid: boolean) => {
    if (valid) {
      if (form.value.knowledgeId != undefined) {
        updateKnowledge(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addKnowledge(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

function handleDelete(row?: SeaAiKnowledge) {
  const knowledgeIds = row?.knowledgeId || ids.value
  proxy.$modal.confirm('是否确认删除知识编号为"' + knowledgeIds + '"的数据项？').then(function() {
    return delKnowledge(knowledgeIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

function handleExport() {
  proxy.download("ai/knowledge/export", {
    ...queryParams.value
  }, `ai_knowledge_${new Date().getTime()}.xlsx`)
}

getTenantOptions()
getList()
</script>
