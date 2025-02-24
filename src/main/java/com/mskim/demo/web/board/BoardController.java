package com.mskim.demo.web.board;

import com.mskim.demo.base.model.VuejsException;
import com.mskim.demo.base.model.VuejsExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
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
        List<Post> posts = boardService.getPostList(type, page);
        int totalpage = boardService.getTotalPostCount(type);
        request.setAttribute("posts", posts);
        request.setAttribute("type", type);
        request.setAttribute("totalpage", totalpage);
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

    @CacheEvict(value = "postList", key = "#id")
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
            request.setAttribute("errorMessage", "post 삭제에 실패했습니다.");
            return "error";
        }

        return "redirect:/board?type=" + type;
    }

    @CacheEvict(value = "postList", key = "#id")
    @PostMapping("updatepost")
    public String updatePost(HttpServletRequest request,
                             @RequestParam("type") String type,
                             @RequestParam("id") String id,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content) {
        boardService.updatePost(type, id, title, content);
        return "redirect:/board?type=" + type;
    }

    @Cacheable(value = "postList", key = "#id")
    @GetMapping("/post")
    public String viewPost(HttpServletRequest request,
                           @RequestParam("id") String id) {
        Post post = boardService.getPost(id);

        if(post == null) {
            request.setAttribute("errorMessage", "잘못된 post 경로입니다.");
            return "error";
        };

        post = boardService.addViews(post);
        request.setAttribute("post", post);
        return "post";
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
