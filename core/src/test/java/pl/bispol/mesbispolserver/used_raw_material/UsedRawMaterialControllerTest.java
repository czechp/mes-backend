package pl.bispol.mesbispolserver.used_raw_material;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.bispol.mesbispolserver.line.LineQueryService;
import pl.bispol.mesbispolserver.report.Report;
import pl.bispol.mesbispolserver.report.ReportQueryService;
import pl.bispol.mesbispolserver.used_raw_material.dto.UsedRawMaterialCommandDto;
import pl.bispol.mesbispolserver.web.WebExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringJUnitConfig(UsedRawMaterialControllerTest.TestConfiguration.class)
class UsedRawMaterialControllerTest {

    private static final String URL = "/api/used-raw-materials";

    @MockBean
    UsedRawMaterialRepository usedRawMaterialRepository;

    @MockBean
    ReportQueryService reportQueryService;

    @MockBean
    LineQueryService lineQueryService;

    ObjectMapper objectMapper;
    @Autowired
    UsedRawMaterialController usedRawMaterialController;
    MockMvc mockMvc;

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(usedRawMaterialController)
                .setControllerAdvice(new WebExceptionHandler())
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void findByTest() throws Exception {
        //given
        final int limit = 3;
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL)
                .param("limit", String.valueOf(limit));
        final List<UsedRawMaterial> materials = Arrays.asList(new UsedRawMaterial(1L, "Some provider1", "Some name1", new Report()),
                new UsedRawMaterial(2L, "Some provider2", "Some name2", new Report()),
                new UsedRawMaterial(3L, "Some provider3", "Some name3", new Report()));
        final PageImpl<UsedRawMaterial> usedRawMaterialsPage = new PageImpl<>(materials);
        //when
        Mockito.when(usedRawMaterialRepository.findAll(PageRequest.of(0, limit))).thenReturn(usedRawMaterialsPage);
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(materials.size())));
    }

    @Test
    void findByIdTest() throws Exception {
        //given
        final long id = 1L;
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL + "/{id}", id);
        final UsedRawMaterial usedRawMaterial = new UsedRawMaterial(123L,
                "Some provider",
                "Some name",
                "Some partNr",
                "Some date",
                new Report());
        //when
        Mockito.when(usedRawMaterialRepository.findById(anyLong())).thenReturn(Optional.of(usedRawMaterial));
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is((int) usedRawMaterial.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.systemId", is((int) usedRawMaterial.getSystemId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reportId", is((int) usedRawMaterial.getReport().getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.provider", is(usedRawMaterial.getProvider())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(usedRawMaterial.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.partNr", is(usedRawMaterial.getPartNr())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", is(usedRawMaterial.getDate())));


    }


    @Test
    void findByIdNotFoundTest() throws Exception {
        //given
        final long id = 1L;
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL + "/{id}", id);
        //when
        Mockito.when(usedRawMaterialRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void saveTest() throws Exception {
        //given
        final Report report = new Report();
        final UsedRawMaterialCommandDto usedRawMaterialCommandDto = new UsedRawMaterialCommandDto(12L,
                "Some provider",
                "Some name",
                "Some part nr",
                "Some date",
                1L);
        final String requestBody = objectMapper.writeValueAsString(usedRawMaterialCommandDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);
        //when
        Mockito.when(lineQueryService.findCurrentOperatorByLineId(anyLong())).thenReturn(Optional.of("Some operator"));
        Mockito.when(reportQueryService.findActiveByLineId(anyLong())).thenReturn(Optional.of(report));
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void saveReportNotFoundTest() throws Exception {
        //given
        final UsedRawMaterialCommandDto usedRawMaterialCommandDto = new UsedRawMaterialCommandDto(12L,
                "Some provider",
                "Some name",
                "Some part nr",
                "Some date",
                1L);
        final String requestBody = objectMapper.writeValueAsString(usedRawMaterialCommandDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);
        //when
        Mockito.when(lineQueryService.findCurrentOperatorByLineId(anyLong())).thenReturn(Optional.of("Some operator"));
        Mockito.when(reportQueryService.findActiveByLineId(anyLong())).thenReturn(Optional.empty());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void saveReportOperatorNotExistsTest() throws Exception {
        //given
        final UsedRawMaterialCommandDto usedRawMaterialCommandDto = new UsedRawMaterialCommandDto(12L,
                "Some provider",
                "Some name",
                "Some part nr",
                "Some date",
                1L);
        final String requestBody = objectMapper.writeValueAsString(usedRawMaterialCommandDto);
        final Report report = new Report();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);
        //when
        Mockito.when(lineQueryService.findCurrentOperatorByLineId(anyLong())).thenReturn(Optional.empty());
        Mockito.when(reportQueryService.findActiveByLineId(anyLong())).thenReturn(Optional.of(report));
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Configuration
    @ComponentScan("pl.bispol.mesbispolserver.used_raw_material")
    static class TestConfiguration {
    }
}