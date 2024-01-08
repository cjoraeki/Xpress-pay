package com.example.xpresspay.utils;

import com.example.xpresspay.security.AuthenticationContext;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthFilter {

    private final JWTUtils jwtUtils;

    private final AuthenticationContext authenticationContext;

    public int doFilter(HttpServletRequest request, HttpServletResponse response) throws JwtException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String jwtToken = authHeader.substring(7);
                log.info("JWT token is -> {}", jwtToken);

                if (jwtUtils.isTokenExpired(jwtToken)) {
                    String email = jwtUtils.extractUsername(jwtToken);

                    log.info("Setting authentication context for email -> {}", email);
                    authenticationContext.setEmail(email);
                    authenticationContext.setAuthenticate(true);
                    return HttpServletResponse.SC_ACCEPTED;
                }

            } catch (JwtException e) {
                log.error("Error while processing JWT token: {}", e.getMessage());
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                throw new JwtException(e.getMessage());
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return HttpServletResponse.SC_ACCEPTED;
    }
}
