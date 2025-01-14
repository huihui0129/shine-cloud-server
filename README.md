# Shine Cloud Server

Shine Cloud Server 是基于 Spring Cloud 微服务架构的项目，提供一系列模块化、可扩展的功能，支持灵活的依赖选择。

## 依赖版本

- **JDK**: 17
- **Spring Cloud**: 2022.0.3
- **Spring Cloud Alibaba**: 2022.0.0.0
- **Spring Boot**: 3.0.0

## 模块结构

### 父工程

- **shine-cloud-server**: 统一依赖管理和版本控制。

### 核心模块

- **api**: 定义对外暴露的接口。
- **service**: 具体业务逻辑的实现。
- **gateway**: 网关服务。
- **common**: 公共依赖模块，提供通用工具、常量、异常处理等。

### 可选依赖模块

- **framework**: 服务可选依赖模块，按需引入所需功能。
- **components**: 基础功能模块，如文件上传、ID生成等。
- **security**:
    - **core**: 提供基础安全功能（如 Token 生成与解析）。
    - **web**: 针对 WebFlux 的安全配置（如拦截器）。

> **注意**: 各服务模块之间不得直接依赖，需通过 API 模块使用远程调用实现服务间通信。

### 扩展功能模块

- **async-starter**: 提供线程池配置，支持异步处理。
- **log-starter**: 定义统一的日志打印格式。
- **mybatis-plus-starter**: 集成 MyBatis-Plus。
- **openfeign-starter**: 支持远程调用。
- **rabbitmq-starter**: RabbitMQ 消息队列配置。
- **redis-starter**: Redis 的基础配置。
- **swagger-starter**: 基于 Spring Doc 的 Swagger 配置。

## 公共模块说明

- **common**: 提供以下功能：
    - 通用常量与枚举接口
    - 全局异常处理
    - 通用响应与响应码

## 示例配置

### 用户服务（Nacos 配置）

```yaml
server:
  port: 9001

spring:
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/hh_user?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8&useUnicode=true
    username: root
    password: 123456

mybatis-plus:
  global-config:
    db-config:
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0
  mapper-locations: classpath:/mapper/*.xml
  type-aliases-package: com.shine.system.entity
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

springdoc:
  api-docs:
    enabled: ${swagger.enable}

swagger:
  enable: true
  title: 用户服务
  description: 用户服务 Swagger 接口文档
  version: 1.0.0
```

## Docker

迁移数据
```shell
# 关闭所有发行版
wsl --shutdown
# 查看停止情况
wsl --list -v
# 备份导出目前已有的数据
wsl --export docker-desktop-data "D:\Docker\wsl\data\docker-desktop-data.tar"
# 删除原有数据
wsl --unregister docker-desktop-data
# 导入数据到新盘
wsl --import docker-desktop-data "D:\Docker\wsl\data" "D:\Docker\wsl\data\docker-desktop-data.tar" --version 2
# 启动Docker即可
```

## RabbitMQ

安装

```shell
docker pull rabbitmq:4.0-management
docker run --name rabbitmq -p 15672:15672 -p 5672:5672 -d rabbitmq:4.0-management
```

启动Web管理页面：

```bash
docker exec -it 容器id /bin/bash
rabbitmq-plugins enable rabbitmq_management  
```

## Nacos

后台运行命令：

```bash
Start-Process -WindowStyle hidden -FilePath "J:\cloud-components\nacos-2.2.1\bin\startup.cmd"
```

## MySql

挂载启动

```shell
docker run --name mysql -p 3306:3306 -v G:\data\mysql\my.ini:/etc/mysql/my.cnf -v G:\data\mysql\data:/var/lib/mysql -v G:\data\mysql\logs:/var/log/mysql -e MYSQL_ROOT_PASSWORD=root --restart=always -d mysql:5.7
```

数据表示例

```sql
CREATE TABLE `table_name` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    create_user BIGINT DEFAULT NULL COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    update_user BIGINT DEFAULT NULL COMMENT '修改人',
    deleted BIT DEFAULT b'0' NOT NULL COMMENT '逻辑删除',
    remark VARCHAR(256) NULL COMMENT '备注'
) COMMENT '表注释';
```

枚举代码生成格式示例

```text
枚举 字段备注：1-枚举名称1|2-枚举名称2|3-枚举名称3
```

## Redis

```shell
docker run --name redis -p 6379:6379 -v G:\data\redis\data:/data -v G:\data\redis\redis.conf:/etc/redis/redis.conf -d redis
```
