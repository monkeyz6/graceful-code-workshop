package com.xk857.framework.swagger.config;

import com.xk857.framework.processor.annotation.RawResponse;
import com.xk857.framework.swagger.builder.SchemaBuilder;
import com.xk857.framework.swagger.constants.SwaggerConstants;
import com.xk857.framework.swagger.generator.ExampleDataGenerator;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 全局Result包装的 Swagger 文档自定义器
 * 仅影响 OpenAPI 文档生成，不影响运行时行为
 */
public class GlobalResultOperationCustomizer implements OperationCustomizer, Ordered {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        // 标注了 @RawResponse 的接口跳过文档层包装
        if (isRawResponse(handlerMethod)) {
            return operation;
        }

        ApiResponses responses = operation.getResponses();
        if (responses == null || responses.isEmpty()) {
            return operation;
        }

        for (Map.Entry<String, ApiResponse> entry : responses.entrySet()) {
            String statusCode = entry.getKey();
            ApiResponse apiResponse = entry.getValue();

            // 仅处理数字状态码
            if (!isNumericStatusCode(statusCode)) {
                continue;
            }

            processApiResponse(apiResponse, handlerMethod);
        }
        return operation;
    }

    /**
     * 处理单个API响应
     */
    private void processApiResponse(ApiResponse apiResponse, HandlerMethod handlerMethod) {
        // 确保有content节点
        if (apiResponse.getContent() == null) {
            apiResponse.setContent(new Content());
        }

        // 获取原始Schema
        Schema<?> originalSchema = extractOriginalSchema(apiResponse);
        
        // 如果Schema缺失或是空对象，尝试推断
        if (originalSchema == null || isVoidLike(originalSchema)) {
            originalSchema = inferSchemaFromHandlerReturnType(handlerMethod);
        }

        // 构建Result包装的Schema
        ObjectSchema resultSchema = buildResultWrappedSchema(originalSchema);
        
        // 设置示例数据 - 添加安全检查
        Object dataExample = null;
        if (originalSchema != null) {
            dataExample = generateDataExample(originalSchema);
        }
        resultSchema.setExample(ExampleDataGenerator.generateCompleteResultExample(dataExample));

        // 设置为JSON响应
        setJsonResponse(apiResponse, resultSchema);
    }

    /**
     * 提取原始Schema
     */
    private Schema<?> extractOriginalSchema(ApiResponse apiResponse) {
        Map<String, MediaType> mediaMap = apiResponse.getContent();
        MediaType jsonMediaType = findJsonMediaType(mediaMap);
        return (jsonMediaType == null) ? null : jsonMediaType.getSchema();
    }

    /**
     * 查找JSON媒体类型
     */
    private MediaType findJsonMediaType(Map<String, MediaType> mediaMap) {
        // 优先查找包含json的媒体类型
        for (Map.Entry<String, MediaType> entry : mediaMap.entrySet()) {
            if (entry.getKey() != null && 
                entry.getKey().toLowerCase().contains(SwaggerConstants.JSON_MEDIA_TYPE_KEY)) {
                return entry.getValue();
            }
        }
        // 如果没找到，返回第一个
        return mediaMap.values().stream().findFirst().orElse(null);
    }

    /**
     * 构建Result包装的Schema
     */
    private ObjectSchema buildResultWrappedSchema(Schema<?> originalSchema) {
        if (originalSchema == null || isAlreadyResultLike(originalSchema)) {
            return SchemaBuilder.buildResultSchema(new ObjectSchema(), null);
        }
        
        String originalName = safeSchemaName(originalSchema);
        return SchemaBuilder.buildResultSchema(originalSchema, originalName);
    }

    /**
     * 生成数据示例
     */
    private Object generateDataExample(Schema<?> originalSchema) {
        if (originalSchema instanceof ArraySchema schema) {
            String itemName = safeSchemaName(schema.getItems());
            return ExampleDataGenerator.generateArrayExample(itemName);
        } else {
            String schemaName = safeSchemaName(originalSchema);
            return ExampleDataGenerator.generateEntityExample(schemaName);
        }
    }

    /**
     * 设置JSON响应
     */
    private void setJsonResponse(ApiResponse apiResponse, ObjectSchema resultSchema) {
        Content content = apiResponse.getContent();
        MediaType json = new MediaType();
        json.setSchema(resultSchema);
        content.clear();
        content.addMediaType(SwaggerConstants.APPLICATION_JSON, json);
    }

    private boolean isRawResponse(HandlerMethod handlerMethod) {
        if (handlerMethod == null) {
            return false;
        }
        if (handlerMethod.getMethod().isAnnotationPresent(RawResponse.class)) {
            return true;
        }
        Class<?> beanType = handlerMethod.getBeanType();
        return beanType.isAnnotationPresent(RawResponse.class);
    }

    private boolean isAlreadyResultLike(Schema<?> schema) {
        if (!(schema instanceof ObjectSchema)) {
            return false;
        }
        Map<String, Schema> props = schema.getProperties();
        if (props == null || props.isEmpty()) {
            return false;
        }
        return SwaggerConstants.RESULT_FIELDS.stream().allMatch(props::containsKey);
    }

    private String safeSchemaName(Schema<?> schema) {
        if (schema == null) {
            return null;
        }
        
        String name = schema.getName();
        if (name != null && !name.isBlank()) {
            return name;
        }
        
        // 从$ref中提取类型名
        String ref = schema.get$ref();
        if (ref != null && !ref.isBlank()) {
            int lastSlashIndex = ref.lastIndexOf('/');
            if (lastSlashIndex >= 0 && lastSlashIndex < ref.length() - 1) {
                return ref.substring(lastSlashIndex + 1);
            }
        }
        
        return null;
    }

    private boolean isVoidLike(Schema<?> schema) {
        // 检查是否是ResultVoid引用
        String ref = schema.get$ref();
        if (ref != null && ref.endsWith(SwaggerConstants.RESULT_VOID_SUFFIX)) {
            return true;
        }
        
        // 检查是否是空的ObjectSchema
        if (schema instanceof ObjectSchema sc) {
            Map<String, Schema> props = sc.getProperties();
            return props == null || props.isEmpty();
        }
        
        return false;
    }

    private Schema<?> inferSchemaFromHandlerReturnType(HandlerMethod handlerMethod) {
        if (handlerMethod == null) {
            return null;
        }
        
        String methodName = handlerMethod.getMethod().getName();
        
        // 根据配置的方法Schema映射生成
        Map<String, String> fieldConfig = SwaggerConstants.METHOD_SCHEMA_CONFIG.get(methodName);
        if (fieldConfig != null) {
            return SchemaBuilder.buildObjectSchemaFromConfig(fieldConfig);
        }
        
        // 处理泛型返回类型
        Type genericReturnType = handlerMethod.getMethod().getGenericReturnType();
        Class<?> returnType = handlerMethod.getMethod().getReturnType();

        // 处理List<T>类型
        if (List.class.isAssignableFrom(returnType) && genericReturnType instanceof ParameterizedType) {
            Type[] typeArgs = ((ParameterizedType) genericReturnType).getActualTypeArguments();
            if (typeArgs.length == 1 && typeArgs[0] instanceof Class) {
                String itemType = ((Class<?>) typeArgs[0]).getSimpleName();
                return SchemaBuilder.createArraySchema(itemType);
            }
        }

        // 处理普通类型：直接引用组件
        if (!Void.TYPE.equals(returnType)) {
            return SchemaBuilder.createEntityRefSchema(returnType.getSimpleName());
        }
        
        return null;
    }

    @Override
    public int getOrder() {
        // 放到最后执行，确保拿到最终 schema 再统一包裹
        return Ordered.LOWEST_PRECEDENCE;
    }

    private boolean isNumericStatusCode(String code) {
        if (code == null || code.isEmpty()) {
            return false;
        }
        for (int i = 0; i < code.length(); i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}


