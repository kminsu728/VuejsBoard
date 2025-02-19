package com.mskim.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JspController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/b")
    public String board() {
        return "board";
    }
}
