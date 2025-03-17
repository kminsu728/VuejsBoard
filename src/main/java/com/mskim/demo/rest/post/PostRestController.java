package com.mskim.demo.rest.post;

import com.mskim.demo.base.model.VueJsResponse;
import com.mskim.demo.base.model.VuejsException;
import com.mskim.demo.base.model.VuejsExceptionType;
import com.mskim.demo.rest.message.QueueMessageType;
import com.mskim.demo.rest.message.QueueProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostRestController {
    private final QueueProducer queueProducer;
    private final PostService postService;

    @GetMapping()
    public ResponseEntity<VueJsResponse> get(HttpServletRequest request,
                      @RequestParam("id") String id) {
        Post post = postService.getPost(id);

        if(post == null) {
            return VueJsResponse.error(new VuejsException(VuejsExceptionType.invalid_post));
        };

        queueProducer.sentToQueue(QueueMessageType.INCREASE_VIEWS, post);

        return VueJsResponse.ok(post);
    }

    @GetMapping("/list")
    public ResponseEntity<VueJsResponse> getListHome(HttpServletRequest request) {
        String types = "qna,community";     //추후 db 에서 게시판 타입 조회 하는걸로 수정예정
        String[] typeArray = types.split(",");
        Map<String, List<Post>> postsByType = new HashMap<>();

        for (String type : typeArray) {
            List<Post> posts = postService.getPostList(type, 1);
            postsByType.put(type, posts);
        }

        return VueJsResponse.ok(postsByType);
    }

    @GetMapping("/list/{type}")
    public ResponseEntity<VueJsResponse> getList(HttpServletRequest request,
                                                 @PathVariable("type") String type,
                                                 @RequestParam("page") int page) {
        Map<String, List<Post>> postsByType = new HashMap<>();

        List<Post> posts = postService.getPostList(type, page);
        int totalpage = postService.getTotalPostCount(type);

        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("posts", posts);
            put("type", type);
            put("totalpage", totalpage);
        }});
    }

    @GetMapping("/search/{type}")
    public ResponseEntity<VueJsResponse> searchPost(HttpServletRequest request,
                             @PathVariable("type") String type,
                             @RequestParam("query") String query,
                             @RequestParam("page") int page) {
        List<Post> posts = postService.getSearchPostList(type, query, page);
        int totalpage = postService.getTotalPostCount(type);

        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("posts", posts);
            put("type", type);
            put("totalpage", totalpage);
        }});
    }

    @GetMapping("/create")
    public ResponseEntity<VueJsResponse> create(HttpServletRequest request,
                         @RequestParam("type") String type) {
        request.setAttribute("type", type);
        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("type", type);
        }});
    }

    @PostMapping("/create")
    public ResponseEntity<VueJsResponse> create(HttpServletRequest request,
                         @RequestBody Post post) {

        queueProducer.sentToQueue(QueueMessageType.CREATE_POST,
                Post.builder()
                        .type(post.getType())
                        .title(post.getTitle())
                        .author(post.getAuthor())
                        .content(post.getContent()).build());

        //return "redirect:/board?type=" + type;
        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("type", post.getType());
        }});
    }

    @PostMapping("/delete")
    public ResponseEntity<VueJsResponse> delete(HttpServletRequest request,
                         @RequestBody Post post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if(isAdmin || post.getAuthor().equals(authentication.getName())) {
            queueProducer.sentToQueue(QueueMessageType.DELETE_POST,
                    Post.builder()
                            .id(post.getId()).build());
            //postService.deletePost(id);
        } else {
            return VueJsResponse.error(new VuejsException(VuejsExceptionType.delete_post_fail));
        }

        //return "redirect:/board?type=" + type;
        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("id", post.getId());
        }});
    }

    @PostMapping("update")
    public ResponseEntity<VueJsResponse> update(HttpServletRequest request,
                         @RequestParam("type") String type,
                         @RequestParam("id") String id,
                         @RequestParam("title") String title,
                         @RequestParam("content") String content) {
        queueProducer.sentToQueue(QueueMessageType.UPDATE_POST,
                Post.builder()
                        .type(type)
                        .id(id)
                        .title(title)
                        .content(content).build());
        //postService.updatePost(type, id, title, content);
        //return "redirect:/board?type=" + type;
        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("type", type);
        }});
    }

}
