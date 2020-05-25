package org.xmcxh.vd.mall.sso.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.xmcxh.vd.mall.sso.modle.UcsRole;
import org.xmcxh.vd.mall.sso.modle.UcsUser;
import org.xmcxh.vd.mall.sso.modle.UcsUserStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by pengq on 2019/9/7 15:03.
 */
public class UserDetail implements UserDetails {
    private UcsUser user;
    private List<UcsRole> roles;

    public UserDetail(UcsUser user, List<UcsRole> roles) {
        this.user = user;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null || roles.isEmpty()) {
            return new ArrayList<>();
        }

        List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (UcsRole role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus() == UcsUserStatus.ENABLED;
    }
}
