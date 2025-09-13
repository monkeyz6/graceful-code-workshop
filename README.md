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

## 分支管理

### 事故到架构：Java设计模式車构录

- dp/bridge-basic：[停止滥用继承！这才是“组合”的正确打开方式：桥接模式深度剖析](https://blog.csdn.net/qq_45740561/article/details/118683594)
- dp/composite-basic：[组合模式实战：从 parent_id 到前端JSON，一次搞定权限菜单系统](https://blog.csdn.net/qq_45740561/article/details/118697630)
- dp/decorator-basic：[用装饰器模式，将复杂的功能链条编排成诗](https://blog.csdn.net/qq_45740561/article/details/118699641)
- dp/proxy-basic：[用代理模式为你的核心服务构建一个刀枪不入的保护层](https://blog.csdn.net/qq_45740561/article/details/118703716)
- dp/facede-basic：[一个好的“外观”，能让你的API像苹果产品一样简单好用](https://blog.csdn.net/qq_45740561/article/details/118704693)
- dp/strategy-basic：[策略设计模式：让你的代码能跟上营销团队的“脑洞”](https://blog.csdn.net/qq_45740561/article/details/118710048)
- dp/template-basic：[当你的代码需要一个“黄金流程”时，模板方法模式就是不二之选](https://blog.csdn.net/qq_45740561/article/details/118728558)
- dp/iterator-basic：[别再只用 for-each 了！我用迭代器模式，写了个“带回滚功能”的轻量工作流引擎](https://blog.csdn.net/qq_45740561/article/details/151283204)


## 别让烂代码，毁了你的职业生涯

- craft/redisson-5-weapons：[Redisson封神之路：锁之外的5款“核武器”](https://blog.csdn.net/qq_45740561/article/details/151372905)
- craft/redisson-8-locks：[Redisson宇宙：8个“生产事故”带你彻底掌握8种分布式锁](https://blog.csdn.net/qq_45740561/article/details/151333619)
- craft/cas-aba-longadder：[你真的理解 AtomicInteger 身后的 ABA 问题和 LongAdder 吗？](https://blog.csdn.net/qq_45740561/article/details/151025432)
- craft/redis-zset-leaderboard：[停止用ZSET做“玩具”！你的实时榜单，离生产环境还差十个“战场”](https://blog.csdn.net/qq_45740561/article/details/151288727)
- craft/redis-lottery-safe：[你用Redis实现的高并发抽奖系统真的靠谱吗？](https://blog.csdn.net/qq_45740561/article/details/151286005)
- craft/guava-swiss-army：[Guava：让你告别99%的垃圾代码，这才是Java高级玩家的“瑞士军刀”](https://blog.csdn.net/qq_45740561/article/details/151049994)
- craft/spring-async-scheduled：[关于Spring定时与异步，官方文档没告诉你的那些“坑”](https://blog.csdn.net/qq_45740561/article/details/151046229)
- craft/thread-start-twice：[一个线程两次调用start()，JVM为何直接“叛乱”？](https://blog.csdn.net/qq_45740561/article/details/150943346)
- craft/reentrantlock-in-practice：[MyBatis 和 JDK 大师们，是如何把 ReentrantLock 玩出花的？](https://blog.csdn.net/qq_45740561/article/details/150936525)
- craft/completablefuture-deadlock：[CPU占用率0%却卡死一切：一次由CompletableFuture主导的“完美谋杀案”](https://blog.csdn.net/qq_45740561/article/details/150872902)
- craft/java-io-zero-copy：[Java IO性能黑洞：一次「上下文切换」到底偷走了你多少性能？](https://blog.csdn.net/qq_45740561/article/details/150788541)
- craft/static-hashmap-storm：[那行static HashMap，是如何把服务器CPU干到100%的？](https://blog.csdn.net/qq_45740561/article/details/150786773)
- craft/string-int-list-traps：[穿透API，看懂Spring、Dubbo如何选择“兵器”](https://blog.csdn.net/qq_45740561/article/details/150784249)
- craft/java-references-ghosts：[Java内存四大“幽灵”：强、软、弱、幻象引用，你被哪个缠上了？](https://blog.csdn.net/qq_45740561/article/details/150781664)
- craft/best-practice-myths：[别再信“最佳实践”了！今天你写的“最优解”，就是明天的技术巨债](https://blog.csdn.net/qq_45740561/article/details/150723236)
- craft/final-defensive-coding：[“final”关键字：一个被80% Java程序员低估的防御性编程核武器](https://blog.csdn.net/qq_45740561/article/details/150722530)
- craft/toString-leak：[你IDE生成的toString()，正在把你的生产环境拖入深渊](https://blog.csdn.net/qq_45740561/article/details/150721730)
- craft/security-three-assassins：[一场价值60亿美金的复盘：深扒潜伏在代码里的“三大刺客”](https://blog.csdn.net/qq_45740561/article/details/150721467)
- craft/integer-overflow-breach：[代码审计启示录：一个整数溢出，竟是史上最大数据泄露的导火索](https://blog.csdn.net/qq_45740561/article/details/150655099)
- craft/stateless-architecture：[为什么你拼命加机器，系统还是扛不住？聊聊状态的“原罪”](https://blog.csdn.net/qq_45740561/article/details/150654476)
- craft/new-wrapper-bans：[为什么阿里代码规范严禁 new 原始类型包装类？看完这篇我悟了](https://blog.csdn.net/qq_45740561/article/details/150620661)
- craft/threadlocal-ghosts：[P0级告警！Spring单例下的幽灵状态，连ThreadLocal都救不了你？](https://blog.csdn.net/qq_45740561/article/details/150620452)
- craft/string-immutability：[String为何被设计成不可变？这背后是Java并发编程的半壁江山](https://blog.csdn.net/qq_45740561/article/details/150563921)
- craft/jdk-bad-designs：[别谈“过度设计”了，我们先来聊聊 JDK 里的“欠揍设计”](https://blog.csdn.net/qq_45740561/article/details/150563543)
- craft/strategy-autoswitch：[Spring源码启示录：别手写策略模式了，学学“无感切换”的艺术](https://blog.csdn.net/qq_45740561/article/details/150535100)
- craft/code-performance-mindset：[为什么你的代码又慢又烂？因为你只在乎“功能实现”](https://blog.csdn.net/qq_45740561/article/details/150534864)
- craft/hidden-feature-p0：[你的“隐藏功能”不是彩蛋，是埋给整个团队的P0级事故](https://blog.csdn.net/qq_45740561/article/details/150483067)
- craft/code-organization-law：[为什么有的人一眼就看出你的代码是外包写的？](https://blog.csdn.net/qq_45740561/article/details/150474233)
- craft/code-hygiene：[为什么你的代码总在“半夜鸡叫”？聊聊那些被忽视的“代码洁癖”](https://blog.csdn.net/qq_45740561/article/details/150473896)
- craft/i-thought-disaster：[从一行烂代码到一场生产灾难，中间只隔着一个“我以为”](https://blog.csdn.net/qq_45740561/article/details/150471175)
- craft/chain-of-responsibility：[停止写你的“面条代码”！用责任链模式+Spring，构建优雅的“处理管道”](https://blog.csdn.net/qq_45740561/article/details/150432222)
- craft/observer-refactor：[停止写“牵一发而动全身”的代码！用观察者模式构建可插拔的业务响应](https://blog.csdn.net/qq_45740561/article/details/150402203)
- craft/flyweight-list：[从“内存溢出”到“秒级渲染”：享元模式在电商商品列表页的极限优化](https://blog.csdn.net/qq_45740561/article/details/150401439)
- craft/decorator-pricing：[装饰器模式：那个让MyBatis缓存和Java IO“无限续杯”的秘密](https://blog.csdn.net/qq_45740561/article/details/150358522)
- craft/composite-pricing：[重构实录：我如何用组合模式，干掉一个充满 instanceof 的价格计算服务？](https://blog.csdn.net/qq_45740561/article/details/150336782)
- craft/bridge-adapter-anticorruption：[真实业务里，桥接比继承强在哪里？适配器又如何护住“反腐层”](https://blog.csdn.net/qq_45740561/article/details/150300256)
- craft/builder-param-hell：[建造者模式：从“参数地狱”到优雅构建](https://blog.csdn.net/qq_45740561/article/details/150205890)
- craft/factory-kill-if：[重构实战：三步用工厂模式干掉那个300行的if-else](https://blog.csdn.net/qq_45740561/article/details/150146500)
- craft/spring-web-traps：[别再信“最佳实践”了，Spring Web这7个“约定”专坑老实人](https://blog.csdn.net/qq_45740561/article/details/150025196)
- craft/spring-aop-order：[别再信“约定优于配置”了！Spring AOP 的执行顺序，是一场精心设计的“鸿门宴”](https://blog.csdn.net/qq_45740561/article/details/149982769)
- craft/spring-scan-traps：[我把Controller文件挪了一下，Spring当场“去世”了](https://blog.csdn.net/qq_45740561/article/details/149947636)
- craft/modern-java-upgrade：[SpringBoot3和Kafka4已叛逃Java17阵营，你的“传家宝”代码还能撑多久？](https://blog.csdn.net/qq_45740561/article/details/149914794)
- craft/dead-letter-curse：[死信的诅咒：那个在队列里“永不超生”的消息，最终搞垮了我们](https://blog.csdn.net/qq_45740561/article/details/149886933)
- craft/sensitive-data-storage：[从删库到坐牢：聊聊那些关于敏感数据存储和传输的致命细节](https://blog.csdn.net/qq_45740561/article/details/149886849)
- craft/generic-reflection-crime：[生产事故复盘：当泛型、反射、继承联手，上演了一出“完美犯罪”](https://blog.csdn.net/qq_45740561/article/details/149886737)
- craft/enum-api-pitfalls：[一来一回，你已不是你：深扒Java枚举在API接口中的两大天坑](https://blog.csdn.net/qq_45740561/article/details/149886596)
- craft/idempotency-uuid：[金融级防重设计：为什么说UUID.randomUUID()是支付接口的“头号公敌”？](https://blog.csdn.net/qq_45740561/article/details/149886168)
- craft/aop-tx-order-bug：[Spring AOP顺序错乱：从“日志功臣”到“事务杀手”的惊魂一夜](https://blog.csdn.net/qq_45740561/article/details/149886023)
- craft/redis-snowball-avalanche：[Redis雪崩时，没有一个Key是无辜的：一次“集体阵亡”引发的血案](https://blog.csdn.net/qq_45740561/article/details/149879659)
- craft/redis-triple-kill：[Redis夺命三连：从KEYS锁死到巨无霸键撑爆内存，鬼知道我经历了什么](https://blog.csdn.net/qq_45740561/article/details/149879562)
- craft/api-refactor-standard：[从“菜鸟崩溃”到“总监点赞”，我把糟糕的API改造成了团队标杆](https://blog.csdn.net/qq_45740561/article/details/149877543)
- craft/restcontrolleradvice-traps：[@RestControllerAdvice的致命一击——我以为的“优雅设计”，却成了新功能的“头号杀手](https://blog.csdn.net/qq_45740561/article/details/149866512)
- craft/log-additivity-pitfall：[从日志重复到服务雪崩：一个additivity=“true“引发的P0级生产事故](https://blog.csdn.net/qq_45740561/article/details/149843975)
- craft/date-y-bug：[就因为一个大写“Y”，我们公司差点提前过年了](https://blog.csdn.net/qq_45740561/article/details/149843939)
- craft/reflection-template：[反射与模板的威力：当你的代码学会“举一反三”](https://blog.csdn.net/qq_45740561/article/details/149843903)
- craft/file-io-traps：[解剖一个拖垮系统的文件读写操作：你的IO代码为何慢如龟爬？](https://blog.csdn.net/qq_45740561/article/details/149814669)
- craft/aop-global-exception-bad：[别再用AOP做“统一异常处理”了，你那是给系统挖坑，不是兜底！](https://blog.csdn.net/qq_45740561/article/details/149814123)
- craft/null-overwrite-bug：[一个null引发的数据蒸发悬案：我是如何拯救被“清空”的用户档案的](https://blog.csdn.net/qq_45740561/article/details/149788829)
- craft/equals-hashset-conflict：[一个对象的两种身份：当equals()说“是”，HashSet却说“不”](https://blog.csdn.net/qq_45740561/article/details/149788868)
- craft/bigdecimal-traps：[BigDecimal的致命一击：一行代码引发的30万资金对账悬案](https://blog.csdn.net/qq_45740561/article/details/149767798)
- craft/list-sublist-leak：[CTO深夜咆哮：“为什么一个1KB的列表，吃掉了1GB的内存？！”](https://blog.csdn.net/qq_45740561/article/details/149767704)
- craft/db-cpu100-bestpractice：[数据库CPU100%！一个应届生是如何用“最佳实践”搞垮整个业务的？](https://blog.csdn.net/qq_45740561/article/details/149757747)
- craft/ribbon-retry-icu：[那年双十一，我们被Ribbon的“善意”重试，送进了ICU](https://blog.csdn.net/qq_45740561/article/details/149737713)
- craft/executors-banned：[为什么《阿里Java开发手册》强制禁用Executors？看完这篇血泪史你就懂了。](https://blog.csdn.net/qq_45740561/article/details/149737591)
- craft/connection-pool-triple：[从数据错乱到服务假死：一个连接池引发的三起连环血案](https://blog.csdn.net/qq_45740561/article/details/149706748)
- craft/lock-misuse-four：[别笑，这四种“锁“的错误用法，我敢说你的项目里至少中了一个](https://blog.csdn.net/qq_45740561/article/details/149694696)
- craft/threadlocal-leak：[我的用户信息，怎么被隔壁老王改了？元凶竟是ThreadLocal！](https://blog.csdn.net/qq_45740561/article/details/149694272)
- craft/requestbody-trust-fail：[一个@RequestBody竟让我们赔了上百万！—— 永远不要相信客户端的数据](https://blog.csdn.net/qq_45740561/article/details/149694049)
- craft/transactional-bombs：[别动！你写的@Transactional，80%都是定时炸弹——说说Spring事务那点事～～](https://blog.csdn.net/qq_45740561/article/details/149693576)
- craft/naming-rules：[代码命名：你以为是技术活儿？不，这是情商税！](https://blog.csdn.net/qq_45740561/article/details/149692624)
- craft/stopwatch-not-ctm：[还在用System.currentTimeMillis()算耗时？求求你，快停手吧！](https://blog.csdn.net/qq_45740561/article/details/149691184)
- craft/value-seven-sins：[别再玷污你的代码了！@Value的七宗罪与@ConfigurationProperties的救赎](https://blog.csdn.net/qq_45740561/article/details/129132933)
- craft/dotenv-spring-config：[别再把密码写在application.yml里了！这个神仙工具让你的 Spring Boot 配置又安全又优雅！](https://blog.csdn.net/qq_45740561/article/details/149690457)
- craft/remote-debug-jdwp：[SpringBoot之DEBUG远程调试黑科技？](https://blog.csdn.net/qq_45740561/article/details/129133024)
