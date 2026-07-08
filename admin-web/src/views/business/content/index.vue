<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="78px">
      <el-form-item label="所属船舶" prop="vesselId">
        <el-select v-model="queryParams.vesselId" placeholder="请选择船舶" clearable filterable style="width: 220px">
          <el-option v-for="vessel in vesselOptions" :key="vessel.vesselId" :label="vessel.vesselName" :value="vessel.vesselId" />
        </el-select>
      </el-form-item>
      <el-form-item label="主题" prop="topic">
        <el-input v-model="queryParams.topic" placeholder="请输入主题" clearable style="width: 220px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="内容类型" prop="contentType">
        <el-select v-model="queryParams.contentType" placeholder="请选择类型" clearable style="width: 180px">
          <el-option v-for="item in contentTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="发布状态" prop="publishStatus">
        <el-select v-model="queryParams.publishStatus" placeholder="请选择状态" clearable style="width: 160px">
          <el-option v-for="item in publishStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="MagicStick" @click="handleGenerate" v-hasPermi="['business:content:generate']">AI生成文案</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate" v-hasPermi="['business:content:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['business:content:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['business:content:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="标题" align="left" prop="title" min-width="220" :show-overflow-tooltip="true" />
      <el-table-column label="船舶" align="center" prop="vesselName" min-width="140" :show-overflow-tooltip="true" />
      <el-table-column label="主题" align="center" prop="topic" min-width="140" :show-overflow-tooltip="true" />
      <el-table-column label="类型" align="center" prop="contentType" width="120">
        <template #default="scope">{{ labelOf(contentTypeOptions, scope.row.contentType) }}</template>
      </el-table-column>
      <el-table-column label="渠道" align="center" prop="publishChannel" width="120">
        <template #default="scope">{{ labelOf(channelOptions, scope.row.publishChannel) }}</template>
      </el-table-column>
      <el-table-column label="发布状态" align="center" prop="publishStatus" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.publishStatus === 'published' ? 'success' : 'info'">{{ labelOf(publishStatusOptions, scope.row.publishStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="推流状态" align="center" prop="pushStatus" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.pushStatus === 'success' ? 'success' : 'warning'">{{ labelOf(pushStatusOptions, scope.row.pushStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template #default="scope">{{ parseTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-tooltip content="修改" placement="top">
            <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['business:content:edit']"></el-button>
          </el-tooltip>
          <el-tooltip content="推流发布" placement="top">
            <el-button link type="primary" icon="Promotion" @click="handlePublish(scope.row)" v-hasPermi="['business:content:publish']"></el-button>
          </el-tooltip>
          <el-tooltip content="删除" placement="top">
            <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['business:content:remove']"></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="760px" append-to-body>
      <el-form ref="contentRef" :model="form" :rules="rules" label-width="92px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="租户ID" prop="tenantId">
              <el-input-number v-model="form.tenantId" controls-position="right" :min="1" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属船舶" prop="vesselId">
              <el-select v-model="form.vesselId" placeholder="请选择船舶" clearable filterable style="width: 100%">
                <el-option v-for="vessel in vesselOptions" :key="vessel.vesselId" :label="vessel.vesselName" :value="vessel.vesselId" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
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
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="主题" prop="topic">
              <el-input v-model="form.topic" placeholder="例如：周末近海观光预约" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="语气风格" prop="tone">
              <el-input v-model="form.tone" placeholder="专业可信、轻松亲切等" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="亮点">
          <el-input v-model="form.highlights" type="textarea" :rows="3" placeholder="输入船舶服务亮点、活动优惠、安全说明等" />
        </el-form-item>
        <el-form-item label="标题">
          <el-input v-model="form.title" placeholder="AI生成时可留空" />
        </el-form-item>
        <el-form-item label="正文">
          <el-input v-model="form.content" type="textarea" :rows="12" placeholder="生成后可编辑正文" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="2" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">{{ form.contentId ? '保 存' : '生成文案' }}</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="ContentPublish">
import { addContent, delContent, generateContent, getContent, listContent, publishContent, updateContent } from "@/api/business/content"
import { listVessel } from "@/api/business/vessel"
import type { ContentQueryParams, SeaContentPublish, SeaVessel } from "@/types"

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
const publishStatusOptions = [
  { value: "draft", label: "草稿" },
  { value: "published", label: "已发布" }
]
const pushStatusOptions = [
  { value: "pending", label: "待推流" },
  { value: "success", label: "成功" },
  { value: "failed", label: "失败" }
]

const loading = ref(true)
const showSearch = ref(true)
const open = ref(false)
const title = ref("")
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const ids = ref<number[]>([])
const contentList = ref<SeaContentPublish[]>([])
const vesselOptions = ref<SeaVessel[]>([])

const data = reactive({
  form: {} as SeaContentPublish,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    vesselId: undefined,
    contentType: undefined,
    topic: undefined,
    publishStatus: undefined
  } as ContentQueryParams,
  rules: {
    tenantId: [{ required: true, message: "租户ID不能为空", trigger: "blur" }],
    contentType: [{ required: true, message: "内容类型不能为空", trigger: "change" }],
    publishChannel: [{ required: true, message: "发布渠道不能为空", trigger: "change" }],
    topic: [{ required: true, message: "主题不能为空", trigger: "blur" }]
  }
})

const { queryParams, form, rules } = toRefs(data)

function labelOf(options: Array<{ value: string; label: string }>, value?: string) {
  return options.find(item => item.value === value)?.label || value || "-"
}

function getList() {
  loading.value = true
  listContent(queryParams.value).then(response => {
    contentList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

function getVesselOptions() {
  listVessel({ pageNum: 1, pageSize: 200 }).then(response => {
    vesselOptions.value = response.rows || []
  })
}

function reset() {
  form.value = {
    tenantId: 1,
    vesselId: undefined,
    contentType: "wechat_article",
    publishChannel: "captain_wechat",
    topic: undefined,
    tone: "专业可信",
    highlights: undefined,
    title: undefined,
    content: undefined,
    publishStatus: "draft",
    pushStatus: "pending",
    remark: undefined
  }
  proxy.resetForm("contentRef")
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleGenerate() {
  reset()
  open.value = true
  title.value = "AI生成文案"
}

function handleUpdate(row?: SeaContentPublish) {
  reset()
  const contentId = row?.contentId || ids.value[0]
  getContent(contentId).then(response => {
    form.value = response.data!
    open.value = true
    title.value = "修改文案"
  })
}

function submitForm() {
  proxy.$refs["contentRef"].validate((valid: boolean) => {
    if (!valid) return
    if (form.value.contentId) {
      updateContent(form.value).then(() => {
        proxy.$modal.msgSuccess("保存成功")
        open.value = false
        getList()
      })
      return
    }
    if (form.value.content) {
      addContent(form.value).then(() => {
        proxy.$modal.msgSuccess("新增成功")
        open.value = false
        getList()
      })
      return
    }
    generateContent(form.value).then(response => {
      form.value = response.data!
      proxy.$modal.msgSuccess("生成成功")
      getList()
    })
  })
}

function handlePublish(row: SeaContentPublish) {
  proxy.$modal.confirm('是否确认推流发布文案"' + row.title + '"？').then(() => {
    return publishContent(row.contentId!)
  }).then(() => {
    proxy.$modal.msgSuccess("发布记录已更新")
    getList()
  }).catch(() => {})
}

function handleDelete(row?: SeaContentPublish) {
  const contentIds = row?.contentId || ids.value
  proxy.$modal.confirm('是否确认删除内容编号为"' + contentIds + '"的数据项？').then(() => {
    return delContent(contentIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

function handleExport() {
  proxy.download("business/content/export", {
    ...queryParams.value
  }, `content_${new Date().getTime()}.xlsx`)
}

function handleSelectionChange(selection: SeaContentPublish[]) {
  ids.value = selection.map(item => item.contentId!)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

function cancel() {
  open.value = false
  reset()
}

getVesselOptions()
getList()
</script>
