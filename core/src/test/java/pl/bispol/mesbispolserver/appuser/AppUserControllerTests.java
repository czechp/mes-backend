package pl.bispol.mesbispolserver.appuser;

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
import pl.bispol.mesbispolserver.appuser.dto.AppUserRegisterDto;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {MesBackendApplication.class})
@AutoConfigureMockMvc()
@ActiveProfiles(value = {"test"})
class AppUserControllerTests {
    private static final String URL = "/api/appusers";
    @Autowired()
    MockMvc mockMvc;

    @MockBean()
    AppUserRepository appUserRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test()
    void registerTest() throws Exception {
        //given
        AppUserRegisterDto appUserRegisterDto = new AppUserRegisterDto("new_user123", "new_pass123", "new_pass123", "new_email@gmial.com");

        String bodyJson = objectMapper.writeValueAsString(appUserRegisterDto);
        //when
        when(appUserRepository.existsByUsernameOrEmail(anyString(), anyString())).thenReturn(false);
        //then
        mockMvc.perform(post(URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJson))
                .andExpect(status().isNoContent());
    }

    @Test()
    void registerTest_UsernameOrEmailAlreadyExists() throws Exception {
        //given
        AppUserRegisterDto appUserRegisterDto = new AppUserRegisterDto("new_user123", "new_pass123", "new_pass123", "new_email@gmial.com");
        String bodyJson = objectMapper.writeValueAsString(appUserRegisterDto);
        //when
        when(appUserRepository.existsByUsernameOrEmail(anyString(), anyString())).thenReturn(true);
        //then
        mockMvc.perform(post(URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJson))
                .andExpect(status().isBadRequest());
    }

    @Test()
    void registerTest_PasswordArentEqual() throws Exception {
        //given
        AppUserRegisterDto appUserRegisterDto = new AppUserRegisterDto("new_user123", "new_pass1234", "new_pass123", "new_email@gmial.com");
        String bodyJson = objectMapper.writeValueAsString(appUserRegisterDto);
        //when
        when(appUserRepository.existsByUsernameOrEmail(anyString(), anyString())).thenReturn(true);
        //then
        mockMvc.perform(post(URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJson))
                .andExpect(status().isBadRequest());
    }

    @Test()
    @WithMockUser(username = "user123", password = "userpswd123")
    void changePasswordTest() throws Exception {
        //given
        AppUser appUser = new AppUser("user123", "oldpassword", "someEmail@gmail.com");
        String newPassword = "newPassword123";
        String email = "someEmail@gmail.com";
        String username = "user123";
        //when
        when(appUserRepository.findAppUserByUsername(anyString())).thenReturn(Optional.of(appUser));
        //then
        mockMvc.perform(patch(URL + "/password")
                .param("password", newPassword)
                .param("email", email)
        ).andExpect(status().isNoContent());
    }

    @Test()
    @WithMockUser(username = "user123", password = "userpswd123")
    void changePasswordTest_EmailsAreNotEquals() throws Exception {
        //given
        AppUser appUser = new AppUser("user123", "oldpassword", "someEmail@gmail.com");
        String newPassword = "newPassword123";
        String email = "anotherEmail@gmail.com";
        String username = "user123";
        //when
        when(appUserRepository.findAppUserByUsername(anyString())).thenReturn(Optional.of(appUser));
        //then
        mockMvc.perform(patch(URL + "/password")
                .param("password", newPassword)
                .param("email", email)
        ).andExpect(status().isBadRequest());
    }
}
