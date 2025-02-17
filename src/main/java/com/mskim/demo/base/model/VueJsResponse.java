package com.mskim.demo.base.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class VueJsResponse {

    private Integer status;
    @Builder.Default
    private long currentTime = System.currentTimeMillis();
    private Map<String, Object> exception;
    private Object body;

    public static VueJsResponse ok(Object body) {
        return VueJsResponse.builder()
                            .status(HttpStatus.OK.value())
                            .body(body).build();
    }

    public static VueJsResponse error(VuejsException vuejsException) {
        return error(vuejsException.getStatus(), vuejsException.getTitle(), vuejsException.getDescription(), vuejsException.getErrorCode());
    }

    public static VueJsResponse error(HttpStatus httpStatus, String errorTitle, String errorDesc, String errorCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", httpStatus);
        map.put("title", errorTitle);
        map.put("description", errorDesc);
        map.put("errorCode", errorCode);

        return VueJsResponse.builder()
                .status(httpStatus.value())
                .exception(map).build();
    }

}