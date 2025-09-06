package com.xk857.main.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单实体（邻接表模型：通过 parentId 表达层级）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Menu", description = "菜单实体，使用 parentId 组织层级关系")
public class Menu {

    @Schema(description = "主键ID", example = "1001")
    private Long id;

    @Schema(description = "父级ID，根节点为null", example = "1000")
    private Long parentId;

    @Schema(description = "菜单名称", example = "系统管理")
    private String name;

    @Schema(description = "前端路由路径", example = "/system")
    private String path;

    @Schema(description = "类型：MENU/BUTTON/LINK", example = "MENU")
    private String type;

    @Schema(description = "同级排序号", example = "10")
    private Integer sortOrder;

    @Schema(description = "图标（可选）", example = "Setting")
    private String icon;

    @Schema(description = "是否隐藏", example = "false")
    private Boolean hidden;
}

