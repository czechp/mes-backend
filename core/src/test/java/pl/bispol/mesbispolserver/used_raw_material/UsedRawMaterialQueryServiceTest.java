package pl.bispol.mesbispolserver.used_raw_material;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.bispol.mesbispolserver.line.LineQueryService;
import pl.bispol.mesbispolserver.report.Report;
import pl.bispol.mesbispolserver.report.ReportQueryService;
import pl.bispol.mesbispolserver.used_raw_material.dto.UsedRawMaterialQueryDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringJUnitConfig(UsedRawMaterialQueryServiceTest.TestConfiguration.class)
class UsedRawMaterialQueryServiceTest {
    @MockBean
    UsedRawMaterialRepository usedRawMaterialRepository;

    @MockBean
    ReportQueryService reportQueryService;

    @MockBean
    LineQueryService lineQueryService;

    @Autowired
    UsedRawMaterialQueryService usedRawMaterialQueryService;

    @Test
    void findByTest() {
        //given
        final Optional<Integer> limit = Optional.empty();
        final List<UsedRawMaterial> materials = Arrays.asList(
                new UsedRawMaterial(1L, "Some provider1", "Some name1", new Report()),
                new UsedRawMaterial(2L, "Some provider2", "Some name2", new Report()),
                new UsedRawMaterial(3L, "Some provider3", "Some name3", new Report())
        );
        PageImpl<UsedRawMaterial> usedRawMaterials = new PageImpl<>(materials);
        //when
        Mockito.when(usedRawMaterialRepository.findAll(Pageable.unpaged())).thenReturn((usedRawMaterials));
        List<UsedRawMaterialQueryDto> result = usedRawMaterialQueryService.findByWithLimit(limit);
        //then
        assertThat(result, hasSize(materials.size()));
    }

    @Test
    void findByIdTest() {
        //given
        final long id = 1L;
        UsedRawMaterial usedRawMaterial = new UsedRawMaterial(123L,
                "Some provider",
                "Some name",
                "Some partNr",
                "Some date",
                new Report());
        //when
        Mockito.when(usedRawMaterialRepository.findById(anyLong())).thenReturn(Optional.of(usedRawMaterial));
        Optional<UsedRawMaterialQueryDto> result = usedRawMaterialQueryService.findById(id);
        //then
        assertTrue(result.isPresent());
        result.ifPresent(r -> {
            assertEquals(usedRawMaterial.getId(), r.getId());
            assertEquals(usedRawMaterial.getProvider(), r.getProvider());
            assertEquals(usedRawMaterial.getName(), r.getName());
            assertEquals(usedRawMaterial.getPartNr(), r.getPartNr());
            assertEquals(usedRawMaterial.getDate(), r.getDate());
        });
    }


    @Configuration
    @ComponentScan("pl.bispol.mesbispolserver.used_raw_material")
    static class TestConfiguration {
    }
}