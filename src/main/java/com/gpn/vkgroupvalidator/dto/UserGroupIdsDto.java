package com.gpn.vkgroupvalidator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public class UserGroupIdsDto {
    @NotNull(message = "used_id is mandatory parameter")
    @JsonProperty("user_id")
    private Integer userId;

    @NotNull(message = "group_id is mandatory parameter")
    @JsonProperty("group_id")
    private Integer groupId;

    public int getUserId() { return userId; }

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
