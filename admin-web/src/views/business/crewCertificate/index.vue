<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="所属租户" prop="tenantId">
        <el-select v-model="queryParams.tenantId" placeholder="请选择租户" clearable filterable style="width: 220px">
          <el-option v-for="tenant in tenantOptions" :key="tenant.tenantId" :label="tenant.tenantName" :value="tenant.tenantId" />
        </el-select>
      </el-form-item>
      <el-form-item label="所属船员" prop="crewId">
        <el-select v-model="queryParams.crewId" placeholder="请选择船员" clearable filterable style="width: 200px">
          <el-option v-for="crew in crewOptions" :key="crew.crewId" :label="crew.crewName" :value="crew.crewId" />
        </el-select>
      </el-form-item>
      <el-form-item label="证书类型" prop="certificateType">
        <el-select v-model="queryParams.certificateType" placeholder="请选择" clearable style="width: 160px">
          <el-option v-for="item in certificateTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="证书编号" prop="certificateNo">
        <el-input v-model="queryParams.certificateNo" placeholder="请输入证书编号" clearable style="width: 190px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="证书状态" prop="certificateStatus">
        <el-select v-model="queryParams.certificateStatus" placeholder="请选择" clearable style="width: 150px">
          <el-option v-for="item in certificateStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['business:crewCertificate:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate" v-hasPermi="['business:crewCertificate:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['business:crewCertificate:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['business:crewCertificate:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="certificateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="所属租户" align="center" prop="tenantName" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="船员" align="center" prop="crewName" width="110" />
      <el-table-column label="证书类型" align="center" prop="certificateType" width="120">
        <template #default="scope">
          <el-tag>{{ formatOption(certificateTypeOptions, scope.row.certificateType) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="证书编号" align="center" prop="certificateNo" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="签发日期" align="center" prop="issueDate" width="120" />
      <el-table-column label="到期日期" align="center" prop="expireDate" width="120" />
      <el-table-column label="签发机构" align="center" prop="issuingAuthority" min-width="140" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" prop="certificateStatus" width="100">
        <template #default="scope">
          <el-tag>{{ formatOption(certificateStatusOptions, scope.row.certificateStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['business:crewCertificate:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['business:crewCertificate:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form ref="certificateRef" :model="form" :rules="rules" label-width="92px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="所属租户" prop="tenantId">
              <el-select v-model="form.tenantId" placeholder="请选择租户" filterable style="width: 100%">
                <el-option v-for="tenant in tenantOptions" :key="tenant.tenantId" :label="tenant.tenantName" :value="tenant.tenantId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属船员" prop="crewId">
              <el-select v-model="form.crewId" placeholder="请选择船员" filterable style="width: 100%">
                <el-option v-for="crew in crewOptions" :key="crew.crewId" :label="crew.crewName" :value="crew.crewId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="证书类型" prop="certificateType">
              <el-select v-model="form.certificateType" placeholder="请选择类型" style="width: 100%">
                <el-option v-for="item in certificateTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="证书编号" prop="certificateNo">
              <el-input v-model="form.certificateNo" placeholder="请输入证书编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="签发日期" prop="issueDate">
              <el-date-picker v-model="form.issueDate" value-format="YYYY-MM-DD" type="date" placeholder="请选择签发日期" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="到期日期" prop="expireDate">
              <el-date-picker v-model="form.expireDate" value-format="YYYY-MM-DD" type="date" placeholder="请选择到期日期" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="签发机构" prop="issuingAuthority">
              <el-input v-model="form.issuingAuthority" placeholder="请输入签发机构" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="证书状态" prop="certificateStatus">
              <el-select v-model="form.certificateStatus" placeholder="请选择状态" style="width: 100%">
                <el-option v-for="item in certificateStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
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

<script setup lang="ts" name="CrewCertificate">
import { optionselectTenant } from "@/api/business/tenant"
import { listCrew } from "@/api/business/crew"
import { addCrewCertificate, delCrewCertificate, getCrewCertificate, listCrewCertificate, updateCrewCertificate } from "@/api/business/crewCertificate"
import type { CrewCertificateQueryParams, SeaCrew, SeaCrewCertificate, SeaTenant } from "@/types"

const { proxy } = getCurrentInstance()

const certificateTypeOptions = [
  { value: "captain", label: "船长证" },
  { value: "crew", label: "船员证" },
  { value: "safety", label: "安全培训" },
  { value: "health", label: "健康证明" },
  { value: "other", label: "其他" }
]
const certificateStatusOptions = [
  { value: "valid", label: "有效" },
  { value: "expiring", label: "临期" },
  { value: "expired", label: "过期" },
  { value: "revoked", label: "作废" }
]

const tenantOptions = ref<SeaTenant[]>([])
const crewOptions = ref<SeaCrew[]>([])
const certificateList = ref<SeaCrewCertificate[]>([])
const open = ref<boolean>(false)
const loading = ref<boolean>(true)
const showSearch = ref<boolean>(true)
const ids = ref<number[]>([])
const single = ref<boolean>(true)
const multiple = ref<boolean>(true)
const total = ref<number>(0)
const title = ref<string>("")

const data = reactive({
  form: {} as SeaCrewCertificate,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    tenantId: undefined,
    crewId: undefined,
    certificateType: undefined,
    certificateNo: undefined,
    certificateStatus: undefined
  } as CrewCertificateQueryParams,
  rules: {
    tenantId: [{ required: true, message: "所属租户不能为空", trigger: "change" }],
    crewId: [{ required: true, message: "所属船员不能为空", trigger: "change" }],
    certificateType: [{ required: true, message: "证书类型不能为空", trigger: "change" }],
    certificateNo: [{ required: true, message: "证书编号不能为空", trigger: "blur" }]
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

function getCrewOptions() {
  listCrew({ pageNum: 1, pageSize: 1000 }).then(response => {
    crewOptions.value = response.rows || []
  })
}

function getList() {
  loading.value = true
  listCrewCertificate(queryParams.value).then(response => {
    certificateList.value = response.rows
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
    certificateId: undefined,
    tenantId: undefined,
    crewId: undefined,
    certificateType: "crew",
    certificateNo: undefined,
    issueDate: undefined,
    expireDate: undefined,
    issuingAuthority: undefined,
    certificateStatus: "valid",
    remark: undefined
  }
  proxy.resetForm("certificateRef")
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleSelectionChange(selection: SeaCrewCertificate[]) {
  ids.value = selection.map(item => item.certificateId!)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

function handleAdd() {
  reset()
  open.value = true
  title.value = "添加船员证书"
}

function handleUpdate(row?: SeaCrewCertificate) {
  reset()
  const certificateId = row?.certificateId || ids.value[0]
  getCrewCertificate(certificateId).then(response => {
    form.value = response.data!
    open.value = true
    title.value = "修改船员证书"
  })
}

function submitForm() {
  proxy.$refs["certificateRef"].validate((valid: boolean) => {
    if (valid) {
      if (form.value.certificateId != undefined) {
        updateCrewCertificate(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addCrewCertificate(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

function handleDelete(row?: SeaCrewCertificate) {
  const certificateIds = row?.certificateId || ids.value
  proxy.$modal.confirm('是否确认删除证书编号为"' + certificateIds + '"的数据项？').then(function() {
    return delCrewCertificate(certificateIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

function handleExport() {
  proxy.download("business/crewCertificate/export", {
    ...queryParams.value
  }, `crew_certificate_${new Date().getTime()}.xlsx`)
}

getTenantOptions()
getCrewOptions()
getList()
</script>
