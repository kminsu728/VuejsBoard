package com.mskim.demo.rest.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mskim.demo.base.model.VueJsResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        VueJsResponse vueJsResponse = null;
        if (authentication == null) {
            vueJsResponse = VueJsResponse.ok(new HashMap<String, Object>(){{
                put("userId", null);
            }});
        } else {
            Object principal = authentication.getPrincipal();
            UserDetails userDetails = (UserDetails) principal;

            vueJsResponse = VueJsResponse.ok(new HashMap<String, Object>(){{
                put("userId", userDetails.getUsername());
            }});
        }

        String jsonResponse = new ObjectMapper().writeValueAsString(vueJsResponse);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonResponse);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
