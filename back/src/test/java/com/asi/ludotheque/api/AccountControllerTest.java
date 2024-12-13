package com.tanguylegoff.templateapp.api;

import com.tanguylegoff.templateapp.api.models.AccountDto;
import com.tanguylegoff.templateapp.api.models.AccountDtoData;
import com.tanguylegoff.templateapp.business.AccountService;
import com.tanguylegoff.templateapp.util.helper.RestControllerTestHelper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.tanguylegoff.templateapp.config.Constants.Api.ACCOUNTS;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@OverrideAutoConfiguration(enabled = true)
@WebMvcTest(AccountController.class)
class AccountControllerTest extends RestControllerTestHelper {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountService accountService;

  @Nested
  class FindAll {
    @Test
    void ShouldBeAuthenticatedToReturnAllAccounts() throws Exception {
      mockMvc.perform(get(ACCOUNTS)
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      verify(accountService, never()).findAll();
    }

    @Test
    void ShouldBeAdministratorToReturnAllAccounts() throws Exception {
      mockMvc.perform(get(ACCOUNTS)
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      verify(accountService, never()).findAll();
    }

    @Test
    void ShouldReturnAllAccounts() throws Exception {
      List<AccountDto> dtos = AccountDtoData.getList();
      when(accountService.findAll()).thenReturn(dtos);

      mockMvc.perform(get(ACCOUNTS)
                      .with(user(ADMIN_USER))
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$", hasSize(2)))
              .andExpect(jsonPath("$[0].id", equalTo(dtos.get(0).getId().intValue())))
              .andExpect(jsonPath("$[0].handle", equalTo(dtos.get(0).getHandle())))
              .andExpect(jsonPath("$[1].id", equalTo(dtos.get(1).getId().intValue())))
              .andExpect(jsonPath("$[1].handle", equalTo(dtos.get(1).getHandle())))
              .andDo(print());

      verify(accountService, times(1)).findAll();
    }
  }

  @Nested
  class FindById {

    @Test
    void ShouldBeAuthenticatedToFindAccountById() throws Exception {
      AccountDto dto = AccountDtoData.getNormal();

      mockMvc.perform(get(ACCOUNTS + "/" + dto.getId())
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      verify(accountService, never()).findById(dto.getId());
    }

    @Test
    void ShouldBeAdministratorToFindAccountById() throws Exception {
      AccountDto dto = AccountDtoData.getNormal();

      mockMvc.perform(get(ACCOUNTS + "/" + dto.getId())
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      verify(accountService, never()).findById(dto.getId());
    }

    @Test
    void ShouldFindAccountById() throws Exception {
      AccountDto dto = AccountDtoData.getNormal();
      when(accountService.findById(dto.getId())).thenReturn(dto);

      mockMvc.perform(get(ACCOUNTS + "/" + dto.getId())
                      .with(user(ADMIN_USER))
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.id", equalTo(dto.getId().intValue())))
              .andExpect(jsonPath("$.handle", equalTo(dto.getHandle())))
              .andDo(print());

      verify(accountService, times(1)).findById(dto.getId());
    }
  }


  @Nested
  class Create {
    @Test
    void ShouldBeAuthenticatedToCreateAccount() throws Exception {
      AccountDto toCreate = AccountDtoData.getNormal();
      toCreate.setId(null);

      mockMvc.perform(post(ACCOUNTS)
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(toCreate))
              )
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      verify(accountService, never()).create(any(AccountDto.class));
    }

    @Test
    void ShouldBeAdministratorToCreateAccount() throws Exception {
      AccountDto toCreate = AccountDtoData.getNormal();
      toCreate.setId(null);

      mockMvc.perform(post(ACCOUNTS)
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(toCreate))
              )
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      verify(accountService, never()).create(any(AccountDto.class));
    }

    @Test
    void ShouldCreateAccount() throws Exception {
      AccountDto toCreate = AccountDtoData.getNormal();
      toCreate.setId(null);

      AccountDto dto = AccountDtoData.getNormal();
      when(accountService.create(toCreate)).thenReturn(dto);

      mockMvc.perform(post(ACCOUNTS)
                      .with(user(ADMIN_USER))
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(toCreate))
              )
              .andExpect(status().isCreated())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.id", equalTo(dto.getId().intValue())))
              .andExpect(jsonPath("$.handle", equalTo(dto.getHandle())))
              .andDo(print());

      verify(accountService, times(1)).create(toCreate);
    }
  }


  @Nested
  class UpdateById {

    @Test
    void ShouldBeAuthenticatedToUpdateAccount() throws Exception {
      AccountDto toUpdate = AccountDtoData.getNormal();

      mockMvc.perform(put(ACCOUNTS + "/" + toUpdate.getId())
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
    void ShouldBeAdministratorToUpdateAccount() throws Exception {
      AccountDto toUpdate = AccountDtoData.getNormal();

      mockMvc.perform(put(ACCOUNTS + "/" + toUpdate.getId())
                      .with(user(NORMAL_USER))
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
    void ShouldUpdateAccount() throws Exception {
      AccountDto toUpdate = AccountDtoData.getNormal();
      when(accountService.updateById(toUpdate.getId(), toUpdate)).thenReturn(toUpdate);

      mockMvc.perform(put(ACCOUNTS + "/" + toUpdate.getId())
                      .with(user(ADMIN_USER))
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(toUpdate))
              )
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.id", equalTo(toUpdate.getId().intValue())))
              .andExpect(jsonPath("$.handle", equalTo(toUpdate.getHandle())))
              .andDo(print());

      verify(accountService, times(1)).updateById(toUpdate.getId(), toUpdate);
    }
  }


  @Nested
  class DeleteById {
    @Test
    void ShouldBeAuthenticatedToDeleteAccount() throws Exception {

      mockMvc.perform(delete(ACCOUNTS + "/1"))
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      verify(accountService, never()).deleteById(anyLong());
    }

    @Test
    void ShouldBeAdministratorToDeleteAccount() throws Exception {

      mockMvc.perform(delete(ACCOUNTS + "/1")
                      .with(user(NORMAL_USER))
              )
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      verify(accountService, never()).deleteById(anyLong());
    }

    @Test
    void ShouldDeleteAccount() throws Exception {
      mockMvc.perform(delete(ACCOUNTS + "/1")
                      .with(user(ADMIN_USER))
              )
              .andExpect(status().isNoContent())
              .andDo(print());

      verify(accountService, times(1)).deleteById(1L);
    }
  }
}