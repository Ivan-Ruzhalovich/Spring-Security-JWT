package com.example.springsecurityjwt.models;

import com.example.springsecurityjwt.entity.OurUsers;
import com.example.springsecurityjwt.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestModel {
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String email;
    private String role;
    private String password;
    private List<Product> products;
    private OurUsers ourUsers;

    public RequestModel() {
    }

    public RequestModel(int statusCode, String error, String message, String token,
                        String refreshToken, String expirationTime, String name, String email,
                        String role, String password, List<Product> products, OurUsers ourUsers) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
        this.token = token;
        this.refreshToken = refreshToken;
        this.expirationTime = expirationTime;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
        this.products = products;
        this.ourUsers = ourUsers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public OurUsers getOurUsers() {
        return ourUsers;
    }

    public void setOurUsers(OurUsers ourUsers) {
        this.ourUsers = ourUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestModel that = (RequestModel) o;
        return statusCode == that.statusCode && Objects.equals(error, that.error) && Objects.equals(message, that.message) && Objects.equals(token, that.token) && Objects.equals(refreshToken, that.refreshToken) && Objects.equals(expirationTime, that.expirationTime) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(role, that.role) && Objects.equals(password, that.password) && Objects.equals(products, that.products) && Objects.equals(ourUsers, that.ourUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, error, message, token, refreshToken, expirationTime, name, email, role, password, products, ourUsers);
    }

    @Override
    public String toString() {
        return "RequestModel{" +
                "statusCode=" + statusCode +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expirationTime='" + expirationTime + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                ", products=" + products +
                ", ourUsers=" + ourUsers +
                '}';
    }
}
