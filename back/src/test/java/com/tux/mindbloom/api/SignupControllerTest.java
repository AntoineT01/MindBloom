package com.tux.mindbloom.api;


import com.tux.mindbloom.api.models.AccountDto;
import com.tux.mindbloom.api.models.AccountDtoData;
import com.tux.mindbloom.api.models.SignupDto;
import com.tux.mindbloom.api.models.SignupDtoData;
import com.tux.mindbloom.business.SignupService;
import com.tux.mindbloom.util.helper.RestControllerTestHelper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.tux.mindbloom.config.Constants.Api.SIGNUPS;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@OverrideAutoConfiguration(enabled = true)
@WebMvcTest(SignupController.class)
class SignupControllerTest extends RestControllerTestHelper {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SignupService signupService;

  @Nested
  class Signup {
    @Test
    void ShouldSignup() throws Exception {
      SignupDto normalSignup = SignupDtoData.getAccount();
      AccountDto normalAccount = AccountDtoData.getNormal();
      when(signupService.signup(normalSignup)).thenReturn(normalAccount);

      mockMvc.perform(post(SIGNUPS)
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(normalSignup))
              )
              .andExpect(status().isCreated())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.id", equalTo(normalAccount.getId().intValue())))
              .andDo(print());

      verify(signupService, times(1)).signup(normalSignup);
    }
  }
}