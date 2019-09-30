package com.vd.mall.admin.security.provider;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by pengq on 2019/9/27 17:02.
 */
@Deprecated
public class ValidateCodeAuthenticationToken extends AbstractAuthenticationToken {
    private Object principal;
    private Object credentials;
    private Object validateCode;

    public ValidateCodeAuthenticationToken(Object principal, Object credentials,Object validateCode) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.validateCode = validateCode;
        setAuthenticated(false);
    }

    public ValidateCodeAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public Object getValidateCode() {
        return validateCode;
    }
}
