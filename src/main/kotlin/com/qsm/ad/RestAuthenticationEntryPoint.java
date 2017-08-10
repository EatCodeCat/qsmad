package com.qsm.ad;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(value = "authenticationEntryPoint")
public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public final void commence
            (HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}