package com.xk857.main.core.service;

import com.xk857.framework.constants.CommonConstants;
import com.xk857.framework.exception.BusinessException;
import com.xk857.main.api.dto.UserDTO;
import com.xk857.main.api.dto.UserQueryDTO;
import com.xk857.main.api.entity.User;
import com.xk857.main.api.enums.UserErrorEnum;
import com.xk857.main.api.service.UserService;
import com.xk857.main.core.dao.UserDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public UserDTO getUserById(Long id) {
        if (id == null || id <= 0) {
            throw BusinessException.of(UserErrorEnum.INVALID_USER_ID).errThrow();
        }
        User user = userDAO.findById(id);
        if (user == null) {
            throw BusinessException.of(UserErrorEnum.USER_NOT_FOUND, id).errThrow();
        }
        return convertToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userDAO.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getUsersByCondition(UserQueryDTO queryDTO) {
        if (queryDTO == null) {
            queryDTO = new UserQueryDTO();
        }

        // 验证分页参数
        if (queryDTO.getPageNum() == null || queryDTO.getPageNum() < CommonConstants.MIN_PAGE_NUM) {
            queryDTO.setPageNum(CommonConstants.DEFAULT_PAGE_NUM);
        }
        if (queryDTO.getPageSize() == null || queryDTO.getPageSize() < 1) {
            queryDTO.setPageSize(10);
        }

        List<User> users = userDAO.findByCondition(queryDTO);

        // 简单分页处理
        int start = (queryDTO.getPageNum() - 1) * queryDTO.getPageSize();
        int end = Math.min(start + queryDTO.getPageSize(), users.size());

        if (start >= users.size()) {
            return List.of();
        }

        return users.subList(start, end).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public long getUserCount() {
        return userDAO.countAll();
    }

    @Override
    public long getUserCountByCondition(UserQueryDTO queryDTO) {
        if (queryDTO == null) {
            queryDTO = new UserQueryDTO();
        }
        return userDAO.countByCondition(queryDTO);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());
        dto.setPhone(user.getPhone());
        return dto;
    }
}
