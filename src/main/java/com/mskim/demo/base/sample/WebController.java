package com.mskim.demo.base.sample;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/1st")
    public String helloWorld() {
        return "hello";
    }
    
    @RequestMapping("/2nd")
    public String secondSubPage() {
        return "test/list";
    }
}
