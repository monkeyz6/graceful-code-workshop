package com.xk857.framework.processor.enmu;

/**
 * @author yeshenyue on 2025/8/3 09:19.
 */
public enum APIVersionEnum {

    V1("/v1", "v1"),
    V2("/v2", "这里可以提供备注，例如：权限模块需要统一迁移到v2，v1版本即将废弃");

    private final String[] path;
    private final String displayName;

    APIVersionEnum(String path, String displayName) {
        this.path = new String[]{path};
        this.displayName = displayName;
    }

    public String[] path() {
        return path;
    }

    public String displayName() {
        return displayName;
    }
}
