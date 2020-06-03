package org.fasttrackit.motocamp.transfer.profile;

public class GetProfileRequest {
    private String namePartialName;
    private String partialUserName;

    public String getNamePartialName() {
        return namePartialName;
    }

    public void setNamePartialName(String namePartialName) {
        this.namePartialName = namePartialName;
    }

    public String getPartialUserName() {
        return partialUserName;
    }

    public void setPartialUserName(String partialUserName) {
        this.partialUserName = partialUserName;
    }
}
