package org.xmcxh.boot.jwt;

import io.jsonwebtoken.Claims;

public interface TokenProvider {
    String issue(String userId);

    String renew(String token);

    boolean validate(String token);

    Claims decode(String token);

    String getHeaderKey();

    String getTokenHead();
}
