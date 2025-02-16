package com.mskim.demo.base.config;

import com.mskim.demo.base.model.VuejsException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Order(1)
@Component
public class ResponseAspect {

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object around(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (VuejsException ve) {
            return generateErrorResponse(ve);
        } catch (Throwable throwable) {
            return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unkown Server Error", throwable.getMessage(), "Z9999");
        }
    }

    private ResponseEntity<Object> generateErrorResponse(VuejsException exceptionType) {
        return generateErrorResponse(exceptionType.getStatus(),
                                    exceptionType.getTitle(),
                                    exceptionType.getDescription(),
                                    exceptionType.getErrorCode());
    }

    private ResponseEntity<Object> generateErrorResponse(HttpStatus httpStatus, String errorTitle, String errorDesc, String errorCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("title", errorTitle);
        errorResponse.put("description", errorDesc);
        errorResponse.put("errcpde", errorCode);
        errorResponse.put("status", httpStatus.value());

        return ResponseEntity.status(httpStatus)
                .headers(headers)
                .body(errorResponse);
    }
}
