package org.xmcxh.vd.mall.sso;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class test {
    @GetMapping("/test")
    public String page() {
        return "/views/user/user_list";
    }
}
