package org.xmcxh.vd.mall.sso.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.xmcxh.vd.mall.sso.dto.UcsRoleRequest;
import org.xmcxh.vd.mall.sso.modle.StatusType;
import org.xmcxh.vd.mall.sso.modle.UcsRole;
import vd.mall.response.PageResponse;

import java.util.List;

/**
 * Created by pengq on 2020/5/25 14:13.
 */
public interface UcsRoleService extends IService<UcsRole> {
    List<UcsRole> listRoleByStatus(StatusType enabled);

    void addRole(UcsRoleRequest ucsRoleRequest);

    boolean existsByName(Long id, String roleName);

    PageResponse pagingRole(Integer page, Integer size, String roleName);

    void deleteRoleById(Long roleId);

    void updateRole(Long roleId, UcsRoleRequest ucsRoleRequest);

    void addMenus(Long roleId, List<Long> menuIds);

    List<Long> getMenusByRoleId(Long roleId);

    void modifyRoleStatus(Long roleId, StatusType statusType);
}
