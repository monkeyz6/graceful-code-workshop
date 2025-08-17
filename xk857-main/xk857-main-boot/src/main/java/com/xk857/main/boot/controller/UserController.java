package com.xk857.main.boot.controller;

import com.xk857.framework.processor.annotation.MyApiResponse;
import com.xk857.framework.processor.annotation.RawResponse;
import com.xk857.framework.constants.ApplicationConstants;
import com.xk857.framework.constants.CommonConstants;
import com.xk857.main.api.dto.UserDTO;
import com.xk857.main.api.dto.UserQueryDTO;
import com.xk857.main.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "用户管理", description = "用户信息的增删改查接口")
public class UserController {

    private final UserService userService;

    @Operation(summary = "根据ID查询用户")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功"),
            @ApiResponse(responseCode = "3002", description = "用户不存在"),
            @ApiResponse(responseCode = "3003", description = "用户ID无效")
    })
    @GetMapping("/{id}")
    public UserDTO getUserById(@Parameter(description = "用户ID", example = "1", required = true)
                               @PathVariable @Min(value = 1, message = "用户ID必须大于0") Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "查询所有用户")
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "条件查询用户")
    @PostMapping("/search")
    public Map<String, Object> searchUsers(@Parameter(description = "查询条件", required = true)
                                           @RequestBody @Valid UserQueryDTO queryDTO) {
        List<UserDTO> users = userService.getUsersByCondition(queryDTO);
        long total = userService.getUserCountByCondition(queryDTO);

        Map<String, Object> result = new HashMap<>();
        result.put("list", users);
        result.put("total", total);
        result.put("pageNum", queryDTO.getPageNum());
        result.put("pageSize", queryDTO.getPageSize());
        result.put("totalPages", (total + queryDTO.getPageSize() - 1) / queryDTO.getPageSize());

        return result;
    }

    @Operation(summary = "用户统计信息")
    @GetMapping("/stats")
    public Map<String, Object> getUserStats() {
        long totalCount = userService.getUserCount();
        Map<String, Object> stats = new HashMap<>();
        stats.put(ApplicationConstants.TOTAL_USERS_FIELD, totalCount);
        stats.put(ApplicationConstants.DESCRIPTION_FIELD, ApplicationConstants.USER_STATS_DESCRIPTION);
        stats.put(ApplicationConstants.UPDATE_TIME_FIELD, java.time.LocalDateTime.now().toString());
        return stats;
    }

    @Operation(summary = "按年龄范围查询")
    @GetMapping("/age-range")
    public List<UserDTO> getUsersByAgeRange(@Parameter(description = "最小年龄", example = "18")
                                            @RequestParam(required = false) Integer minAge,
                                            @Parameter(description = "最大年龄", example = "60")
                                            @RequestParam(required = false) Integer maxAge) {
        UserQueryDTO queryDTO = new UserQueryDTO();
        queryDTO.setMinAge(minAge);
        queryDTO.setMaxAge(maxAge);
        queryDTO.setPageNum(CommonConstants.DEFAULT_PAGE_NUM);
        queryDTO.setPageSize(100);
        return userService.getUsersByCondition(queryDTO);
    }
}