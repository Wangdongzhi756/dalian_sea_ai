<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="所属租户" prop="tenantId">
        <el-select v-model="queryParams.tenantId" placeholder="请选择租户" clearable filterable style="width: 220px">
          <el-option v-for="tenant in tenantOptions" :key="tenant.tenantId" :label="tenant.tenantName" :value="tenant.tenantId" />
        </el-select>
      </el-form-item>
      <el-form-item label="所属船舶" prop="vesselId">
        <el-select v-model="queryParams.vesselId" placeholder="请选择船舶" clearable filterable style="width: 220px">
          <el-option v-for="vessel in vesselOptions" :key="vessel.vesselId" :label="vessel.vesselName" :value="vessel.vesselId" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请类型" prop="applicationType">
        <el-select v-model="queryParams.applicationType" placeholder="请选择" clearable style="width: 160px">
          <el-option v-for="item in applicationTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请单号" prop="applicationNo">
        <el-input v-model="queryParams.applicationNo" placeholder="请输入申请单号" clearable style="width: 190px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 150px">
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
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['business:application:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate" v-hasPermi="['business:application:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Checked" :disabled="single" @click="handleAudit" v-hasPermi="['business:application:audit']">审核</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['business:application:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['business:application:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="applicationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="申请单号" align="center" prop="applicationNo" min-width="155" :show-overflow-tooltip="true" />
      <el-table-column label="申请类型" align="center" prop="applicationType" width="120">
        <template #default="scope">
          <el-tag>{{ formatOption(applicationTypeOptions, scope.row.applicationType) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="申请标题" align="center" prop="title" min-width="170" :show-overflow-tooltip="true" />
      <el-table-column label="租户" align="center" prop="tenantName" min-width="145" :show-overflow-tooltip="true" />
      <el-table-column label="船舶" align="center" prop="vesselName" min-width="130" :show-overflow-tooltip="true" />
      <el-table-column label="船员" align="center" prop="crewName" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="申请人" align="center" prop="applicantName" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="紧急程度" align="center" prop="priority" width="100">
        <template #default="scope">
          <el-tag :type="priorityTagType(scope.row.priority)">{{ formatOption(priorityOptions, scope.row.priority) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template #default="scope">
          <el-tag :type="statusTagType(scope.row.status)">{{ formatOption(statusOptions, scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="进度" align="center" prop="progressPercent" width="150">
        <template #default="scope">
          <el-progress :percentage="scope.row.progressPercent || 0" :stroke-width="8" />
        </template>
      </el-table-column>
      <el-table-column label="当前节点" align="center" prop="currentStep" width="110" />
      <el-table-column label="提交时间" align="center" prop="submittedTime" width="170">
        <template #default="scope">
          <span>{{ parseTime(scope.row.submittedTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="245" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleDetail(scope.row)" v-hasPermi="['business:application:query']">详情</el-button>
          <el-button link type="primary" icon="Checked" @click="handleAudit(scope.row)" v-hasPermi="['business:application:audit']">审核</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['business:application:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['business:application:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="780px" append-to-body>
      <el-form ref="applicationRef" :model="form" :rules="rules" label-width="92px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="所属租户" prop="tenantId">
              <el-select v-model="form.tenantId" placeholder="请选择租户" filterable style="width: 100%">
                <el-option v-for="tenant in tenantOptions" :key="tenant.tenantId" :label="tenant.tenantName" :value="tenant.tenantId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属船舶" prop="vesselId">
              <el-select v-model="form.vesselId" placeholder="请选择船舶" clearable filterable style="width: 100%">
                <el-option v-for="vessel in vesselOptions" :key="vessel.vesselId" :label="vessel.vesselName" :value="vessel.vesselId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属船员" prop="crewId">
              <el-select v-model="form.crewId" placeholder="请选择船员" clearable filterable style="width: 100%">
                <el-option v-for="crew in crewOptions" :key="crew.crewId" :label="crew.crewName" :value="crew.crewId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请类型" prop="applicationType">
              <el-select v-model="form.applicationType" placeholder="请选择类型" style="width: 100%">
                <el-option v-for="item in applicationTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请人" prop="applicantName">
              <el-input v-model="form.applicantName" placeholder="请输入申请人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="applicantPhone">
              <el-input v-model="form.applicantPhone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="紧急程度" prop="priority">
              <el-select v-model="form.priority" placeholder="请选择紧急程度" style="width: 100%">
                <el-option v-for="item in priorityOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请单号" prop="applicationNo">
              <el-input v-model="form.applicationNo" placeholder="留空自动生成" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="申请标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入申请标题" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="申请内容" prop="content">
              <el-input v-model="form.content" type="textarea" :rows="4" placeholder="请输入申请内容" />
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

    <el-dialog title="业务申请审核" v-model="auditOpen" width="560px" append-to-body>
      <el-form ref="auditRef" :model="auditForm" :rules="auditRules" label-width="92px">
        <el-form-item label="申请单号">
          <el-input v-model="auditForm.applicationNo" disabled />
        </el-form-item>
        <el-form-item label="申请标题">
          <el-input v-model="auditForm.title" disabled />
        </el-form-item>
        <el-form-item label="审核结果" prop="status">
          <el-radio-group v-model="auditForm.status">
            <el-radio label="approved">通过</el-radio>
            <el-radio label="rejected">驳回</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核意见" prop="auditOpinion">
          <el-input v-model="auditForm.auditOpinion" type="textarea" :rows="4" placeholder="请输入审核意见" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitAudit">确 定</el-button>
          <el-button @click="auditOpen = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <el-drawer v-model="detailOpen" title="业务申请详情" size="760px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="申请单号">{{ detail.applicationNo }}</el-descriptions-item>
        <el-descriptions-item label="申请类型">{{ formatOption(applicationTypeOptions, detail.applicationType) }}</el-descriptions-item>
        <el-descriptions-item label="申请标题" :span="2">{{ detail.title }}</el-descriptions-item>
        <el-descriptions-item label="所属租户">{{ detail.tenantName }}</el-descriptions-item>
        <el-descriptions-item label="所属船舶">{{ detail.vesselName || '-' }}</el-descriptions-item>
        <el-descriptions-item label="所属船员">{{ detail.crewName || '-' }}</el-descriptions-item>
        <el-descriptions-item label="申请人">{{ detail.applicantName }}</el-descriptions-item>
        <el-descriptions-item label="当前状态">
          <el-tag :type="statusTagType(detail.status)">{{ formatOption(statusOptions, detail.status) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="当前节点">{{ detail.currentStep }}</el-descriptions-item>
        <el-descriptions-item label="申请内容" :span="2">{{ detail.content || '-' }}</el-descriptions-item>
        <el-descriptions-item label="审核人">{{ detail.auditBy || '-' }}</el-descriptions-item>
        <el-descriptions-item label="审核时间">{{ parseTime(detail.auditTime) || '-' }}</el-descriptions-item>
        <el-descriptions-item label="审核意见" :span="2">{{ detail.auditOpinion || '-' }}</el-descriptions-item>
      </el-descriptions>

      <el-divider content-position="left">进度记录</el-divider>
      <el-timeline>
        <el-timeline-item v-for="item in progressList" :key="item.progressId" :timestamp="parseTime(item.handleTime)" placement="top">
          <el-card shadow="never">
            <div class="timeline-title">{{ item.stepName }} · {{ formatOption(statusOptions, item.stepStatus) }}</div>
            <div class="timeline-text">{{ item.handleOpinion || '-' }}</div>
            <div class="timeline-meta">处理人：{{ item.handlerName || '-' }}</div>
          </el-card>
        </el-timeline-item>
      </el-timeline>

      <el-divider content-position="left">业务通知</el-divider>
      <el-table :data="noticeList" border>
        <el-table-column label="标题" prop="noticeTitle" min-width="160" :show-overflow-tooltip="true" />
        <el-table-column label="类型" prop="noticeType" width="110">
          <template #default="scope">
            {{ formatOption(noticeTypeOptions, scope.row.noticeType) }}
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="readFlag" width="90">
          <template #default="scope">
            <el-tag :type="scope.row.readFlag === '1' ? 'info' : 'success'">{{ scope.row.readFlag === '1' ? '已读' : '未读' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime" width="170">
          <template #default="scope">
            {{ parseTime(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="90" align="center">
          <template #default="scope">
            <el-button v-if="scope.row.readFlag !== '1'" link type="primary" @click="handleReadNotice(scope.row.noticeId)">已读</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>
  </div>
</template>

<script setup lang="ts" name="BusinessApplication">
import { optionselectTenant } from "@/api/business/tenant"
import { listVessel } from "@/api/business/vessel"
import { listCrew } from "@/api/business/crew"
import {
  addApplication,
  auditApplication,
  delApplication,
  getApplication,
  listApplication,
  listApplicationProgress,
  listBusinessNotice,
  readBusinessNotice,
  updateApplication
} from "@/api/business/application"
import type {
  ApplicationQueryParams,
  SeaBusinessApplication,
  SeaBusinessNotice,
  SeaBusinessProgress,
  SeaCrew,
  SeaTenant,
  SeaVessel
} from "@/types"

const { proxy } = getCurrentInstance()

const applicationTypeOptions = [
  { value: "vessel_service", label: "船舶业务" },
  { value: "crew_service", label: "船员业务" },
  { value: "certificate", label: "证照办理" },
  { value: "repair", label: "维修报备" },
  { value: "other", label: "其他" }
]
const priorityOptions = [
  { value: "normal", label: "普通" },
  { value: "urgent", label: "紧急" },
  { value: "critical", label: "特急" }
]
const statusOptions = [
  { value: "submitted", label: "已提交" },
  { value: "approved", label: "已通过" },
  { value: "rejected", label: "已驳回" }
]
const noticeTypeOptions = [
  { value: "application", label: "申请通知" },
  { value: "audit", label: "审核通知" }
]

const tenantOptions = ref<SeaTenant[]>([])
const vesselOptions = ref<SeaVessel[]>([])
const crewOptions = ref<SeaCrew[]>([])
const applicationList = ref<SeaBusinessApplication[]>([])
const progressList = ref<SeaBusinessProgress[]>([])
const noticeList = ref<SeaBusinessNotice[]>([])
const open = ref<boolean>(false)
const auditOpen = ref<boolean>(false)
const detailOpen = ref<boolean>(false)
const loading = ref<boolean>(true)
const showSearch = ref<boolean>(true)
const ids = ref<number[]>([])
const single = ref<boolean>(true)
const multiple = ref<boolean>(true)
const total = ref<number>(0)
const title = ref<string>("")
const detail = ref<SeaBusinessApplication>({})

const data = reactive({
  form: {} as SeaBusinessApplication,
  auditForm: {} as SeaBusinessApplication,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    tenantId: undefined,
    vesselId: undefined,
    applicationNo: undefined,
    applicationType: undefined,
    status: undefined
  } as ApplicationQueryParams,
  rules: {
    tenantId: [{ required: true, message: "所属租户不能为空", trigger: "change" }],
    applicationType: [{ required: true, message: "申请类型不能为空", trigger: "change" }],
    title: [{ required: true, message: "申请标题不能为空", trigger: "blur" }],
    applicantName: [{ required: true, message: "申请人不能为空", trigger: "blur" }]
  },
  auditRules: {
    status: [{ required: true, message: "审核结果不能为空", trigger: "change" }],
    auditOpinion: [{ required: true, message: "审核意见不能为空", trigger: "blur" }]
  }
})

const { queryParams, form, rules, auditForm, auditRules } = toRefs(data)

function formatOption(options: Array<{ value: string; label: string }>, value?: string) {
  return options.find(item => item.value === value)?.label || value || "-"
}

function statusTagType(status?: string) {
  if (status === "approved") return "success"
  if (status === "rejected") return "danger"
  return "warning"
}

function priorityTagType(priority?: string) {
  if (priority === "critical") return "danger"
  if (priority === "urgent") return "warning"
  return "info"
}

function getTenantOptions() {
  optionselectTenant().then(response => {
    tenantOptions.value = response.data || []
  })
}

function getVesselOptions() {
  listVessel({ pageNum: 1, pageSize: 1000 }).then(response => {
    vesselOptions.value = response.rows || []
  })
}

function getCrewOptions() {
  listCrew({ pageNum: 1, pageSize: 1000 }).then(response => {
    crewOptions.value = response.rows || []
  })
}

function getList() {
  loading.value = true
  listApplication(queryParams.value).then(response => {
    applicationList.value = response.rows
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
    applicationId: undefined,
    tenantId: undefined,
    vesselId: undefined,
    crewId: undefined,
    applicationNo: undefined,
    applicationType: "vessel_service",
    title: undefined,
    applicantName: undefined,
    applicantPhone: undefined,
    priority: "normal",
    content: undefined,
    remark: undefined
  }
  proxy.resetForm("applicationRef")
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleSelectionChange(selection: SeaBusinessApplication[]) {
  ids.value = selection.map(item => item.applicationId!)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

function handleAdd() {
  reset()
  open.value = true
  title.value = "添加业务申请"
}

function handleUpdate(row?: SeaBusinessApplication) {
  reset()
  const applicationId = row?.applicationId || ids.value[0]
  getApplication(applicationId).then(response => {
    form.value = response.data!
    open.value = true
    title.value = "修改业务申请"
  })
}

function submitForm() {
  proxy.$refs["applicationRef"].validate((valid: boolean) => {
    if (valid) {
      if (form.value.applicationId != undefined) {
        updateApplication(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addApplication(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

function handleAudit(row?: SeaBusinessApplication) {
  const applicationId = row?.applicationId || ids.value[0]
  getApplication(applicationId).then(response => {
    const application = response.data!
    auditForm.value = {
      applicationId: application.applicationId,
      applicationNo: application.applicationNo,
      title: application.title,
      status: application.status === "rejected" ? "rejected" : "approved",
      auditOpinion: application.auditOpinion
    }
    auditOpen.value = true
  })
}

function submitAudit() {
  proxy.$refs["auditRef"].validate((valid: boolean) => {
    if (valid) {
      auditApplication(auditForm.value).then(() => {
        proxy.$modal.msgSuccess("审核成功")
        auditOpen.value = false
        getList()
      })
    }
  })
}

function handleDetail(row: SeaBusinessApplication) {
  const applicationId = row.applicationId!
  getApplication(applicationId).then(response => {
    detail.value = response.data || {}
    detailOpen.value = true
  })
  listApplicationProgress(applicationId).then(response => {
    progressList.value = response.data || []
  })
  listBusinessNotice({ pageNum: 1, pageSize: 1000, applicationId }).then(response => {
    noticeList.value = response.rows || []
  })
}

function handleReadNotice(noticeId?: number) {
  if (!noticeId) return
  readBusinessNotice(noticeId).then(() => {
    proxy.$modal.msgSuccess("标记成功")
    if (detail.value.applicationId) {
      listBusinessNotice({ pageNum: 1, pageSize: 1000, applicationId: detail.value.applicationId }).then(response => {
        noticeList.value = response.rows || []
      })
    }
  })
}

function handleDelete(row?: SeaBusinessApplication) {
  const applicationIds = row?.applicationId || ids.value
  proxy.$modal.confirm('是否确认删除申请编号为"' + applicationIds + '"的数据项？').then(function() {
    return delApplication(applicationIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

function handleExport() {
  proxy.download("business/application/export", {
    ...queryParams.value
  }, `business_application_${new Date().getTime()}.xlsx`)
}

getTenantOptions()
getVesselOptions()
getCrewOptions()
getList()
</script>

<style scoped>
.timeline-title {
  font-weight: 600;
  line-height: 22px;
}

.timeline-text {
  color: var(--el-text-color-regular);
  line-height: 22px;
  margin-top: 4px;
}

.timeline-meta {
  color: var(--el-text-color-secondary);
  font-size: 12px;
  line-height: 20px;
  margin-top: 6px;
}
</style>
