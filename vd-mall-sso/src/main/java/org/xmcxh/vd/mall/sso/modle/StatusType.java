package org.xmcxh.vd.mall.sso.modle;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum StatusType {
    DISABLED(0, "禁用"),
    ENABLED(1, "启用");

    @EnumValue
    private Integer code;
    private String description;

    StatusType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
