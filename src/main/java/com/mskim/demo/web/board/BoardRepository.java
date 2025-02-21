package com.mskim.demo.web.board;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardRepository extends MongoRepository<Board, String> {
    Board findByType(String type);
}
