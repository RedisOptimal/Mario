package com.renren.infra.xweb.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/index")
public class IndexController {

    @RequestMapping(value = "")
    public String index() {

        return "dashboard/index";
    }

}
