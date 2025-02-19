package com.mskim.demo.rest.board;

import com.mskim.demo.base.model.VueJsResponse;
import com.mskim.demo.base.model.VuejsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test")
    public ResponseEntity<VueJsResponse> removeItem() {
        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("board", "123");
            put("board2", "345");
        }});
    }
}
