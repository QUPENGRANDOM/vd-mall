package org.xmcxh.vd.mall.sso.modle;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Created by pengq on 2020/5/12 14:28.
 */
@TableName("ucs_user")
@Data
public class UcsUser {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String address;

    private UcsUserStatus status;

    @TableField("role_id")
    private Long roleId;

    @TableField("last_login_time")
    private Date lastLoginTime;

    @TableField("create_time")
    private Date createTime;

    @TableField(exist = false)
    private boolean accountLocked;
}
