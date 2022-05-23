package pl.bispol.mesbispolserver.used_raw_material;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.bispol.mesbispolserver.exception.BadRequestException;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.line.LineQueryService;
import pl.bispol.mesbispolserver.report.Report;
import pl.bispol.mesbispolserver.report.ReportQueryService;
import pl.bispol.mesbispolserver.used_raw_material.dto.UsedRawMaterialCommandDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringJUnitConfig(UsedRawMaterialCommandServiceTest.TestConfiguration.class)
class UsedRawMaterialCommandServiceTest {

    @MockBean
    UsedRawMaterialRepository usedRawMaterialRepository;
    @MockBean
    ReportQueryService reportQueryService;
    @MockBean
    LineQueryService lineQueryService;

    @Autowired
    UsedRawMaterialCommandService usedRawMaterialCommandService;

    @Test
    void deleteByIdTest() {
        //given
        final long id = 1L;
        //when
        Mockito.when(usedRawMaterialRepository.existsById(anyLong())).thenReturn(true);
        usedRawMaterialCommandService.deleteById(id);
        //then
        Mockito.verify(usedRawMaterialRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void deleteByIdTestNotFound() {
        //given
        final long id = 1L;
        //when
        Mockito.when(usedRawMaterialRepository.existsById(anyLong())).thenReturn(false);
        //then
        assertThrows(NotFoundException.class, () -> {
            usedRawMaterialCommandService.deleteById(id);
        });
    }

    @Test
    void saveTest() {
        //given
        final UsedRawMaterialCommandDto usedRawMaterialCommandDto = new UsedRawMaterialCommandDto(12L,
                "Some provider",
                "Some name",
                "Some part nr",
                "Some date",
                1L);
        final Report report = new Report();
        //when
        Mockito.when(lineQueryService.findCurrentOperatorByLineId(anyLong())).thenReturn(Optional.of("Some operator"));
        Mockito.when(reportQueryService.findActiveByLineId(anyLong())).thenReturn(Optional.of(report));
        usedRawMaterialCommandService.save(usedRawMaterialCommandDto);
        //then
        Mockito.verify(usedRawMaterialRepository, Mockito.times(1)).save(any(UsedRawMaterial.class));
    }

    @Test
    void saveReportNotExistsTest() {
        //given
        final UsedRawMaterialCommandDto usedRawMaterialCommandDto = new UsedRawMaterialCommandDto(12L,
                "Some provider",
                "Some name",
                "Some part nr",
                "Some date",
                1L);
        //when
        Mockito.when(lineQueryService.findCurrentOperatorByLineId(anyLong())).thenReturn(Optional.of("Some operator"));
        Mockito.when(reportQueryService.findActiveByLineId(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(BadRequestException.class, () -> {
            usedRawMaterialCommandService.save(usedRawMaterialCommandDto);
        });
    }

    @Test
    void saveOperatorNotExists() {
        //given
        final UsedRawMaterialCommandDto usedRawMaterialCommandDto = new UsedRawMaterialCommandDto(12L,
                "Some provider",
                "Some name",
                "Some part nr",
                "Some date",
                1L);
        //when
        Mockito.when(lineQueryService.findCurrentOperatorByLineId(anyLong())).thenReturn(Optional.of(""));
        Mockito.when(reportQueryService.findActiveByLineId(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(BadRequestException.class, () -> usedRawMaterialCommandService.save(usedRawMaterialCommandDto));
    }

    @Configuration
    @ComponentScan("pl.bispol.mesbispolserver.used_raw_material")
    static class TestConfiguration {

    }
}