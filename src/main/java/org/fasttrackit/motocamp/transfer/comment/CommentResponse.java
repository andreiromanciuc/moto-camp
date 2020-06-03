package org.fasttrackit.motocamp.transfer.comment;

import java.time.LocalDate;

public class CommentResponse {
    private LocalDate date;
    private String content;
    private String username;
    private String imageUrl;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String
    toString() {
        return "CommentResponse{" +
                "date=" + date +
                ", content='" + content + '\'' +
                '}';
    }
}
