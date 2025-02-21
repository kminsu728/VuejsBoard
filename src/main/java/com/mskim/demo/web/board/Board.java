package com.mskim.demo.web.board;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Board {
    @Id
    String id;

    String type;

    String name;
}
