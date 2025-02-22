package com.mskim.demo.rest.comment;

import com.mskim.demo.rest.user.User;
import com.mskim.demo.web.board.Post;
import com.mskim.demo.web.board.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;

    private static final int PAGE_SIZE = 10;
    private static final String TYPE = "comment";

    public void addComment(String id, String author, String content) {
        Post newPost = Post.builder()
                .type("comment")
                .title(null)
                .author(author)
                .content(content)
                .date(LocalDateTime.now())
                .views(0)
                .pid(id)
                .comments(0)
                .build();

        postRepository.save(newPost);

        Optional<Post> post = postRepository.findById(id);
        post.ifPresent(pp -> {
            pp.setComments(pp.getComments() + 1);
            postRepository.save(pp);
        });
    }

    public List<Post> getComments(String id, int page) {
        PageRequest pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "date"));
        Page<Post> postPage = postRepository.findByTypeAndPid(TYPE, id, pageable);
        return postPage.getContent();
    }

    public void deleteComment(String id) {
        Optional<Post> post = postRepository.findById(id);

        post.ifPresent(p -> {
            String pid = p.getPid();
            if (pid != null) {postRepository.findById(pid).ifPresent(pp -> {
                pp.setComments(pp.getComments() - 1);
                postRepository.save(pp);
            });}
        });

        postRepository.deleteById(id);
    }

}
