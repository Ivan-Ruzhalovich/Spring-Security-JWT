package com.example.springsecurityjwt.config;

import com.example.springsecurityjwt.service.JWTUtilsImpl;
import com.example.springsecurityjwt.service.OurUsersDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTUtilsImpl jwtUtils;
    private final OurUsersDetailsServiceImpl service;

    public JWTAuthFilter(JWTUtilsImpl jwtUtils, OurUsersDetailsServiceImpl service) {
        this.jwtUtils = jwtUtils;
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader==null || authHeader.isBlank()){
            filterChain.doFilter(request,response);
            return;
        }
        String token = authHeader.substring(7);
        final String userEmail = jwtUtils.extractUserName(token);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = service.loadUserByUsername(userEmail);
            if (jwtUtils.isTokenValid(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,
                                userDetails.getAuthorities());
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(authToken);
                SecurityContextHolder.setContext(securityContext);
            }
            else throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT,"I_AM_A_TEAPOT");
        }
        filterChain.doFilter(request,response);
    }
}
