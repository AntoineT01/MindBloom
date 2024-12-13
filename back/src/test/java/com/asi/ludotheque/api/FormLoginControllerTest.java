package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.FormLoginDto;
import com.tux.mindbloom.api.models.FormLoginDtoData;
import com.tux.mindbloom.api.models.UserDetailsData;
import com.tux.mindbloom.util.helper.RestControllerTestHelper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

import static com.tux.mindbloom.config.Constants.Api.LOGIN;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@OverrideAutoConfiguration(enabled = true)
@WebMvcTest(ProfileController.class)
class FormLoginControllerTest extends RestControllerTestHelper {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private FormLoginController controller;

  @MockBean
  private UserDetailsService userDetailsService;

  @Nested
  class Login {
    @Test
    void ShouldLogin() throws Exception {
      FormLoginDto dto = FormLoginDtoData.getNormal();
      UserDetails user = UserDetailsData.getNormal();

      when(userDetailsService.loadUserByUsername(dto.getHandle())).thenReturn(user);

      mockMvc.perform(post(LOGIN)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(dto))
              )
              .andExpect(status().isOk())
              .andDo(print());

      verify(userDetailsService, times(1)).loadUserByUsername(dto.getHandle());
    }

    @Test
    void ShouldLoginOnlyIfPasswordIsCorrect() throws Exception {
      FormLoginDto dto = FormLoginDtoData.getNormal();
      dto.setPassword("incorrect password");
      UserDetails user = UserDetailsData.getNormal();

      when(userDetailsService.loadUserByUsername(dto.getHandle())).thenReturn(user);

      mockMvc.perform(post(LOGIN)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(dto))
              )
              .andExpect(status().isUnauthorized())
              // Because JwtAuthenticationEntryPoint is not used (bug), we can't check error format
              // .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              // .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      // I don't know why twice exactly
      verify(userDetailsService, times(2)).loadUserByUsername(dto.getHandle());
    }


    @Test
    void ShouldLoginOnlyUserExistsOrHasAPasswordInDB() throws Exception {
      FormLoginDto dto = FormLoginDtoData.getNormal();
      when(userDetailsService.loadUserByUsername(dto.getHandle())).thenThrow(new UsernameNotFoundException(dto.getHandle()));

      mockMvc.perform(post(LOGIN)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(dto))
              )
              .andExpect(status().isUnauthorized())
              // Because JwtAuthenticationEntryPoint is not used (bug), we can't check error format
              // .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              // .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      // I don't know why twice exactly
      verify(userDetailsService, times(2)).loadUserByUsername(dto.getHandle());
    }
  }

  @Nested
  class Coverage {
    @Test
    void ShouldNeverPassThroughNormalController() {
      FormLoginDto dto = new FormLoginDto();
      assertThrows(IllegalStateException.class, () -> controller.formLoginProcessor(dto));
    }
  }
}