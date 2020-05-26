package org.xmcxh.vd.mall.sso.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.xmcxh.vd.mall.sso.modle.UcsUser;

import java.util.List;

/**
 * Created by pengq on 2020/5/12 16:12.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class UcsUserVO extends UcsUser {
    private List<String> roleNames;

    public static UcsUserVO build(UcsUser ucsUser) {
        UcsUserVO vo = new UcsUserVO();
        BeanUtils.copyProperties(ucsUser, vo);
        return vo;
    }
}
