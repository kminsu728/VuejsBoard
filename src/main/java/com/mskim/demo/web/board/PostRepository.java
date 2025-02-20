package com.mskim.demo.web.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findByType(String type, Pageable pageable);

    Page<Post> findByTypeAndPid(String type, String pid, Pageable pageable);
}
