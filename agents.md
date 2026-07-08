# Agents

本文件说明 AI 编码助手在本仓库内协作时应遵守的项目约定。

## 仓库结构

- `RuoYi-Vue`: 后端服务，来自若依 `springboot3` 分支。
- `RuoYi-Vue3`: Web 管理端，来自若依 Vue3 TypeScript 分支。
- `RuoYi-App`: 小程序/移动端，来自若依 App `vue3` 分支。
- `ai`: 项目计划、当前计划、长期计划和产品/技术 spec。

## 工作约定

- 默认使用中文沟通和编写项目文档。
- 修改代码前先确认所属端: 后端、Web 管理端或小程序。
- 不提交本地密钥、生产配置、数据库密码、云服务凭证或构建产物。
- 保持三端边界清晰，跨端约定优先写入 `ai` 文档。
- 对若依原始代码做业务改造时，优先保留框架已有权限、菜单、字典、日志和代码生成模式。

## 常用命令

### 后端

```bash
cd RuoYi-Vue
mvn clean package -DskipTests
```

### Web 管理端

```bash
cd RuoYi-Vue3
npm install
npm run dev
npm run build:prod
```

### 小程序/移动端

```bash
cd RuoYi-App
```

使用 HBuilderX 或微信开发者工具导入项目，并根据环境修改 `config.js` 中的接口地址。

## 当前目标

先完成统一仓库、三端基线确认和本地启动文档，再进入海事业务模块和 AI 能力设计。

