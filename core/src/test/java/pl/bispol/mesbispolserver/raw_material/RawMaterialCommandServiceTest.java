package pl.bispol.mesbispolserver.raw_material;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.line.Line;
import pl.bispol.mesbispolserver.line.LineQueryService;
import pl.bispol.mesbispolserver.raw_material.dto.RawMaterialCommandDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringJUnitConfig(RawMaterialCommandServiceTest.TestConfiguration.class)
class RawMaterialCommandServiceTest {

    @MockBean
    RawMaterialRepository rawMaterialRepository;
    @MockBean
    LineQueryService lineQueryService;

    @Autowired
    RawMaterialCommandService rawMaterialCommandService;

    @Test
    void deleteByIdTest() {
        //given
        final long id = 1L;
        //when
        Mockito.when(rawMaterialRepository.existsById(anyLong())).thenReturn(true);
        rawMaterialCommandService.deleteById(id);
        //then
        Mockito.verify(rawMaterialRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void deleteByIdTestNotFound() {
        //given
        final long id = 1L;
        //when
        Mockito.when(rawMaterialRepository.existsById(anyLong())).thenReturn(false);
        //then
        assertThrows(NotFoundException.class, () -> rawMaterialCommandService.deleteById(id));
    }

    @Test
    void saveTest() {
        //given
        final RawMaterialCommandDto rawMaterialCommandDto
                = new RawMaterialCommandDto(1L, 123L, "Test provider", "Test name");
        final Line line = new Line();
        //when
        Mockito.when(lineQueryService.findById(anyLong())).thenReturn(Optional.of(line));
        rawMaterialCommandService.save(rawMaterialCommandDto);
        //then
        Mockito.verify(rawMaterialRepository, Mockito.times(1)).save(any());
    }

    @Test
    void saveTestLineNotFound() {
        //given
        final RawMaterialCommandDto rawMaterialCommandDto
                = new RawMaterialCommandDto(1L, 123L, "Test provider", "Test name");
        //when
        Mockito.when(lineQueryService.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> rawMaterialCommandService.save(rawMaterialCommandDto));
    }

    @Configuration
    @ComponentScan({"pl.bispol.mesbispolserver.raw_material"})
    static class TestConfiguration {
    }
}