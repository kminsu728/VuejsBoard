package com.mskim.demo.rest.board;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardRepository extends MongoRepository<Board, String> {
    Board findByType(String type);
}
