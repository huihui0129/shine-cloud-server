# hh-cloud-service

## 依赖版本

> jdk：17
> spring-cloud：2022.0.3
> spring-cloud-alibaba：2022.0.0.0
> spring-boot：3.0.0


## 服务Nacos配置

### 用户服务

```yml
server:
  port: 9001

user:
  test: 123465

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
      logic-delete-field: deleted # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
      logic-delete-value: 1 # 逻辑已删除值(默认为 1)
      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
  mapper-locations: classpath:/mapper/*.xml
  type-aliases-package: com.hh.user.entity
  configuration:
    map-underscore-to-camel-case: true # 开启驼峰命名：默认开启，【二选一】
    cache-enabled: true # 开启二级缓存，默认开启
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

springdoc:
  api-docs:
    enabled: ${swagger.enable}

swagger:
  enable: true
  title: 用户服务
  description: 用户服务Swagger接口文档
  version: 1.0.0
  documentation-description: 用户服务Swagger接口文档
  url: /
```

## RabbitMq

启动web服务
```text
docker exec -it 容器id /bin/bash
rabbitmq-plugins enable rabbitmq_management  
```
