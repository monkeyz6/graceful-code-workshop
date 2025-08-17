package com.xk857.framework.swagger.constants;

import java.util.Map;
import java.util.Set;

/**
 * Swagger文档相关常量
 * 
 * @author cv大魔王
 */
public final class SwaggerConstants {
    
    /**
     * 私有构造函数，防止实例化
     */
    private SwaggerConstants() {
        throw new UnsupportedOperationException("Utility class");
    }

    // ================== Result结构常量 ==================
    
    /**
     * Result成功字段名
     */
    public static final String RESULT_SUCCESS_FIELD = "success";
    
    /**
     * Result状态码字段名
     */
    public static final String RESULT_CODE_FIELD = "code";
    
    /**
     * Result消息字段名
     */
    public static final String RESULT_MESSAGE_FIELD = "message";
    
    /**
     * Result数据字段名
     */
    public static final String RESULT_DATA_FIELD = "data";

    /**
     * Result结构的所有字段
     */
    public static final Set<String> RESULT_FIELDS = Set.of(
        RESULT_SUCCESS_FIELD,
        RESULT_CODE_FIELD,
        RESULT_MESSAGE_FIELD,
        RESULT_DATA_FIELD
    );

    // ================== 媒体类型常量 ==================
    
    /**
     * JSON媒体类型关键字
     */
    public static final String JSON_MEDIA_TYPE_KEY = "json";
    
    /**
     * 应用JSON媒体类型
     */
    public static final String APPLICATION_JSON = "application/json";
    
    // ================== Schema常量 ==================
    
    /**
     * 组件Schema路径前缀
     */
    public static final String COMPONENTS_SCHEMAS_PREFIX = "#/components/schemas/";
    
    /**
     * ResultVoid引用后缀
     */
    public static final String RESULT_VOID_SUFFIX = "/ResultVoid";
    
    /**
     * Result名称模板
     */
    public static final String RESULT_NAME_TEMPLATE = "Result«%s»";
    
    // ================== 默认示例数据常量 ==================
    
    /**
     * 默认成功状态
     */
    public static final boolean DEFAULT_SUCCESS = true;
    
    /**
     * 默认成功状态码
     */
    public static final int DEFAULT_SUCCESS_CODE = 200;
    
    /**
     * 默认成功消息
     */
    public static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";

    // ================== 特殊方法Schema配置 ==================

    /**
     * 搜索用户方法的字段配置
     */
    public static final Map<String, String> SEARCH_USERS_FIELDS = Map.of(
        "list", "array",
        "total", "integer",
        "pageNum", "integer",
        "pageSize", "integer",
        "totalPages", "integer"
    );

    /**
     * 用户统计方法的字段配置
     */
    public static final Map<String, String> USER_STATS_FIELDS = Map.of(
        "totalUsers", "integer",
        "description", "string",
        "updateTime", "string"
    );

    /**
     * 方法名到字段配置的映射
     */
    public static final Map<String, Map<String, String>> METHOD_SCHEMA_CONFIG = Map.of(
        "searchUsers", SEARCH_USERS_FIELDS,
        "getUserStats", USER_STATS_FIELDS
    );

    // ================== 已知实体类型常量 ==================

    /**
     * 用户DTO类型名
     */
    public static final String USER_DTO_TYPE = "UserDTO";

    /**
     * 支持示例数据生成的实体类型
     */
    public static final Set<String> SUPPORTED_ENTITY_TYPES = Set.of(USER_DTO_TYPE);
}
