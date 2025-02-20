package com.mskim.demo.rest.comment;

import com.mskim.demo.base.model.VueJsResponse;
import com.mskim.demo.web.board.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/addcomment")
    public ResponseEntity<VueJsResponse> addComment(HttpServletRequest request,
                                        @RequestParam("id") String id,
                                        @RequestParam("author") String author,
                                        @RequestParam("content") String content) {
        commentService.addComment(id, author, content);
        return VueJsResponse.ok(null);
    }

    @GetMapping("/list")
    public ResponseEntity<VueJsResponse> getComments(HttpServletRequest request,
                                                     @RequestParam("id") String id,
                                                     @RequestParam(value="page", defaultValue = "1") int page) {
        List<Post> comments = commentService.getComments(id, page);
        return VueJsResponse.ok(comments);
    }

    @PostMapping("/delete")
    public ResponseEntity<VueJsResponse> deleteComment(HttpServletRequest request,
                                                       @RequestParam("id") String id,
                                                       @RequestParam("author") String author) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if(isAdmin) {
            commentService.deleteComment(id);
        } else if (author.equals(authentication.getName())){
            commentService.deleteComment(id);
        } else {
            throw new AccessDeniedException("You do not have permission to delete this comment");
        }

        return VueJsResponse.ok(null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test")
    public ResponseEntity<VueJsResponse> removeItem() {
        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("comment", "123");
            put("comment2", "345");
        }});
    }
}
