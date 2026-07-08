<template>
  <div class="dashboard">
    <section class="dashboard__header">
      <div>
        <p class="dashboard__eyebrow">Dalian Sea AI SaaS</p>
        <h1>运营工作台</h1>
      </div>
      <el-tag type="success" effect="dark">准备阶段</el-tag>
    </section>

    <el-row :gutter="16">
      <el-col :xs="24" :sm="12" :lg="6" v-for="item in metrics" :key="item.label">
        <div class="metric">
          <span>{{ item.label }}</span>
          <strong>{{ item.value }}</strong>
        </div>
      </el-col>
    </el-row>

    <el-card shadow="never" class="panel">
      <template #header>
        <span>当前开发重点</span>
      </template>
      <el-timeline>
        <el-timeline-item timestamp="M1" type="primary">完成脚手架痕迹清理和本地启动验证</el-timeline-item>
        <el-timeline-item timestamp="M2">设计租户、船东、船舶、船员、证书和业务申请模型</el-timeline-item>
        <el-timeline-item timestamp="M3">接入 AI 服务层、知识库和调用日志</el-timeline-item>
      </el-timeline>
    </el-card>
  </div>
</template>

<script setup lang="ts">
const metrics = [
  { label: '保留核心', value: '权限/组织' },
  { label: '后端模块', value: '4' },
  { label: '业务阶段', value: 'M1' },
  { label: 'AI 接入', value: '规划中' }
]
</script>

<style scoped lang="scss">
.dashboard {
  padding: 24px;
}

.dashboard__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
}

.dashboard__eyebrow {
  margin: 0 0 6px;
  color: #64748b;
  font-size: 13px;
}

h1 {
  margin: 0;
  color: #0f172a;
  font-size: 24px;
  font-weight: 650;
}

.metric {
  min-height: 96px;
  margin-bottom: 16px;
  padding: 18px;
  border: 1px solid #e5e7eb;
  border-radius: 6px;
  background: #fff;
}

.metric span {
  display: block;
  color: #64748b;
  font-size: 13px;
}

.metric strong {
  display: block;
  margin-top: 12px;
  color: #111827;
  font-size: 22px;
}

.panel {
  border-radius: 6px;
}
</style>
