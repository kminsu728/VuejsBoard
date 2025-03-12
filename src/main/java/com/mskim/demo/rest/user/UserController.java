package com.mskim.demo.rest.user;

import com.mskim.demo.base.model.VueJsResponse;
import com.mskim.demo.base.model.VuejsException;
import com.mskim.demo.base.model.VuejsExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public static final String AUTH_LOGIN_KEY = "___USER___";
    public static final String AUTH_ROLE_KEY = "___ROLE___";

    @GetMapping("/info")
    public ResponseEntity<VueJsResponse> getUser(HttpServletRequest request,
                                                    @RequestParam("username") String username) {
        if(username == null || username.isEmpty()) {
            return VueJsResponse.ok(null);
        }

        User user = userService.getUser(username);

        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("userId", user.getUserId());
            put("name", user.getName());
            put("email", user.getEmail());
            put("role", user.getRoles());
        }});
    }

    @PostMapping("/info/update")
    public ResponseEntity<VueJsResponse> infoUpdate(HttpServletRequest request,
                                                    @RequestParam("username") String username,
                                                    @RequestParam("name") String name,
                                                    @RequestParam("email") String email) {

        User user = userService.updateUser(username, name, email);

        return VueJsResponse.ok(new HashMap<String, Object>(){{
            put("userId", user.getUserId());
            put("name", user.getName());
            put("email", user.getEmail());
        }});
    }


    @GetMapping("/login/deprecated")
    public ResponseEntity<VueJsResponse> login(HttpServletRequest request, @RequestParam String userId, @RequestParam String password) {
        User user = userService.authenticate(userId, password);
        try{
            if (user != null) {
                request.getSession().setAttribute(AUTH_LOGIN_KEY, user.getUserId());
                request.getSession().setAttribute(AUTH_ROLE_KEY, user.getRoles());
                return VueJsResponse.ok(new HashMap<String, Object>(){{
                    put("userId", user.getUserId());
                    put("role", user.getRoles());
                }});
            } else {
                throw new VuejsException(VuejsExceptionType.login_fail);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/logout/deprecated")
    public ResponseEntity<VueJsResponse> logout(HttpServletRequest request) {
        try {
            request.getSession().invalidate();
            return VueJsResponse.ok("logout Success");
        } catch (Exception e) {
            throw new VuejsException(VuejsExceptionType.logout_fail);
        }
    }

}
