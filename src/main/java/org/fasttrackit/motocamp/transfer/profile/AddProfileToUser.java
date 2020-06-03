package org.fasttrackit.motocamp.transfer.profile;

public class AddProfileToUser {

    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "AddProfileToUser{" +
                "customerId=" + userId +
                '}';
    }
}
