package org.fasttrackit.motocamp.transfer.comment;

import java.time.LocalDate;

public class CreateComment {
    private String content;
    private LocalDate date;
    private long profileId;
    private long postId;

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CreateComment{" +
                "content='" + content + '\'' +
                ", date=" + date +
                ", profileId=" + profileId +
                ", postId=" + postId +
                '}';
    }
}
