package com.xk857.main.boot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 端点信息实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndpointInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 4808766386503453359L;

    private String method;
    private String url;
    private String description;
}