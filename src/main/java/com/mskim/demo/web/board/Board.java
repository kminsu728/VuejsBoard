package com.mskim.demo.web.board;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Data
@Builder
public class Board implements Serializable {
    @Id
    String id;

    String type;

    String name;
}
