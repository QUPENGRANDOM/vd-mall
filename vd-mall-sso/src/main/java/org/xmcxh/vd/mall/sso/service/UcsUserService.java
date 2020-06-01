package org.xmcxh.vd.mall.sso.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.xmcxh.vd.mall.sso.dto.LoginRequest;
import org.xmcxh.vd.mall.sso.dto.ModifyPasswordRequest;
import org.xmcxh.vd.mall.sso.dto.UcsUserRequest;
import org.xmcxh.vd.mall.sso.exception.UserNameExistsException;
import org.xmcxh.vd.mall.sso.exception.UserNotFoundException;
import org.xmcxh.vd.mall.sso.exception.UserPasswordException;
import org.xmcxh.vd.mall.sso.modle.StatusType;
import org.xmcxh.vd.mall.sso.modle.UcsUser;
import org.xmcxh.vd.mall.sso.security.UserDetail;
import org.xmcxh.vd.mall.sso.vo.UcsUserVO;
import vd.mall.response.PageResponse;

/**
 * Created by pengq on 2020/5/12 14:29.
 */
public interface UcsUserService {

    void createUser(UcsUserRequest ucsUser);

    void modifyUser(Long userId, UcsUser ucsUser) throws UserNotFoundException, UserNameExistsException;

    boolean exists(Long Id, String username);

    void modifyUserPassword(Long userId, ModifyPasswordRequest modifyPasswordRequest) throws UserPasswordException;

    void modifyUserStatus(Long userId, StatusType statusType);

    UcsUserVO getUserAndRoleById(Long userId);

    PageResponse pagingUser(Integer page, Integer size, String name);

    UserDetail getUserDetailsByUserName(String username) throws UsernameNotFoundException;

    void removeUserById(Long userId);

    void restPassword(Long userId);

    String login(LoginRequest loginRequest);

    UcsUserVO getUserByToken(String token);
}
