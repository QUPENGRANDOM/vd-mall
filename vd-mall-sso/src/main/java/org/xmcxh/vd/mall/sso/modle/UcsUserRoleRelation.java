package org.xmcxh.vd.mall.sso.modle;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by pengq on 2020/5/26 13:30.
 */
@Data
@TableName("ucs_user_role_relation")
public class UcsUserRoleRelation {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("role_id")
    private Long roleId;
}
