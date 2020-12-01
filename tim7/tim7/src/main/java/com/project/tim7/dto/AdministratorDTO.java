package com.project.tim7.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AdministratorDTO {

    private int id;

    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp="[a-z]+[a-z0-9._]*[a-z0-9]+@[a-z]*.com")
    private String email;

    @NotBlank
    @Size(min=8)
    private String password;

    private boolean verified;

    public AdministratorDTO() {
    }

    public AdministratorDTO(Integer id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
