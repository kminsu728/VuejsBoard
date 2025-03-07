package com.mskim.demo.rest.post;

import com.mskim.demo.base.model.VueJsResponse;
import com.mskim.demo.rest.message.QueueMessageType;
import com.mskim.demo.rest.message.QueueProducer;
import com.mskim.demo.web.board.BoardService;
import com.mskim.demo.web.post.Post;
import com.mskim.demo.web.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostRestController {

    private final QueueProducer queueProducer;
    private final PostService postService;
    private final BoardService boardService;

    @GetMapping()
    public ResponseEntity<VueJsResponse> get(HttpServletRequest request,
                                             @RequestParam("id") String id) {
        Post post = postService.getPost(id);
        queueProducer.sentToQueue(QueueMessageType.INCREASE_VIEWS, post);
        return VueJsResponse.ok(post);
    }

    @GetMapping("/list")
    public ResponseEntity<VueJsResponse> getList(HttpServletRequest request,
                          @RequestParam("type") String type,
                          @RequestParam(defaultValue = "1") int page) {
        List<Post> posts = boardService.getPostList(type, page);
        int totalpage = boardService.getTotalPostCount(type);

        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("posts", posts);
            put("type", type);
            put("totalpage", totalpage);
        }});
    }


}
