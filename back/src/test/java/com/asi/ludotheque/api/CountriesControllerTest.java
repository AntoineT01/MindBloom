package com.tanguylegoff.templateapp.api;

import com.tanguylegoff.templateapp.api.models.CountryDto;
import com.tanguylegoff.templateapp.api.models.CountryDtoData;
import com.tanguylegoff.templateapp.business.impl.CountriesServiceImpl;
import com.tanguylegoff.templateapp.business.mappers.CountriesMapper;
import com.tanguylegoff.templateapp.util.helper.RestControllerTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static com.tanguylegoff.templateapp.config.Constants.Api.COUNTRIES;
import static com.tanguylegoff.templateapp.utils.JacksonPrinter.toJson;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
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
@WebMvcTest(CountriesController.class)
class CountriesControllerTest extends RestControllerTestHelper {

  @Autowired
  private MockMvc mockMvc;

  private List<CountryDto> dtos;

  @Autowired
  private CountriesMapper countriesMapper;

  @BeforeEach
  void setUp() {
    CountriesServiceImpl.COUNTRIES_DATA = countriesMapper.toEntities(CountryDtoData.getList());
    dtos = CountryDtoData.getList();
  }

  @Nested
  class FindAll {
    @Test
    void ShouldReturnAllCountries() throws Exception {
      mockMvc.perform(get(COUNTRIES)
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$", hasSize(6)))
              .andExpect(jsonPath("$[0].id", equalTo(dtos.get(0).getId().intValue())))
              .andExpect(jsonPath("$[0].label", equalTo(dtos.get(0).getLabel())))
              .andExpect(jsonPath("$[1].id", equalTo(dtos.get(1).getId().intValue())))
              .andExpect(jsonPath("$[1].label", equalTo(dtos.get(1).getLabel())))
              .andDo(print());

    }

    @Test
    void ShouldReturnAllCountriesWithFilter() throws Exception {
      mockMvc.perform(get(COUNTRIES + "?maxId=3")
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$", hasSize(3)))
              .andExpect(jsonPath("$[0].id", equalTo(dtos.get(0).getId().intValue())))
              .andExpect(jsonPath("$[0].label", equalTo(dtos.get(0).getLabel())))
              .andExpect(jsonPath("$[1].id", equalTo(dtos.get(1).getId().intValue())))
              .andExpect(jsonPath("$[1].label", equalTo(dtos.get(1).getLabel())))
              .andDo(print());

    }

    @Test
    void ShouldSetPositiveIdAsFilterToGetCountries() throws Exception {
      mockMvc.perform(get(COUNTRIES + "?maxId=0")
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().is4xxClientError())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andDo(print());
    }

    @Test
    void ShouldSetIntegerAsFilterToGetCountries() throws Exception {
      mockMvc.perform(get(COUNTRIES + "?maxId=abc")
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().is4xxClientError())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andDo(print());
    }
  }

  @Nested
  class FindById {
    @Test
    void ShouldReturnCountryById() throws Exception {
      CountryDto dto = dtos.getFirst();

      mockMvc.perform(get(COUNTRIES + "/" + dto.getId())
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.id", equalTo(dto.getId().intValue())))
              .andExpect(jsonPath("$.label", equalTo(dto.getLabel())))
              .andDo(print());
    }

    @Test
    void ShouldSetAvailableIdToGetCountry() throws Exception {
      mockMvc.perform(get(COUNTRIES + "/15")
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isNotFound())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andDo(print());
    }

    @Test
    void ShouldSetPositiveIntegerAsIdToGetCountry() throws Exception {
      mockMvc.perform(get(COUNTRIES + "/0")
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().is4xxClientError())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andDo(print());
    }

    @Test
    void ShouldSetIntegerAsIdToGetCountry() throws Exception {
      mockMvc.perform(get(COUNTRIES + "/abc")
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().is4xxClientError())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andDo(print());
    }
  }

  @Nested
  class Create {
    private static Stream<Arguments> provideDataForTest() {
      return Stream.of(
              Arguments.of("15", 404, MediaType.APPLICATION_JSON_VALUE),
              Arguments.of("0", 400, MediaType.APPLICATION_JSON_VALUE),
              Arguments.of("abc", 400, MediaType.APPLICATION_JSON_VALUE)
      );
    }

    @ParameterizedTest
    @MethodSource("provideDataForTest")
    void ShouldSetCorrectIdToGetCountry(String id, int expectedStatus, String expectedContentType) throws Exception {
      mockMvc.perform(get(COUNTRIES + "/" + id)
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().is(expectedStatus))
              .andExpect(content().contentType(expectedContentType))
              .andDo(print());
    }

    @Test
    void ShouldGiveLongEnoughLabelToCreateCountry() throws Exception {
      CountryDto dto = CountryDtoData.getNew();
      dto.setLabel("K");
      String json = toJson(dto);

      assert json != null; // to avoid warning about json being null
      mockMvc.perform(post(COUNTRIES)
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json)
              )
              .andExpect(status().is4xxClientError())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andDo(print());
    }

    @Test
    void ShouldGiveNotTooLongLabelToCreateCountry() throws Exception {
      CountryDto dto = CountryDtoData.getNew();
      dto.setLabel("The United Kingdom of Great Britain and Northern Irelandt");
      String json = toJson(dto);

      assert json != null; // to avoid warning about json being null
      mockMvc.perform(post(COUNTRIES)
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json)
              )
              .andExpect(status().is4xxClientError())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andDo(print());

    }

    @Test
    void ShouldCreateCountryWithNullLabel() throws Exception {
      CountryDto dto = CountryDtoData.getNew();
      dto.setLabel(null);
      String json = toJson(dto);

      assert json != null; // to avoid warning about json being null
      mockMvc.perform(post(COUNTRIES)
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json)
              )
              .andExpect(status().isCreated())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              // int est null dans le dto, donc on verif que l'id est bien un entier
              .andExpect(jsonPath("$.id").isNumber())
              .andExpect(jsonPath("$.label").doesNotExist())
              .andDo(print());
    }

    @Test
    void ShouldCreateCountry() throws Exception {
      CountryDto dto = CountryDtoData.getNew();
      String json = toJson(dto);

      assert json != null; // to avoid warning about json being null
      mockMvc.perform(post(COUNTRIES)
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json)
              )
              .andExpect(status().isCreated())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.id").isNumber())
              .andExpect(jsonPath("$.label", equalTo(dto.getLabel())))
              .andDo(print());
    }
  }

  @Nested
  class UpdateById {
    @Test
    void ShouldUpdateCountryWithNullLabel() throws Exception {
      CountryDto dto = dtos.getFirst();
      dto.setLabel(null);
      String json = toJson(dto);

      assert json != null; // to avoid warning about json being null
      mockMvc.perform(put(COUNTRIES + "/" + dto.getId())
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json)
              )
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.id", equalTo(dto.getId().intValue())))
              .andExpect(jsonPath("$.label").doesNotExist())
              .andDo(print());
    }

    @Test
    void ShouldUpdateCountry() throws Exception {
      CountryDto dto = dtos.getFirst();
      dto.setLabel("Allemagne");
      String json = toJson(dto);

      assert json != null; // to avoid warning about json being null
      mockMvc.perform(put(COUNTRIES + "/" + dto.getId())
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json)
              )
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.id", equalTo(dto.getId().intValue())))
              .andExpect(jsonPath("$.label", equalTo(dto.getLabel())))
              .andDo(print());
    }
  }

  @Nested
  class DeleteById {
    @Test
    void ShouldSetPositiveIntegerAsIdToDeleteCountry() throws Exception {
      mockMvc.perform(delete(COUNTRIES + "/0")
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().is4xxClientError())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andDo(print());
    }

    @Test
    void ShouldSetIntegerAsIdToDeleteCountry() throws Exception {
      mockMvc.perform(delete(COUNTRIES + "/abc")
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().is4xxClientError())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andDo(print());
    }

    @Test
    void ShouldDeleteCountry() throws Exception {
      CountryDto dto = CountryDtoData.getList().getFirst();

      mockMvc.perform(delete(COUNTRIES + "/" + dto.getId())
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isNoContent())
              .andDo(print());

      mockMvc.perform(delete(COUNTRIES + "/" + dto.getId())
                      .with(user(NORMAL_USER))
                      .accept(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isNotFound())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andDo(print());
    }
  }
}