# Docker 开发环境

## 目标

本环境用于本机开发，单独启动 MySQL 和 Redis，不占用默认 `3306/6379` 端口。

## 服务

| 服务 | 容器名 | 宿主机端口 | 容器端口 | 说明 |
| --- | --- | --- | --- | --- |
| MySQL | `dalian-sea-ai-mysql` | `13306` | `3306` | 数据库 `dalian_sea_ai`，root 密码 `password` |
| Redis | `dalian-sea-ai-redis` | `16379` | `6379` | 无密码，AOF 持久化 |

## 常用命令

```bash
docker compose -f docker-compose.dev.yml up -d
docker compose -f docker-compose.dev.yml ps
docker compose -f docker-compose.dev.yml down
```

已有容器重新初始化数据库:

```bash
docker compose -f docker-compose.dev.yml exec -T mysql sh -lc 'mysql -uroot -p"$MYSQL_ROOT_PASSWORD" dalian_sea_ai < /sql/ry_20260417.sql'
```

后端连接这套环境:

```bash
mvn spring-boot:run -pl dalian-sea-admin -am -Dspring-boot.run.profiles=druid,docker
```
