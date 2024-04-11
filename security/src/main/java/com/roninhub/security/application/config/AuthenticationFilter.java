package com.roninhub.security.application.config;

import com.roninhub.security.application.constant.ResponseCode;
import com.roninhub.security.application.dto.exception.AuthException;
import com.roninhub.security.application.service.ResponseFactory;
import com.roninhub.security.domain.auth.constant.PermissionName;
import com.roninhub.security.infrastructure.constant.DefaultValue;
import com.roninhub.security.infrastructure.constant.FieldName;
import com.roninhub.security.infrastructure.util.JwtUtils;
import com.roninhub.security.infrastructure.util.ObjectMapperUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final ResponseFactory responseFactory;

    private final Map<String, List<PermissionName>> authorizeHttpRequests;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var requestURI = request.getRequestURI();

        for (String uriPattern : authorizeHttpRequests.keySet()) {
            if (requestURI.contains(uriPattern)) {
                var requirePermissions = authorizeHttpRequests.get(uriPattern);
                if (requirePermissions.isEmpty()) {
                    filterChain.doFilter(request, response);
                    return;
                }

                // check permission for API
                String authorizationHeader = request.getHeader(FieldName.AUTHORIZATION);
                String accessToken = authorizationHeader.replace(DefaultValue.BEARER, "");
                try {
                    // 1. verify token
                    if (!JwtUtils.validateJwtToken(accessToken)) {
                        buildErrorResponse(response, ResponseCode.UNAUTHORIZED);
                        return;
                    }

                    // 2. Check whether the User's permissions are valid or not?
                    var permissions = JwtUtils.getPermissionsFromJwtToken(accessToken);
                    if (permissions.stream().noneMatch(requirePermissions::contains)) {
                        buildErrorResponse(response, ResponseCode.FORBIDDEN);
                        return;
                    }

                    filterChain.doFilter(request, response);
                } catch (AuthException e) {
                    log.error("Exception at verifying token");
                    e.printStackTrace();
                    buildErrorResponse(response, ResponseCode.UNAUTHORIZED);
                } catch (Exception e) {
                    log.error("Filter exception: {}", e.getMessage());
                    e.printStackTrace();
                    buildErrorResponse(response, ResponseCode.INTERNAL_SERVER_ERROR);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    @SneakyThrows
    private void buildErrorResponse(HttpServletResponse response, ResponseCode responseCode) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(responseCode.getStatus());

        var serialized = ObjectMapperUtil.OBJECT_MAPPER.writeValueAsString(responseFactory.response(responseCode));

        response.getOutputStream().write(serialized.getBytes());
    }
}