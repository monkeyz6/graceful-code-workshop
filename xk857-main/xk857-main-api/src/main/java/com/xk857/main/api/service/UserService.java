package com.xk857.main.api.service;

import com.xk857.main.api.dto.UserDTO;
import com.xk857.main.api.dto.UserQueryDTO;

import java.util.List;

/**
 * @author CV大魔王 on 2025/8/16 12:49.
 */
public interface UserService {

    /**
     * 根据ID查询用户
     */
    UserDTO getUserById(Long id);


    /**
     * 查询所有用户
     */
    List<UserDTO> getAllUsers();

    /**
     * 条件查询用户
     */
    List<UserDTO> getUsersByCondition(UserQueryDTO queryDTO);

    /**
     * 获取用户总数
     */
    long getUserCount();

    /**
     * 根据条件获取用户数量
     */
    long getUserCountByCondition(UserQueryDTO queryDTO);
}