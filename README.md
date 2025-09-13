# Redisson 

## 原文

[Redisson封神之路：锁之外的5款“核武器”](https://blog.csdn.net/qq_45740561/article/details/151372905)

## 核心调试入口

- 启动类：`xk857-main-boot/Application`
- Redis：本地 `localhost:6379`，无密码（单节点）。
- Redis：配置位于 `application.yml` 的 `spring.data.redis.*`（默认 `127.0.0.1:6379`，密码留空）。
- 配置类：`config/RedissonConfig`（创建 `RedissonClient`）。
- 基础服务：`service/RedissonDemoService`（RLock/RBucket/RRateLimiter）。
- 基础接口：`controller/RedissonDemoController`（路径前缀 `/redisson`）。
- 短信限流：`service/SmsService` + `controller/SmsController`（RRateLimiter OVERALL）。演示为 1 QPS，生产建议 ≥200 QPS 且配置化。
- 延迟取消：`service/OrderService` + `consumer/OrderCancelConsumer` + `controller/OrderController`（RDelayedQueue）。
- 集合/缓存/广播：`service/ProductService` + `listener/CacheInvalidationListener` + `controller/ProductController`（RBloomFilter/RMapCache/RTopic）。
- 事务：`service/PostLikeService` + `controller/PostController`（RTransaction）。

### 快速跑起来

- 准备 Redis：本机启动 Redis（无密码，端口 6379）。
- 启动应用：运行 `Application` 主类，访问 `http://localhost:18080/doc.html`。
- 断点建议：在 `RedissonDemoService#doWithLock`、`setAndGet`、`rateLimitTryAcquire` 处下断点。

### 接口速测（浏览器/curl均可）

- 锁：`GET /redisson/lock?key=order123&leaseSeconds=5&bizMillis=2000`
- KV：`GET /redisson/kv?key=k1&value=v1&ttlSeconds=30`
- 全局限流：`GET /redisson/sms/send?phone=13800000000`
- 下单：`POST /redisson/order/create?delaySeconds=5`（演示默认 5s；生产建议 15~30min 按业务设定）
- 支付：`POST /redisson/order/pay?id=1`
- 查单：`GET /redisson/order/get?id=1`
- 商品获取（带Bloom+MapCache）：`GET /redisson/product/get?id=p-123`
- 更新价格并广播：`POST /redisson/product/updatePrice?id=p-123&price=6666`
- 本地缓存查看：`GET /redisson/product/localCache?id=p-123`
- 点赞：`POST /redisson/post/like?userId=u1&postId=postA`
- 点赞数：`GET /redisson/post/likes?postId=postA`

### 说明
- 仅接入 Redisson，不依赖真实数据库；Redis 地址固定为本地 6379（可按需在 `RedissonConfig` 中调整）。
