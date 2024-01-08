package com.example.xpresspay.utils;

import com.example.xpresspay.security.AuthenticationContext;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

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
//                    String role = jwtUtils.extractRoleClaim(jwtToken);

                    log.info("Setting authentication context for email -> {}", email);
                    authenticationContext.setEmail(email);
//                    log.info("Authentication context set for user role -> {}", role);
//                    authenticationContext.setRole(Role.valueOf(role));
                    authenticationContext.setAuthenticate(true);
//                    if (!"ADMIN".equals(role)) {
//                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User is not an admin.");
//                    }
//                    doFilter(request, response);
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
