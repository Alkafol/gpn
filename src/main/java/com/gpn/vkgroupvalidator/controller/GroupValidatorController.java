package com.gpn.vkgroupvalidator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gpn.vkgroupvalidator.dto.UserGroupIdsDto;
import com.gpn.vkgroupvalidator.dto.UserInfoDto;
import com.gpn.vkgroupvalidator.service.GroupValidatorService;
import org.springframework.web.bind.annotation.*;

@RestController
public class GroupValidatorController {
    private final GroupValidatorService groupValidatorService;

    public GroupValidatorController(GroupValidatorService groupValidatorService){
        this.groupValidatorService = groupValidatorService;
    }

    @GetMapping("/validate_group")
    public UserInfoDto checkGroup(@RequestHeader("vk_service_token") String vkServiceToken, @RequestBody UserGroupIdsDto userGroupIdsDto) throws JsonProcessingException {
        return groupValidatorService.checkGroupForUser(vkServiceToken, userGroupIdsDto);
    }

}
