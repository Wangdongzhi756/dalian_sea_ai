# Agents

本文件说明 AI 编码助手在本仓库内协作时应遵守的项目约定。

## 仓库结构

- `backend`: 后端服务，Spring Boot 3 / JDK 17+。
- `admin-web`: Web 管理端，Vue 3 + TypeScript + Vite。
- `mobile-app`: 小程序/移动端，uni-app + Vue 3。
- `ai`: 项目计划、当前计划和产品/技术 spec。

## 工作约定

- 默认使用中文沟通和编写项目文档。
- 修改代码前先确认所属端: 后端、Web 管理端或小程序。
- 不提交本地密钥、生产配置、数据库密码、云服务凭证或构建产物。
- 保持三端边界清晰，跨端约定优先写入 `ai` 文档。
- 后端保留用户、组织、登录、权限、数据权限、登录日志、操作日志等核心能力。
- 代码生成、定时任务、演示入口和默认品牌信息不作为核心能力保留。

## 常用命令

### 后端

```bash
cd backend
mvn clean package -DskipTests
```

### Web 管理端

```bash
cd admin-web
npm install
npm run dev
npm run build:prod
```

### 小程序/移动端

```bash
cd mobile-app
```

使用 HBuilderX 或微信开发者工具导入项目，并根据环境修改 `config.js` 中的接口地址。

## 当前目标

先完成统一仓库、三端基线确认和本地启动文档，再进入海事业务模块和 AI 能力设计。
