package com.mskim.demo.web;

import com.mskim.demo.web.board.BoardService;
import com.mskim.demo.web.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class JspController {

    private final BoardService boardService;
    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        String types = "qna,community";     //추후 db 에서 게시판 타입 조회 하는걸로 수정예정
        String[] typeArray = types.split(",");
        Map<String, List<Post>> postsByType = new HashMap<>();

        for (String type : typeArray) {
            List<Post> posts = boardService.getPostList(type, 1);
            postsByType.put(type, posts);
        }

        request.setAttribute("posts", postsByType);
        return "index";
    }

    @RequestMapping("/b")
    public String board() {
        return "board";
    }
}
