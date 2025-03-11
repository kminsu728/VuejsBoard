package com.mskim.demo.rest.stat;

import com.mskim.demo.rest.post.Post;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatRepository extends MongoRepository<Post, String> {

    // 최근 1시간 동안 type별 게시글 수 통계
    @Aggregation(pipeline = {
            "{ $match: { date: { $gte: ?0, $lt: ?1 } } }",
            "{ $group: { _id: '$type', count: { $sum: 1 } } }"
    })
    List<PostByTypeStat> countPostsByType(LocalDateTime startTime, LocalDateTime endTime);

    // 최근 1시간 동안 author별 게시글 수 통계
    @Aggregation(pipeline = {
            "{ $match: { date: { $gte: ?0, $lt: ?1 } } }",
            "{ $group: { _id: '$author', count: { $sum: 1 } } }"
    })
    List<PostByAuthorStat> countPostsByAuthor(LocalDateTime startTime, LocalDateTime endTime);
}
