package pl.bispol.mesbispolserver.raw_material;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.bispol.mesbispolserver.line.Line;
import pl.bispol.mesbispolserver.line.LineQueryService;
import pl.bispol.mesbispolserver.raw_material.dto.RawMaterialCommandDto;
import pl.bispol.mesbispolserver.web.WebExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;


@SpringJUnitConfig(RawMaterialControllerTest.TestConfiguration.class)
@ActiveProfiles({"test"})
class RawMaterialControllerTest {
    private static final String URL = "/api/raw-materials";
    @MockBean
    RawMaterialRepository rawMaterialRepository;
    @MockBean
    LineQueryService lineQueryService;

    @Autowired
    RawMaterialController rawMaterialController;
    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @BeforeEach()
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(rawMaterialController)
                .setControllerAdvice(new WebExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test()
    @WithMockUser
    void findByIdTest() throws Exception {
        //given
        final long id = 1L;
        RawMaterial rawMaterial = new RawMaterial(123, "Test provider", "Test name");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL + "/{id}", id);
        //when
        Mockito.when(rawMaterialRepository.findById(anyLong())).thenReturn(Optional.of(rawMaterial));
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.systemId", is((int) rawMaterial.getSystemId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.provider", is(rawMaterial.getProvider())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(rawMaterial.getName())));

    }


    @Test
    @WithMockUser
    void findByIdTest_NotFound() throws Exception {
        //given
        final long id = 1L;
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL + "/{id}", id);
        //when
        Mockito.when(rawMaterialRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser
    void findByTest() throws Exception {
        //given
        final int limit = 2;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL);
        final List<RawMaterial> rawMaterialsList = Arrays.asList(
                new RawMaterial(123L, "First provider", "First name"),
                new RawMaterial(123L, "Second provider", "Second name"),
                new RawMaterial(123L, "Third provider", "Third name")
        );

        PageImpl<RawMaterial> pageRawMaterials = new PageImpl<>(rawMaterialsList);
        //when
        Mockito.when(rawMaterialRepository.findAll(any(Pageable.class))).thenReturn(pageRawMaterials);
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(rawMaterialsList.size())));
    }

    @Test
    @WithMockUser
    void deleteByIdTest() throws Exception {
        //given
        final long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", id);
        //when
        Mockito.when(rawMaterialRepository.existsById(anyLong())).thenReturn(true);
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    @WithMockUser
    void deleteByIdTestNotFound() throws Exception {
        //given
        final long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", id);
        //when
        Mockito.when(rawMaterialRepository.existsById(anyLong())).thenReturn(false);
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser
    void saveTest() throws Exception {
        //given
        final RawMaterialCommandDto rawMaterialCommandDto
                = new RawMaterialCommandDto(1L, 123L, "Test provider", "Test name");
        final String requestBody = objectMapper.writeValueAsString(rawMaterialCommandDto);
        final Line line = new Line();
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        //when
        Mockito.when(lineQueryService.findById(anyLong())).thenReturn(Optional.of(line));
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser
    void saveLineNotFoundTest() throws Exception {
        //given
        final RawMaterialCommandDto rawMaterialCommandDto
                = new RawMaterialCommandDto(1L, 123L, "Test provider", "Test name");
        final String requestBody = objectMapper.writeValueAsString(rawMaterialCommandDto);
        final Line line = new Line();
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        //when
        Mockito.when(lineQueryService.findById(anyLong())).thenReturn(Optional.empty());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser
    void findByLineIdTest() throws Exception {
        //given
        final long lineId = 1L;
        final List<RawMaterial> materials = Arrays.asList(
                new RawMaterial(123L, "First provider", "First name"),
                new RawMaterial(123L, "Second provider", "Second name"),
                new RawMaterial(123L, "Third provider", "Third name")
        );
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL + "/line/{lineId}", lineId)
                .accept(MediaType.APPLICATION_JSON);
        //when
        Mockito.when(rawMaterialRepository.findByLine_Id(anyLong())).thenReturn(materials);
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(materials.size())));
    }

    @Configuration
    @ComponentScan("pl.bispol.mesbispolserver.raw_material")
    static class TestConfiguration {
    }
}