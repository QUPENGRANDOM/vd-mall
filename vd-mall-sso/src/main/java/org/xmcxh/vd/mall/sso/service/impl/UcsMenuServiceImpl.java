package org.xmcxh.vd.mall.sso.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmcxh.vd.mall.sso.dto.UcsMenuRequest;
import org.xmcxh.vd.mall.sso.modle.UcsMenu;
import org.xmcxh.vd.mall.sso.modle.UcsRoleMenuRelation;
import org.xmcxh.vd.mall.sso.repository.UcsMenuRepository;
import org.xmcxh.vd.mall.sso.repository.UcsRoleMenuRelationRepository;
import org.xmcxh.vd.mall.sso.service.UcsMenuService;
import vd.mall.response.PageResponse;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by pengq on 2020/6/3 15:05.
 */
@Service
@Slf4j
public class UcsMenuServiceImpl implements UcsMenuService {
    @Autowired
    UcsMenuRepository ucsMenuRepository;

    @Autowired
    UcsRoleMenuRelationRepository ucsRoleMenuRelationRepository;

    @Override
    public void addMenu(UcsMenuRequest ucsMenuRequest) {
        UcsMenu ucsMenu = this.getOneByTitle(ucsMenuRequest.getTitle());

        if (ucsMenu != null) {
            log.warn("该名称已存在：{}", ucsMenu.getTitle());
            return;
        }

        ucsMenuRepository.insert(ucsMenuRequest.transfer());
    }

    @Override
    public PageResponse pagingMenus(Integer page, Integer size, Long parentId) {
        Wrapper<UcsMenu> wrapper = Wrappers.<UcsMenu>lambdaQuery()
                .eq(UcsMenu::getParentId, parentId)
                .orderByAsc(UcsMenu::getSort)
                .orderByDesc(UcsMenu::getCreateTime);

        IPage<UcsMenu> pageData = ucsMenuRepository.selectPage(new Page<>(page, size), wrapper);

        return PageResponse.build(pageData);
    }

    @Override
    public List<UcsMenu> listMenuByRoleId(Long roleId) {
        if (roleId == null) {
            return null;
        }

        return listMenuByRoleId(Collections.singletonList(roleId));
    }

    @Override
    public List<UcsMenu> listMenuByRoleId(Collection<Long> roleIdLis) {
        if (roleIdLis == null || roleIdLis.isEmpty()) {
            return null;
        }

        LambdaQueryWrapper<UcsRoleMenuRelation> queryRelation = Wrappers.<UcsRoleMenuRelation>lambdaQuery()
                .in(UcsRoleMenuRelation::getRoleId, roleIdLis);

        List<UcsRoleMenuRelation> relations = ucsRoleMenuRelationRepository.selectList(queryRelation);

        if (relations == null || relations.isEmpty()) {
            return null;
        }

        Set<Long> menuIdSet = relations.stream().map(UcsRoleMenuRelation::getMenuId).collect(Collectors.toSet());

        LambdaQueryWrapper<UcsMenu> queryMenus = Wrappers.<UcsMenu>lambdaQuery()
                .in(UcsMenu::getId, menuIdSet)
                .orderByAsc(UcsMenu::getSort);

        return ucsMenuRepository.selectList(queryMenus);
    }

    private UcsMenu getOneByTitle(String title) {
        Wrapper<UcsMenu> wrapper = Wrappers.<UcsMenu>lambdaQuery().eq(UcsMenu::getTitle, title);
        return ucsMenuRepository.selectOne(wrapper);
    }
}
