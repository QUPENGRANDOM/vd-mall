package org.xmcxh.vd.mall.sso.service;

import org.xmcxh.vd.mall.sso.dto.UcsRoleRequest;
import org.xmcxh.vd.mall.sso.modle.StatusType;
import org.xmcxh.vd.mall.sso.modle.UcsRole;
import vd.mall.response.PageResponse;

import java.util.List;

/**
 * Created by pengq on 2020/5/25 14:13.
 */
public interface UcsRoleService {
    List<UcsRole> listRoleByStatus(StatusType enabled);

    void addRole(UcsRoleRequest ucsRoleRequest);

    boolean existsByName(Long id, String roleName);

    PageResponse pagingRole(Integer page, Integer size);

    void deleteRoleById(Long roleId);

    void updateRole(Long roleId, UcsRoleRequest ucsRoleRequest);
}
