package com.xk857.framework.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xk857.framework.processor.annotation.MyApiResponse;
import com.xk857.framework.processor.annotation.RawResponse;
import com.xk857.framework.common.Result;
import com.xk857.framework.constants.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 全局响应拦截器
 * 自动将返回值包装为统一的 Result 格式
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;
    
    // OpenAPI与Swagger相关路径前缀
    private static final Set<String> API_DOC_PREFIXES = Set.of(
            "/v3/api-docs",
            "/swagger-ui",
            "/knife4j",
            "/webjars/",
            "/swagger-resources"
    );
    
    // 精确匹配的特殊路径
    private static final Set<String> EXACT_BYPASS_PATHS = Set.of(
            "/swagger-ui.html",
            "/doc.html",
            "/favicon.ico"
    );
    
    // 静态资源路径前缀
    private static final Set<String> STATIC_RESOURCE_PREFIXES = Set.of(
            "/assets/",
            "/static/",
            "/public/"
    );
    
    // 需要放行的媒体类型
    private static final Set<MediaType> BYPASS_MEDIA_TYPES = Set.of(
            MediaType.TEXT_HTML,
            MediaType.valueOf("text/css"),
            MediaType.valueOf("application/javascript")
    );

    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        // 检查方法或其所在的类上，是否有 @RawResponse 注解，如果有注解则直接放行
        return !returnType.hasMethodAnnotation(RawResponse.class)
                && !returnType.getContainingClass().isAnnotationPresent(RawResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {
        // 放行Swagger/Knife4j及静态资源请求，避免被统一包装
        String path = request.getURI().getPath();
        if (shouldBypass(path, selectedContentType)) {
            return body;
        }

        // 1.如果已经是Result，则不做额外处理
        if (body instanceof Result) {
            return body;
        }

        Method method = returnType.getMethod();
        if (method != null) {
            // 2.检查方法上是否有 @ApiResponse 注解
            MyApiResponse myApiResponse = method.getAnnotation(MyApiResponse.class);

            // 3.如果有注解，则需要特殊处理
            if (myApiResponse != null) {
                response.setStatusCode(HttpStatus.valueOf(myApiResponse.httpCode()));
                Result<Object> result = Result.success(myApiResponse.code(), myApiResponse.msg(), body);
                return handleStringResponse(result, selectedConverterType);
            }
        }

        // 4.默认包装为成功响应
        Result<Object> result = Result.success(body);
        return handleStringResponse(result, selectedConverterType);
    }

    /**
     * 判断是否需要放行（不进行统一包装）
     */
    private boolean shouldBypass(String path, MediaType mediaType) {
        if (path == null) {
            return false;
        }
        
        // 检查精确匹配的路径
        if (EXACT_BYPASS_PATHS.contains(path)) {
            return true;
        }
        
        // 检查API文档相关路径前缀
        if (API_DOC_PREFIXES.stream().anyMatch(path::startsWith)) {
            return true;
        }
        
        // 检查静态资源路径前缀
        if (STATIC_RESOURCE_PREFIXES.stream().anyMatch(path::startsWith)) {
            return true;
        }
        
        // 检查媒体类型
        if (mediaType != null) {
            return BYPASS_MEDIA_TYPES.stream().anyMatch(type -> type.includes(mediaType));
        }
        
        return false;
    }

    /**
     * 处理String类型响应的特殊情况
     * 当Spring选择StringHttpMessageConverter时，需要返回JSON字符串而不是Result对象
     */
    private Object handleStringResponse(Result<Object> result, Class<? extends HttpMessageConverter<?>> converterType) {
        if (StringHttpMessageConverter.class == converterType) {
            try {
                return objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                log.error("序列化Result对象失败", e);
                return "{\"success\":false,\"code\":" + CommonConstants.HTTP_INTERNAL_SERVER_ERROR + ",\"message\":\"" + CommonConstants.SERIALIZATION_FAILED_MESSAGE + "\",\"data\":null}";
            }
        }
        return result;
    }
}
