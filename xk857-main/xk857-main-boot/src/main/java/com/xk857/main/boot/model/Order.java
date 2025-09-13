package com.xk857.main.boot.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {
    private String id;
    private String status; // CREATED, PAID, CANCELED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

