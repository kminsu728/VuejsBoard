package com.mskim.demo.web.board;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class BoardService {

    private final PostRepository postRepository;

    private static final int PAGE_SIZE = 10;

    public List<Post> getPost(String type, int page) {
        PageRequest pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "date"));
        Page<Post> postPage = postRepository.findByType(type, pageable);
        return postPage.getContent();
    }

    public void createPost(String type, String title, String author, String content){
        Post newPost = Post.builder()
                .type(type)
                .title(title)
                .author(author)
                .content(content)
                .date(new Timestamp(System.currentTimeMillis()))
                .views(0)
                .build();

        postRepository.insert(newPost);
    }




}
