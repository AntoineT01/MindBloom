package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.AccountDtoData;
import com.tux.mindbloom.api.models.AccountDto;
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

import static com.tux.mindbloom.config.Constants.Api.ACCOUNTS;
import static com.tux.mindbloom.config.Constants.Api.ME;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@OverrideAutoConfiguration(enabled = true)
@WebMvcTest(MyAccountController.class)
class MyAccountControllerTest extends RestControllerTestHelper {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountService accountService;

  @Nested
  class FindMyself {
    @Test
    void ShouldBeAuthenticatedToFindMyself() throws Exception {
      AccountDto dto = AccountDtoData.getNormal();

      mockMvc.perform(get(ACCOUNTS + ME)
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      verify(accountService, never()).findById(dto.getId());
    }

    @Test
    void ShouldFindMyself() throws Exception {
      AccountDto dto = AccountDtoData.getNormal();
      when(accountService.findById(dto.getId())).thenReturn(dto);

      mockMvc.perform(get(ACCOUNTS + ME)
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.id", equalTo(dto.getId().intValue())))
              .andDo(print());

      verify(accountService, times(1)).findById(dto.getId());
    }
  }

  @Nested
  class UpdateMyself {

    @Test
    void ShouldBeAuthenticatedToUpdateMyself() throws Exception {
      AccountDto toUpdate = AccountDtoData.getNormal();

      mockMvc.perform(put(ACCOUNTS + ME)
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(toUpdate))
              )
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      verify(accountService, never()).updateById(anyLong(), any(AccountDto.class));
    }

    @Test
    void ShouldUpdateMyself() throws Exception {
      AccountDto toUpdate = AccountDtoData.getNormal();
      when(accountService.findById(toUpdate.getId())).thenReturn(toUpdate);
      when(accountService.updateById(eq(toUpdate.getId()), any(AccountDto.class))).thenReturn(toUpdate);

      mockMvc.perform(put(ACCOUNTS + ME)
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(toUpdate))
              )
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.id", equalTo(toUpdate.getId().intValue())))
              .andDo(print());

      // We probably should check that only the wanted members were updated with a matcher
      verify(accountService, times(1)).updateById(eq(toUpdate.getId()), any(AccountDto.class));
    }
  }


  @Nested
  class DeleteMyself {
    @Test
    void ShouldBeAuthenticatedToDeleteMyself() throws Exception {

      mockMvc.perform(delete(ACCOUNTS + "/1"))
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      verify(accountService, never()).deleteById(anyLong());
    }

    @Test
    void ShouldDeleteMyself() throws Exception {
      mockMvc.perform(delete(ACCOUNTS + ME)
                      .with(user(NORMAL_USER))
              )
              .andExpect(status().isNoContent())
              .andDo(print());

      verify(accountService, times(1)).deleteById(2L);
    }
  }
}