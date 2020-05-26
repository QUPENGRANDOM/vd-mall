package org.xmcxh.boot.jwt;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(JWTProperties.class)
public class JWTAutoConfiguration {
    @Getter
    private JWTProperties jwtProperties;

    public JWTAutoConfiguration(JWTProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

   @Bean
    public TokenProvider tokenProvider(){
       JwtTokenProvider provider = new JwtTokenProvider();
       provider.setIssuer(jwtProperties.getIssuer());
       provider.setAlgorithm(jwtProperties.getAlgorithm());
       provider.setExpireIn(jwtProperties.getExpireIn());
       provider.setExpireLeeway(jwtProperties.getExpireLeeway());
       provider.setLeeway(jwtProperties.getLeeway());
       provider.setSecret(jwtProperties.getSecret());
       provider.setHeaderKey(jwtProperties.getHeaderKey());
       provider.setTokenHead(jwtProperties.getTokenHead());
       return provider;
   }
}
