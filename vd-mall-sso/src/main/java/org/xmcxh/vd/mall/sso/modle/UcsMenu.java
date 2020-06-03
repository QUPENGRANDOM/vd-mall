package org.xmcxh.vd.mall.sso.modle;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Created by pengq on 2020/6/3 14:39.
 */
@Data
@TableName("ucs_menu")
public class UcsMenu {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private Integer sort;

    private Long parentId;

    private String icon;

    @TableField("icon_type")
    private IconType iconType;

    private StatusType status;

    @TableField("create_time")
    private Date createTime;
}
