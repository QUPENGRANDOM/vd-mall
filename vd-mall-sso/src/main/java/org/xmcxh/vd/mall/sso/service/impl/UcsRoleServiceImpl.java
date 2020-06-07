package org.xmcxh.vd.mall.sso.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xmcxh.vd.mall.sso.dto.UcsRoleRequest;
import org.xmcxh.vd.mall.sso.modle.StatusType;
import org.xmcxh.vd.mall.sso.modle.UcsRole;
import org.xmcxh.vd.mall.sso.modle.UcsRoleMenuRelation;
import org.xmcxh.vd.mall.sso.modle.UcsUserRoleRelation;
import org.xmcxh.vd.mall.sso.repository.UcsRoleMenuRelationRepository;
import org.xmcxh.vd.mall.sso.repository.UcsRoleRepository;
import org.xmcxh.vd.mall.sso.repository.UcsUserRoleRelationRepository;
import org.xmcxh.vd.mall.sso.service.UcsRoleService;
import vd.mall.response.PageResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pengq on 2020/5/25 14:14.
 */
@Slf4j
@Service
public class UcsRoleServiceImpl implements UcsRoleService {
    @Autowired
    UcsRoleRepository ucsRoleRepository;

    @Autowired
    UcsUserRoleRelationRepository ucsUserRoleRelationRepository;

    @Autowired
    UcsRoleMenuRelationRepository ucsRoleMenuRelationRepository;

    @Override
    public List<UcsRole> listRoleByStatus(StatusType enabled) {
        Wrapper<UcsRole> queryWrapper = Wrappers.<UcsRole>lambdaQuery().eq(UcsRole::getStatus, enabled).orderByAsc(UcsRole::getRoleName);

        return ucsRoleRepository.selectList(queryWrapper);
    }

    @Override
    public void addRole(UcsRoleRequest ucsRoleRequest) {
        boolean exists = this.existsByName(null, ucsRoleRequest.getRoleName());
        if (exists) {
            log.warn("添加角色失败，角色名称[{}]已存在", ucsRoleRequest.getRoleName());
            // TODO: 2020/5/26  throw new Exception();
            return;
        }

        ucsRoleRepository.insert(ucsRoleRequest.transfer());
    }

    @Override
    public boolean existsByName(Long id, String roleName) {
        Wrapper<UcsRole> wrapper = Wrappers.<UcsRole>lambdaQuery().eq(UcsRole::getRoleName, roleName);
        UcsRole ucsRole = ucsRoleRepository.selectOne(wrapper);
        return ucsRole != null && !ucsRole.getId().equals(id);
    }

    @Override
    public PageResponse pagingRole(Integer page, Integer size, String roleName) {
        LambdaQueryWrapper<UcsRole> wrapper = Wrappers.<UcsRole>lambdaQuery().orderByDesc(UcsRole::getCreateTime);
        if (!StringUtils.isBlank(roleName)){
            wrapper.like(UcsRole::getRoleName,roleName);
        }
        IPage<UcsRole> pageData = ucsRoleRepository.selectPage(new Page<>(page, size), wrapper);
        return PageResponse.build(pageData);
    }

    @Override
    public void deleteRoleById(Long roleId) {
        Wrapper<UcsUserRoleRelation> queryWrapper = Wrappers.<UcsUserRoleRelation>lambdaQuery().eq(UcsUserRoleRelation::getRoleId, roleId);

        Integer count = ucsUserRoleRelationRepository.selectCount(queryWrapper);
        if (count > 0) {
            log.warn("该角色正在被使用，不能删除：{}", roleId);
            return;
        }

        ucsRoleRepository.deleteById(roleId);
    }

    @Override
    public void updateRole(Long roleId, UcsRoleRequest ucsRoleRequest) {
        boolean exists = this.existsByName(roleId, ucsRoleRequest.getRoleName());
        if (exists) {
            log.warn("角色名称已存在：{}", ucsRoleRequest.getRoleName());
            return;
        }

        UcsRole ucsRole = ucsRoleRequest.transfer();
        ucsRole.setId(roleId);

        ucsRoleRepository.updateById(ucsRole);
    }

    @Override
    @Transactional
    public void addMenus(Long roleId, List<Long> menuIds) {
        UcsRole ucsRole = ucsRoleRepository.selectById(roleId);
        if (ucsRole == null) {
            return;
        }

        UcsRoleMenuRelation relation = new UcsRoleMenuRelation();

        for (Long menuId : menuIds) {
            relation.setMenuId(menuId);
            relation.setRoleId(roleId);
            ucsRoleMenuRelationRepository.insert(relation);
        }
    }

    @Override
    public List<Long> getMenusByRoleId(Long roleId) {
        LambdaQueryWrapper<UcsRoleMenuRelation> wrapper = Wrappers.<UcsRoleMenuRelation>lambdaQuery()
                .eq(UcsRoleMenuRelation::getRoleId, roleId);
        List<UcsRoleMenuRelation> ucsRoleMenuRelations = ucsRoleMenuRelationRepository.selectList(wrapper);

        return ucsRoleMenuRelations == null || ucsRoleMenuRelations.isEmpty() ?
                Collections.emptyList() : ucsRoleMenuRelations.stream().map(UcsRoleMenuRelation::getMenuId).collect(Collectors.toList());
    }

    @Override
    public void modifyRoleStatus(Long roleId, StatusType statusType) {
        UcsRole ucsRole = ucsRoleRepository.selectById(roleId);
        if (ucsRole == null || ucsRole.getStatus() == statusType) {
            return;
        }

        ucsRole.setStatus(statusType);
        ucsRoleRepository.updateById(ucsRole);
    }
}
