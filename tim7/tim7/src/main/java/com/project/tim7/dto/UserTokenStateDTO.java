package com.project.tim7.dto;

public class UserTokenStateDTO {
    private String accessToken;
    private Long id;

    public UserTokenStateDTO() {
        this.accessToken = null;
        this.id = null;
    }

    public UserTokenStateDTO(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.id = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
