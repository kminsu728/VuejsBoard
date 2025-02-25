package com.mskim.demo.web.board;

import com.mskim.demo.web.post.Post;
import com.mskim.demo.web.post.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
