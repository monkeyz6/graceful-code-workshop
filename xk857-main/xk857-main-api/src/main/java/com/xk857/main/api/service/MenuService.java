package com.xk857.main.api.service;

import com.xk857.main.api.dto.MenuDTO;

import java.util.List;

/**
 * 菜单服务接口
 */
public interface MenuService {

    /**
     * 根据用户构建完整菜单树（已按 sortOrder 排好序）
     */
    List<MenuDTO> buildMenuTreeForUser(Long userId);

    /**
     * 拉取指定根节点下的子树（已按 sortOrder 排好序）
     */
    List<MenuDTO> buildSubTree(Long rootId, Long userId);

    /**
     * 拉取授权后的扁平菜单列表
     */
    List<MenuDTO> listFlatMenus(Long userId);
}

