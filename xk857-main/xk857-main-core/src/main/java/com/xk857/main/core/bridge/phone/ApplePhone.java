package com.xk857.main.core.bridge.phone;

/**
 * 品牌手机：Apple
 */
public class ApplePhone extends Phone {
    public ApplePhone(Color color) { super(color); }

    @Override
    public void run() {
        System.out.println(color.render() + "苹果手机");
    }
}
