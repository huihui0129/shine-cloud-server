# shine-cloud-server



## 依赖版本

> jdk：17
> 
> spring-cloud：2022.0.3
> 
> spring-cloud-alibaba：2022.0.0.0
> 
> spring-boot：3.0.0



## 模块依赖

> 为了防止我自己忘记，所以写的
>
> framework工程下面全部是服务可选依赖，需要什么功能即可引入；
>
> components为组件，也是服务可选依赖；
>
> 不同与framework，components是提供基础功能的，比如文件上传、ID生成之类的
>
> 安全模块（Security）比较特殊，考虑到其作用较大以及网关是基于webflux实现的，故将安全模块拆分为core和web，前者提供基础功能，例如Token生成和解析，后者进行web相关的配置，比如拦截器
>
> 注：各个service服务之间不可相互依赖，也决不可依赖另一个service，如需访问，请依赖对方的api模块，使用远程调用访问



模块说明：

shine-cloud-server：父工程，进行依赖管理

api：对外暴露接口

service：对内实现逻辑

gateway：网关

common：公共依赖

security：安全模块，提供oauth认证

async-start：异步模块，提供基本的线程池配置，可依赖后自行配置

log-starter：配置了日志打印格式

mybatis-plus-starter：模块如其名，无需多言（操作数据库的）

openfeign-starter：远程调用的

rabbitmq-starter：消息中间件，RabbitMq

redis-starter：redis的基础配置

swagger-starter：spring-doc的基础配置，可以在引用模块后自行配置

security：安全聚合模块，详情请看模块依赖说明，有详细解释

...大概就这么多吧。



## 公共模块说明

common：顾名思义，公共依赖模块，里面提供了常量基础、枚举接口、通用异常接口以及对应的实现，全局异常处理，以及通用响应、响应码



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



## MySql

```sql
create table `table_name` (
    id bigint auto_increment primary key comment 'id',
    create_time datetime default current_timestamp not null comment '创建时间',
    create_user bigint default null comment '创建人',
    update_time datetime default current_timestamp not null on update current_timestamp comment '修改时间',
    update_user bigint default null comment '修改人',
    deleted bit default b'0' not null comment '逻辑删除',
    remark varchar(256) null comment '备注'
) comment '表注释';
```

