package com.mskim.demo.web.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findByType(String type, Pageable pageable);

    Page<Post> findByTypeAndPid(String type, String pid, Pageable pageable);

    void deleteByPid(String pid);

    int countByType(String type);

    Page<Post> findByTypeAndTitleContaining(String type, String title, Pageable pageable);
}
