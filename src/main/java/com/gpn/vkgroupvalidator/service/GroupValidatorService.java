package com.gpn.vkgroupvalidator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gpn.vkgroupvalidator.dto.UserGroupIdsDto;
import com.gpn.vkgroupvalidator.dto.UserInfoDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GroupValidatorService {
    public GroupValidatorService(){}

    public UserInfoDto checkGroupForUser(String vkServiceToken, UserGroupIdsDto userGroupIdsDto) throws JsonProcessingException {
        final String url = "https://api.vk.com/method/users.get?user_ids=" + userGroupIdsDto.getUserId() +
                "&fields=nickname&access_token=" + vkServiceToken + "&v=5.131";

        RestTemplate restTemplate = new RestTemplate();
        String respond = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.readValue(respond, ObjectNode.class);

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setFirstName(node.get("response").get(0).get("first_name").toString());
        userInfoDto.setLastName(node.get("response").get(0).get("last_name").toString());
        userInfoDto.setMiddleName(node.get("response").get(0).get("nickname").toString());
        userInfoDto.setMember(isMember(vkServiceToken, userGroupIdsDto.getUserId(), userGroupIdsDto.getGroupId()));

        return userInfoDto;
    }

    private boolean isMember(String vkServiceToken, int userId, int groupId) throws JsonProcessingException {
        final String url = "https://api.vk.com/method/groups.isMember?user_id=" + userId +
                "&group_id=" + groupId + "&access_token=" + vkServiceToken + "&v=5.131";

        RestTemplate restTemplate = new RestTemplate();
        String respond = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.readValue(respond, ObjectNode.class);

        return node.get("response").intValue() == 1;
    }
}