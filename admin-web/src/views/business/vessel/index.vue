<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="所属租户" prop="tenantId">
        <el-select v-model="queryParams.tenantId" placeholder="请选择租户" clearable filterable style="width: 220px">
          <el-option v-for="tenant in tenantOptions" :key="tenant.tenantId" :label="tenant.tenantName" :value="tenant.tenantId" />
        </el-select>
      </el-form-item>
      <el-form-item label="船舶编码" prop="vesselCode">
        <el-input v-model="queryParams.vesselCode" placeholder="请输入船舶编码" clearable style="width: 200px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="船舶名称" prop="vesselName">
        <el-input v-model="queryParams.vesselName" placeholder="请输入船舶名称" clearable style="width: 200px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="船舶类型" prop="vesselType">
        <el-select v-model="queryParams.vesselType" placeholder="请选择类型" clearable style="width: 160px">
          <el-option v-for="item in vesselTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 160px">
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
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['business:vessel:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate" v-hasPermi="['business:vessel:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['business:vessel:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['business:vessel:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="vesselList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="所属租户" align="center" prop="tenantName" min-width="160" :show-overflow-tooltip="true" />
      <el-table-column label="船舶编码" align="center" prop="vesselCode" width="130" />
      <el-table-column label="船舶名称" align="center" prop="vesselName" min-width="160" :show-overflow-tooltip="true" />
      <el-table-column label="类型" align="center" prop="vesselType" width="100">
        <template #default="scope">
          <el-tag>{{ formatVesselType(scope.row.vesselType) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="载客" align="center" prop="capacity" width="80" />
      <el-table-column label="母港" align="center" prop="homePort" min-width="120" :show-overflow-tooltip="true" />
      <el-table-column label="船长" align="center" prop="captainName" width="110" />
      <el-table-column label="船长电话" align="center" prop="captainPhone" width="130" />
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
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['business:vessel:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['business:vessel:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="680px" append-to-body>
      <el-form ref="vesselRef" :model="form" :rules="rules" label-width="92px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="所属租户" prop="tenantId">
              <el-select v-model="form.tenantId" placeholder="请选择租户" filterable style="width: 100%">
                <el-option v-for="tenant in tenantOptions" :key="tenant.tenantId" :label="tenant.tenantName" :value="tenant.tenantId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="船舶编码" prop="vesselCode">
              <el-input v-model="form.vesselCode" placeholder="请输入船舶编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="船舶名称" prop="vesselName">
              <el-input v-model="form.vesselName" placeholder="请输入船舶名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="船舶类型" prop="vesselType">
              <el-select v-model="form.vesselType" placeholder="请选择类型" style="width: 100%">
                <el-option v-for="item in vesselTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="载客人数" prop="capacity">
              <el-input-number v-model="form.capacity" controls-position="right" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="母港" prop="homePort">
              <el-input v-model="form.homePort" placeholder="请输入母港" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="船长" prop="captainName">
              <el-input v-model="form.captainName" placeholder="请输入船长" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="船长电话" prop="captainPhone">
              <el-input v-model="form.captainPhone" placeholder="请输入船长电话" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="服务区域" prop="serviceArea">
              <el-input v-model="form.serviceArea" placeholder="请输入服务区域" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
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

<script setup lang="ts" name="Vessel">
import { optionselectTenant } from "@/api/business/tenant"
import { addVessel, delVessel, getVessel, listVessel, updateVessel } from "@/api/business/vessel"
import type { SeaTenant, SeaVessel, VesselQueryParams } from "@/types"

const { proxy } = getCurrentInstance()
const { sys_normal_disable } = useDict("sys_normal_disable")

const vesselTypeOptions = [
  { value: "sightseeing", label: "观光" },
  { value: "fishing", label: "海钓" },
  { value: "transport", label: "交通" },
  { value: "other", label: "其他" }
]

const tenantOptions = ref<SeaTenant[]>([])
const vesselList = ref<SeaVessel[]>([])
const open = ref<boolean>(false)
const loading = ref<boolean>(true)
const showSearch = ref<boolean>(true)
const ids = ref<number[]>([])
const single = ref<boolean>(true)
const multiple = ref<boolean>(true)
const total = ref<number>(0)
const title = ref<string>("")

const data = reactive({
  form: {} as SeaVessel,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    tenantId: undefined,
    vesselCode: undefined,
    vesselName: undefined,
    vesselType: undefined,
    status: undefined
  } as VesselQueryParams,
  rules: {
    tenantId: [{ required: true, message: "所属租户不能为空", trigger: "change" }],
    vesselCode: [{ required: true, message: "船舶编码不能为空", trigger: "blur" }],
    vesselName: [{ required: true, message: "船舶名称不能为空", trigger: "blur" }]
  }
})

const { queryParams, form, rules } = toRefs(data)

function formatVesselType(value?: string) {
  return vesselTypeOptions.find(item => item.value === value)?.label || value || "-"
}

function getTenantOptions() {
  optionselectTenant().then(response => {
    tenantOptions.value = response.data || []
  })
}

function getList() {
  loading.value = true
  listVessel(queryParams.value).then(response => {
    vesselList.value = response.rows
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
    vesselId: undefined,
    tenantId: undefined,
    vesselCode: undefined,
    vesselName: undefined,
    vesselType: "sightseeing",
    capacity: 0,
    homePort: undefined,
    captainName: undefined,
    captainPhone: undefined,
    serviceArea: undefined,
    status: "0",
    remark: undefined
  }
  proxy.resetForm("vesselRef")
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleSelectionChange(selection: SeaVessel[]) {
  ids.value = selection.map(item => item.vesselId!)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

function handleAdd() {
  reset()
  open.value = true
  title.value = "添加船舶"
}

function handleUpdate(row?: SeaVessel) {
  reset()
  const vesselId = row?.vesselId || ids.value[0]
  getVessel(vesselId).then(response => {
    form.value = response.data!
    open.value = true
    title.value = "修改船舶"
  })
}

function submitForm() {
  proxy.$refs["vesselRef"].validate((valid: boolean) => {
    if (valid) {
      if (form.value.vesselId != undefined) {
        updateVessel(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addVessel(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

function handleDelete(row?: SeaVessel) {
  const vesselIds = row?.vesselId || ids.value
  proxy.$modal.confirm('是否确认删除船舶编号为"' + vesselIds + '"的数据项？').then(function() {
    return delVessel(vesselIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

function handleExport() {
  proxy.download("business/vessel/export", {
    ...queryParams.value
  }, `vessel_${new Date().getTime()}.xlsx`)
}

getTenantOptions()
getList()
</script>
