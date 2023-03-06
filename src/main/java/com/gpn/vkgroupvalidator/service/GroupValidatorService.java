package com.gpn.vkgroupvalidator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gpn.vkgroupvalidator.dto.UserGroupIdsDto;
import com.gpn.vkgroupvalidator.dto.UserInfoDto;
import com.gpn.vkgroupvalidator.tools.vkMethodException;
import com.gpn.vkgroupvalidator.tools.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GroupValidatorService {
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public GroupValidatorService(RestTemplate restTemplate, ObjectMapper objectMapper){
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public UserInfoDto checkGroupForUser(String vkServiceToken, UserGroupIdsDto userGroupIdsDto) throws JsonProcessingException, UserNotFoundException, vkMethodException {
        final String url = "https://api.vk.com/method/users.get?user_ids=" + userGroupIdsDto.getUserId() +
                "&fields=nickname&access_token=" + vkServiceToken + "&v=5.131";

        String respond = restTemplate.getForObject(url, String.class);
        ObjectNode node = objectMapper.readValue(respond, ObjectNode.class);
        if (node.get("error") != null){
            throw new vkMethodException(node.get("error").get("error_msg").toString());
        }
        if (node.get("response").size() == 0){
            throw new UserNotFoundException();
        }

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setFirstName(node.get("response").get(0).get("first_name").toString());
        userInfoDto.setLastName(node.get("response").get(0).get("last_name").toString());
        userInfoDto.setMiddleName(node.get("response").get(0).get("nickname").toString());
        userInfoDto.setMember(isMember(vkServiceToken, userGroupIdsDto.getUserId(), userGroupIdsDto.getGroupId()));

        return userInfoDto;
    }

    public boolean isMember(String vkServiceToken, int userId, int groupId) throws JsonProcessingException, vkMethodException {
        final String url = "https://api.vk.com/method/groups.isMember?user_id=" + userId +
                "&group_id=" + groupId + "&access_token=" + vkServiceToken + "&v=5.131";

        String respond = restTemplate.getForObject(url, String.class);
        ObjectNode node = objectMapper.readValue(respond, ObjectNode.class);
        if (node.get("error") != null){
            throw new vkMethodException(node.get("error").get("error_msg").toString());
        }

        return node.get("response").intValue() == 1;
    }
}