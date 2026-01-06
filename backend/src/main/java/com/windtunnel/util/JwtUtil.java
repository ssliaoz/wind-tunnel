package com.windtunnel.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JWT工具类
 * 
 * 提供JWT令牌的生成、解析和验证功能
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Component
public class JwtUtil {

    @Value("${wind-tunnel.jwt.secret:windtunnel-secret-key}")
    private String secret;

    @Value("${wind-tunnel.jwt.expire:86400000}") // 默认24小时
    private Long expire;

    /**
     * 生成JWT令牌
     * 
     * @param userId 用户ID
     * @param username 用户名
     * @return JWT令牌
     */
    public String generateToken(Long userId, String username) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expire);

        Key signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 从JWT令牌中解析用户信息
     * 
     * @param token JWT令牌
     * @return 用户信息
     */
    public Claims getClaimsFromToken(String token) {
        try {
            Key signingKey = Keys.hmacShaKeyFor(secret.getBytes());
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("解析JWT令牌失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 验证JWT令牌
     * 
     * @param token JWT令牌
     * @param username 用户名
     * @return 验证结果
     */
    public Boolean validateToken(String token, String username) {
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return false;
            }
            String tokenUsername = claims.getSubject();
            Date expiration = claims.getExpiration();
            return username.equals(tokenUsername) && !expiration.before(new Date());
        } catch (Exception e) {
            log.error("验证JWT令牌失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 从JWT令牌中获取用户名
     * 
     * @param token JWT令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims != null ? claims.getSubject() : null;
        } catch (Exception e) {
            log.error("从JWT令牌获取用户名失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 从JWT令牌中获取用户ID
     * 
     * @param token JWT令牌
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims != null) {
                Object userId = claims.get("userId");
                if (userId instanceof Integer) {
                    return ((Integer) userId).longValue();
                } else if (userId instanceof Long) {
                    return (Long) userId;
                }
            }
            return null;
        } catch (Exception e) {
            log.error("从JWT令牌获取用户ID失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 检查JWT令牌是否过期
     * 
     * @param token JWT令牌
     * @return 过期状态
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            log.error("检查JWT令牌过期失败: {}", e.getMessage());
            return true;
        }
    }

}