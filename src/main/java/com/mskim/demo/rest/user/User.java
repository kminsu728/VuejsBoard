package com.mskim.demo.rest.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class User {
    @Indexed(unique = true)
    private String userId;

    private String name;

    private String password;

    private String email;

    private String role;

}
