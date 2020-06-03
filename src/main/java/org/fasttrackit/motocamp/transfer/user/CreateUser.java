package org.fasttrackit.motocamp.transfer.user;


import javax.validation.constraints.NotNull;

public class CreateUser {

    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String fullName;
    private String imageUrl;


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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "CreateUser{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fulName='" + fullName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
