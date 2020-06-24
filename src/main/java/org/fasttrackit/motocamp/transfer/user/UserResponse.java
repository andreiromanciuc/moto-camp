package org.fasttrackit.motocamp.transfer.user;


public class UserResponse {
    private long id;
    private String username;
    private String email;
    private String imageUrl;
    private String motorUsername;
    private String motorPhoto;
    private String motorModel;

    public String getMotorUsername() {
        return motorUsername;
    }

    public void setMotorUsername(String motorUsername) {
        this.motorUsername = motorUsername;
    }

    public String getMotorPhoto() {
        return motorPhoto;
    }

    public void setMotorPhoto(String motorPhoto) {
        this.motorPhoto = motorPhoto;
    }

    public String getMotorModel() {
        return motorModel;
    }

    public void setMotorModel(String motorModel) {
        this.motorModel = motorModel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
