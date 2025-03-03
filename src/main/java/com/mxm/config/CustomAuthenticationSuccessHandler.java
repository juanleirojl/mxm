package com.mxm.config;
import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
                                        Authentication authentication) throws IOException {
        String redirectURL = "/403";

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            if (role.equals("ADMIN") || role.equals("CIMENTO")) {
                redirectURL = "/dashboard";
            } else if (role.equals("LICITACAO_FEIRA")) {
                redirectURL = "/licitacao-feira-dashboard";
            }
        }

        response.sendRedirect(redirectURL);
    }
}
