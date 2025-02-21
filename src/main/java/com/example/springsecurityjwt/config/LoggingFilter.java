package com.example.springsecurityjwt.config;

import com.example.springsecurityjwt.entity.Log;
import com.example.springsecurityjwt.repository.LogRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Enumeration;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    LogRepository logRepository;
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    public LoggingFilter(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        logger.info("Request URI: {}", request.getRequestURI());
        logger.info("Request Method: {}", request.getMethod());
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logger.info("Request Header: {} = {}", headerName, request.getHeader(headerName));
        }
        Log log = logRepository.save(new Log(LocalDateTime.now(),request.getRequestURI(),request.getMethod(),
                null, HttpStatus.FORBIDDEN.toString()));
        filterChain.doFilter(request,response);
        logger.info("Response Status: {}", response.getStatus());
        log.setResponseStatus(String.valueOf(response.getStatus()));
        logRepository.save(log);
    }
}
