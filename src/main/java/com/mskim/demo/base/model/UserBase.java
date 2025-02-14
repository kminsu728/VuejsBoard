package com.mskim.demo.base.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserBase {
    private String name;
    private String email;
    private int age;
}
