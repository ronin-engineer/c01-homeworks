package com.roninhub.security.domain.auth.entity;

import com.roninhub.security.domain.auth.constant.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Role {
    private Long id;

    private RoleName roleName;

    private Set<Permission> permissions;
}