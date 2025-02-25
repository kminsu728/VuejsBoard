package com.mskim.demo.web.post;

import com.mskim.demo.rest.message.QueueMessageType;
import com.mskim.demo.rest.message.QueueProducer;
import lombok.RequiredArgsConstructor;
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

    private final QueueProducer queueProducer;
    private final PostService postService;

    @GetMapping()
    public String get(HttpServletRequest request,
                           @RequestParam("id") String id) {
        Post post = postService.getPost(id);

        if(post == null) {
            request.setAttribute("errorMessage", "잘못된 post 경로입니다.");
            return "error";
        };

        queueProducer.sentToQueue(QueueMessageType.INCREASE_VIEWS, post);
        //post = postService.increaseViews(post);
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

        queueProducer.sentToQueue(QueueMessageType.CREATE_POST,
                Post.builder()
                        .type(type)
                        .title(title)
                        .author(author)
                        .content(content).build());

        //postService.createPost(type, title, author, content);
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

        if(isAdmin || author.equals(authentication.getName())) {
            queueProducer.sentToQueue(QueueMessageType.DELETE_POST,
                    Post.builder()
                            .id(id).build());
            //postService.deletePost(id);
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
        queueProducer.sentToQueue(QueueMessageType.UPDATE_POST,
                Post.builder()
                        .type(type)
                        .id(id)
                        .title(title)
                        .content(content).build());
        //postService.updatePost(type, id, title, content);
        return "redirect:/board?type=" + type;
    }

}
