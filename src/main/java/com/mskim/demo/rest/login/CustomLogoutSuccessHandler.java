package com.mskim.demo.rest.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mskim.demo.base.model.VueJsResponse;
import com.mskim.demo.base.model.VuejsException;
import com.mskim.demo.base.model.VuejsExceptionType;
import com.opennaru.khan.session.listener.SessionLoginManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        ResponseEntity<VueJsResponse> responseEntity;

        if (authentication == null) {
            responseEntity = VueJsResponse.ok(new HashMap<String, Object>(){{
                put("username", null);
            }});
        } else {
            Object principal = authentication.getPrincipal();
            UserDetails userDetails = (UserDetails) principal;

            responseEntity = VueJsResponse.ok(new HashMap<String, Object>(){{
                put("username", userDetails.getUsername());
            }});
        }

        VueJsResponse vueJsResponse = responseEntity.getBody();
        int status = responseEntity.getStatusCodeValue();

        String jsonResponse = new ObjectMapper().writeValueAsString(vueJsResponse);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonResponse);
        response.setStatus(status);

        try {
            clusterLogout(request);
        } catch (Exception e) {
            throw new VuejsException(VuejsExceptionType.logout_success_handle_fail);
        }
    }

    public void clusterLogout(HttpServletRequest request) throws Exception {
        SessionLoginManager.getInstance().logout(request);
    }
}
