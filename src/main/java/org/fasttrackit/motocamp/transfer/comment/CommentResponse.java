package org.fasttrackit.motocamp.transfer.comment;

import java.time.LocalDate;

public class CommentResponse {
    private LocalDate date;
    private String content;

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

    @Override
    public String
    toString() {
        return "CommentResponse{" +
                "date=" + date +
                ", content='" + content + '\'' +
                '}';
    }
}
