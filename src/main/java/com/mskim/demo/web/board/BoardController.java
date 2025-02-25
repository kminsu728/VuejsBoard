package com.mskim.demo.web.board;

import com.mskim.demo.web.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String getList(HttpServletRequest request,
                           @RequestParam("type") String type,
                           @RequestParam(defaultValue = "1") int page) {
        List<Post> posts = boardService.getPostList(type, page);
        int totalpage = boardService.getTotalPostCount(type);
        request.setAttribute("posts", posts);
        request.setAttribute("type", type);
        request.setAttribute("totalpage", totalpage);
        return "board";
    }



    @GetMapping("/search")
    public String searchPost(HttpServletRequest request,
                             @RequestParam("type") String type,
                             @RequestParam("searchKeyword") String searchKeyword) {
        List<Post> posts = boardService.getSearchPostList(type, searchKeyword, 1);
        int totalpage = boardService.getTotalPostCount(type);
        request.setAttribute("posts", posts);
        request.setAttribute("type", type);
        request.setAttribute("totalpage", totalpage);
        return "board";
    }

}
