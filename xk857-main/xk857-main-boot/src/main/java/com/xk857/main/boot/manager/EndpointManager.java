package com.xk857.main.boot.manager;

import com.xk857.framework.constants.ApplicationConstants;
import com.xk857.main.boot.dto.EndpointInfo;
import com.xk857.main.boot.dto.WelcomeResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 端点管理器，在Spring启动时扫描所有端点并缓存结果
 */
@Slf4j
@Component
public class EndpointManager implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ApplicationContext applicationContext;

    private WelcomeResponse cachedWelcomeResponse;

    /**
     * 需要过滤的端点URL关键词
     */
    private static final Set<String> FILTERED_URL_KEYWORDS = Set.of(
            "${springdoc.api-docs.path",
            "/swagger",
            "/api-docs",
            "/actuator",
            "/error"
    );

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        log.info("开始扫描并缓存端点信息...");
        List<EndpointInfo> endpoints = scanAllEndpoints();

        this.cachedWelcomeResponse = new WelcomeResponse(
                ApplicationConstants.WELCOME_MESSAGE,
                ApplicationConstants.APP_DESCRIPTION,
                ApplicationConstants.APP_VERSION,
                endpoints
        );

        log.info("端点信息扫描完成，共发现 {} 个端点", endpoints.size());
    }

    /**
     * 获取缓存的欢迎页面响应
     */
    public WelcomeResponse getWelcomeResponse() {
        return cachedWelcomeResponse;
    }

    /**
     * 扫描所有控制器端点
     */
    private List<EndpointInfo> scanAllEndpoints() {
        List<EndpointInfo> endpoints = new ArrayList<>();

        // 获取所有带有@RestController注解的Bean
        String[] controllerNames = applicationContext.getBeanNamesForAnnotation(RestController.class);

        for (String controllerName : controllerNames) {
            Object controller = applicationContext.getBean(controllerName);
            Class<?> controllerClass = controller.getClass();

            // 获取类级别的RequestMapping
            String baseUrl = getBaseUrl(controllerClass);

            // 遍历所有方法
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(controllerClass);
            for (Method method : methods) {
                processMethod(method, baseUrl, endpoints);
            }
        }
        return endpoints;
    }

    /**
     * 获取控制器基础URL
     */
    private String getBaseUrl(Class<?> controllerClass) {
        Class<?> targetClass = controllerClass;
        if (controllerClass.getName().contains("$$")) {
            targetClass = controllerClass.getSuperclass();
        }

        if (targetClass.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping classMapping = targetClass.getAnnotation(RequestMapping.class);

            // 优先使用value属性
            if (classMapping.value().length > 0 && !classMapping.value()[0].isEmpty()) {
                return classMapping.value()[0];
            }

            // 如果value为空，尝试使用path属性
            if (classMapping.path().length > 0 && !classMapping.path()[0].isEmpty()) {
                return classMapping.path()[0];
            }
        }
        return "";
    }

    /**
     * 处理单个方法
     */
    private void processMethod(Method method, String baseUrl, List<EndpointInfo> endpoints) {
        String description = getMethodDescription(method);

        if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping getMapping = method.getAnnotation(GetMapping.class);
            String[] paths = getMapping.value();
            String methodUrl = paths.length > 0 ? paths[0] : "";
            String fullUrl = baseUrl + methodUrl;
            if (FILTERED_URL_KEYWORDS.stream().noneMatch(fullUrl::contains)) {
                endpoints.add(new EndpointInfo("GET", fullUrl, description));
            }
        }

        if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping postMapping = method.getAnnotation(PostMapping.class);
            String[] paths = postMapping.value();
            String methodUrl = paths.length > 0 ? paths[0] : "";
            String fullUrl = baseUrl + methodUrl;
            if (FILTERED_URL_KEYWORDS.stream().noneMatch(fullUrl::contains)) {
                endpoints.add(new EndpointInfo("POST", fullUrl, description));
            }
        }
    }

    /**
     * 获取方法描述
     */
    private String getMethodDescription(Method method) {
        if (method.isAnnotationPresent(Operation.class)) {
            Operation operation = method.getAnnotation(Operation.class);
            return operation.summary();
        }
        return "";
    }
}