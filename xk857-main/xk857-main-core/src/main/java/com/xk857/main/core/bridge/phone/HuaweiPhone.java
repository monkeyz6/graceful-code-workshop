package com.xk857.main.core.bridge.phone;

/**
 * 品牌手机：HUAWEI
 */
public class HuaweiPhone extends Phone {
    public HuaweiPhone(Color color) { super(color); }

    @Override
    public void run() {
        System.out.println(color.render() + "HUAWEI手机");
    }
}
