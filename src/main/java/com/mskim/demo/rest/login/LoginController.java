package com.mskim.demo.rest.login;

import com.mskim.demo.base.model.VueJsResponse;
import com.mskim.demo.base.model.VuejsException;
import com.mskim.demo.base.model.VuejsExceptionType;
import com.mskim.demo.rest.user.User;
import com.mskim.demo.rest.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<VueJsResponse> getLoginUser(HttpServletRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            // 인증 정보가 없거나, 익명 사용자일 경우 기본값 반환
            if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
                return VueJsResponse.ok(new HashMap<String, Object>() {{
                    put("username", null);
                    put("nickname", null);
                    put("role", "");
                }});
            }

            String username = auth.getName();
            User user = userService.getUser(username);

            return VueJsResponse.ok(new HashMap<String, Object>() {{
                put("username", user.getUserId());
                put("nickname", user.getName());
                put("role", user.getRoles());
            }});
        } catch (Exception e) {
            // 예외 발생 시에도 기본값 반환 (로그인 안 해도 화면은 보여야 하므로)
            return VueJsResponse.ok(new HashMap<String, Object>() {{
                put("username", null);
                put("nickname", null);
                put("role", "");
            }});
        }
    }
}
