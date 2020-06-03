package org.fasttrackit.motocamp.transfer.motorcycle;

import javax.validation.constraints.NotNull;

public class CreateMotorcycle {
    @NotNull
    private String modelMotor;
    private String userName;
    private String imageUrl;
    private long userId;

    public String getModelMotor() {
        return modelMotor;
    }

    public void setModelMotor(String modelMotor) {
        this.modelMotor = modelMotor;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CreateMotorcycle{" +
                "modelMotor='" + modelMotor + '\'' +
                ", userName='" + userName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", userId=" + userId +
                '}';
    }
}
