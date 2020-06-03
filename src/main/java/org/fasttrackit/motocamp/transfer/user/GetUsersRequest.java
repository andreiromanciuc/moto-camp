package org.fasttrackit.motocamp.transfer.user;

public class GetUsersRequest {
    private String userPartialName;
    private String emailPartialName;
    private String namePartialName;

    public String getUserPartialName() {
        return userPartialName;
    }

    public void setUserPartialName(String userPartialName) {
        this.userPartialName = userPartialName;
    }

    public String getEmailPartialName() {
        return emailPartialName;
    }

    public void setEmailPartialName(String emailPartialName) {
        this.emailPartialName = emailPartialName;
    }

    public String getNamePartialName() {
        return namePartialName;
    }

    public void setNamePartialName(String namePartialName) {
        this.namePartialName = namePartialName;
    }


}
