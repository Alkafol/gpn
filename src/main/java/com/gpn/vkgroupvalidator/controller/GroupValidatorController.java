package com.gpn.vkgroupvalidator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gpn.vkgroupvalidator.dto.UserGroupIdsDto;
import com.gpn.vkgroupvalidator.dto.UserInfoDto;
import com.gpn.vkgroupvalidator.service.GroupValidatorService;
import com.gpn.vkgroupvalidator.tools.vkMethodException;
import com.gpn.vkgroupvalidator.tools.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class GroupValidatorController {
    private final GroupValidatorService groupValidatorService;

    public GroupValidatorController(GroupValidatorService groupValidatorService){
        this.groupValidatorService = groupValidatorService;
    }

    @GetMapping("/validate_group")
    public UserInfoDto checkGroup(@RequestHeader("vk_service_token") String vkServiceToken, @Valid @RequestBody UserGroupIdsDto userGroupIdsDto){
        try {
            return groupValidatorService.checkGroupForUser(vkServiceToken, userGroupIdsDto);
        }
        catch (UserNotFoundException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User with given ID wasn't found", e);
        }
        catch (vkMethodException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getVkMessage(), e);
        }
        catch (JsonProcessingException e){
            throw new ResponseStatusException(
                    HttpStatus.FAILED_DEPENDENCY, "Unknown error", e);
        }
    }

}
