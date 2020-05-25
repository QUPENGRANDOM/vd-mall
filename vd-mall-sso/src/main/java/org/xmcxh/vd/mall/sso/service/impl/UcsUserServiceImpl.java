package org.xmcxh.vd.mall.sso.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xmcxh.vd.mall.sso.exception.UserNameExistsException;
import org.xmcxh.vd.mall.sso.exception.UserNotFoundException;
import org.xmcxh.vd.mall.sso.exception.UserPasswordException;
import org.xmcxh.vd.mall.sso.dto.ModifyPasswordRequest;
import org.xmcxh.vd.mall.sso.modle.UcsRole;
import org.xmcxh.vd.mall.sso.modle.UcsUser;
import org.xmcxh.vd.mall.sso.repository.UcsRoleRepository;
import org.xmcxh.vd.mall.sso.repository.UcsUserRepository;
import org.xmcxh.vd.mall.sso.service.UcsUserService;
import org.xmcxh.vd.mall.sso.vo.UcsUserVO;
import org.xmcxh.vd.mall.sso.security.UserDetail;
import vd.mall.response.PageResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(UcsUser ucsUser) {
        String password = passwordEncoder.encode(ucsUser.getPassword());
        ucsUser.setPassword(password);
        ucsUserRepository.insert(ucsUser);
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

        user.setNickname(ucsUser.getNickname());
        user.setStatus(ucsUser.getStatus());
        user.setUsername(ucsUser.getUsername());
        user.setId(userId);

        ucsUserRepository.updateById(user);
    }

    @Override
    public boolean exists(Long id, String username) {
        Wrapper<UcsUser> wrapper = Wrappers.<UcsUser>lambdaQuery().eq(UcsUser::getUsername, username);
        UcsUser wuser = ucsUserRepository.selectOne(wrapper);
        return wuser != null && !wuser.getId().equals(id);
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
    public UcsUserVO getUserAndRoleById(Long userId) {
        UcsUser ucsUser = ucsUserRepository.selectById(userId);
        UcsRole ucsRole = ucsRoleRepository.selectById(ucsUser.getRoleId());
        UcsUserVO vo = UcsUserVO.build(ucsUser);
        vo.setRoleName(ucsRole.getRoleName());
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
            UcsUserVO vo = UcsUserVO.build(record);
            vo.setRoleName(roleMap.getOrDefault(vo.getRoleId(), ""));
            vos.add(vo);
        }

        PageResponse pageResponse = PageResponse.build(pageData, false);
        pageResponse.setList(vos);

        return pageResponse;
    }

    @Override
    public UserDetail getUserDetailsByUserName(String username) {
        LambdaQueryWrapper<UcsUser> queryUserWrapper = Wrappers.<UcsUser>lambdaQuery().eq(UcsUser::getUsername, username);
        UcsUser ucsUser = ucsUserRepository.selectOne(queryUserWrapper);
        if (ucsUser == null) {
            return null;
        }

        LambdaQueryWrapper<UcsRole> queryRoleWrapper = Wrappers.<UcsRole>lambdaQuery().eq(UcsRole::getId, ucsUser.getRoleId());
        List<UcsRole> roles = ucsRoleRepository.selectList(queryRoleWrapper);
        return new UserDetail(ucsUser, roles);
    }

    @Override
    public void removeUserById(Long userId) {
         ucsUserRepository.deleteById(userId);
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
