package com.xk857.main.core.dao;

import com.xk857.main.api.entity.User;
import com.xk857.main.api.dto.UserQueryDTO;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户数据访问层
 */
@Repository
public class UserDAO {

    // 模拟数据库数据
    private static final List<User> MOCK_USERS = Arrays.asList(
            new User(1L, "张三", "zhangsan@example.com", 25, "13800138001", "2025-01-01 10:00:00", "2025-01-01 10:00:00"),
            new User(2L, "李四", "lisi@example.com", 30, "13800138002", "2025-01-02 10:00:00", "2025-01-02 10:00:00"),
            new User(3L, "王五", "wangwu@example.com", 28, "13800138003", "2025-01-03 10:00:00", "2025-01-03 10:00:00"),
            new User(4L, "赵六", "zhaoliu@example.com", 35, "13800138004", "2025-01-04 10:00:00", "2025-01-04 10:00:00"),
            new User(5L, "钱七", "qianqi@example.com", 22, "13800138005", "2025-01-05 10:00:00", "2025-01-05 10:00:00"),
            new User(6L, "孙八", "sunba@example.com", 27, "13800138006", "2025-01-06 10:00:00", "2025-01-06 10:00:00"),
            new User(7L, "周九", "zhoujiu@example.com", 31, "13800138007", "2025-01-07 10:00:00", "2025-01-07 10:00:00"),
            new User(8L, "吴十", "wushi@example.com", 26, "13800138008", "2025-01-08 10:00:00", "2025-01-08 10:00:00"),
            new User(9L, "郑十一", "zhengshiyi@example.com", 29, "13800138009", "2025-01-09 10:00:00", "2025-01-09 10:00:00"),
            new User(10L, "王十二", "wangshier@example.com", 33, "13800138010", "2025-01-10 10:00:00", "2025-01-10 10:00:00"),
            new User(11L, "陈十三", "chenshisan@example.com", 24, "13800138011", "2025-01-11 10:00:00", "2025-01-11 10:00:00"),
            new User(12L, "林十四", "linshisi@example.com", 32, "13800138012", "2025-01-12 10:00:00", "2025-01-12 10:00:00"),
            new User(13L, "黄十五", "huangshiwu@example.com", 28, "13800138013", "2025-01-13 10:00:00", "2025-01-13 10:00:00"),
            new User(14L, "刘十六", "liushiliu@example.com", 30, "13800138014", "2025-01-14 10:00:00", "2025-01-14 10:00:00"),
            new User(15L, "张十七", "zhangshiqi@example.com", 26, "13800138015", "2025-01-15 10:00:00", "2025-01-15 10:00:00"),
            new User(16L, "李十八", "lishiba@example.com", 34, "13800138016", "2025-01-16 10:00:00", "2025-01-16 10:00:00"),
            new User(17L, "王十九", "wangshijiu@example.com", 23, "13800138017", "2025-01-17 10:00:00", "2025-01-17 10:00:00"),
            new User(18L, "赵二十", "zhaoershi@example.com", 31, "13800138018", "2025-01-18 10:00:00", "2025-01-18 10:00:00"),
            new User(19L, "钱二一", "qianery@example.com", 27, "13800138019", "2025-01-19 10:00:00", "2025-01-19 10:00:00"),
            new User(20L, "孙二二", "sunerer@example.com", 29, "13800138020", "2025-01-20 10:00:00", "2025-01-20 10:00:00")
    );

    /**
     * 根据ID查询用户
     */
    public User findById(Long id) {
        return MOCK_USERS.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * 查询所有用户
     */
    public List<User> findAll() {
        return new ArrayList<>(MOCK_USERS);
    }

    /**
     * 条件查询用户
     */
    public List<User> findByCondition(UserQueryDTO queryDTO) {
        return MOCK_USERS.stream().filter(user -> {
            if (queryDTO.getUsername() != null && !queryDTO.getUsername().isEmpty()) {
                if (!user.getUsername().contains(queryDTO.getUsername())) {
                    return false;
                }
            }
            if (queryDTO.getEmail() != null && !queryDTO.getEmail().isEmpty()) {
                if (!user.getEmail().contains(queryDTO.getEmail())) {
                    return false;
                }
            }
            if (queryDTO.getMinAge() != null) {
                if (user.getAge() < queryDTO.getMinAge()) {
                    return false;
                }
            }
            if (queryDTO.getMaxAge() != null) {
                return user.getAge() <= queryDTO.getMaxAge();
            }
            return true;
        }).collect(Collectors.toList());
    }

    /**
     * 统计用户总数
     */
    public long countAll() {
        return MOCK_USERS.size();
    }

    /**
     * 根据条件统计用户数量
     */
    public long countByCondition(UserQueryDTO queryDTO) {
        return findByCondition(queryDTO).size();
    }
}
