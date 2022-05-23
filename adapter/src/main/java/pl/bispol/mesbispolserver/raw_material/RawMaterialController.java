package pl.bispol.mesbispolserver.raw_material;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.bispol.mesbispolserver.AbstractController;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.raw_material.dto.RawMaterialCommandDto;
import pl.bispol.mesbispolserver.raw_material.dto.RawMaterialQueryDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/raw-materials")
@CrossOrigin("*")
class RawMaterialController implements AbstractController<RawMaterialCommandDto, RawMaterialQueryDto> {
    private final RawMaterialQueryService rawMaterialQueryService;
    private final RawMaterialCommandService rawMaterialCommandService;

    @Autowired
    RawMaterialController(RawMaterialQueryService rawMaterialQueryService, RawMaterialCommandService rawMaterialCommandService) {
        this.rawMaterialQueryService = rawMaterialQueryService;
        this.rawMaterialCommandService = rawMaterialCommandService;
    }


    @Override
    public List<RawMaterialQueryDto> findBy(Optional<Integer> limit) {
        return rawMaterialQueryService.findAll(limit);
    }

    @Override
    public RawMaterialQueryDto findById(long id) {
        return rawMaterialQueryService.findById(id)
                .orElseThrow(() -> new NotFoundException(Statements.RAW_MATERIAL_NOT_FOUND));
    }

    @GetMapping("/line/{lineId}")
    @ResponseStatus(HttpStatus.OK)
    List<RawMaterialQueryDto> findByLineId(
            @PathVariable(name = "lineId") final long lineId
    ) {
        return rawMaterialQueryService.findByLineId(lineId);
    }

    @Override
    public void deleteById(long id) {
        rawMaterialCommandService.deleteById(id);
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    void save(
            @RequestBody @Valid RawMaterialCommandDto rawMaterialCommandDto
    ) {
        rawMaterialCommandService.save(rawMaterialCommandDto);
    }
}
