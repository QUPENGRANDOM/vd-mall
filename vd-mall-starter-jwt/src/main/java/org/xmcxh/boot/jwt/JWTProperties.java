package org.xmcxh.boot.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by pengq on 2020/5/11 10:06.
 */
@Data
@ConfigurationProperties(prefix = JWTProperties.PREFIX)
public class JWTProperties {
    public static final String PREFIX = "spring.jwt";
    private String issuer = "";
    private Long expireIn = 30L;
    private Long leeway = 30L;
    private Long expireLeeway = 30L;
    private String secret;
    private String headerKey;
    private String tokenHead;
    private SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
}
