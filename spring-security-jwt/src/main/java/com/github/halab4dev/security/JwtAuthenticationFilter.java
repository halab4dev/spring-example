package com.github.halab4dev.security;

import com.github.halab4dev.service.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 *
 * @author halab
 */
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String accessToken = getAccessToken(httpServletRequest);
        if (!StringUtils.isEmpty(accessToken)) {
            Authentication authentication = jwtService.getAuthentication(accessToken);

            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getAccessToken(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader(AUTHORIZATION);
        if (StringUtils.isEmpty(authorization)) {
            return authorization;
        }
        return authorization.replaceFirst(BEARER, "");
    }
}
