package com.mskim.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JspController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/board_1")
    public String board() {
        return "board_1";
    }
    @RequestMapping("/board_2")
    public String board2() {
        return "board_2";
    }
}
