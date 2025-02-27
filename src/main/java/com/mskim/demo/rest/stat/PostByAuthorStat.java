package com.mskim.demo.rest.stat;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PostByAuthorStat {
    private String  _id;
    private long count;
    private Instant time;
}
