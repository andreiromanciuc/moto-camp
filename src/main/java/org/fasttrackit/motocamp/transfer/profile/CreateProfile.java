package org.fasttrackit.motocamp.transfer.profile;

public class CreateProfile {

    private String fullName;
    private String imageUrl;
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
        return "CreateProfile{" +
                "fullName='" + fullName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", userId=" + userId +
                '}';
    }
}
