package com.mskim.demo.rest.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mskim.demo.base.model.VueJsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        VueJsResponse vueJsResponse = VueJsResponse.ok(new HashMap<String, Object>(){{
            put("userId", userDetails.getUsername());
        }});

        String jsonResponse = new ObjectMapper().writeValueAsString(vueJsResponse);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonResponse);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
