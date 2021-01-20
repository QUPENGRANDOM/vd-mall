package org.xmcxh.vd.mall.sso.service;

import org.xmcxh.vd.mall.sso.dto.UcsMenuRequest;
import org.xmcxh.vd.mall.sso.modle.UcsMenu;
import vd.mall.response.PageResponse;

import java.util.Collection;
import java.util.List;

public interface UcsMenuService {
    void addMenu(UcsMenuRequest ucsMenuRequest);

    PageResponse pagingMenus(Integer page, Integer size, Long parentId);

    List<UcsMenu> listMenuByRoleId(Long roleId);

    List<UcsMenu> listMenuByRoleId(Collection<Long> roleIdLis);

}
