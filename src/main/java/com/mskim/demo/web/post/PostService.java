package com.mskim.demo.web.post;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    PostRepository postRepository;

    @Cacheable(value = "post", key = "#id")
    public Post getPost(String id) {
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()) return post.get();
        return null;
    }

    public Post increaseViews(Post post){
        post.setViews(post.getViews() + 1);
        postRepository.save(post);
        return post;
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
                .comments(0)
                .build();

        postRepository.save(newPost);
    }

    @CacheEvict(value = "post", key = "#id")
    public void deletePost(String id) {
        postRepository.deleteById(id);
        postRepository.deleteByPid(id);
    }

    @CacheEvict(value = "post", key = "#id")
    public void updatePost(String type, String id, String title, String content){
        Optional<Post> post = postRepository.findById(id);

        if(post.isPresent()) {
            Post newPost = post.get();
            newPost.setTitle(title);
            newPost.setContent(content);

            postRepository.save(newPost);
        }
    }
}
