package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.ProfileDto;
import com.tux.mindbloom.api.models.ProfileDtoData;
import com.tux.mindbloom.business.ProfileService;
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

import static com.tux.mindbloom.config.Constants.Api.PROFILES;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@OverrideAutoConfiguration(enabled = true)
@WebMvcTest(ProfileController.class)
class ProfileControllerTest extends RestControllerTestHelper {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProfileService profileService;

  @Nested
  class FindAll {
    @Test
    void ShouldReturnAllProfiles() throws Exception {
      List<ProfileDto> dtos = ProfileDtoData.getList();
      when(profileService.findAll()).thenReturn(dtos);

      mockMvc.perform(get(PROFILES)
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

      verify(profileService, times(1)).findAll();
    }
  }
}