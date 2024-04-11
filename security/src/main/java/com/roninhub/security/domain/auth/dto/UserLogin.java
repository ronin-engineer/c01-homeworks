package com.roninhub.security.domain.auth.dto;

import com.roninhub.security.domain.auth.constant.RoleName;
import com.roninhub.security.domain.auth.entity.Role;
import com.roninhub.security.domain.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserLogin {
    private Long id;

    private String username;

    private Set<RoleName> roles;

    public static UserLogin of(final User user) {
        var roles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet());

        return new UserLogin(user.getId(), user.getPassword(), roles);
    }
}