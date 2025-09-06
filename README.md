
# 桥接模式实战：告警系统（三维解耦）

## 访问地址

- **Knife4j UI**: http://localhost:18080/doc.html

## 说明文档

[停止滥用继承！这才是“组合”的正确打开方式：桥接模式深度剖析](https://blog.csdn.net/qq_45740561/article/details/118683594)

## 代码阅读

- 接口：POST `/api/alerts/send`

代码地图（阅读/调试建议）：
- 控制器：`AlertController`
- 服务：`AlertServiceImpl`
- 桥接抽象（严重级别）：`core/alert/bridge/{Alert, InfoAlert, CriticalAlert}`
- 渠道实现：`core/alert/channel/{ChannelSender, SmsSender, FeishuSender, EmailSender}`
- 模板实现：`core/alert/template/{AlertTemplate, TextTemplate, MarkdownTemplate}`
- IoC 工厂：`core/alert/factory/{SenderFactory, TemplateFactory, AlertFactory}`
- 配置绑定：`core/alert/config/AlertProperties`

调试建议：
- 在 `AlertFactory#create`、`CriticalAlert#doSend`、各 `ChannelSender#send` 处设断点，观察“严重级别×渠道×模板”的正交装配与调用链。
- 通过修改 `application.yml` 或请求体 `channel/template` 覆盖项，验证零 if-else 的切换效果。

扩展指引：
- 新增渠道：实现 `ChannelSender` 并标注 `@Component("your-channel")`。
- 新增模板：实现 `AlertTemplate` 并标注 `@Component("your-template")`。
- 新增级别：在 `AlertSeverity` 添加枚举，并在 `AlertFactory` 返回对应 `Alert` 子类。

## 桥接模式热身：手机 × 颜色（组合优于继承）

- 代码位置：`xk857-main-core/src/main/java/com/xk857/main/core/bridge/phone`
- 核心：`Phone`（抽象层）组合 `Color`（实现层），两个变化维度正交扩展。
- 运行演示：执行 `PhoneBridgeExample#main`，输出：
  - `蓝色的HUAWEI手机`
  - `红色的苹果手机`

