package com.xk857.main.core.dao;

import com.xk857.main.api.entity.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单DAO（使用内存数据模拟）
 */
@Slf4j
@Repository
public class MenuDAO {

    // 模拟的菜单数据（邻接表）
    private static final List<Menu> ALL_MENUS = List.of(
            // 根：系统管理
            new Menu(1000L, null, "系统管理", "/system", "MENU", 10, "Setting", false),
            new Menu(1100L, 1000L, "用户管理", "/system/user", "MENU", 10, "User", false),
            new Menu(1110L, 1100L, "用户列表", "/system/user/list", "MENU", 10, "List", false),
            new Menu(1111L, 1110L, "新增用户", "/system/user/create", "BUTTON", 10, null, true),
            new Menu(1112L, 1110L, "删除用户", "/system/user/delete", "BUTTON", 20, null, true),
            new Menu(1200L, 1000L, "角色管理", "/system/role", "MENU", 20, "Team", false),
            new Menu(1210L, 1200L, "角色列表", "/system/role/list", "MENU", 10, "List", false),

            // 根：研发中心
            new Menu(2000L, null, "研发中心", "/rd", "MENU", 20, "Cpu", false),
            new Menu(2100L, 2000L, "项目看板", "/rd/board", "MENU", 10, "Kanban", false),
            new Menu(2200L, 2000L, "制品库", "/rd/artifact", "MENU", 20, "Box", false),

            // 根：运维中心
            new Menu(3000L, null, "运维中心", "/ops", "MENU", 30, "Monitor", false),
            new Menu(3100L, 3000L, "监控大盘", "/ops/monitor", "MENU", 10, "Histogram", false),
            new Menu(3200L, 3000L, "日志检索", "/ops/log", "MENU", 20, "Search", false)
    );

    // 用户授权关系（userId -> 可见菜单ID集合）
    private static final Map<Long, Set<Long>> USER_MENU_AUTH = Map.of(
            1L, Set.of(1000L, 1100L, 1110L, 1111L, 1112L, 1200L, 1210L, 2000L, 2100L, 2200L, 3000L, 3100L, 3200L), // 超管
            2L, Set.of(1000L, 1100L, 1110L, 2000L, 2100L), // 研发
            3L, Set.of(3000L, 3100L, 3200L) // 运维
    );

    /**
     * 拉取授权菜单（扁平），自动补齐缺失的祖先节点
     */
    public List<Menu> findAllAuthorizedFlat(Long userId) {
        Set<Long> authIds = USER_MENU_AUTH.getOrDefault(userId, Collections.emptySet());
        if (authIds.isEmpty()) {
            return List.of();
        }

        Map<Long, Menu> allMap = ALL_MENUS.stream().collect(Collectors.toMap(Menu::getId, m -> m));
        Set<Long> resultIds = new HashSet<>(authIds);

        // 祖先补齐：确保前端能连通渲染
        for (Long id : new ArrayList<>(authIds)) {
            Long cur = id;
            // 往上回溯直到根
            while (cur != null) {
                Menu curMenu = allMap.get(cur);
                if (curMenu == null) {
                    break;
                }
                if (curMenu.getParentId() == null) {
                    resultIds.add(curMenu.getId());
                    break;
                }
                resultIds.add(curMenu.getParentId());
                cur = curMenu.getParentId();
            }
        }

        return ALL_MENUS.stream()
                .filter(m -> resultIds.contains(m.getId()))
                .collect(Collectors.toList());
    }

    /**
     * 根据 rootId 拉取授权后子树（扁平返回）
     */
    public List<Menu> findAuthorizedSubTreeFlat(Long rootId, Long userId) {
        List<Menu> flat = findAllAuthorizedFlat(userId);
        Map<Long, List<Menu>> p2c = flat.stream().collect(Collectors.groupingBy(m -> m.getParentId() == null ? 0L : m.getParentId()));
        Map<Long, Menu> idMap = flat.stream().collect(Collectors.toMap(Menu::getId, m -> m));

        if (!idMap.containsKey(rootId) && !p2c.containsKey(rootId)) {
            return List.of();
        }

        // 非递归BFS，避免深递归风险
        List<Menu> result = new ArrayList<>();
        Deque<Long> queue = new ArrayDeque<>();
        queue.add(rootId);
        Set<Long> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Long cur = queue.poll();
            if (!visited.add(cur)) {
                log.warn("检测到潜在环或重复访问，节点ID={}", cur);
                continue;
            }
            if (idMap.containsKey(cur)) {
                result.add(idMap.get(cur));
            }
            List<Menu> children = p2c.getOrDefault(cur, List.of());
            for (Menu child : children) {
                queue.add(child.getId());
            }
        }
        return result;
    }
}

