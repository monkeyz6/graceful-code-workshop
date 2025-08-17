package com.xk857.framework.constants;

/**
 * 公共常量类
 * 用于存储项目中的通用常量，避免硬编码
 * 
 * @author cv大魔王
 */
public final class CommonConstants {
    
    /**
     * 私有构造函数，防止实例化
     */
    private CommonConstants() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    // ================== HTTP状态码常量 ==================
    
    /**
     * HTTP状态码 - 成功
     */
    public static final int HTTP_SUCCESS = 200;
    
    /**
     * HTTP状态码 - 请求参数错误
     */
    public static final int HTTP_BAD_REQUEST = 400;
    
    /**
     * HTTP状态码 - 未授权
     */
    public static final int HTTP_UNAUTHORIZED = 401;
    
    /**
     * HTTP状态码 - 拒绝访问
     */
    public static final int HTTP_FORBIDDEN = 403;
    
    /**
     * HTTP状态码 - 资源不存在
     */
    public static final int HTTP_NOT_FOUND = 404;
    
    /**
     * HTTP状态码 - 请求方法不允许
     */
    public static final int HTTP_METHOD_NOT_ALLOWED = 405;
    
    /**
     * HTTP状态码 - 服务器内部错误
     */
    public static final int HTTP_INTERNAL_SERVER_ERROR = 500;
    
    /**
     * HTTP状态码 - 服务暂不可用
     */
    public static final int HTTP_SERVICE_UNAVAILABLE = 503;
    
    // ================== 业务状态码常量 ==================
    
    /**
     * 业务状态码 - 成功
     */
    public static final int SUCCESS_CODE = 200;
    
    /**
     * 业务状态码 - 服务器内部错误
     */
    public static final int ERROR_CODE = 500;
    
    // ================== 通用消息常量 ==================
    
    /**
     * 成功消息
     */
    public static final String SUCCESS_MESSAGE = "操作成功";
    
    /**
     * 查询成功消息
     */
    public static final String QUERY_SUCCESS_MESSAGE = "查询成功";
    
    /**
     * 参数校验失败消息
     */
    public static final String VALIDATION_FAILED_MESSAGE = "参数校验失败";
    
    /**
     * 参数绑定失败消息
     */
    public static final String BINDING_FAILED_MESSAGE = "参数绑定失败";
    
    /**
     * 资源未找到消息
     */
    public static final String RESOURCE_NOT_FOUND_MESSAGE = "资源未找到";
    
    /**
     * 服务器错误消息
     */
    public static final String SERVER_ERROR_MESSAGE = "服务器开小差了，请稍后重试";
    
    /**
     * 响应序列化失败消息
     */
    public static final String SERIALIZATION_FAILED_MESSAGE = "响应序列化失败";
    
    // ================== 分页常量 ==================
    
    /**
     * 默认页码
     */
    public static final int DEFAULT_PAGE_NUM = 1;
    
    /**
     * 最小页码
     */
    public static final int MIN_PAGE_NUM = 1;
}
