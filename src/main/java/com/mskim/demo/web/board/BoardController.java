package com.mskim.demo.web.board;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String getBoard(HttpServletRequest request,
                           @RequestParam("type") String type,
                           @RequestParam(defaultValue = "1") int page) {
        List<Post> qnaPosts = boardService.getPostList(type, page);
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

    @PostMapping("/deletepost")
    public String deletePost(HttpServletRequest request,
                             @RequestParam("type") String type,
                             @RequestParam("id") String id,
                             @RequestParam("author") String author) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if(isAdmin) {
            boardService.deletePost(id);
        } else if (author.equals(authentication.getName())){
            boardService.deletePost(id);
        } else {
            throw new AccessDeniedException("You do not have permission to delete this post");
        }

        return "redirect:/board?type=" + type;
    }

    @GetMapping("/post")
    public String viewPost(HttpServletRequest request,
                           @RequestParam("id") String id) {
        Post post = boardService.getPost(id);
        request.setAttribute("post", post);
        return "post";
    }

}
