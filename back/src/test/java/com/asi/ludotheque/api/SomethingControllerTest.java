package com.tanguylegoff.templateapp.api;

import com.tanguylegoff.templateapp.api.models.SomethingDto;
import com.tanguylegoff.templateapp.api.models.SomethingDtoData;
import com.tanguylegoff.templateapp.business.SomethingService;
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

import static com.tanguylegoff.templateapp.config.Constants.Api.SOMETHING;
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
@WebMvcTest(SomethingController.class)
class SomethingControllerTest extends RestControllerTestHelper {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SomethingService somethingService;

  @Nested
  class FindAll {
    @Test
    void ShouldReturnAllSomethings() throws Exception {
      List<SomethingDto> dtos = SomethingDtoData.getList();
      when(somethingService.findAll()).thenReturn(dtos);

      mockMvc.perform(get(SOMETHING)
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$", hasSize(2)))
              .andExpect(jsonPath("$[0].id", equalTo(dtos.get(0).getId().intValue())))
              .andExpect(jsonPath("$[0].label", equalTo(dtos.get(0).getLabel())))
              .andExpect(jsonPath("$[1].id", equalTo(dtos.get(1).getId().intValue())))
              .andExpect(jsonPath("$[1].label", equalTo(dtos.get(1).getLabel())))
              .andDo(print());

      verify(somethingService, times(1)).findAll();
    }
  }

  @Nested
  class FindById {
    @Test
    void ShouldFindSomethingById() throws Exception {
      SomethingDto dto = SomethingDtoData.getSome();
      when(somethingService.findById(dto.getId())).thenReturn(dto);

      mockMvc.perform(get(SOMETHING + "/" + dto.getId())
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.id", equalTo(dto.getId().intValue())))
              .andExpect(jsonPath("$.label", equalTo(dto.getLabel())))
              .andDo(print());

      verify(somethingService, times(1)).findById(dto.getId());
    }
  }


  @Nested
  class Create {
    @Test
    void ShouldBeAuthenticatedToCreateSomething() throws Exception {
      SomethingDto toCreate = SomethingDtoData.getSome();
      toCreate.setId(null);

      mockMvc.perform(post(SOMETHING)
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(toCreate))
              )
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      verify(somethingService, never()).create(any(SomethingDto.class));
    }

    @Test
    void ShouldCreateSomething() throws Exception {
      SomethingDto toCreate = SomethingDtoData.getSome();
      toCreate.setId(null);

      SomethingDto dto = SomethingDtoData.getSome();

      when(somethingService.create(toCreate)).thenReturn(dto);

      mockMvc.perform(post(SOMETHING)
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(toCreate))
              )
              .andExpect(status().isCreated())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.id", equalTo(dto.getId().intValue())))
              .andExpect(jsonPath("$.label", equalTo(dto.getLabel())))
              .andDo(print());

      verify(somethingService, times(1)).create(toCreate);
    }
  }


  @Nested
  class UpdateById {

    @Test
    void ShouldBeAuthenticatedToUpdateSomething() throws Exception {
      SomethingDto toUpdate = SomethingDtoData.getSome();

      mockMvc.perform(put(SOMETHING + "/" + toUpdate.getId())
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(toUpdate))
              )
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      verify(somethingService, never()).updateById(anyLong(), any(SomethingDto.class));
    }

    @Test
    void ShouldUpdateSomething() throws Exception {
      SomethingDto toUpdate = SomethingDtoData.getSome();
      when(somethingService.updateById(toUpdate.getId(), toUpdate)).thenReturn(toUpdate);

      mockMvc.perform(put(SOMETHING + "/" + toUpdate.getId())
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(asJsonString(toUpdate))
              )
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.id", equalTo(toUpdate.getId().intValue())))
              .andExpect(jsonPath("$.label", equalTo(toUpdate.getLabel())))
              .andDo(print());

      verify(somethingService, times(1)).updateById(toUpdate.getId(), toUpdate);
    }
  }


  @Nested
  class DeleteById {
    @Test
    void ShouldBeAuthenticatedToDeleteSomething() throws Exception {

      mockMvc.perform(delete(SOMETHING + "/1"))
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.http-status", equalTo(401)))
              .andDo(print());

      verify(somethingService, never()).deleteById(anyLong());
    }

    @Test
    void ShouldDeleteSomething() throws Exception {
      mockMvc.perform(delete(SOMETHING + "/1")
                      .with(user(NORMAL_USER))
              )
              .andExpect(status().isNoContent())
              .andDo(print());

      verify(somethingService, times(1)).deleteById(1L);
    }
  }
}