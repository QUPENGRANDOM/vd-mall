package org.xmcxh.vd.mall.sso.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xmcxh.boot.jwt.TokenProvider;
import org.xmcxh.vd.mall.sso.dto.LoginRequest;
import org.xmcxh.vd.mall.sso.dto.UcsUserRequest;
import org.xmcxh.vd.mall.sso.exception.UserNameExistsException;
import org.xmcxh.vd.mall.sso.exception.UserNotFoundException;
import org.xmcxh.vd.mall.sso.exception.UserPasswordException;
import org.xmcxh.vd.mall.sso.dto.ModifyPasswordRequest;
import org.xmcxh.vd.mall.sso.modle.*;
import org.xmcxh.vd.mall.sso.repository.UcsRoleMenuRelationRepository;
import org.xmcxh.vd.mall.sso.repository.UcsRoleRepository;
import org.xmcxh.vd.mall.sso.repository.UcsUserRepository;
import org.xmcxh.vd.mall.sso.repository.UcsUserRoleRelationRepository;
import org.xmcxh.vd.mall.sso.security.UserDetail;
import org.xmcxh.vd.mall.sso.service.UcsMenuService;
import org.xmcxh.vd.mall.sso.service.UcsUserService;
import org.xmcxh.vd.mall.sso.vo.UcsUserVO;
import vd.mall.response.PageResponse;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by pengq on 2020/5/12 14:30.
 */
@Service
@Slf4j
public class UcsUserServiceImpl implements UcsUserService {
    @Autowired
    UcsUserRepository ucsUserRepository;

    @Autowired
    UcsRoleRepository ucsRoleRepository;

    @Autowired
    UcsUserRoleRelationRepository ucsUserRoleRelationRepository;

    @Autowired
    UcsMenuService ucsMenuService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenProvider tokenProvider;

    @Override
    @Transactional
    public void createUser(UcsUserRequest ucsUserRequest) {
        UcsUser ucsUser = ucsUserRequest.transfer();
        ucsUser.setPassword(passwordEncoder.encode(ucsUserRequest.getPassword()));
        ucsUserRepository.insert(ucsUser);

        List<Long> roleIdList = ucsUserRequest.getRoleIdList();
        if (roleIdList != null && !roleIdList.isEmpty()) {
            UcsUserRoleRelation relation = new UcsUserRoleRelation();
            for (Long roleId : roleIdList) {
                relation.setRoleId(roleId);
                relation.setUserId(ucsUser.getId());
                ucsUserRoleRelationRepository.insert(relation);
            }
        }
    }

    @Override
    @Transactional
    public void modifyUser(Long userId, UcsUser ucsUser) throws UserNotFoundException, UserNameExistsException {
        UcsUser user = ucsUserRepository.selectById(userId);
        if (user == null) {
            log.error("未找到该用户：[{}]", userId);
            throw new UserNotFoundException("未找到该用户：" + userId);
        }

        //检查用户名是否存在
        if (this.exists(userId, ucsUser.getUsername())) {
            //名称存在但不是自己
            throw new UserNameExistsException("用户名已存在:" + ucsUser.getUsername());
        }

        ucsUser.setId(userId);

        ucsUserRepository.updateById(ucsUser);
    }

    @Override
    public boolean exists(Long id, String username) {
        Wrapper<UcsUser> wrapper = Wrappers.<UcsUser>lambdaQuery().eq(UcsUser::getUsername, username);
        UcsUser user = ucsUserRepository.selectOne(wrapper);
        return user != null && !user.getId().equals(id);
    }

    @Override
    public void modifyUserPassword(Long userId, ModifyPasswordRequest modifyPasswordRequest) throws UserPasswordException {
        //校验两次密码是否一致
        if (!modifyPasswordRequest.getPassword().equals(modifyPasswordRequest.getConfirmPassword())) {
            throw new UserPasswordException("用户输入的两次密码不一致");
        }

        UcsUser ucsUser = ucsUserRepository.selectById(userId);
        if (passwordEncoder.matches(ucsUser.getPassword(), modifyPasswordRequest.getOldPassword())) {
            throw new UserPasswordException("用户输入的原始密码不正确");
        }

        ucsUser.setPassword(modifyPasswordRequest.getPassword());

        ucsUserRepository.updateById(ucsUser);
    }

    @Override
    public void modifyUserStatus(Long userId, StatusType statusType) {
        UcsUser ucsUser = ucsUserRepository.selectById(userId);
        if (ucsUser == null) {
            log.warn("未找到该用户：{}", userId);
            return;
        }

        if (ucsUser.getStatus() == statusType) {
            return;
        }

        ucsUser.setStatus(statusType);

        ucsUserRepository.updateById(ucsUser);
    }

    @Override
    public UcsUserVO getUserAndRoleById(Long userId) {
        UcsUser ucsUser = ucsUserRepository.selectById(userId);

        Wrapper<UcsUserRoleRelation> wrapper = Wrappers.<UcsUserRoleRelation>lambdaQuery().eq(UcsUserRoleRelation::getUserId, userId);
        List<UcsUserRoleRelation> ucsUserRoleRelations = ucsUserRoleRelationRepository.selectList(wrapper);
        UcsUserVO vo = UcsUserVO.build(ucsUser);
        if (ucsUserRoleRelations != null && !ucsUserRoleRelations.isEmpty()) {
            List<Long> roleIds = ucsUserRoleRelations.stream().map(UcsUserRoleRelation::getRoleId).collect(Collectors.toList());
            List<UcsRole> ucsRoles = ucsRoleRepository.selectBatchIds(roleIds);
            List<String> names = ucsRoles.stream().map(UcsRole::getRoleName).collect(Collectors.toList());
            vo.setRoleNames(names);
            vo.setRoleIds(roleIds);
        }

        return vo;
    }

    @Override
    public PageResponse pagingUser(Integer page, Integer size, String name) {
        LambdaQueryWrapper<UcsUser> wrapper = Wrappers.<UcsUser>lambdaQuery().orderByDesc(UcsUser::getCreateTime);
        if (name != null && !name.isEmpty()) {
            wrapper.like(UcsUser::getUsername, name);
        }

        IPage<UcsUser> pageData = ucsUserRepository.selectPage(new Page<>(page, size), wrapper);

        List<UcsUserVO> vos = new ArrayList<>(size);
        List<UcsRole> roles = ucsRoleRepository.selectList(null);
        Map<Long, String> roleMap = roles.stream().collect(Collectors.toMap(UcsRole::getId, UcsRole::getRoleName, (k1, k2) -> k1));

        for (UcsUser record : pageData.getRecords()) {
            Wrapper<UcsUserRoleRelation> queryWrapper = Wrappers.<UcsUserRoleRelation>lambdaQuery().eq(UcsUserRoleRelation::getUserId, record.getId());
            List<UcsUserRoleRelation> ucsUserRoleRelations = ucsUserRoleRelationRepository.selectList(queryWrapper);
            UcsUserVO vo = UcsUserVO.build(record);
            if (ucsUserRoleRelations != null && !ucsUserRoleRelations.isEmpty()) {
                List<Long> roleIds = ucsUserRoleRelations.stream().map(UcsUserRoleRelation::getRoleId).collect(Collectors.toList());
                List<String> roleIdList = new ArrayList<>();
                for (Long roleId : roleIds) {
                    roleIdList.add(roleMap.getOrDefault(roleId, ""));
                }
                vo.setRoleNames(roleIdList);
                vo.setRoleIds(roleIds);
            }

            vos.add(vo);
        }

        PageResponse pageResponse = PageResponse.build(pageData, false);
        pageResponse.setList(vos);

        return pageResponse;
    }

    @Override
    public UserDetail loadUserDetailsByUserName(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<UcsUser> queryUserWrapper = Wrappers.<UcsUser>lambdaQuery().eq(UcsUser::getUsername, username);
        UcsUser ucsUser = ucsUserRepository.selectOne(queryUserWrapper);
        if (ucsUser == null) {
            throw new UsernameNotFoundException("Not found username:" + username);
        }

        List<UcsRole> roles =this.listRoleByUserId(ucsUser.getId());
        return new UserDetail(ucsUser, roles);
    }

    @Override
    public void removeUserById(Long userId) {
        ucsUserRepository.deleteById(userId);
    }

    @Override
    public void restPassword(Long userId) {
        String password = RandomStringUtils.randomAlphabetic(8);
        UcsUser ucsUser = ucsUserRepository.selectById(userId);
        ucsUser.setPassword(passwordEncoder.encode(password));
        ucsUserRepository.updateById(ucsUser);
        // TODO: 2020/6/1 send the password to mail
        log.debug("Password:{}", password);
    }

    @Override
    public String login(LoginRequest loginRequest) {
        UserDetails userDetails = this.loadUserDetailsByUserName(loginRequest.getUsername());
        if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.issue(userDetails.getUsername());
    }

    @Override
    public UcsUserVO getUserByToken(String token) {
        Claims claims = tokenProvider.decode(token);
        String username = claims.getSubject();
        LambdaQueryWrapper<UcsUser> queryUserWrapper = Wrappers.<UcsUser>lambdaQuery().eq(UcsUser::getUsername, username);
        UcsUser ucsUser = ucsUserRepository.selectOne(queryUserWrapper);
        return this.getUserAndRoleById(ucsUser.getId());
    }

    @Override
    public List<UcsMenu> listMenusByUserId(Long userId) {
        List<UcsRole> ucsRoles = this.listRoleByUserId(userId);

        if (ucsRoles == null || ucsRoles.isEmpty()){
            return Collections.emptyList();
        }

        Set<Long> roleIdSet = ucsRoles.stream().map(UcsRole::getId).collect(Collectors.toSet());

        return ucsMenuService.listMenuByRoleId(roleIdSet);

    }

    private List<UcsRole> listRoleByUserId(Long userId) {
        LambdaQueryWrapper<UcsUserRoleRelation> relationWrapper = Wrappers.<UcsUserRoleRelation>lambdaQuery().eq(UcsUserRoleRelation::getUserId, userId);
        List<UcsUserRoleRelation> relations = ucsUserRoleRelationRepository.selectList(relationWrapper);
        if (relations == null || relations.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> roleIdList = relations.stream().map(UcsUserRoleRelation::getRoleId).collect(Collectors.toList());
        LambdaQueryWrapper<UcsRole> queryRoleWrapper = Wrappers.<UcsRole>lambdaQuery().in(UcsRole::getId, roleIdList);
        return ucsRoleRepository.selectList(queryRoleWrapper);
    }
}
