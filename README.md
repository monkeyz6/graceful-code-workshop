
## 源码阅读指引

### 原文

[用装饰器模式，将复杂的功能链条编排成诗](https://blog.csdn.net/qq_45740561/article/details/118699641?sharetype=blogdetail&sharerId=118699641&sharerefer=PC&sharesource=qq_45740561)

### 核心调试入口

- 控制器：`PaymentController`
  - 正确链：`POST /payment/correct`（日志→重试→折扣→幂等→核心）
  - 错误链：`POST /payment/wrong`（幂等在外、重试在内，用于演示问题）
- 装饰链关键方法（建议按此顺序打断点）
  - `LoggingPaymentDecorator.pay`
  - `RetryPaymentDecorator.pay`
  - `DiscountPaymentDecorator.pay`
  - `IdempotentPaymentDecorator.pay`
  - `BasicPaymentService.pay`
- 组装策略：看 `PaymentServiceBuilder.build`（顺序已固化，避免人为装错）

快速验证
- 正确链：
  - POST `/payment/correct?retries=3&discount=0.9`
  - Body 示例：`{"uniqueKey":"order-key-123","userId":1,"amount":100.00,"remark":"FAIL_FIRST_2"}`
  - 预期：`coreAttempts≈3`，且金额被九折
- 错误链：
  - POST `/payment/wrong?retries=3`
  - Body 示例同上
  - 预期：核心在一次请求内被多次调用（说明顺序问题）

提示
- 幂等尽量贴近核心；重试/熔断在外层；日志最外层。
- VIP 用户示例：`1、2`；默认折扣 `0.9`。
