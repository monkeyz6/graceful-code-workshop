package com.xk857.main.boot.controller;

import com.xk857.framework.processor.annotation.APIVersion;
import com.xk857.framework.processor.enmu.APIVersionEnum;
import com.xk857.main.api.dto.MenuDTO;
import com.xk857.main.api.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单控制器：将 parentId 关系转换为前端需要的树形JSON
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@APIVersion(APIVersionEnum.V1)
@RequestMapping("/api/menus")
@Tag(name = "菜单管理", description = "基于组合模式的层级菜单构建")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "获取用户完整菜单树")
    @GetMapping("/tree")
    public List<MenuDTO> getMenuTree(@Parameter(description = "用户ID", example = "1")
                                     @RequestParam(defaultValue = "1") @Min(1) Long userId) {
        return menuService.buildMenuTreeForUser(userId);
    }

    @Operation(summary = "获取指定根节点的子树")
    @GetMapping("/subtree/{rootId}")
    public List<MenuDTO> getSubTree(@Parameter(description = "根节点ID", example = "1000")
                                    @PathVariable @Min(1) Long rootId,
                                    @Parameter(description = "用户ID", example = "1")
                                    @RequestParam(defaultValue = "1") @Min(1) Long userId) {
        return menuService.buildSubTree(rootId, userId);
    }

    @Operation(summary = "获取授权后的扁平菜单列表")
    @GetMapping("/flat")
    public List<MenuDTO> getFlatMenus(@Parameter(description = "用户ID", example = "1")
                                      @RequestParam(defaultValue = "1") @Min(1) Long userId) {
        return menuService.listFlatMenus(userId);
    }
}

