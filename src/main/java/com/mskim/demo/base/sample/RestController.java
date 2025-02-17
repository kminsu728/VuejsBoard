package com.mskim.demo.base.sample;

import com.mskim.demo.base.model.VuejsException;
import com.mskim.demo.base.model.VuejsExceptionType;
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
    public ResponseEntity<UserBase> response() throws Exception {
        try {
            UserBase user = UserBase.builder()
                    .name("mskim")
                    .email("kminsu728@nate.com")
                    .age(35).build();

            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (VuejsException ve) {
            throw ve;
        }
    }

    @RequestMapping(value = "/response2", method = RequestMethod.GET)
    public UserBase response2() throws Exception {
        try {
            UserBase user = UserBase.builder()
                    .name("mskim")
                    .email("kminsu728@nate.com")
                    .age(35).build();

            return user;
        } catch (VuejsException ve) {
            throw ve;
        }
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ResponseEntity<String> error() throws Exception {
        throw new VuejsException(VuejsExceptionType.invalid_request, "custom error message");
    }

    @RequestMapping(value = "/error/unknown", method = RequestMethod.GET)
    public ResponseEntity<String> errorUnkown() throws Exception {
        throw new ArithmeticException("by zero /");
    }

}