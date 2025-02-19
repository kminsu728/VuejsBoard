package com.mskim.demo.rest.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@Builder
public class User {
    @Indexed(unique = true)
    private String userId;

    private String name;

    private String password;

    private String email;

    private List<String> roles;

    private Boolean enabled;
}
