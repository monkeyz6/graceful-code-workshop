## 外观模式实战：让多渠道推送像苹果一样丝滑

### 原文

[一个好的“外观”，能让你的API像苹果产品一样简单好用](https://blog.csdn.net/qq_45740561/article/details/118704693)

### 核心入口与调试步骤

- 关键调试类：
  - 外观接口：`MessageFacade`
  - 同步外观实现：`DefaultMessageFacade`
  - 异步外观实现：`AsyncMessageFacade`（基于内存 `SimpleEventBus` 模拟队列）
  - 渠道客户端：`SmsChannelClient`、`WeComChannelClient`、`DingTalkChannelClient`、`InternalMessageChannelClient`
  - 演示控制器：`MessageController`

### 最小调用（建议用 curl 或 Swagger UI）

1. 广播（同步）
```
POST /api/message/broadcast
{
  "title": "登录成功",
  "content": "欢迎回来",
  "receiver": "some-receiver"
}
```

2. 策略发送（同步，默认优先级：企微→钉钉→短信→站内；`breakOnFirstSuccess=true`）
```
POST /api/message/send?breakOnFirstSuccess=true
{
  "title": "登录成功",
  "content": "欢迎回来",
  "receiver": "token-or-phone"
}
```

3. 异步调用（立即返回 queued，后台消费者再转调同步外观）
```
POST /api/message/broadcast-async
POST /api/message/send-async?breakOnFirstSuccess=true
```

提示：为便于演示失败场景，可在 `content` 中包含 `fail:SMS`、`fail:WeCom`、`fail:DingTalk`、`fail:Internal` 触发对应渠道的模拟失败。

