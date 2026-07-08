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
      <el-form-item label="证照类型" prop="licenseType">
        <el-select v-model="queryParams.licenseType" placeholder="请选择" clearable style="width: 160px">
          <el-option v-for="item in licenseTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="证照编号" prop="licenseNo">
        <el-input v-model="queryParams.licenseNo" placeholder="请输入证照编号" clearable style="width: 190px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="证照状态" prop="licenseStatus">
        <el-select v-model="queryParams.licenseStatus" placeholder="请选择" clearable style="width: 150px">
          <el-option v-for="item in licenseStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['business:vesselLicense:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate" v-hasPermi="['business:vesselLicense:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['business:vesselLicense:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['business:vesselLicense:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="licenseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="所属租户" align="center" prop="tenantName" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="船舶" align="center" prop="vesselName" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="证照类型" align="center" prop="licenseType" width="120">
        <template #default="scope">
          <el-tag>{{ formatOption(licenseTypeOptions, scope.row.licenseType) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="证照编号" align="center" prop="licenseNo" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="签发日期" align="center" prop="issueDate" width="120" />
      <el-table-column label="到期日期" align="center" prop="expireDate" width="120" />
      <el-table-column label="附件" align="center" prop="attachmentName" min-width="150" :show-overflow-tooltip="true">
        <template #default="scope">
          <a v-if="scope.row.attachmentUrl" class="link-type" :href="scope.row.attachmentUrl" target="_blank">{{ scope.row.attachmentName || '查看附件' }}</a>
          <span v-else>{{ scope.row.attachmentName || '-' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="licenseStatus" width="100">
        <template #default="scope">
          <el-tag>{{ formatOption(licenseStatusOptions, scope.row.licenseStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['business:vesselLicense:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['business:vesselLicense:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="720px" append-to-body>
      <el-form ref="licenseRef" :model="form" :rules="rules" label-width="92px">
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
              <el-select v-model="form.vesselId" placeholder="请选择船舶" filterable style="width: 100%">
                <el-option v-for="vessel in vesselOptions" :key="vessel.vesselId" :label="vessel.vesselName" :value="vessel.vesselId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="证照类型" prop="licenseType">
              <el-select v-model="form.licenseType" placeholder="请选择类型" style="width: 100%">
                <el-option v-for="item in licenseTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="证照编号" prop="licenseNo">
              <el-input v-model="form.licenseNo" placeholder="请输入证照编号" />
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
            <el-form-item label="证照状态" prop="licenseStatus">
              <el-select v-model="form.licenseStatus" placeholder="请选择状态" style="width: 100%">
                <el-option v-for="item in licenseStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="附件名称" prop="attachmentName">
              <el-input v-model="form.attachmentName" placeholder="请输入附件名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="附件地址" prop="attachmentUrl">
              <el-input v-model="form.attachmentUrl" placeholder="请输入附件地址" />
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

<script setup lang="ts" name="VesselLicense">
import { optionselectTenant } from "@/api/business/tenant"
import { listVessel } from "@/api/business/vessel"
import { addVesselLicense, delVesselLicense, getVesselLicense, listVesselLicense, updateVesselLicense } from "@/api/business/vesselLicense"
import type { SeaTenant, SeaVessel, SeaVesselLicense, VesselLicenseQueryParams } from "@/types"

const { proxy } = getCurrentInstance()

const licenseTypeOptions = [
  { value: "operation", label: "营运证" },
  { value: "inspection", label: "检验证" },
  { value: "insurance", label: "保险单" },
  { value: "safety", label: "安全证书" },
  { value: "other", label: "其他" }
]
const licenseStatusOptions = [
  { value: "valid", label: "有效" },
  { value: "expiring", label: "临期" },
  { value: "expired", label: "过期" },
  { value: "revoked", label: "作废" }
]

const tenantOptions = ref<SeaTenant[]>([])
const vesselOptions = ref<SeaVessel[]>([])
const licenseList = ref<SeaVesselLicense[]>([])
const open = ref<boolean>(false)
const loading = ref<boolean>(true)
const showSearch = ref<boolean>(true)
const ids = ref<number[]>([])
const single = ref<boolean>(true)
const multiple = ref<boolean>(true)
const total = ref<number>(0)
const title = ref<string>("")

const data = reactive({
  form: {} as SeaVesselLicense,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    tenantId: undefined,
    vesselId: undefined,
    licenseType: undefined,
    licenseNo: undefined,
    licenseStatus: undefined
  } as VesselLicenseQueryParams,
  rules: {
    tenantId: [{ required: true, message: "所属租户不能为空", trigger: "change" }],
    vesselId: [{ required: true, message: "所属船舶不能为空", trigger: "change" }],
    licenseType: [{ required: true, message: "证照类型不能为空", trigger: "change" }],
    licenseNo: [{ required: true, message: "证照编号不能为空", trigger: "blur" }]
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

function getVesselOptions() {
  listVessel({ pageNum: 1, pageSize: 1000 }).then(response => {
    vesselOptions.value = response.rows || []
  })
}

function getList() {
  loading.value = true
  listVesselLicense(queryParams.value).then(response => {
    licenseList.value = response.rows
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
    licenseId: undefined,
    tenantId: undefined,
    vesselId: undefined,
    licenseType: "operation",
    licenseNo: undefined,
    issueDate: undefined,
    expireDate: undefined,
    issuingAuthority: undefined,
    attachmentName: undefined,
    attachmentUrl: undefined,
    licenseStatus: "valid",
    remark: undefined
  }
  proxy.resetForm("licenseRef")
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleSelectionChange(selection: SeaVesselLicense[]) {
  ids.value = selection.map(item => item.licenseId!)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

function handleAdd() {
  reset()
  open.value = true
  title.value = "添加船舶证照"
}

function handleUpdate(row?: SeaVesselLicense) {
  reset()
  const licenseId = row?.licenseId || ids.value[0]
  getVesselLicense(licenseId).then(response => {
    form.value = response.data!
    open.value = true
    title.value = "修改船舶证照"
  })
}

function submitForm() {
  proxy.$refs["licenseRef"].validate((valid: boolean) => {
    if (valid) {
      if (form.value.licenseId != undefined) {
        updateVesselLicense(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addVesselLicense(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

function handleDelete(row?: SeaVesselLicense) {
  const licenseIds = row?.licenseId || ids.value
  proxy.$modal.confirm('是否确认删除证照编号为"' + licenseIds + '"的数据项？').then(function() {
    return delVesselLicense(licenseIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

function handleExport() {
  proxy.download("business/vesselLicense/export", {
    ...queryParams.value
  }, `vessel_license_${new Date().getTime()}.xlsx`)
}

getTenantOptions()
getVesselOptions()
getList()
</script>
