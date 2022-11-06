package com.gpn.vkgroupvalidator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gpn.vkgroupvalidator.dto.UserGroupIdsDto;
import com.gpn.vkgroupvalidator.dto.UserInfoDto;
import com.gpn.vkgroupvalidator.service.GroupValidatorService;
import com.gpn.vkgroupvalidator.tools.UserNotFoundException;
import com.gpn.vkgroupvalidator.tools.vkMethodException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GroupValidatorControllerTest {
    @Mock
    GroupValidatorService groupValidatorService;

    @InjectMocks
    GroupValidatorController underTest = new GroupValidatorController();

    @Test
    void checkGroupTest() throws Exception {
        // given
        String vkServiceToken = "1";
        UserGroupIdsDto userGroupIdsDto = new UserGroupIdsDto();
        UserInfoDto userInfoDto = new UserInfoDto();
        when(groupValidatorService.checkGroupForUser(vkServiceToken, userGroupIdsDto)).thenReturn(userInfoDto);

        // when
        UserInfoDto controllerRespond = underTest.checkGroup(vkServiceToken, userGroupIdsDto);

        // then
        assertEquals(userInfoDto, controllerRespond);
    }

    @Test
    void checkGroupWithUserNotFoundExceptionTest() throws Exception{
        // given
        String vkServiceToken = "1";
        UserGroupIdsDto userGroupIdsDto = new UserGroupIdsDto();
        when(groupValidatorService.checkGroupForUser(vkServiceToken, userGroupIdsDto)).thenThrow(new UserNotFoundException());

        // when
        // then
        assertThrows(ResponseStatusException.class, () -> underTest.checkGroup(vkServiceToken, userGroupIdsDto));
    }

    @Test
    void checkGroupWithVkMethodExceptionTest() throws Exception{
        // given
        String vkServiceToken = "1";
        UserGroupIdsDto userGroupIdsDto = new UserGroupIdsDto();
        String rawVkMessage = "/some message/";
        String handledVkMessage = "some message";
        vkMethodException vkMethodException = new vkMethodException(rawVkMessage);
        when(groupValidatorService.checkGroupForUser(vkServiceToken, userGroupIdsDto)).thenThrow(vkMethodException);

        // when
        // then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> underTest.checkGroup(vkServiceToken, userGroupIdsDto));
        assertEquals(handledVkMessage, exception.getReason());
    }

    @Test
    void checkGroupWithJsonProcessingException() throws Exception{
        // given
        String vkServiceToken = "1";
        UserGroupIdsDto userGroupIdsDto = new UserGroupIdsDto();
        JsonProcessingException jsonProcessingException = mock(JsonProcessingException.class);
        when(groupValidatorService.checkGroupForUser(vkServiceToken, userGroupIdsDto)).thenThrow(jsonProcessingException);

        // when
        // then
        assertThrows(ResponseStatusException.class, () -> underTest.checkGroup(vkServiceToken, userGroupIdsDto));
    }
}
