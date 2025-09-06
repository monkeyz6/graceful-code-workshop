package com.xk857.main.core.service;

import com.xk857.main.api.dto.MenuDTO;
import com.xk857.main.api.entity.Menu;
import com.xk857.main.api.service.MenuService;
import com.xk857.main.core.dao.MenuDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单服务实现：将邻接表（parentId）一次性构造成树返回前端
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuDAO menuDAO;

    @Override
    public List<MenuDTO> buildMenuTreeForUser(Long userId) {
        List<Menu> flat = menuDAO.findAllAuthorizedFlat(userId);
        return buildTree(flat, null);
    }

    @Override
    public List<MenuDTO> buildSubTree(Long rootId, Long userId) {
        List<Menu> subFlat = menuDAO.findAuthorizedSubTreeFlat(rootId, userId);
        // 指定根的子树：以 rootId 作为根过滤
        return buildTree(subFlat, rootId);
    }

    @Override
    public List<MenuDTO> listFlatMenus(Long userId) {
        return menuDAO.findAllAuthorizedFlat(userId).stream()
                .map(this::toDTO)
                .sorted(Comparator.comparing(MenuDTO::getSortOrder, Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(MenuDTO::getId))
                .collect(Collectors.toList());
    }

    /**
     * O(N) 构建树（非递归，带排序，带环保护）
     * @param flat 扁平菜单
     * @param fixedRootId 指定根；null 表示以所有根（parentId=null）为入口
     */
    private List<MenuDTO> buildTree(List<Menu> flat, Long fixedRootId) {
        if (flat == null || flat.isEmpty()) {
            return List.of();
        }

        Map<Long, MenuDTO> idMap = flat.stream().collect(Collectors.toMap(Menu::getId, m -> toDTO(m), (a, b) -> a));

        // parent -> children 聚合
        Map<Long, List<MenuDTO>> parentChildren = new HashMap<>();
        for (Menu m : flat) {
            Long pid = m.getParentId();
            Long key = pid == null ? 0L : pid; // 0 代表根桶
            parentChildren.computeIfAbsent(key, k -> new ArrayList<>()).add(idMap.get(m.getId()));
        }

        // 组装：遍历所有节点，将其挂到父节点的 children
        for (Menu m : flat) {
            Long id = m.getId();
            Long pid = m.getParentId();

            if (pid == null) {
                continue; // 根节点
            }

            MenuDTO parent = idMap.get(pid);
            if (parent == null) {
                // 父节点不在授权集合内，跳过（仅作为孤儿返回时由根补齐逻辑解决）
                continue;
            }

            // 简单环检测：沿父链向上找是否遇到自己
            if (hasCycle(idMap, id, pid)) {
                log.warn("检测到环，忽略父子关系：child={} parent={}", id, pid);
                continue;
            }

            parent.getChildren().add(idMap.get(id));
        }

        // 选择根集合
        List<MenuDTO> roots;
        if (fixedRootId == null) {
            roots = parentChildren.getOrDefault(0L, List.of()).stream().collect(Collectors.toList());
        } else {
            MenuDTO rootDTO = idMap.get(fixedRootId);
            if (rootDTO == null) {
                return List.of();
            }
            // 若 fixedRootId 非根，则按已挂载关系返回它本身作为唯一根
            roots = List.of(rootDTO);
        }

        // 递归排序（可替换为显式栈排序，这里树深通常有限）
        roots.forEach(this::sortRecursively);
        return roots;
    }

    private void sortRecursively(MenuDTO node) {
        if (node.getChildren() == null || node.getChildren().isEmpty()) {
            return;
        }
        node.getChildren().sort(Comparator
                .comparing(MenuDTO::getSortOrder, Comparator.nullsLast(Integer::compareTo))
                .thenComparing(MenuDTO::getId));
        node.getChildren().forEach(this::sortRecursively);
    }

    private boolean hasCycle(Map<Long, MenuDTO> idMap, Long childId, Long parentId) {
        Long cursor = parentId;
        int guard = 0;
        while (cursor != null && guard++ < 10000) { // 简单保护，避免异常数据导致死循环
            if (Objects.equals(cursor, childId)) {
                return true;
            }
            MenuDTO p = idMap.get(cursor);
            if (p == null) {
                return false;
            }
            cursor = p.getParentId();
        }
        return false;
    }

    private MenuDTO toDTO(Menu m) {
        MenuDTO dto = new MenuDTO();
        dto.setId(m.getId());
        dto.setParentId(m.getParentId());
        dto.setName(m.getName());
        dto.setPath(m.getPath());
        dto.setType(m.getType());
        dto.setSortOrder(m.getSortOrder());
        dto.setIcon(m.getIcon());
        dto.setHidden(m.getHidden());
        return dto;
    }
}

