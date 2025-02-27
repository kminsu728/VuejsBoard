package com.mskim.demo.rest.stat;

import com.mskim.demo.base.model.VueJsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stat")
public class StatController {

    private final StatService statService;

    @GetMapping("/post-by-type")
    public ResponseEntity<VueJsResponse> getPostByTypeStats() {
        List<PostByTypeStat> stats = statService.getPostByTypeStat();
        return VueJsResponse.ok(stats);
    }

    @GetMapping("/post-by-author")
    public ResponseEntity<VueJsResponse> getPostByAuthorStats() {
        List<PostByAuthorStat> stats = statService.getPostByAuthorStat();
        return VueJsResponse.ok(stats);
    }

}
