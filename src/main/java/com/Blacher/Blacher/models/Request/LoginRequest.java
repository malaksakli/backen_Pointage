package com.Blacher.Blacher.models.Request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    @Column(nullable = false)
    public void setPassword(String password) {
        this.password = password;
    }

}
