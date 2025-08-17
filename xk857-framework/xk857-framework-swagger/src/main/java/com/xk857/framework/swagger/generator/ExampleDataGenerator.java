package com.xk857.framework.swagger.generator;

import com.xk857.framework.swagger.constants.SwaggerConstants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 示例数据生成器
 * 用于生成Swagger文档中的示例数据
 * 
 * @author cv大魔王
 */
public final class ExampleDataGenerator {
    
    /**
     * 私有构造函数，防止实例化
     */
    private ExampleDataGenerator() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    /**
     * 生成基础Result示例
     */
    public static Map<String, Object> generateBaseResultExample() {
        Map<String, Object> example = new LinkedHashMap<>();
        example.put(SwaggerConstants.RESULT_SUCCESS_FIELD, SwaggerConstants.DEFAULT_SUCCESS);
        example.put(SwaggerConstants.RESULT_CODE_FIELD, SwaggerConstants.DEFAULT_SUCCESS_CODE);
        example.put(SwaggerConstants.RESULT_MESSAGE_FIELD, SwaggerConstants.DEFAULT_SUCCESS_MESSAGE);
        return example;
    }
    
    /**
     * 生成完整Result示例（包含data字段）
     */
    public static Map<String, Object> generateCompleteResultExample(Object dataExample) {
        Map<String, Object> example = generateBaseResultExample();
        example.put(SwaggerConstants.RESULT_DATA_FIELD, dataExample != null ? dataExample : new LinkedHashMap<>());
        return example;
    }
    
    /**
     * 根据实体类型名生成示例数据
     */
    public static Object generateEntityExample(String entityType) {
        // 添加null检查，避免空指针异常
        if (entityType == null || !SwaggerConstants.SUPPORTED_ENTITY_TYPES.contains(entityType)) {
            return new LinkedHashMap<>();
        }
        
        switch (entityType) {
            case SwaggerConstants.USER_DTO_TYPE:
                return generateUserExample();
            default:
                return new LinkedHashMap<>();
        }
    }
    
    /**
     * 生成用户示例数据
     */
    private static Map<String, Object> generateUserExample() {
        Map<String, Object> user = new LinkedHashMap<>();
        user.put("id", 1);
        user.put("username", "张三");
        user.put("email", "zhangsan@example.com");
        user.put("age", 25);
        user.put("phone", "13800138001");
        return user;
    }
    
    /**
     * 生成数组示例数据
     */
    public static List<Object> generateArrayExample(String itemType) {
        List<Object> arrayExample = new ArrayList<>();
        // 添加null检查
        if (itemType != null && SwaggerConstants.SUPPORTED_ENTITY_TYPES.contains(itemType)) {
            arrayExample.add(generateEntityExample(itemType));
        }
        return arrayExample;
    }
}
