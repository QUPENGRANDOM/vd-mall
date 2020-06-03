package org.xmcxh.vd.mall.sso.service;

import org.xmcxh.vd.mall.sso.dto.UcsMenuRequest;
import vd.mall.response.PageResponse;

public interface UcsMenuService {
    void addMenu(UcsMenuRequest ucsMenuRequest);

    PageResponse pagingMenus(Integer page, Integer size, Long parentId);
}
