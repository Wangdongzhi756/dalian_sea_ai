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
      <el-form-item label="船员编码" prop="crewCode">
        <el-input v-model="queryParams.crewCode" placeholder="请输入船员编码" clearable style="width: 180px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="船员姓名" prop="crewName">
        <el-input v-model="queryParams.crewName" placeholder="请输入船员姓名" clearable style="width: 180px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="在船状态" prop="onboardStatus">
        <el-select v-model="queryParams.onboardStatus" placeholder="请选择" clearable style="width: 150px">
          <el-option v-for="item in onboardStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 150px">
          <el-option v-for="dict in sys_normal_disable" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['business:crew:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate" v-hasPermi="['business:crew:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['business:crew:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['business:crew:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="crewList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="所属租户" align="center" prop="tenantName" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="所属船舶" align="center" prop="vesselName" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="船员编码" align="center" prop="crewCode" width="130" />
      <el-table-column label="船员姓名" align="center" prop="crewName" width="110" />
      <el-table-column label="性别" align="center" prop="gender" width="80">
        <template #default="scope">
          <dict-tag :options="sys_user_sex" :value="scope.row.gender" />
        </template>
      </el-table-column>
      <el-table-column label="联系电话" align="center" prop="phone" width="130" />
      <el-table-column label="岗位" align="center" prop="positionName" width="120" :show-overflow-tooltip="true" />
      <el-table-column label="在船状态" align="center" prop="onboardStatus" width="100">
        <template #default="scope">
          <el-tag>{{ formatOption(onboardStatusOptions, scope.row.onboardStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="健康状态" align="center" prop="healthStatus" width="100">
        <template #default="scope">
          <el-tag type="success">{{ formatOption(healthStatusOptions, scope.row.healthStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="90">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['business:crew:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['business:crew:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="760px" append-to-body>
      <el-form ref="crewRef" :model="form" :rules="rules" label-width="92px">
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
            <el-form-item label="船员编码" prop="crewCode">
              <el-input v-model="form.crewCode" placeholder="请输入船员编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="船员姓名" prop="crewName">
              <el-input v-model="form.crewName" placeholder="请输入船员姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择性别" style="width: 100%">
                <el-option v-for="dict in sys_user_sex" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出生日期" prop="birthDate">
              <el-date-picker v-model="form.birthDate" value-format="YYYY-MM-DD" type="date" placeholder="请选择出生日期" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="证件号码" prop="idCard">
              <el-input v-model="form.idCard" placeholder="请输入证件号码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="岗位职务" prop="positionName">
              <el-input v-model="form.positionName" placeholder="请输入岗位职务" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="在船状态" prop="onboardStatus">
              <el-select v-model="form.onboardStatus" placeholder="请选择在船状态" style="width: 100%">
                <el-option v-for="item in onboardStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="健康状态" prop="healthStatus">
              <el-select v-model="form.healthStatus" placeholder="请选择健康状态" style="width: 100%">
                <el-option v-for="item in healthStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio v-for="dict in sys_normal_disable" :key="dict.value" :value="dict.value">{{ dict.label }}</el-radio>
              </el-radio-group>
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

<script setup lang="ts" name="Crew">
import { optionselectTenant } from "@/api/business/tenant"
import { listVessel } from "@/api/business/vessel"
import { addCrew, delCrew, getCrew, listCrew, updateCrew } from "@/api/business/crew"
import type { CrewQueryParams, SeaCrew, SeaTenant, SeaVessel } from "@/types"

const { proxy } = getCurrentInstance()
const { sys_normal_disable, sys_user_sex } = useDict("sys_normal_disable", "sys_user_sex")

const onboardStatusOptions = [
  { value: "onboard", label: "在船" },
  { value: "standby", label: "待派" },
  { value: "leave", label: "休假" },
  { value: "offboard", label: "离船" }
]
const healthStatusOptions = [
  { value: "normal", label: "正常" },
  { value: "attention", label: "关注" },
  { value: "unavailable", label: "不适岗" }
]

const tenantOptions = ref<SeaTenant[]>([])
const vesselOptions = ref<SeaVessel[]>([])
const crewList = ref<SeaCrew[]>([])
const open = ref<boolean>(false)
const loading = ref<boolean>(true)
const showSearch = ref<boolean>(true)
const ids = ref<number[]>([])
const single = ref<boolean>(true)
const multiple = ref<boolean>(true)
const total = ref<number>(0)
const title = ref<string>("")

const data = reactive({
  form: {} as SeaCrew,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    tenantId: undefined,
    vesselId: undefined,
    crewCode: undefined,
    crewName: undefined,
    onboardStatus: undefined,
    status: undefined
  } as CrewQueryParams,
  rules: {
    tenantId: [{ required: true, message: "所属租户不能为空", trigger: "change" }],
    crewCode: [{ required: true, message: "船员编码不能为空", trigger: "blur" }],
    crewName: [{ required: true, message: "船员姓名不能为空", trigger: "blur" }]
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
  listCrew(queryParams.value).then(response => {
    crewList.value = response.rows
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
    crewId: undefined,
    tenantId: undefined,
    vesselId: undefined,
    crewCode: undefined,
    crewName: undefined,
    gender: "2",
    birthDate: undefined,
    idCard: undefined,
    phone: undefined,
    positionName: undefined,
    onboardStatus: "standby",
    healthStatus: "normal",
    status: "0",
    remark: undefined
  }
  proxy.resetForm("crewRef")
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleSelectionChange(selection: SeaCrew[]) {
  ids.value = selection.map(item => item.crewId!)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

function handleAdd() {
  reset()
  open.value = true
  title.value = "添加船员"
}

function handleUpdate(row?: SeaCrew) {
  reset()
  const crewId = row?.crewId || ids.value[0]
  getCrew(crewId).then(response => {
    form.value = response.data!
    open.value = true
    title.value = "修改船员"
  })
}

function submitForm() {
  proxy.$refs["crewRef"].validate((valid: boolean) => {
    if (valid) {
      if (form.value.crewId != undefined) {
        updateCrew(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addCrew(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

function handleDelete(row?: SeaCrew) {
  const crewIds = row?.crewId || ids.value
  proxy.$modal.confirm('是否确认删除船员编号为"' + crewIds + '"的数据项？').then(function() {
    return delCrew(crewIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

function handleExport() {
  proxy.download("business/crew/export", {
    ...queryParams.value
  }, `crew_${new Date().getTime()}.xlsx`)
}

getTenantOptions()
getVesselOptions()
getList()
</script>
