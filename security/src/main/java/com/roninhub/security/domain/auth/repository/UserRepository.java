package com.roninhub.security.domain.auth.repository;

import com.roninhub.security.domain.auth.entity.User;

public interface UserRepository {
    User findByUsername(String username);
}
