package com.xk857.framework.swagger.builder;

import com.xk857.framework.swagger.constants.SwaggerConstants;
import io.swagger.v3.oas.models.media.*;

import java.util.Map;

/**
 * Schema构建器
 * 用于构建各种类型的Schema对象
 * 
 * @author cv大魔王
 */
public final class SchemaBuilder {
    
    /**
     * 私有构造函数，防止实例化
     */
    private SchemaBuilder() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    /**
     * 构建Result包装的Schema
     */
    public static ObjectSchema buildResultSchema(Schema<?> dataSchema, String originalName) {
        ObjectSchema resultSchema = new ObjectSchema();
        resultSchema.addProperty(SwaggerConstants.RESULT_SUCCESS_FIELD, new BooleanSchema());
        resultSchema.addProperty(SwaggerConstants.RESULT_CODE_FIELD, new IntegerSchema());
        resultSchema.addProperty(SwaggerConstants.RESULT_MESSAGE_FIELD, new StringSchema());
        resultSchema.addProperty(SwaggerConstants.RESULT_DATA_FIELD, dataSchema != null ? dataSchema : new ObjectSchema());
        
        if (originalName != null && !originalName.isBlank()) {
            resultSchema.setName(String.format(SwaggerConstants.RESULT_NAME_TEMPLATE, originalName));
        }
        
        return resultSchema;
    }
    
    /**
     * 根据字段配置构建ObjectSchema
     */
    public static ObjectSchema buildObjectSchemaFromConfig(Map<String, String> fieldConfig) {
        ObjectSchema schema = new ObjectSchema();
        
        for (Map.Entry<String, String> entry : fieldConfig.entrySet()) {
            String fieldName = entry.getKey();
            String fieldType = entry.getValue();
            
            Schema<?> fieldSchema = createSchemaByType(fieldType);
            schema.addProperty(fieldName, fieldSchema);
        }
        
        return schema;
    }
    
    /**
     * 根据类型字符串创建Schema
     */
    private static Schema<?> createSchemaByType(String type) {
        switch (type.toLowerCase()) {
            case "string":
                return new StringSchema();
            case "integer":
                return new IntegerSchema();
            case "boolean":
                return new BooleanSchema();
            case "array":
                ArraySchema arraySchema = new ArraySchema();
                arraySchema.setItems(createEntityRefSchema(SwaggerConstants.USER_DTO_TYPE));
                return arraySchema;
            default:
                return new ObjectSchema();
        }
    }
    
    /**
     * 创建实体引用Schema
     */
    public static Schema<?> createEntityRefSchema(String entityName) {
        return new Schema<>().$ref(SwaggerConstants.COMPONENTS_SCHEMAS_PREFIX + entityName);
    }
    
    /**
     * 创建数组Schema
     */
    public static ArraySchema createArraySchema(String itemType) {
        ArraySchema arraySchema = new ArraySchema();
        arraySchema.setItems(createEntityRefSchema(itemType));
        return arraySchema;
    }
}
