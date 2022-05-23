package pl.bispol.mesbispolserver.used_raw_material;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.bispol.mesbispolserver.AbstractController;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.used_raw_material.dto.UsedRawMaterialCommandDto;
import pl.bispol.mesbispolserver.used_raw_material.dto.UsedRawMaterialQueryDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/used-raw-materials")
@CrossOrigin("*")
class UsedRawMaterialController implements AbstractController<UsedRawMaterialCommandDto, UsedRawMaterialQueryDto> {
    private final UsedRawMaterialQueryService usedRawMaterialQueryService;

    private final UsedRawMaterialCommandService usedRawMaterialCommandService;

    UsedRawMaterialController(UsedRawMaterialQueryService usedRawMaterialQueryService, UsedRawMaterialCommandService usedRawMaterialCommandService) {
        this.usedRawMaterialQueryService = usedRawMaterialQueryService;
        this.usedRawMaterialCommandService = usedRawMaterialCommandService;
    }

    @Override
    public List<UsedRawMaterialQueryDto> findBy(Optional<Integer> limit) {
        return usedRawMaterialQueryService.findByWithLimit(limit);
    }


    @Override
    public UsedRawMaterialQueryDto findById(long id) {
        return usedRawMaterialQueryService.findById(id)
                .orElseThrow(() -> new NotFoundException(Statements.RAW_MATERIAL_NOT_FOUND));
    }

    @Override
    public void deleteById(long id) {
        usedRawMaterialCommandService.deleteById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void save(@RequestBody @Valid UsedRawMaterialCommandDto dto) {
        usedRawMaterialCommandService.save(dto);
    }
}

