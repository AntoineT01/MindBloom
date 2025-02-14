package com.tux.mindbloom.api;


import com.tux.mindbloom.api.models.AccountDtoData;
import com.tux.mindbloom.api.models.AccountDto;
import com.tux.mindbloom.api.models.AccountRequestDto;
import com.tux.mindbloom.api.models.AccountRequestDtoData;
import com.tux.mindbloom.business.AccountRequestService;
import com.tux.mindbloom.business.AccountService;
import com.tux.mindbloom.util.helper.RestControllerTestHelper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Api.ACCOUNT_REQUESTS;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@OverrideAutoConfiguration(enabled = true)
@WebMvcTest(AccountRequestController.class)
class AccountRequestControllerTest extends RestControllerTestHelper {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountRequestService accountRequestService;

  @MockBean
  private AccountService accountService;

  @Nested
  class ResolveRequests {
    @Test
    void ShouldResolveRequests() throws Exception {
      AccountRequestDto dto = AccountRequestDtoData.getToken();

      mockMvc.perform(post(ACCOUNT_REQUESTS)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(dto))
              )
              .andExpect(status().isNoContent())
              .andDo(print());

      verify(accountRequestService, times(1)).resolveRequest(dto);
    }
  }

  @Nested
  class ResetPassword {
    @Test
    void ShouldAskForAPasswordReset() throws Exception {
      List<AccountDto> accounts = AccountDtoData.getList();
      when(accountService.findAllByMail("whatever@example.com")).thenReturn(accounts);
      mockMvc.perform(get(ACCOUNT_REQUESTS + "/reset?mail=whatever@example.com")
              )
              .andExpect(status().isNoContent())
              .andDo(print());

      verify(accountRequestService, times(1)).requestPasswordReset(accounts.get(0));
      verify(accountRequestService, times(1)).requestPasswordReset(accounts.get(accounts.size()-1));
    }
  }

  @Nested
  class ChangePassword {
    @Test
    void ShouldChangePassword() throws Exception {
      AccountDto account = AccountDtoData.getNormal();
      when(accountService.findById(2L)).thenReturn(account);
      mockMvc.perform(get(ACCOUNT_REQUESTS + "/change")
                      .with(user(NORMAL_USER))
              )
              .andExpect(status().isNoContent())
              .andDo(print());

      verify(accountService, times(1)).findById(2L);
      verify(accountRequestService, times(1)).requestPasswordModification(account);
    }

    @Test
    void ShouldBeAuthenticatedToChangePassword() throws Exception {
      mockMvc.perform(get(ACCOUNT_REQUESTS + "/change")
              )
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      verify(accountService, never()).findById(2L);
      verify(accountRequestService, never()).requestPasswordModification(any());
    }
  }
}