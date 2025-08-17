package com.xk857.framework.constants;

/**
 * 应用程序常量类
 * 用于存储应用程序相关的配置常量
 * 
 * @author cv大魔王
 */
public final class ApplicationConstants {
    
    /**
     * 私有构造函数，防止实例化
     */
    private ApplicationConstants() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    // ================== 应用信息常量 ==================
    
    /**
     * 应用程序版本
     */
    public static final String APP_VERSION = "1.0.0";
    
    /**
     * 应用程序描述
     */
    public static final String APP_DESCRIPTION = "旨在探索如何写出正确且优雅的代码";
    
    /**
     * 欢迎消息
     */
    public static final String WELCOME_MESSAGE = "🎉 欢迎来到优雅代码实战工坊！";
    
    /**
     * Hello消息
     */
    public static final String HELLO_MESSAGE = "Hello, 欢迎来到优雅代码实战工坊! 🎉";
    
    /**
     * 个性化Hello消息模板
     */
    public static final String HELLO_NAME_MESSAGE_TEMPLATE = "Hello, %s! 欢迎来到优雅代码实战工坊! 🚀";
    

    // ================== 统计信息常量 ==================
    
    /**
     * 用户统计描述
     */
    public static final String USER_STATS_DESCRIPTION = "用户统计信息";
    
    /**
     * 总用户数字段名
     */
    public static final String TOTAL_USERS_FIELD = "totalUsers";
    
    /**
     * 描述字段名
     */
    public static final String DESCRIPTION_FIELD = "description";
    
    /**
     * 更新时间字段名
     */
    public static final String UPDATE_TIME_FIELD = "updateTime";
}
