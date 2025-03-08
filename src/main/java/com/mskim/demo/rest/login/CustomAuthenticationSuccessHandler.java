package com.mskim.demo.rest.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mskim.demo.base.model.VueJsResponse;
import com.mskim.demo.base.model.VuejsException;
import com.mskim.demo.base.model.VuejsExceptionType;
//import com.opennaru.khan.session.listener.LoginUser;
//import com.opennaru.khan.session.listener.SessionLoginManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        try {
            ResponseEntity<VueJsResponse> responseEntity = VueJsResponse.ok(new HashMap<String, Object>(){{
                put("username", userDetails.getUsername());
            }});

            VueJsResponse vueJsResponse = responseEntity.getBody();
            int status = responseEntity.getStatusCodeValue();

            // JSON 문자열로 변환
            String jsonResponse = new ObjectMapper().writeValueAsString(vueJsResponse);

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(jsonResponse);
            response.setStatus(status);

            request.getSession().setAttribute("loginSession", request.getSession().getId());

            clusterLogin(request, userDetails.getUsername());
        } catch (Exception e) {
            throw new VuejsException(VuejsExceptionType.login_success_handle_fail);
        }
    }

    public boolean clusterLogin(HttpServletRequest request, String userId) throws Exception {
        //SessionLoginManager.getInstance().login(request, userId);
//        List<LoginUser> duplicatedUsers = SessionLoginManager.getInstance().login(request, userId);
//        // 동일한 아이디로 로그인된 기록이 없으면 현재 자신 정보로 size는 1
//        if (duplicatedUsers != null && duplicatedUsers.size() > 1) {
//            for (LoginUser duplicatedUser : duplicatedUsers) {
//                log.info("===== sessionId(): {}, clientIp: {}, 로그인 시간: {}, 본인 세션 여부: {}", duplicatedUser.getSessionId(), duplicatedUser.getClientIp(), duplicatedUser.getCreationTime(), duplicatedUser.isThisSession());
//
//                // (!!!주의!!!) 필요에 따라 다른 사용자 로그아웃(별도의 Controller API로 만드는 경우 인증이 필수)
//                if (!duplicatedUser.isThisSession()) {
//                    SessionLoginManager.getInstance().logout(duplicatedUser.getLoginId(), duplicatedUser.getSessionId(), duplicatedUser.getType());
//                    return false;
//                }
//            }
//        }
        return true;
    }

}
