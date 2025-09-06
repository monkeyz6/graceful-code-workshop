# 优雅代码实战工坊

## 项目介绍

一个基于 Spring Boot 3（Java 17）的示例项目，旨在演示如何编写优雅、易维护的代码。涵盖常用设计模式实现、开发中常见的坑及其规避方案，提供完整文档及实战示例。

## 技术栈

- **Java**: JDK 17
- **框架**: Spring Boot 3.5.4
- **构建工具**: Maven
- **开发工具**: Lombok
- **API文档**: Swagger3 + Knife4j 4.4.0
- **注解处理**: 自定义注解处理器（编译时检查）

## 项目结构

```
graceful-code-workshop/
├── xk857-framework/                  # 框架核心模块
│   ├── xk857-framework-api/          # 框架API模块
│   │   └── src/main/java/com/xk857/framework/
│   │       ├── constants/            # 常量定义
│   │       │   ├── CommonConstants.java    # 通用常量
│   │       │   └── ApplicationConstants.java # 应用常量
│   │       ├── common/               # 公共类
│   │       │   └── Result.java       # 统一响应结果类
│   │       ├── enums/                # 枚举类
│   │       │   └── SystemErrorEnum.java # 系统错误枚举
│   │       └── exception/            # 异常定义
│   │           ├── BaseErrorEnum.java    # 基础错误枚举接口
│   │           └── BusinessException.java # 业务异常类
│   ├── xk857-framework-processor/    # 注解处理器模块
│   │   └── src/main/java/com/xk857/framework/processor/
│   │       ├── annotation/           # 注解定义
│   │       │   ├── MyApiResponse.java     # API响应注解
│   │       │   └── RawResponse.java       # 原始响应注解
│   │       └── check/                # 注解处理器
│   │           └── MutualExclusionProcessor.java # 互斥注解检查器
│   ├── xk857-framework-core/         # 框架核心实现模块
│   │   └── src/main/java/com/xk857/framework/
│   │       ├── advice/
│   │       │   └── GlobalResponseAdvice.java # 全局响应处理
│   │       └── exception/           
│   │           └── GlobalExceptionHandler.java # 全局异常处理器
│   └── xk857-framework-swagger/      # Swagger文档模块
│       └── src/main/java/com/xk857/framework/swagger/
│           ├── builder/              # 构建器
│           │   └── SchemaBuilder.java # Schema构建器
│           ├── config/               # 配置类
│           │   ├── GlobalResultOperationCustomizer.java # 全局结果操作定制器
│           │   └── SwaggerConfig.java # Swagger配置
│           ├── constants/            # 常量
│           │   └── SwaggerConstants.java # Swagger常量
│           └── generator/            # 生成器
│               └── ExampleDataGenerator.java # 示例数据生成器
└── xk857-main/                       # 主业务模块聚合器
    ├── xk857-main-api/               # API接口定义模块
    │   └── src/main/java/com/xk857/main/api/
    │       ├── dto/                  # 数据传输对象
    │       │   ├── UserDTO.java      # 用户DTO
    │       │   └── UserQueryDTO.java # 用户查询DTO
    │       ├── entity/               # 实体类
    │       │   └── User.java         # 用户实体类
    │       ├── enums/                # 枚举类
    │       │   └── UserErrorEnum.java # 用户错误枚举
    │       └── service/              # 服务接口
    │           └── UserService.java  # 用户服务接口
    ├── xk857-main-core/              # 核心业务逻辑模块
    │   └── src/main/java/com/xk857/main/core/
    │       ├── dao/                  # 数据访问层
    │       │   └── UserDAO.java      # 用户数据访问对象
    │       └── service/              # 业务逻辑层
    │           └── UserServiceImpl.java # 用户服务实现类
    └── xk857-main-boot/              # 主启动模块
        └── src/main/
            ├── java/com/xk857/main/boot/
            │   ├── Application.java  # 主启动类
            │   ├── controller/       # 控制器层
            │   │   ├── HelloController.java # Hello控制器
            │   │   └── UserController.java  # 用户控制器
            │   ├── dto/              # 数据传输对象
            │   │   ├── EndpointInfo.java    # 端点信息DTO
            │   │   └── WelcomeResponse.java # 欢迎响应DTO
            │   └── manager/          # 管理器
            │       └── EndpointManager.java # 端点管理器
            └── resources/
                └── application.yml   # 应用配置文件
```

## 模块说明

### 框架模块 (xk857-framework)

- **xk857-framework-api**: 框架API定义模块，包含通用响应、异常定义、常量等
- **xk857-framework-processor**: 注解处理器模块，包含自定义注解和编译时检查器
- **xk857-framework-core**: 框架核心实现模块，包含全局响应处理、异常处理等
- **xk857-framework-swagger**: Swagger文档模块，提供API文档生成和响应包装

### 主业务模块 (xk857-main)

- **xk857-main-api**: API接口定义模块，包含DTO、实体、枚举、服务接口等
- **xk857-main-core**: 核心业务逻辑模块，包含DAO、服务实现等
- **xk857-main-boot**: 主启动模块，包含控制器、配置、启动类等

## 📖 快速开始

### 1. 环境要求
- JDK 17+
- Maven 3.6+

### 2. 启动步骤
```bash
# 设置Java环境
export JAVA_HOME=/path/to/jdk-17

# 编译框架项目
cd xk857-framework && mvn clean install

# 启动主项目
cd ../xk857-main/xk857-main-boot && mvn spring-boot:run
```

### 3. 访问地址
- **项目首页**: http://localhost:18080/
- **Swagger UI**: http://localhost:18080/swagger-ui.html
- **Knife4j UI**: http://localhost:18080/doc.html
- **API文档**: http://localhost:18080/v3/api-docs

### 注解处理器说明

项目包含自定义的注解处理器，用于编译时检查注解使用规范：

- **`@MyApiResponse`**: 用于自定义API响应的状态码和消息
- **`@RawResponse`**: 标记方法或类不被全局响应拦截器处理
- **`MutualExclusionProcessor`**: 检查`@MyApiResponse`和`@RawResponse`不能同时使用

注解处理器会在编译时自动检查，如果发现违规使用会报告编译错误，确保代码质量。


## 学习文档

- [@RestControllerAdvice的致命一击——我以为的“优雅设计”，却成了新功能的“头号杀手”](https://blog.csdn.net/qq_45740561/article/details/149866512)
- [从“菜鸟崩溃”到“总监点赞”，我把糟糕的API改造成了团队标杆](https://blog.csdn.net/qq_45740561/article/details/149877543)

### 分支管理
- dp/bridge-basic：[停止滥用继承！这才是“组合”的正确打开方式：桥接模式深度剖析](https://blog.csdn.net/qq_45740561/article/details/118683594)
- dp/composite-basic：[组合模式实战：从 parent_id 到前端JSON，一次搞定权限菜单系统](https://blog.csdn.net/qq_45740561/article/details/118697630)
- dp/decorator-basic：[用装饰器模式，将复杂的功能链条编排成诗](https://blog.csdn.net/qq_45740561/article/details/118699641)
- dp/proxy-basic：[用代理模式为你的核心服务构建一个刀枪不入的保护层](https://blog.csdn.net/qq_45740561/article/details/118703716)