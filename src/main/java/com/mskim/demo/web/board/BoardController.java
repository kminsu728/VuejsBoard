package com.mskim.demo.web.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String getBoard(HttpServletRequest request,
                           @RequestParam("type") String type,
                           @RequestParam(defaultValue = "1") int page) {
        List<Post> qnaPosts = boardService.getPost(type, page);
        request.setAttribute("posts", qnaPosts);
        request.setAttribute("type", type);
        return "board";
    }

    @GetMapping("/createpost")
    public String createPost(HttpServletRequest request,
                             @RequestParam("type") String type) {
        request.setAttribute("type", type);
        return "createpost";
    }

    @PostMapping("/createpost")
    public String createPost(HttpServletRequest request,
                             @RequestParam("type") String type,
                             @RequestParam("title") String title,
                             @RequestParam("author") String author,
                             @RequestParam("content") String content) {

        boardService.createPost(type, title, author, content);
        return "redirect:/board?type=" + type;
    }

}
