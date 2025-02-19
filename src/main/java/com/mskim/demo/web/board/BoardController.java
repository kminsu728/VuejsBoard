package com.mskim.demo.web.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String getBoard(@RequestParam("type") String boardType, HttpServletRequest request) {
        List<Post> qnaPosts = boardService.getPost(boardType, 1);
        request.setAttribute("posts", qnaPosts);
        request.setAttribute("type", boardType);
        return "board";
    }

}
