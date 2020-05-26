package org.xmcxh.boot.jwt;

import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by pengq on 2020/5/15 16:29.
 */
@Data
@Slf4j
public class JwtTokenProvider implements TokenProvider {
    private String issuer;
    //过期时间
    private Long expireIn;
    private Long leeway;
    //剩余过期时间
    private Long expireLeeway;
    private String secret;
    private String headerKey;
    private String tokenHead;
    private SignatureAlgorithm algorithm;

    @Override
    public String issue(String userId) {
        long currentTime = System.currentTimeMillis();
        Date issuedAt = new Date(currentTime);
        Date expiresAt = new Date(currentTime + this.expireIn * 1000L);
        Date refreshBefore = new Date(currentTime + this.expireLeeway * 1000L);

        JwtBuilder builder = Jwts.builder()
                .signWith(algorithm, secret)
                .setClaims(new HashMap<String, Object>() {{
                    put("refreshUntil", refreshBefore);
                }})
                .setIssuer(issuer)
                .setSubject(userId)
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt);

        return builder.compact();
    }

    @Override
    public String renew(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }

        Claims claims = this.decode(token);
        if (claims == null) {
            return null;
        }

        if (!claims.getExpiration().after(new Date())) {
            log.warn("this token is expiration");
            return null;
        }

        if (claims.get("refreshUntil",Date.class).after(new Date())){
            return token;
        }

        return issue(claims.getSubject());

    }

    @Override
    public boolean validate(String token) {
        Claims claims = this.decode(token);
        return claims != null && claims.getExpiration().after(new Date());
    }

    @Override
    public Claims decode(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }

        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
