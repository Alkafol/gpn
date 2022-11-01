package com.gpn.vkgroupvalidator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserGroupIdsDto {
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("group_id")
    private int groupId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
