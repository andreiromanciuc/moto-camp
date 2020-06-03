package org.fasttrackit.motocamp.transfer.post;

public class UpdatePost {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "UpdatePost{" +
                "content='" + content + '\'' +
                '}';
    }
}
