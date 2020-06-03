package org.xmcxh.vd.mall.sso.dto;

import lombok.Data;
import org.xmcxh.vd.mall.sso.modle.IconType;
import org.xmcxh.vd.mall.sso.modle.StatusType;
import org.xmcxh.vd.mall.sso.modle.UcsMenu;

/**
 * Created by pengq on 2020/6/3 15:14.
 */
@Data
public class UcsMenuRequest {
    private String title;

    private Integer sort;

    private Long parentId;

    private String icon;

    private IconType iconType;

    private StatusType status;

    public UcsMenu transfer() {
        UcsMenu ucsMenu = new UcsMenu();
        ucsMenu.setTitle(title);
        ucsMenu.setSort(sort == null ? 0 : sort);
        ucsMenu.setParentId(parentId == null ? 0 : parentId);
        ucsMenu.setIcon(icon);
        ucsMenu.setIconType(iconType);
        ucsMenu.setStatus(status);

        return ucsMenu;
    }
}
