## 策略模式计价引擎

### 原文

[策略设计模式：让你的代码能跟上营销团队的“脑洞”](https://blog.csdn.net/qq_45740561/article/details/118710048)

### 核心类

- 接口：`PricingStrategy`（定义计价军规）
- 上下文：`PriceContext`（标价、用户画像、策略码）
- 服务：`SpringPricingService`（按策略码路由并执行策略）
- 策略实现：`DiscountVipStrategy`、`Voucher300_50Strategy`、`NewUserSpecialStrategy`、`NormalStrategy`
- 控制器：`PricingController`

### 类路径

- main-api
  - dto/`PriceContext`
  - strategy/`PricingStrategy`
  - enums/`PricingErrorEnum`
- main-core
  - service/pricing/`SpringPricingService`、`UserProfileService`
  - strategy/`DiscountVipStrategy`、`Voucher300_50Strategy`、`NewUserSpecialStrategy`、`NormalStrategy`
- main-boot
  - controller/`PricingController`

### 快速调试入口

- 列出可用策略：`GET /api/pricing/strategies`
- 计算价格：`POST /api/pricing/calc`

示例请求（计算 VIP 折扣）：
```
POST /api/pricing/calc
{
  "listPrice": 399.00,
  "userId": 2,
  "strategyCode": "DISCOUNT_VIP"
}
```
