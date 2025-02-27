package com.mskim.demo.rest.stat;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PostByTypeStat {
    private String _id;
    private String name;
    private long count;
    private Instant time;
}
