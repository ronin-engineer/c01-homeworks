package com.roninhub.security.infrastructure.util;

import com.roninhub.security.domain.auth.constant.PermissionName;
import com.roninhub.security.domain.auth.entity.Permission;
import com.roninhub.security.domain.auth.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import javax.crypto.SecretKey;
import java.util.*;

@UtilityClass
public class JwtUtils {
    private final String jwtSecret = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D";
    private final int jwtExpirationMs = 600000;

    public static String generateJwtToken(final User user) {
        var permissions = user.getRoles().stream().flatMap(role -> role.getPermissions().stream()).toList();
        Map<String, Object> permissionsClaim = new HashMap<>();
        if (!CollectionUtils.isEmpty(permissions)) {
            permissionsClaim.put("permissions", permissions.stream().map(Permission::getName).toList());
        }

        return Jwts.builder()
                .subject((user.getUsername()))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey())
                .claims().add(permissionsClaim)
                .and()
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public List<PermissionName> getPermissionsFromJwtToken(final String token) {
        var extractAllClaims = Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build().parseSignedClaims(token).getPayload();

        var permissions = extractAllClaims.get("permissions", List.class);

        return CollectionUtils.isEmpty(permissions) ?
                new ArrayList<>() : permissions.stream().map(p -> PermissionName.valueOf((String) p)).toList();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(authToken);
            return true;
        } catch (SignatureException e) {
            System.out.printf("Invalid JWT signature: %s%n", e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.printf("Invalid JWT token: %s%n", e.getMessage());
            e.printStackTrace();
        } catch (ExpiredJwtException e) {
            System.out.printf("JWT token is expired: %s%n", e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.printf("JWT token is unsupported: %s%n", e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.printf("JWT claims string is empty: %s%n", e.getMessage());
        } catch (Exception e) {
            System.out.printf("JWT Ex: %s%n", e.getMessage());
        }

        return false;
    }
}