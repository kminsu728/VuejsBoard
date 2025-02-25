package com.mskim.demo.web.post;

import com.mskim.demo.web.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final BoardService boardService;
    private final PostService postService;

    @GetMapping()
    public String get(HttpServletRequest request,
                           @RequestParam("id") String id) {
        Post post = postService.getPost(id);

        if(post == null) {
            request.setAttribute("errorMessage", "잘못된 post 경로입니다.");
            return "error";
        };

        post = postService.addViews(post);
        request.setAttribute("post", post);
        return "post";
    }

    @GetMapping("/create")
    public String create(HttpServletRequest request,
                             @RequestParam("type") String type) {
        request.setAttribute("type", type);
        return "createpost";
    }

    @PostMapping("/create")
    public String create(HttpServletRequest request,
                             @RequestParam("type") String type,
                             @RequestParam("title") String title,
                             @RequestParam("author") String author,
                             @RequestParam("content") String content) {

        postService.createPost(type, title, author, content);
        return "redirect:/board?type=" + type;
    }

    @PostMapping("/delete")
    public String delete(HttpServletRequest request,
                             @RequestParam("type") String type,
                             @RequestParam("id") String id,
                             @RequestParam("author") String author) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if(isAdmin) {
            postService.deletePost(id);
        } else if (author.equals(authentication.getName())){
            postService.deletePost(id);
        } else {
            request.setAttribute("errorMessage", "post 삭제에 실패했습니다.");
            return "error";
        }

        return "redirect:/board?type=" + type;
    }

    @PostMapping("update")
    public String update(HttpServletRequest request,
                             @RequestParam("type") String type,
                             @RequestParam("id") String id,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content) {
        postService.updatePost(type, id, title, content);
        return "redirect:/board?type=" + type;
    }

}
