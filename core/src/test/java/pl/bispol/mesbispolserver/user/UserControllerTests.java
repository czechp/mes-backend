package pl.bispol.mesbispolserver.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.bispol.mesbispolserver.MesBackendApplication;
import pl.bispol.mesbispolserver.user.dto.UserCommandDto;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {MesBackendApplication.class})
@AutoConfigureMockMvc()
@ActiveProfiles(value = {"test"})
class UserControllerTests {
    private static final String URL = "/api/users";
    @Autowired()
    MockMvc mockMvc;
    @MockBean()
    UserRepository userRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test()
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void addUserTest() throws Exception {
        //given
        UserCommandDto userCommandDto = new UserCommandDto("Jacek", "Placek", "12345678911", UserRole.PRODUCTION);
        User user = UserFactory.toEntity(userCommandDto);
        String jsonBody = objectMapper.writeValueAsString(userCommandDto);
        user.setId(1L);
        //when
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.existsByRfidId(anyString())).thenReturn(false);
        //then
        mockMvc.perform(post(URL)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test()
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void addUserTest_RfidIdExists() throws Exception {
        //given
        UserCommandDto userCommandDto = new UserCommandDto("Jacek", "Placek", "12345678911", UserRole.PRODUCTION);
        User user = UserFactory.toEntity(userCommandDto);
        String jsonBody = objectMapper.writeValueAsString(userCommandDto);
        user.setId(1L);
        //when
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.existsByRfidId(anyString())).thenReturn(true);
        //then
        mockMvc.perform(post(URL)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test()
    @WithMockUser(authorities = {"ROLE_SUPERUSER", "ROLE_ADMIN"})
    void updateTest() throws Exception {
        //given
        long userId = 1L;
        UserCommandDto userCommandDto = new UserCommandDto(
                "NewFirstName",
                "SecondName",
                "12345678901",
                UserRole.QUALITY_CONTROL);
        User user = new User("FirstName",
                "SecondName",
                "12345678901",
                UserRole.QUALITY_CONTROL);
        String jsonBody = objectMapper.writeValueAsString(userCommandDto);
        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        //then
        mockMvc.perform(put(URL + "/{userdId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
        ).andExpect(status().isNoContent());
    }

    @Test()
    @WithMockUser(authorities = {"ROLE_SUPERUSER", "ROLE_ADMIN"})
    void updateTest_RfidIdAlreadyExists() throws Exception {
        //given
        long userId = 1L;
        UserCommandDto userCommandDto = new UserCommandDto(
                "NewFirstName",
                "SecondName",
                "12345678901",
                UserRole.QUALITY_CONTROL);
        User user = new User("FirstName",
                "SecondName",
                "12345678902",
                UserRole.QUALITY_CONTROL);
        String jsonBody = objectMapper.writeValueAsString(userCommandDto);
        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.existsByRfidId(anyString())).thenReturn(true);
        //then
        mockMvc.perform(put(URL + "/{userdId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
        ).andExpect(status().isBadRequest());
    }


}
