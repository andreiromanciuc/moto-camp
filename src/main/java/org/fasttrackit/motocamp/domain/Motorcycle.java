package org.fasttrackit.motocamp.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Motorcycle {

    @Id
    private long id;
    @NotNull
    private String modelMotor;
    private String userName;
    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Motorcycle{" +
                "id=" + id +
                ", modelMotor='" + modelMotor + '\'' +
                ", userName='" + userName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
