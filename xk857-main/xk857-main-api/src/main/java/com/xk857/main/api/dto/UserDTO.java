package com.xk857.main.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Email;

/**
 * 用户数据传输对象
 */
@Data
@Schema(description = "用户信息")
public class UserDTO {
    
    /**
     * 用户ID
     */
    @Schema(description = "用户ID", example = "1")
    @NotNull(message = "用户ID不能为空")
    @Min(value = 1, message = "用户ID必须大于0")
    private Long id;
    
    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "张三")
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    /**
     * 邮箱
     */
    @Schema(description = "用户邮箱", example = "zhangsan@example.com")
    @Email(message = "邮箱格式不正确")
    private String email;
    
    /**
     * 年龄
     */
    @Schema(description = "用户年龄", example = "25", minimum = "0", maximum = "120")
    @Min(value = 0, message = "年龄不能小于0")
    private Integer age;
    
    /**
     * 手机号
     */
    @Schema(description = "手机号码", example = "13800138001")
    private String phone;
}
