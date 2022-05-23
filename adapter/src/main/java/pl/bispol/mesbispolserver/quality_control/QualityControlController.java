package pl.bispol.mesbispolserver.quality_control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.quality_control.dto.QualityControlQueryDto;
import pl.bispol.mesbispolserver.quality_control.dto.QualityInspectionCommandDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/quality-controls")
@CrossOrigin("*")
class QualityControlController {

    private final QualityControlCommandService qualityControlCommandService;
    private final QualityControlQueryService qualityControlQueryService;

    @Autowired()
    QualityControlController(QualityControlCommandService qualityControlCommandService, QualityControlQueryService qualityControlQueryService) {
        this.qualityControlCommandService = qualityControlCommandService;
        this.qualityControlQueryService = qualityControlQueryService;
    }

    @PostMapping("/line/{lineId}")
    @ResponseStatus(HttpStatus.CREATED)
    void save(
            @RequestBody @Valid() final List<QualityInspectionCommandDto> inspections,
            @PathVariable(name = "lineId") final long lineId
    ) {
        qualityControlCommandService.save(lineId, inspections);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<QualityControlQueryDto> findAll(
            @RequestParam(name = "limit") Optional<Integer> limit,
            @RequestParam(name = "onlyNok", defaultValue = "false") boolean onlyNok
    ) {
        return qualityControlQueryService.findAll(limit, onlyNok);
    }

    @GetMapping("/{qualityControlId}")
    @ResponseStatus(HttpStatus.OK)
    QualityControlQueryDto findById(
            @PathVariable(name = "qualityControlId") final long qualityControlId
    ) {
        return qualityControlQueryService.findById(qualityControlId)
                .orElseThrow(() -> new NotFoundException(Statements.QUALITY_CONTROL_NOT_EXISTS));
    }


    @GetMapping("/line/{lineId}")
    @ResponseStatus(HttpStatus.OK)
    List<QualityControlQueryDto> findByLine(
            @PathVariable(name = "lineId") final long lineId
    ) {
        return qualityControlQueryService.findByLineId(lineId);
    }

    @DeleteMapping("/{qualityControlId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN", "ROLE_SUPERUSER"})
    void deleteById(
            @PathVariable(name = "qualityControlId") final long qualityControlId
    ) {
        qualityControlCommandService.deleteById(qualityControlId);
    }

}
