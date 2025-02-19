package com.mskim.demo.web.board;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Document
@Data
@Builder
public class Post {
    @Indexed(unique = true)
    private long id;

    private String title;

    private String content;

    private String author;

    private Timestamp date;

    private String views;

    private String type;

}
