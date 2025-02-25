package com.mskim.demo.web.post;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@Builder
public class Post {
    @Id
    @Indexed
    private String id;

    private String title;

    private String content;

    private String author;

    private LocalDateTime date;

    private long views;

    private String type;

    private String pid;

    private long comments;

}
