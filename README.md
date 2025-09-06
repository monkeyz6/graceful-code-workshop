
## 代理模式六大场景最小可跑示例

提供“可直接跑”的代理模式实践代码，覆盖保护代理、远程代理、缓存代理、虚拟代理、重试与日志追踪，均基于 Spring AOP/Cache 或 JDK 动态代理实现，使用内存与随机数据，无需外部依赖。

### 原文

[用代理模式为你的核心服务构建一个刀枪不入的保护层](https://blog.csdn.net/qq_45740561/article/details/118703716)

### 核心类入口：

- 注解：framework-core aop.annotation 下的 `@AdminOnly`、`@RetryableLite`、`@Traceable`
- 切面：framework-core aop 下的 `SecurityAspect`、`RetryAspect`、`TraceAspect`
- 缓存启用：framework-core config 下的 `CacheConfig`
- 交易核心：main-core trading 下的 `CoreTradingEngine`、`TradingService`、`TradingQueryService`
- 远程代理：main-core market 下的 `MarketDataProxyFactory`（JDK 动态代理生成 `MarketDataClient`）
- 虚拟代理：main-core virtual 下的 `ChartVirtualProxy`、`HeavyChart`
- API 控制器：main-boot controller 下的 `TradingController`

### 快速调试步骤：

1. 启动：运行 `xk857-main-boot/Application`（端口 `18080`）。
2. 执行交易（保护代理 + 重试 + 远程代理 + 追踪）：
   - 必须带头 `X-ROLE: admin`
   - POST `http://localhost:18080/api/trade/execute`
   - Body: `{ "symbol":"BTCUSDT", "quantity":3, "side":"BUY", "clientId":"c1" }`
3. 查询摘要（缓存代理 + 追踪）：
   - GET `http://localhost:18080/api/trade/summary/1` 连续调用两次，第二次应命中缓存。
4. 虚拟代理（首次慢、后续快）：
   - GET `http://localhost:18080/api/trade/chart`

### 说明：

- 所有横切关注点（权限、重试、日志、缓存）不入侵核心类 `CoreTradingEngine`，仅通过注解与代理生效。
- 远程调用通过 JDK 动态代理模拟，无真实网络依赖。

