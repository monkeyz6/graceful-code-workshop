# Redisson 并发“兵器谱”实战

覆盖 8 个常见业务场景。示例对齐现有项目风格：核心逻辑放在 `xk857-main-core`，对外接口位于 `xk857-main-boot`。

## 原文

craft/redisson-8-locks：[Redisson宇宙：8个“生产事故”带你彻底掌握8种分布式锁](https://blog.csdn.net/qq_45740561/article/details/151333619)

## 快速开始（Debug 入口）

- 运行入口：`xk857-main-boot/Application.main`
- 本地 Redis：`localhost:6379`（无密码，db=0）
- 启动步骤：
  - 本地起 Redis（如 `redis-server`）。
  - 启动应用（IDEA 运行 `Application.main` 或 `mvn -pl xk857-main/xk857-main-boot spring-boot:run`）。
  - 打开 Swagger：`http://localhost:18080/swagger-ui.html`（应用端口见 application.yml），在 Redisson 分组下调接口即可。

## 场景 & 关键类（按需查阅）

- 可重入锁 RLock（秒杀）
  - 核心类：`StockService`（@Service），控制器：`RedissonLockController`（@RestController）
  - 尝试：`POST /api/redisson/stock/reset`，`POST /api/redisson/stock/buy`
- 公平锁 RFairLock（摇号）
  - 核心类：`LotteryService`，控制器：`RedissonFairLockController`
  - 尝试：`POST /api/redisson/lottery/apply`，`GET /api/redisson/lottery/queue`
- 联锁 MultiLock（跨账户转账）
  - 核心类：`TransferService`，控制器：`RedissonMultiLockController`
  - 尝试：`POST /api/redisson/transfer/reset`，`POST /api/redisson/transfer`
- 红锁 RedLock（世界BOSS）
  - 核心类：`WorldBossService`，控制器：`RedissonRedLockController`
  - 尝试：`POST /api/redisson/worldboss/spawn`
  - 说明：示例为演示用途使用同一 Redis，多主需按生产架构配置。
- 读写锁 RReadWriteLock（航班大屏）
  - 核心类：`FlightInfoService`，控制器：`RedissonRWLockController`
  - 尝试：`GET /api/redisson/flight/status`，`POST /api/redisson/flight/update`
- 信号量 RSemaphore（转码限流）
  - 核心类：`TranscodingService`，控制器：`RedissonSemaphoreController`
  - 尝试：`POST /api/redisson/transcoding/submit`
- 可过期信号量 RPermitExpirableSemaphore（容错许可）
  - 核心类：`TranscodingServiceV2`，控制器：`RedissonSemaphoreController`
  - 尝试：`POST /api/redisson/transcoding/submit-exp`
- 闭锁 RCountDownLatch（报表聚合）
  - 核心类：`ReportService`，控制器：`RedissonLatchController`
  - 尝试：`POST /api/redisson/report/generate`

## 配置说明
- Redisson 配置：见 `application.yml` 的 `redisson.*`，配置类 `RedissonConfig`（@Configuration）基于该配置初始化客户端。
- 示例中的数据（库存、账户、航班状态等）均为内存模拟，不依赖真实数据库，便于本地并发演练。

> 注：文章细节与机制解析请参考原文；此处仅保留最小可跑骨架与调试入口，方便快速上手与验证。
