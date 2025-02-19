package com.mskim.demo.web.board;

import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class BoardService {

    private final PostRepository postRepository;

    public List<Post> getPost(String boardType, int page) {
        List<Post> posts = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            posts.add(new Post(i, "post" + i, "this is post", "test", new Timestamp(System.currentTimeMillis()), "100","qna"));
        }

        return posts;
    }



}
