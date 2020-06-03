package org.fasttrackit.motocamp.transfer.motorcycle;

public class AddMotorToUser {
    private long userId;
    private long motorId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMotorId() {
        return motorId;
    }

    public void setMotorId(long motorId) {
        this.motorId = motorId;
    }

    @Override
    public String toString() {
        return "AddMotorToUser{" +
                "userId=" + userId +
                ", motorId=" + motorId +
                '}';
    }
}
