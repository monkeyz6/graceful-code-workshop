package com.xk857.framework.processor;

import com.xk857.framework.processor.annotation.APIVersion;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import java.lang.reflect.Method;

/**
 * 自定义请求处理器映射，用于实现基于注解的API版本控制。
 * 它会重写Spring MVC的默认URL注册行为。
 */
public class APIVersionHandlerMapping extends RequestMappingHandlerMapping {

    /**
     * 仅处理带有 @Controller 注解的Bean（@RestController包含了@Controller）
     */
    @Override
    protected boolean isHandler(@NonNull Class<?> beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, Controller.class);
    }

    @Override
    protected void registerHandlerMethod(Object handler, @NonNull Method method, @NonNull RequestMappingInfo mapping) {
        Class<?> controllerClass = method.getDeclaringClass();

        // 优先获取方法上的注解
        APIVersion apiVersion = AnnotationUtils.findAnnotation(method, APIVersion.class);
        if (apiVersion == null) {
            apiVersion = AnnotationUtils.findAnnotation(controllerClass, APIVersion.class);
        }

        if (apiVersion != null) {
            String[] versionPaths = apiVersion.value().path();

            RequestMappingInfo.BuilderConfiguration options = new RequestMappingInfo.BuilderConfiguration();
            if (getPatternParser() != null) {
                // 优先使用 Spring Boot 3 的默认策略
                options.setPatternParser(getPatternParser());
            } else {
                // 兼容旧的 AntPathMatcher 策略（SpringBoot2环境，可以直接使用configureLegacyOptions，而不必使用options.setPatternParser(getPatternParser());）
                configureLegacyOptions(options);
            }

            RequestMappingInfo prefixInfo = RequestMappingInfo.paths(versionPaths)
                    .options(options)
                    .build();

            mapping = prefixInfo.combine(mapping);
        }

        super.registerHandlerMethod(handler, method, mapping);
    }

    /**
     * 为旧的 {@code AntPathMatcher} 路径匹配策略配置构建器选项。
     * <p>
     * <b>为什么在 Spring Boot 3 中还需要此方法？</b>
     * <p>
     * 一个标准的 Spring Boot 3 应用默认使用性能更好、功能更明确的 {@code PathPatternParser}，因此<b>不会</b>执行到此方法。
     * <p>
     * 然而，为了保证对旧项目的最大兼容性，Spring 仍然允许开发者通过在 {@code application.properties} 文件中设置
     * {@code spring.mvc.pathmatch.matching-strategy=ant-path-matcher}
     * 来强制应用回退到传统的 {@code AntPathMatcher} 策略。
     * <p>
     * 在这种（非默认的）遗留模式下，本方法就变得至关重要。它能确保我们自定义的 {@code APIVersionHandlerMapping}
     * 能够正确读取并应用那些全局的、但已被官方弃用的配置（如后缀匹配、尾斜杠匹配），从而使我们动态生成的URL前缀
     * 与Spring MVC框架其余部分的行为保持完全一致，避免路由不匹配的风险。
     * <p>
     * 由于 {@code useSuffixPatternMatch()} 等方法是为访问已被弃用的属性而存在的，因此它们自身也被标记为弃用。
     * 在此兼容性场景下，调用它们是不可避免且正确的，故使用 {@code @SuppressWarnings("deprecation")} 来抑制相关编译器警告。
     *
     * @param options 需要被配置的 {@link RequestMappingInfo.BuilderConfiguration} 实例
     */
    @SuppressWarnings("deprecation")
    private void configureLegacyOptions(RequestMappingInfo.BuilderConfiguration options) {
        options.setPathMatcher(getPathMatcher());
        options.setSuffixPatternMatch(useSuffixPatternMatch());
        options.setTrailingSlashMatch(useTrailingSlashMatch());
    }
}
