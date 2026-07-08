# 大连海 AI SaaS 后端

后端基于 Spring Boot 3 / JDK 17，当前保留用户、部门、岗位、角色、菜单、登录认证、数据权限、登录日志、操作日志、文件上传和安全配置等核心能力。

已清理内容：

- 代码生成模块。
- 定时任务模块。
- Swagger 测试入口。
- 旧 Vue2 管理端。
- 默认官网、演示数据和脚手架入口。

## 模块

| 模块 | 说明 |
| --- | --- |
| `dalian-sea-admin` | Web/API 启动入口。 |
| `dalian-sea-framework` | 安全、Web、Redis、日志、数据源等框架能力。 |
| `dalian-sea-system` | 用户、组织、角色、菜单、字典、参数、通知、日志等系统能力。 |
| `dalian-sea-common` | 通用工具、注解、常量和基础模型。 |

## 构建

```bash
mvn clean package -DskipTests
```
