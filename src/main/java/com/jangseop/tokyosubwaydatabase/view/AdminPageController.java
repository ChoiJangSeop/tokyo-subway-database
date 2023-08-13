package com.jangseop.tokyosubwaydatabase.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class AdminPageController {

    @RequestMapping("/page/admin")
    public String admin() {

        log.info("enter admin page");
        return "admin";
    }

}
