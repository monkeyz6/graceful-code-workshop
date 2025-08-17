package com.xk857.main.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户查询请求对象
 */
@Data
@Schema(description = "用户查询条件")
public class UserQueryDTO {
    
    /**
     * 用户名（模糊查询）
     */
    @Schema(description = "用户名（支持模糊查询）", example = "张")
    private String username;
    
    /**
     * 邮箱（模糊查询）
     */
    @Schema(description = "邮箱（支持模糊查询）", example = "example.com")
    private String email;
    
    /**
     * 最小年龄
     */
    @Schema(description = "最小年龄", example = "18", minimum = "0")
    private Integer minAge;
    
    /**
     * 最大年龄
     */
    @Schema(description = "最大年龄", example = "60", maximum = "120")
    private Integer maxAge;
    
    /**
     * 页码（从1开始）
     */
    @Schema(description = "页码（从1开始）", example = "1", minimum = "1", defaultValue = "1")
    private Integer pageNum = 1;
    
    /**
     * 每页数量
     */
    @Schema(description = "每页数量", example = "10", minimum = "1", maximum = "100", defaultValue = "10")
    private Integer pageSize = 10;
}
