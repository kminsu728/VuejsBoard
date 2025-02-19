package com.mskim.demo.rest.comment;

import com.mskim.demo.base.model.VueJsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test")
    public ResponseEntity<VueJsResponse> removeItem() {
        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("comment", "123");
            put("comment2", "345");
        }});
    }
}
