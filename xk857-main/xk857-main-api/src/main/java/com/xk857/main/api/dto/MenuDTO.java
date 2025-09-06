package com.xk857.main.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单DTO：用于对外返回，包含 children 形成树
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "MenuDTO", description = "菜单树节点（对外返回）")
public class MenuDTO {

    @Schema(description = "主键ID", example = "1001")
    private Long id;

    @Schema(description = "父级ID", example = "1000")
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

    @Schema(description = "子节点")
    private List<MenuDTO> children = new ArrayList<>();
}

