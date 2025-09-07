# 模板方法模式——消息发送流水线

本次新增一套基于模板方法模式的“消息发送流水线”，固化消息发送的黄金流程（校验 -> 加载用户 -> 构建上下文 -> 反垃圾 -> 幂等 -> 限流 -> 渲染 -> 渠道转换 -> 可选签名/加密 -> 实际发送 -> 审计归档）。

## 原文

[当你的代码需要一个“黄金流程”时，模板方法模式就是不二之选](https://blog.csdn.net/qq_45740561/article/details/118728558)

## 源码解读

### 关键类与入口

- 抽象模板：`AbstractMessageSender`
- 具体渠道：`SmsMessageSender`、`WecomMessageSender`
- 策略接口：`UserLoaderStrategy` 及 `MockUserLoader`
- 统一出入口：`MessageSendService`
- Web 调试入口：`MessageController`

### 快速调试步骤

1. 启动：运行 `xk857-main-boot` 的 `Application`。
2. 发送请求：POST `http://localhost:18080/api/messages/send`
   - 示例入参：
     {
       "channel":"SMS",
       "userId":1,
       "content":"**Hello** ${name}",
       "params":{"name":"张三"},
       "requestId":"req-001"
     }
3. 观察：控制台与标准化审计日志输出；重复 `requestId` 可命中幂等；高频请求可触发限流；包含敏感词可触发反垃圾。

