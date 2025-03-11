package com.mskim.demo.rest.comment;

import com.mskim.demo.base.model.VueJsResponse;
import com.mskim.demo.rest.message.QueueMessageType;
import com.mskim.demo.rest.message.QueueProducer;
import com.mskim.demo.rest.websocket.WebSocketService;
import com.mskim.demo.rest.post.Post;
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

    private final QueueProducer queueProducer;
    private final CommentService commentService;
    private final WebSocketService webSocketService;

    @PostMapping("/add")
    public ResponseEntity<VueJsResponse> add(HttpServletRequest request,
                                        @RequestParam("id") String pid,
                                        @RequestParam("author") String author,
                                        @RequestParam("content") String content) {

        queueProducer.sentToQueue(QueueMessageType.ADD_COMMENT,
                Post.builder()
                        .pid(pid)
                        .author(author)
                        .content(content).build());
        //commentService.addComment(id, author, content);

        webSocketService.websocketNewComment(pid, "새 댓글이 달렸습니다: " + content);

        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("id", pid);
        }});
    }

    @GetMapping("/list")
    public ResponseEntity<VueJsResponse> getList(HttpServletRequest request,
                                                     @RequestParam("id") String id,
                                                     @RequestParam(value="page", defaultValue = "1") int page) {
        List<Post> comments = commentService.getCommentList(id, page);
        request.setAttribute("comments", comments);
        return VueJsResponse.ok(comments);
    }

    @PostMapping("/delete")
    public ResponseEntity<VueJsResponse> delete(HttpServletRequest request,
                                                       @RequestParam("id") String id,
                                                       @RequestParam("author") String author) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if(isAdmin || author.equals(authentication.getName())) {
            queueProducer.sentToQueue(QueueMessageType.DELETE_COMMENT,
                    Post.builder()
                            .id(id).build());
            //commentService.deleteComment(id);
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
