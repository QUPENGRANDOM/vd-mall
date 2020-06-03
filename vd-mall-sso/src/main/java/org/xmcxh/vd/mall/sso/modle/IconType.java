package org.xmcxh.vd.mall.sso.modle;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * Created by pengq on 2020/6/3 14:42.
 */
@Getter
public enum IconType {
    CSS(0),IMG(1);

    @EnumValue
    private Integer code;

    IconType(Integer code) {
        this.code = code;
    }
}
