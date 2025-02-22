package com.example.springsecurityjwt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Data
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    private String requestURI;
    private String requestMethod;
    private String requestHeader;
    private String responseStatus;


    public Log(LocalDateTime dateTime, String requestURI, String requestMethod, String requestHeader, String responseStatus) {
        this.dateTime = dateTime;
        this.requestURI = requestURI;
        this.requestMethod = requestMethod;
        this.requestHeader = requestHeader;
        this.responseStatus = responseStatus;
    }
}
