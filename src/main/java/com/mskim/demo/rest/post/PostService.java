package com.mskim.demo.rest.post;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    private static final int PAGE_SIZE = 10;
    PostRepository postRepository;

    @Cacheable(value = "post", key = "#id")
    public Post getPost(String id) {
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()) return post.get();
        return null;
    }

    public List<Post> getPostList(String type, int page) {
        PageRequest pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "date"));
        Page<Post> postPage = postRepository.findByType(type, pageable);
        return postPage.getContent();
    }

    public List<Post> getSearchPostList(String type, String title, int page) {
        PageRequest pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "date"));
        Page<Post> postPage = postRepository.findByTypeAndTitleContaining(type, title, pageable);
        return postPage.getContent();
    }

    public int getTotalPostCount(String type) {
        int totalpage = 1;
        int totalcount = postRepository.countByType(type);
        if(totalcount > 0) {
            totalpage += (totalcount - 1) / PAGE_SIZE;
        }
        return totalpage;
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
