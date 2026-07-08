<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="租户编码" prop="tenantCode">
        <el-input v-model="queryParams.tenantCode" placeholder="请输入租户编码" clearable style="width: 220px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="租户名称" prop="tenantName">
        <el-input v-model="queryParams.tenantName" placeholder="请输入租户名称" clearable style="width: 220px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="联系电话" prop="contactPhone">
        <el-input v-model="queryParams.contactPhone" placeholder="请输入联系电话" clearable style="width: 220px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="套餐" prop="planType">
        <el-select v-model="queryParams.planType" placeholder="请选择套餐" clearable style="width: 180px">
          <el-option v-for="item in planOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 180px">
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
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['business:tenant:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate" v-hasPermi="['business:tenant:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['business:tenant:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['business:tenant:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tenantList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="租户编码" align="center" prop="tenantCode" width="140" />
      <el-table-column label="租户名称" align="center" prop="tenantName" min-width="180" :show-overflow-tooltip="true" />
      <el-table-column label="联系人" align="center" prop="contactName" width="120" />
      <el-table-column label="联系电话" align="center" prop="contactPhone" width="140" />
      <el-table-column label="套餐" align="center" prop="planType" width="110">
        <template #default="scope">
          <el-tag>{{ formatPlanType(scope.row.planType) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="船舶额度" align="center" prop="maxVessels" width="100" />
      <el-table-column label="到期日期" align="center" prop="expireTime" width="120" />
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
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['business:tenant:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['business:tenant:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="620px" append-to-body>
      <el-form ref="tenantRef" :model="form" :rules="rules" label-width="92px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="租户编码" prop="tenantCode">
              <el-input v-model="form.tenantCode" placeholder="请输入租户编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="租户名称" prop="tenantName">
              <el-input v-model="form.tenantName" placeholder="请输入租户名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人" prop="contactName">
              <el-input v-model="form.contactName" placeholder="请输入联系人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="contactPhone">
              <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系邮箱" prop="contactEmail">
              <el-input v-model="form.contactEmail" placeholder="请输入联系邮箱" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="套餐" prop="planType">
              <el-select v-model="form.planType" placeholder="请选择套餐" style="width: 100%">
                <el-option v-for="item in planOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="船舶额度" prop="maxVessels">
              <el-input-number v-model="form.maxVessels" controls-position="right" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="到期日期" prop="expireTime">
              <el-date-picker v-model="form.expireTime" value-format="YYYY-MM-DD" type="date" placeholder="请选择日期" style="width: 100%" />
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

<script setup lang="ts" name="Tenant">
import { addTenant, delTenant, getTenant, listTenant, updateTenant } from "@/api/business/tenant"
import type { SeaTenant, TenantQueryParams } from "@/types"

const { proxy } = getCurrentInstance()
const { sys_normal_disable } = useDict("sys_normal_disable")

const planOptions = [
  { value: "trial", label: "试用版" },
  { value: "standard", label: "标准版" },
  { value: "enterprise", label: "企业版" }
]

const tenantList = ref<SeaTenant[]>([])
const open = ref<boolean>(false)
const loading = ref<boolean>(true)
const showSearch = ref<boolean>(true)
const ids = ref<number[]>([])
const single = ref<boolean>(true)
const multiple = ref<boolean>(true)
const total = ref<number>(0)
const title = ref<string>("")

const data = reactive({
  form: {} as SeaTenant,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    tenantCode: undefined,
    tenantName: undefined,
    contactPhone: undefined,
    planType: undefined,
    status: undefined
  } as TenantQueryParams,
  rules: {
    tenantCode: [{ required: true, message: "租户编码不能为空", trigger: "blur" }],
    tenantName: [{ required: true, message: "租户名称不能为空", trigger: "blur" }],
    maxVessels: [{ required: true, message: "船舶额度不能为空", trigger: "blur" }]
  }
})

const { queryParams, form, rules } = toRefs(data)

function formatPlanType(value?: string) {
  return planOptions.find(item => item.value === value)?.label || value || "-"
}

function getList() {
  loading.value = true
  listTenant(queryParams.value).then(response => {
    tenantList.value = response.rows
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
    tenantId: undefined,
    tenantCode: undefined,
    tenantName: undefined,
    contactName: undefined,
    contactPhone: undefined,
    contactEmail: undefined,
    planType: "standard",
    maxVessels: 0,
    expireTime: undefined,
    status: "0",
    remark: undefined
  }
  proxy.resetForm("tenantRef")
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleSelectionChange(selection: SeaTenant[]) {
  ids.value = selection.map(item => item.tenantId!)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

function handleAdd() {
  reset()
  open.value = true
  title.value = "添加租户"
}

function handleUpdate(row?: SeaTenant) {
  reset()
  const tenantId = row?.tenantId || ids.value[0]
  getTenant(tenantId).then(response => {
    form.value = response.data!
    open.value = true
    title.value = "修改租户"
  })
}

function submitForm() {
  proxy.$refs["tenantRef"].validate((valid: boolean) => {
    if (valid) {
      if (form.value.tenantId != undefined) {
        updateTenant(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addTenant(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

function handleDelete(row?: SeaTenant) {
  const tenantIds = row?.tenantId || ids.value
  proxy.$modal.confirm('是否确认删除租户编号为"' + tenantIds + '"的数据项？').then(function() {
    return delTenant(tenantIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

function handleExport() {
  proxy.download("business/tenant/export", {
    ...queryParams.value
  }, `tenant_${new Date().getTime()}.xlsx`)
}

getList()
</script>
