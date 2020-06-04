package org.xmcxh.vd.mall.sso.modle;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by pengq on 2020/6/4 9:37.
 */
@Data
@TableName("ucs_role_menu_relation")
public class UcsRoleMenuRelation {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("role_id")
    private Long roleId;

    @TableField("menu_id")
    private Long MenuId;
}
