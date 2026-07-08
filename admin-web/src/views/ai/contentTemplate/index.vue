<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="所属租户" prop="tenantId">
        <el-select v-model="queryParams.tenantId" placeholder="请选择租户" clearable filterable style="width: 220px">
          <el-option v-for="tenant in tenantOptions" :key="tenant.tenantId" :label="tenant.tenantName" :value="tenant.tenantId" />
        </el-select>
      </el-form-item>
      <el-form-item label="模板名称" prop="templateName">
        <el-input v-model="queryParams.templateName" placeholder="请输入模板名称" clearable style="width: 220px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="内容类型" prop="contentType">
        <el-select v-model="queryParams.contentType" placeholder="请选择类型" clearable style="width: 180px">
          <el-option v-for="item in contentTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
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
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['ai:contentTemplate:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate" v-hasPermi="['ai:contentTemplate:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['ai:contentTemplate:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['ai:contentTemplate:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="templateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="模板名称" align="center" prop="templateName" min-width="180" :show-overflow-tooltip="true" />
      <el-table-column label="所属租户" align="center" prop="tenantName" min-width="140" :show-overflow-tooltip="true" />
      <el-table-column label="内容类型" align="center" prop="contentType" width="130">
        <template #default="scope">{{ formatOption(contentTypeOptions, scope.row.contentType) }}</template>
      </el-table-column>
      <el-table-column label="发布渠道" align="center" prop="publishChannel" width="130">
        <template #default="scope">{{ formatOption(channelOptions, scope.row.publishChannel) }}</template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sortOrder" width="80" />
      <el-table-column label="状态" align="center" prop="status" width="90">
        <template #default="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'info'">{{ formatOption(statusOptions, scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="170">
        <template #default="scope">{{ parseTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['ai:contentTemplate:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['ai:contentTemplate:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="820px" append-to-body>
      <el-form ref="templateRef" :model="form" :rules="rules" label-width="104px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="所属租户" prop="tenantId">
              <el-select v-model="form.tenantId" placeholder="请选择租户" filterable style="width: 100%">
                <el-option v-for="tenant in tenantOptions" :key="tenant.tenantId" :label="tenant.tenantName" :value="tenant.tenantId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="模板名称" prop="templateName">
              <el-input v-model="form.templateName" placeholder="请输入模板名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="内容类型" prop="contentType">
              <el-select v-model="form.contentType" style="width: 100%">
                <el-option v-for="item in contentTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发布渠道" prop="publishChannel">
              <el-select v-model="form.publishChannel" style="width: 100%">
                <el-option v-for="item in channelOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序" prop="sortOrder">
              <el-input-number v-model="form.sortOrder" controls-position="right" :min="0" style="width: 100%" />
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
            <el-form-item label="模板内容" prop="promptTemplate">
              <el-input v-model="form.promptTemplate" type="textarea" :rows="12" placeholder="可使用 {title}、{topic}、{vesselName}、{captainName}、{highlights}、{tone} 等变量" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" :rows="2" />
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

<script setup lang="ts" name="AiContentTemplate">
import { optionselectTenant } from "@/api/business/tenant"
import { addContentTemplate, delContentTemplate, getContentTemplate, listContentTemplate, updateContentTemplate } from "@/api/ai"
import type { ContentTemplateQueryParams, SeaContentTemplate, SeaTenant } from "@/types"

const { proxy } = getCurrentInstance()

const contentTypeOptions = [
  { value: "wechat_article", label: "公众号文章" },
  { value: "moments", label: "朋友圈文案" },
  { value: "short_video_script", label: "短视频脚本" },
  { value: "live_stream_script", label: "直播推流脚本" }
]
const channelOptions = [
  { value: "captain_wechat", label: "船长公众号" },
  { value: "wechat_public", label: "平台公众号" },
  { value: "video_account", label: "视频号" },
  { value: "live_stream", label: "直播推流" }
]
const statusOptions = [
  { value: "0", label: "启用" },
  { value: "1", label: "停用" }
]

const tenantOptions = ref<SeaTenant[]>([])
const templateList = ref<SeaContentTemplate[]>([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref<number[]>([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const defaultTemplate = "【{title}】\n\n这次主题是“{topic}”。\n\n服务亮点：\n1. {highlights}\n2. 船长{captainName}负责航前确认，重点关注天气、航线、载客和设备状态。\n3. 如遇临时海况变化，将第一时间调整计划并同步通知。\n\n欢迎通过公众号咨询预约，也欢迎关注后续出航动态。"

const data = reactive({
  form: {} as SeaContentTemplate,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    tenantId: undefined,
    templateName: undefined,
    contentType: undefined,
    status: undefined
  } as ContentTemplateQueryParams,
  rules: {
    tenantId: [{ required: true, message: "所属租户不能为空", trigger: "change" }],
    templateName: [{ required: true, message: "模板名称不能为空", trigger: "blur" }],
    contentType: [{ required: true, message: "内容类型不能为空", trigger: "change" }],
    publishChannel: [{ required: true, message: "发布渠道不能为空", trigger: "change" }],
    promptTemplate: [{ required: true, message: "模板内容不能为空", trigger: "blur" }]
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
  listContentTemplate(queryParams.value).then(response => {
    templateList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

function reset() {
  form.value = {
    templateId: undefined,
    tenantId: 1,
    templateName: undefined,
    contentType: "wechat_article",
    publishChannel: "captain_wechat",
    promptTemplate: defaultTemplate,
    status: "0",
    sortOrder: 0,
    remark: undefined
  }
  proxy.resetForm("templateRef")
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleSelectionChange(selection: SeaContentTemplate[]) {
  ids.value = selection.map(item => item.templateId!)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

function handleAdd() {
  reset()
  open.value = true
  title.value = "添加文案模板"
}

function handleUpdate(row?: SeaContentTemplate) {
  reset()
  const templateId = row?.templateId || ids.value[0]
  getContentTemplate(templateId).then(response => {
    form.value = response.data!
    open.value = true
    title.value = "修改文案模板"
  })
}

function submitForm() {
  proxy.$refs["templateRef"].validate((valid: boolean) => {
    if (!valid) return
    if (form.value.templateId) {
      updateContentTemplate(form.value).then(() => {
        proxy.$modal.msgSuccess("修改成功")
        open.value = false
        getList()
      })
      return
    }
    addContentTemplate(form.value).then(() => {
      proxy.$modal.msgSuccess("新增成功")
      open.value = false
      getList()
    })
  })
}

function handleDelete(row?: SeaContentTemplate) {
  const templateIds = row?.templateId || ids.value
  proxy.$modal.confirm('是否确认删除模板编号为"' + templateIds + '"的数据项？').then(() => {
    return delContentTemplate(templateIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

function handleExport() {
  proxy.download("ai/contentTemplate/export", {
    ...queryParams.value
  }, `content_template_${new Date().getTime()}.xlsx`)
}

function cancel() {
  open.value = false
  reset()
}

getTenantOptions()
getList()
</script>
