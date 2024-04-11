package com.roninhub.security.application.config;

import com.roninhub.security.domain.auth.constant.PermissionName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class WebSecurityConfig {
    @Bean
    public Map<String, List<PermissionName>> authorizeHttpRequests() {

        return Map.of("/posts/premium", List.of(PermissionName.POST_PREMIUM_VIEW),
                "/posts/free", new ArrayList<>());
    }
}
