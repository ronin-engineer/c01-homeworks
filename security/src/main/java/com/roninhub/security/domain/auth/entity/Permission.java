package com.roninhub.security.domain.auth.entity;

import com.roninhub.security.domain.auth.constant.PermissionName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Permission {
    private Long id;

    private PermissionName name;
}