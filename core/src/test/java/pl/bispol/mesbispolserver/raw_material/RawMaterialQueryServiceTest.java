package pl.bispol.mesbispolserver.raw_material;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.bispol.mesbispolserver.line.LineQueryService;
import pl.bispol.mesbispolserver.raw_material.dto.RawMaterialQueryDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringJUnitConfig(RawMaterialQueryServiceTest.TestConfiguration.class)
class RawMaterialQueryServiceTest {
    @MockBean
    RawMaterialRepository rawMaterialRepository;
    @MockBean
    LineQueryService lineQueryService;

    @Autowired
    RawMaterialQueryService rawMaterialQueryService;

    @Test
    void findByIdTest() {
        //given
        final long id = 1L;
        RawMaterial rawMaterial = new RawMaterial(123, "Test provider", "Test name");
        //when
        Mockito.when(rawMaterialRepository.findById(anyLong())).thenReturn(Optional.of(rawMaterial));
        Optional<RawMaterial> result = rawMaterialRepository.findById(id);
        //then
        assertTrue(result.isPresent());
        result.ifPresent((r) -> {
            assertEquals(rawMaterial.getSystemId(), r.getSystemId());
            assertEquals(rawMaterial.getProvider(), r.getProvider());
            assertEquals(rawMaterial.getName(), r.getName());
        });
    }

    @Test
    void findAllTest() {
        //given
        final int limit = 3;
        final Optional<Integer> parameter = Optional.of(limit);
        final List<RawMaterial> rawMaterialsList = Arrays.asList(
                new RawMaterial(123L, "First provider", "First name"),
                new RawMaterial(123L, "Second provider", "Second name"),
                new RawMaterial(123L, "Third provider", "Third name")
        );
        PageImpl<RawMaterial> rawMaterialsPage = new PageImpl<>(rawMaterialsList);
        //when
        Mockito.when(rawMaterialRepository.findAll(PageRequest.of(0, limit))).thenReturn(rawMaterialsPage);
        Mockito.when(rawMaterialRepository.findAll(Pageable.unpaged())).thenReturn(rawMaterialsPage);
        List<RawMaterialQueryDto> firstResult = rawMaterialQueryService.findAll(Optional.of(limit));
        List<RawMaterialQueryDto> secondResult = rawMaterialQueryService.findAll(Optional.empty());
        //then
        assertThat(secondResult, hasSize(limit));
        assertThat(secondResult, hasSize(rawMaterialsList.size()));
    }

    @Test
    void findByLineId() {
        //given
        RawMaterial rawMaterial = new RawMaterial();
        final long lineId = 1L;
        final List<RawMaterial> rawMaterials = Arrays.asList(
                new RawMaterial(123L, "First provider", "First name"),
                new RawMaterial(123L, "Second provider", "Second name"),
                new RawMaterial(123L, "Third provider", "Third name")
        );
        //when
        Mockito.when(rawMaterialRepository.findByLine_Id(anyLong()))
                .thenReturn(rawMaterials);
        List<RawMaterialQueryDto> result = rawMaterialQueryService.findByLineId(lineId);
        //then
        assertThat(result, hasSize(rawMaterials.size()));

    }

    @Configuration
    @ComponentScan("pl.bispol.mesbispolserver.raw_material")
    static class TestConfiguration {
    }
}