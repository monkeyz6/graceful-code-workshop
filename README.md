# 迭代器模式实战

## 原文

[别再只用 for-each 了！我用迭代器模式，写了个“带回滚功能”的轻量工作流引擎](https://blog.csdn.net/qq_45740561/article/details/151283204)

## 源码解读

核心思路：将流程拆成独立 Step（积木），由 Workflow 托盘顺序装载，WorkflowEngine 负责正向执行与失败时反向补偿，彻底把“怎么走”和“走哪里”分离开。

### 核心类

- workflow 引擎：`Workflow`, `WorkflowEngine`, `FlowContext`, `Step`, `Compensable`, `StepResult`
- 业务步骤：`ValidateInputStep`, `ReserveInventoryStep`, `ChargeBalanceStep`, `NotifyUserStep`
- 模拟网关/仓库：`InventoryRepository`, `AccountRepository`, `NotificationGateway`
- 服务与接口：`OrderService`、`OrderServiceImpl`
- 调试入口：`OrderController`

### 快速调试

- 运行入口：`Application`（main-boot 模块）
- 启动后访问 Swagger：`http://localhost:18080/swagger-ui.html` 或 Knife4j：`http://localhost:18080/doc.html`
- 下单接口：`POST /api/order/place`
  示例请求体：
  {
    "userId": 1,
    "productId": 1001,
    "quantity": 2,
    "forcePayFail": false
  }
- 回滚演示：将 `forcePayFail` 置为 `true` 可触发支付失败，观察日志中“反向补偿”流程。

### Debug建议

- 优先断点：`OrderController.placeOrder -> OrderServiceImpl.placeOrder`
- 工作流核心：`WorkflowEngine.run`（正向执行/异常回滚的主干逻辑）
- 步骤编排：`ValidateInputStep/ReserveInventoryStep/ChargeBalanceStep/NotifyUserStep`
