package org.xmcxh.vd.mall.sso.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmcxh.vd.mall.sso.dto.UcsMenuRequest;
import org.xmcxh.vd.mall.sso.modle.UcsMenu;
import org.xmcxh.vd.mall.sso.repository.UcsMenuRepository;
import org.xmcxh.vd.mall.sso.service.UcsMenuService;
import vd.mall.response.PageResponse;

/**
 * Created by pengq on 2020/6/3 15:05.
 */
@Service
@Slf4j
public class UcsMenuServiceImpl implements UcsMenuService {
    @Autowired
    UcsMenuRepository ucsMenuRepository;

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

    private UcsMenu getOneByTitle(String title) {
        Wrapper<UcsMenu> wrapper = Wrappers.<UcsMenu>lambdaQuery().eq(UcsMenu::getTitle, title);
        return ucsMenuRepository.selectOne(wrapper);
    }
}