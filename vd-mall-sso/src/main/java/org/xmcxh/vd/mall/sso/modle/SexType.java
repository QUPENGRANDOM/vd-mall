package org.xmcxh.vd.mall.sso.modle;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * Created By pengq On 2020/5/29 20:56
 */
@Getter
public enum SexType {
    MEN(0),WOMEN(1);

    @EnumValue
    private Integer code;

    SexType(Integer code) {
        this.code = code;
    }
}
