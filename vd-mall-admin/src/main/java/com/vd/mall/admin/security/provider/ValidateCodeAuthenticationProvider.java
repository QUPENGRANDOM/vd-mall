package com.vd.mall.admin.security.provider;

import com.vd.mall.admin.security.validatecode.exception.ValidateCodeException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by pengq on 2019/9/27 11:45.
 * 原本打算通过重写provider的方式来做验证码校验，但是写完之后发现和UsernamePassword没啥区别，
 * 而且还把他的有关于User校验的逻辑干掉了，后来就用了一个简单的Filter做了实现
 * 后续打算把这个类改成 手机快速登陆的实现，原本没打算把security写的这么庞大，
 * 但是写着写着就不断有新的想法出现，后续 security 打算做成单独的包，做成自动配置的方式。
 */
@Deprecated
public class ValidateCodeAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public ValidateCodeAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ValidateCodeAuthenticationToken token = (ValidateCodeAuthenticationToken) authentication;
        if (!token.getValidateCode().equals("1234")) {
            throw new ValidateCodeException("");
        }

        String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("Bad credentials");
        }

        ValidateCodeAuthenticationToken result = new ValidateCodeAuthenticationToken(username, authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());

        return createSuccessAuthentication(user,authentication,user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ValidateCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Authentication createSuccessAuthentication(Object principal,
                                                         Authentication authentication, UserDetails user) {
        // Ensure we return the original credentials the user supplied,
        // so subsequent attempts are successful even with encoded passwords.
        // Also ensure we return the original getDetails(), so that future
        // authentication events after cache expiry contain the details
        ValidateCodeAuthenticationToken result = new ValidateCodeAuthenticationToken(
                principal, authentication.getCredentials(),
                authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());

        return result;
    }
}
