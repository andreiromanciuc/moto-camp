package org.fasttrackit.motocamp.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate date;
    private String title;
    private String content;
    private String imageUrl;
    private String nameFromUser;
    private String photoUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getNameFromUser() {
        return nameFromUser;
    }

    public void setNameFromUser(String nameFromUser) {
        this.nameFromUser = nameFromUser;
    }

    public String getPhotoUser() {
        return photoUser;
    }

    public void setPhotoUser(String imageUser) {
        this.photoUser = imageUser;
    }
}
