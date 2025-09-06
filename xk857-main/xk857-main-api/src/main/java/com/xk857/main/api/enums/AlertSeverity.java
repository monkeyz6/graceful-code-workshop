package com.xk857.main.api.enums;

/**
 * 告警严重级别
 */
public enum AlertSeverity {
    INFO,
    WARN,
    CRITICAL;

    public static AlertSeverity from(String s) {
        if (s == null) return INFO;
        try {
            return AlertSeverity.valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return INFO;
        }
    }
}

