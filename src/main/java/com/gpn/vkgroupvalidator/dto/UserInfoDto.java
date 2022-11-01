package com.gpn.vkgroupvalidator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;

@JsonPropertyOrder({ "first_name", "last_name", "middle_name", "member"})
public class UserInfoDto {
    @JsonRawValue
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    @JsonRawValue
    private String lastName;

    @JsonProperty("middle_name")
    @JsonRawValue
    private String middleName;

    @JsonProperty("member")
    @JsonRawValue
    private boolean isMember;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }
}
