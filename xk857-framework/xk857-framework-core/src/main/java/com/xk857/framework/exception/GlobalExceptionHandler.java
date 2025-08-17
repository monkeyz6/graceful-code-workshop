package com.xk857.framework.exception;

import com.xk857.framework.common.Result;
import com.xk857.framework.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * 专门处理我们自定义的业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK) // 业务异常我们倾向于返回200，由code和success标示错误
    public Result<Void> handleBusinessException(BusinessException ex) {
        log.warn("业务异常: code={}, message={}", ex.getCode(), ex.getMessage());
        return Result.fail(ex.getCode(), ex.getMessage());
    }
    
    /**
     * 处理方法参数校验异常（@RequestBody @Valid）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        log.warn("参数校验异常: {}", errorMessage);
        return Result.fail(CommonConstants.HTTP_BAD_REQUEST, CommonConstants.VALIDATION_FAILED_MESSAGE + ": " + errorMessage);
    }
    
    /**
     * 处理Bean Validation异常（@Validated）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));
        log.warn("参数校验异常: {}", errorMessage);
        return Result.fail(CommonConstants.HTTP_BAD_REQUEST, CommonConstants.VALIDATION_FAILED_MESSAGE + ": " + errorMessage);
    }
    
    /**
     * 处理绑定异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleBindException(BindException ex) {
        String errorMessage = ex.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        log.warn("参数绑定异常: {}", errorMessage);
        return Result.fail(CommonConstants.HTTP_BAD_REQUEST, CommonConstants.BINDING_FAILED_MESSAGE + ": " + errorMessage);
    }
    
    /**
     * 处理静态资源未找到异常（Spring Boot 3新特性）
     * 不记录错误日志，避免污染日志
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<Void> handleNoResourceFoundException(NoResourceFoundException ex) {
        // 不记录错误日志，这些通常是正常的静态资源请求
        return Result.fail(CommonConstants.HTTP_NOT_FOUND, CommonConstants.RESOURCE_NOT_FOUND_MESSAGE);
    }
    
    /**
     * 处理运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Void> handleRuntimeException(RuntimeException ex) {
        log.error("运行时异常: {}", ex.getMessage(), ex);
        return Result.fail(CommonConstants.HTTP_INTERNAL_SERVER_ERROR, CommonConstants.SERVER_ERROR_MESSAGE);
    }
    
    /**
     * 兜底处理器，处理所有未被捕获的异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 系统级错误，返回500
    public Result<Void> handleGlobalException(Exception ex) {
        log.error("系统异常: {}", ex.getMessage(), ex);
        // 对外暴露统一的、模糊的系统错误提示
        return Result.fail(CommonConstants.HTTP_INTERNAL_SERVER_ERROR, CommonConstants.SERVER_ERROR_MESSAGE);
    }
}
