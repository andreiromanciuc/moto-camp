package org.fasttrackit.motocamp.domain;

import javax.persistence.*;

@Entity
public class Profile {

    @Id
    private long id;
    private String fullName;
    private String imageUrl;
    private String userName;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
