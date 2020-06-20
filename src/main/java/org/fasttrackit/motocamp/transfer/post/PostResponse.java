package org.fasttrackit.motocamp.transfer.post;


import java.time.LocalDate;

public class PostResponse {
    private Long id;
    private LocalDate date;
    private String title;
    private String content;
    private String imageUrl;
    private String nameFromUser;
    private String photoUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getNameFromUser() {
        return nameFromUser;
    }

    public void setNameFromUser(String nameFromUser) {
        this.nameFromUser = nameFromUser;
    }

    public String getPhotoUser() {
        return photoUser;
    }

    public void setPhotoUser(String photoUser) {
        this.photoUser = photoUser;
    }
}
