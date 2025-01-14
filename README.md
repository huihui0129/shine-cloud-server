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


## xxl-Job

数据库

```sql
CREATE database if NOT EXISTS `shine_xxl_job` default character set utf8mb4 collate utf8mb4_general_ci;
use `shine_xxl_job`;

SET NAMES utf8mb4;
CREATE TABLE `xxl_job_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_desc` varchar(255) NOT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `author` varchar(64) DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) DEFAULT NULL COMMENT '报警邮件',
  `schedule_type` varchar(50) NOT NULL DEFAULT 'NONE' COMMENT '调度类型',
  `schedule_conf` varchar(128) DEFAULT NULL COMMENT '调度配置，值含义取决于调度类型',
  `misfire_strategy` varchar(50) NOT NULL DEFAULT 'DO_NOTHING' COMMENT '调度过期策略',
  `executor_route_strategy` varchar(50) DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int(11) NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `glue_type` varchar(50) NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '上次调度时间',
  `trigger_next_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '下次调度时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `xxl_job_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `trigger_time` datetime DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int(11) NOT NULL COMMENT '调度-结果',
  `trigger_msg` text COMMENT '调度-日志',
  `handle_time` datetime DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int(11) NOT NULL COMMENT '执行-状态',
  `handle_msg` text COMMENT '执行-日志',
  `alarm_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  PRIMARY KEY (`id`),
  KEY `I_trigger_time` (`trigger_time`),
  KEY `I_handle_code` (`handle_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `xxl_job_log_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime DEFAULT NULL COMMENT '调度-时间',
  `running_count` int(11) NOT NULL DEFAULT '0' COMMENT '运行中-日志数量',
  `suc_count` int(11) NOT NULL DEFAULT '0' COMMENT '执行成功-日志数量',
  `fail_count` int(11) NOT NULL DEFAULT '0' COMMENT '执行失败-日志数量',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_trigger_day` (`trigger_day`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `xxl_job_logglue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `xxl_job_registry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) NOT NULL,
  `registry_key` varchar(255) NOT NULL,
  `registry_value` varchar(255) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `i_g_k_v` (`registry_group`,`registry_key`,`registry_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `xxl_job_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) NOT NULL COMMENT '执行器名称',
  `address_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` text COMMENT '执行器地址列表，多地址逗号分隔',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `xxl_job_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `role` tinyint(4) NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `xxl_job_lock` (
  `lock_name` varchar(50) NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `xxl_job_group`(`id`, `app_name`, `title`, `address_type`, `address_list`, `update_time`) VALUES (1, 'xxl-job-executor-sample', '示例执行器', 0, NULL, '2018-11-03 22:21:31' );
INSERT INTO `xxl_job_info`(`id`, `job_group`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `schedule_type`, `schedule_conf`, `misfire_strategy`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (1, 1, '测试任务1', '2018-11-03 22:21:31', '2018-11-03 22:21:31', 'XXL', '', 'CRON', '0 0 0 * * ? *', 'DO_NOTHING', 'FIRST', 'demoJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2018-11-03 22:21:31', '');
INSERT INTO `xxl_job_user`(`id`, `username`, `password`, `role`, `permission`) VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, NULL);
INSERT INTO `xxl_job_lock` ( `lock_name`) VALUES ( 'schedule_lock');

commit;
```

> xxl_job_lock：任务调度锁表；
> xxl_job_group：执行器信息表，维护任务执行器信息；
> xxl_job_info：调度扩展信息表： 用于保存XXL-JOB调度任务的扩展信息，如任务分组、任务名、机器地址、执行器、执行入参和报警邮件等等；
> xxl_job_log：调度日志表： 用于保存XXL-JOB任务调度的历史信息，如调度结果、执行结果、调度入参、调度机器和执行器等等；
> xxl_job_log_report：调度日志报表：用户存储XXL-JOB任务调度日志的报表，调度中心报表功能页面会用到；
> xxl_job_logglue：任务GLUE日志：用于保存GLUE更新历史，用于支持GLUE的版本回溯功能；
> xxl_job_registry：执行器注册表，维护在线的执行器和调度中心机器地址信息；
> xxl_job_user：系统用户表；


```shell
# 启动
docker run -d -e PARAMS="--spring.datasource.url=jdbc:mysql://192.168.128.1:3306/shine_xxl_job?Unicode=true&characterEncoding=UTF-8 --spring.datasource.username=root --spring.datasource.password=123456 --xxl.job.accessToken=shine"  -p 28080:8080 -v J:/cloud-components/xxl-job/logs:/data/applogs --name shine-xxl-job --privileged=true xuxueli/xxl-job-admin:2.4.0
```

> 地址：http://localhost:28080/xxl-job-admin/toLogin
> 账号：admin
> 密码：123456






