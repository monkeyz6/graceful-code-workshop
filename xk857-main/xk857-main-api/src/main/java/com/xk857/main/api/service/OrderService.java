package com.xk857.main.api.service;

import com.xk857.main.api.dto.OrderRequest;
import com.xk857.main.api.dto.OrderResultDTO;

/**
 * 订单服务接口
 */
public interface OrderService {
    OrderResultDTO placeOrder(OrderRequest request);
}

