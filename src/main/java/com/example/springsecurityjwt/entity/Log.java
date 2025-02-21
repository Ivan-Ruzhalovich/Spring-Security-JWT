package com.example.springsecurityjwt.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    private String requestURI;
    private String requestMethod;
    private String requestHeader;
    private String responseStatus;

    public Log() {
    }

    public Log(LocalDateTime dateTime, String requestURI, String requestMethod) {
        this.dateTime = dateTime;
        this.requestURI = requestURI;
        this.requestMethod = requestMethod;
    }

    public Log(Long id, LocalDateTime dateTime, String requestURI, String requestMethod, String requestHeader, String responseStatus) {
        this.id = id;
        this.dateTime = dateTime;
        this.requestURI = requestURI;
        this.requestMethod = requestMethod;
        this.requestHeader = requestHeader;
        this.responseStatus = responseStatus;
    }

    public Log(LocalDateTime dateTime, String requestURI, String requestMethod, String requestHeader, String responseStatus) {
        this.dateTime = dateTime;
        this.requestURI = requestURI;
        this.requestMethod = requestMethod;
        this.requestHeader = requestHeader;
        this.responseStatus = responseStatus;
    }

    public Log(LocalDateTime dateTime, String requestURI, String requestMethod, String requestHeader) {
        this.dateTime = dateTime;
        this.requestURI = requestURI;
        this.requestMethod = requestMethod;
        this.requestHeader = requestHeader;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }
}
