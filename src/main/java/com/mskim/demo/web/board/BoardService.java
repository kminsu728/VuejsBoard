package com.mskim.demo.web.board;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BoardService {

    private final PostRepository postRepository;

    private static final int PAGE_SIZE = 10;

    public List<Post> getPostList(String type, int page) {
        PageRequest pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "date"));
        Page<Post> postPage = postRepository.findByType(type, pageable);
        return postPage.getContent();
    }

    public Post getPost(String id) {
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()) return post.get();
        return null;
    }

    public void createPost(String type, String title, String author, String content){
        Post newPost = Post.builder()
                .type(type)
                .title(title)
                .author(author)
                .content(content)
                .date(LocalDateTime.now())
                .views(0)
                .pid(null)
                .build();

        postRepository.save(newPost);
    }

    public Post addViews(Post post){
        post.setViews(post.getViews() + 1);
        postRepository.save(post);
        return post;
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }




}
