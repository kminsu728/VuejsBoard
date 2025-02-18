package com.mskim.demo.rest.board;

import com.mskim.demo.base.model.VueJsResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/board")
public class BoardController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test")
    public VueJsResponse removeItem() {

        int a = 0;
        return null;
    }
}
