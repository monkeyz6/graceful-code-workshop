package com.xk857.main.core.bridge.phone;

/**
 * 演示：手机 × 颜色 的桥接组合
 * 说明：展示两轴正交扩展的效果，新增颜色或品牌互不影响。
 */
public class PhoneBridgeExample {
    public static void main(String[] args) {
        Phone blueHuawei = new HuaweiPhone(new Blue());
        Phone redApple = new ApplePhone(new Red());

        blueHuawei.run();
        redApple.run();
    }
}
