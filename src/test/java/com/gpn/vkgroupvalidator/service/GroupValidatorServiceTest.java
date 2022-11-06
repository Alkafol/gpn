package com.gpn.vkgroupvalidator.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gpn.vkgroupvalidator.dto.UserGroupIdsDto;
import com.gpn.vkgroupvalidator.dto.UserInfoDto;
import com.gpn.vkgroupvalidator.tools.UserNotFoundException;
import com.gpn.vkgroupvalidator.tools.vkMethodException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GroupValidatorServiceTest {
    @Mock
    RestTemplate restTemplate;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    GroupValidatorService underTest = new GroupValidatorService();

    @Test
    void isMemberWithErrorTest() throws Exception{
        // given
        String vkServiceToken = "1";
        int groupId = 1;
        int userId = 1;
        final String url = "https://api.vk.com/method/groups.isMember?user_id=" + userId +
                "&group_id=" + groupId + "&access_token=" + vkServiceToken + "&v=5.131";
        ObjectNode objectNode = mock(ObjectNode.class);
        JsonNode jsonNode = mock(JsonNode.class);
        when(restTemplate.getForObject(url, String.class)).thenReturn("error json");
        when(objectMapper.readValue("error json", ObjectNode.class)).thenReturn(objectNode);
        when(objectNode.get("error")).thenReturn(jsonNode);
        when(jsonNode.get("error_msg")).thenReturn(jsonNode);

        // when
        // then
        assertThrows(vkMethodException.class, () -> underTest.isMember(vkServiceToken, userId, groupId));
        verify(restTemplate).getForObject(url, String.class);
        verify(objectMapper).readValue("error json", ObjectNode.class);
        verify(objectNode, times(2)).get("error");
    }

    @Test
    void isMemberWithoutErrorTest() throws Exception{
        // given
        String vkServiceToken = "1";
        int groupId = 1;
        int userId = 1;
        final String url = "https://api.vk.com/method/groups.isMember?user_id=" + userId +
                "&group_id=" + groupId + "&access_token=" + vkServiceToken + "&v=5.131";
        ObjectNode objectNode = mock(ObjectNode.class);
        JsonNode jsonNode = mock(JsonNode.class);
        when(restTemplate.getForObject(url, String.class)).thenReturn("json without error");
        when(objectMapper.readValue("json without error", ObjectNode.class)).thenReturn(objectNode);
        when(objectNode.get("error")).thenReturn(null);
        when(objectNode.get("response")).thenReturn(jsonNode);
        when(jsonNode.intValue()).thenReturn(1);

        // when
        boolean answer = underTest.isMember(vkServiceToken, userId, groupId);

        // then
        verify(restTemplate).getForObject(url, String.class);
        verify(objectMapper).readValue("json without error", ObjectNode.class);
        verify(objectNode).get("error");
        verify(objectNode).get("response");
        assertTrue(answer);
    }

    @Test
    void checkGroupForUserWithErrorTest() throws Exception{
        // given
        String vkServiceToken = "1";
        int userId = 1;
        int groupId = 1;
        final String url = "https://api.vk.com/method/users.get?user_ids=" + userId +
                "&fields=nickname&access_token=" + vkServiceToken + "&v=5.131";

        UserGroupIdsDto userGroupIdsDto = new UserGroupIdsDto();
        userGroupIdsDto.setGroupId(groupId);
        userGroupIdsDto.setUserId(userId);
        ObjectNode objectNode = mock(ObjectNode.class);
        JsonNode jsonNode = mock(JsonNode.class);

        when(restTemplate.getForObject(url, String.class)).thenReturn("json with error");
        when(objectMapper.readValue("json with error", ObjectNode.class)).thenReturn(objectNode);
        when(objectNode.get("error")).thenReturn(jsonNode);
        when(jsonNode.get("error_msg")).thenReturn(jsonNode);

        // when
        // then
        assertThrows(vkMethodException.class, () -> underTest.checkGroupForUser(vkServiceToken, userGroupIdsDto));
        verify(restTemplate).getForObject(url, String.class);
        verify(objectMapper).readValue("json with error", ObjectNode.class);
        verify(objectNode, times(2)).get("error");
        verify(jsonNode).get("error_msg");
    }

    @Test
    void checkGroupForUserWithEmptyResponseTest() throws Exception{
        // given
        String vkServiceToken = "1";
        int userId = 1;
        int groupId = 1;
        final String url = "https://api.vk.com/method/users.get?user_ids=" + userId +
                "&fields=nickname&access_token=" + vkServiceToken + "&v=5.131";

        UserGroupIdsDto userGroupIdsDto = new UserGroupIdsDto();
        userGroupIdsDto.setGroupId(groupId);
        userGroupIdsDto.setUserId(userId);
        ObjectNode objectNode = mock(ObjectNode.class);
        JsonNode jsonNode = mock(JsonNode.class);

        when(restTemplate.getForObject(url, String.class)).thenReturn("json with empty response");
        when(objectMapper.readValue("json with empty response", ObjectNode.class)).thenReturn(objectNode);
        when(objectNode.get("error")).thenReturn(null);
        when(objectNode.get("response")).thenReturn(jsonNode);
        when(jsonNode.size()).thenReturn(0);

        // when
        // then
        assertThrows(UserNotFoundException.class, () -> underTest.checkGroupForUser(vkServiceToken, userGroupIdsDto));
        verify(restTemplate).getForObject(url, String.class);
        verify(objectMapper).readValue("json with empty response", ObjectNode.class);
        verify(objectNode).get("error");
        verify(objectNode).get("response");
        verify(jsonNode).size();
    }

    @Test
    void checkGroupForUserTest() throws Exception{
        // given
        String vkServiceToken = "1";
        int userId = 1;
        int groupId = 1;
        final String url = "https://api.vk.com/method/users.get?user_ids=" + userId +
                "&fields=nickname&access_token=" + vkServiceToken + "&v=5.131";

        underTest = spy(underTest);
        UserGroupIdsDto userGroupIdsDto = new UserGroupIdsDto();
        userGroupIdsDto.setGroupId(groupId);
        userGroupIdsDto.setUserId(userId);
        ObjectNode objectNode = mock(ObjectNode.class);
        JsonNode jsonNode = mock(JsonNode.class);
        JsonNode firstNameNode = mock(JsonNode.class);
        JsonNode lastNameNode = mock(JsonNode.class);
        JsonNode middleNameNode = mock(JsonNode.class);

        when(restTemplate.getForObject(url, String.class)).thenReturn("json with empty response");
        when(objectMapper.readValue("json with empty response", ObjectNode.class)).thenReturn(objectNode);
        when(objectNode.get("error")).thenReturn(null);
        when(objectNode.get("response")).thenReturn(jsonNode);
        when(jsonNode.size()).thenReturn(1);
        when(jsonNode.get(0)).thenReturn(jsonNode);
        when(jsonNode.get("first_name")).thenReturn(firstNameNode);
        when(jsonNode.get("last_name")).thenReturn(lastNameNode);
        when(jsonNode.get("nickname")).thenReturn(middleNameNode);
        when(firstNameNode.toString()).thenReturn("Pavel");
        when(lastNameNode.toString()).thenReturn("Durov");
        when(middleNameNode.toString()).thenReturn("");
        doReturn(false).when(underTest).isMember(vkServiceToken, userId, groupId);

        // when
        UserInfoDto userInfoDto = underTest.checkGroupForUser(vkServiceToken, userGroupIdsDto);

        // then
        assertEquals("Pavel", userInfoDto.getFirstName());
        assertEquals("Durov", userInfoDto.getLastName());
        assertEquals("", userInfoDto.getMiddleName());
        assertFalse(userInfoDto.isMember());
        verify(restTemplate).getForObject(url, String.class);
        verify(objectMapper).readValue("json with empty response", ObjectNode.class);
        verify(objectNode).get("error");
        verify(objectNode, times(4)).get("response");
        verify(jsonNode).get("first_name");
        verify(jsonNode).get("last_name");
        verify(jsonNode).get("nickname");
    }
}
