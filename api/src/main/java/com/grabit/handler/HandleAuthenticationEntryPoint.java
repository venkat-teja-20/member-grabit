package com.grabit.handler;

import com.grabit.Utilities.Utility;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class HandleAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(401);
        response.setContentType("application/json");
        if("INVALID_SIGNATURE".equals(authException.getMessage()))
            response.getWriter().write(Utility.toJson(Map.of("code","INVALID_SIGNATURE","message","Error decoding signature")));
        else if("AUTHENTICATION_EXPIRED".equals(authException.getMessage()))
            response.getWriter().write(Utility.toJson(Map.of("code","AUTHENTICATION_EXPIRED","message","Authentication token was expired")));
        else
            response.getWriter().write(Utility.toJson(Map.of("code","INVALID_TOKEN","message","Error while deocding the token")));
    }
}
