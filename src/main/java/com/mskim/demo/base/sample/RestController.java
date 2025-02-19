package com.mskim.demo.base.sample;

import com.mskim.demo.base.model.VuejsException;
import com.mskim.demo.base.model.VuejsExceptionType;
import com.mskim.demo.base.model.VueJsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RestController {

    @Autowired
    DemoService demoService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }

    @RequestMapping(value = "/response", method = RequestMethod.GET)
    public ResponseEntity<VueJsResponse> response3() throws Exception {
        try {
            UserBase user = UserBase.builder()
                    .name("mskim")
                    .email("kminsu728@nate.com")
                    .age(35).build();

            return VueJsResponse.ok(user);
        } catch (VuejsException ve) {
            throw ve;
        }
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ResponseEntity<VueJsResponse> error() throws Exception {
        throw new VuejsException(VuejsExceptionType.invalid_request, "custom error message");
    }

    @RequestMapping(value = "/error/unknown", method = RequestMethod.GET)
    public ResponseEntity<VueJsResponse> errorUnkown() throws Exception {
        throw new ArithmeticException("by zero /");
    }

}