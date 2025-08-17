package com.xk857.main.boot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 欢迎页面响应实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WelcomeResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -8135542902830656162L;

    private String welcome;
    private String description;
    private String version;
    private List<EndpointInfo> availableEndpoints;
}