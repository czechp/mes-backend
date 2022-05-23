package pl.bispol.mesbispolserver.breakdown;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.bispol.mesbispolserver.AbstractController;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.breakdown.dto.BreakdownCommandDto;
import pl.bispol.mesbispolserver.breakdown.dto.BreakdownQueryDto;
import pl.bispol.mesbispolserver.exception.NotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/breakdowns")
@Validated
class BreakdownController implements AbstractController<BreakdownCommandDto, BreakdownQueryDto> {
    private final BreakdownQueryService breakdownQueryService;
    private final BreakdownCommandService breakdownCommandService;

    BreakdownController(BreakdownQueryService breakdownQueryService, BreakdownCommandService breakdownCommandService) {
        this.breakdownQueryService = breakdownQueryService;
        this.breakdownCommandService = breakdownCommandService;
    }

    @Override
    public List<BreakdownQueryDto> findBy(
            @RequestParam(name = "limit") Optional<Integer> limit
    ) {
        return breakdownQueryService.findBy(limit);
    }

    @Override
    public BreakdownQueryDto findById(long id) {
        return breakdownQueryService.findById(id).orElseThrow(() -> new NotFoundException(Statements.BREAKDOWN_NOT_EXISTS));
    }

    @GetMapping("/line/{lineId}")
    @ResponseStatus(HttpStatus.OK)
    List<BreakdownQueryDto> findByLineId(
            @PathVariable(name = "lineId") final long lineId
    ) {
        return breakdownQueryService.findByLineId(lineId);
    }

    @Override
    public void deleteById(long id) {
        breakdownCommandService.deleteById(id);
    }

    @PostMapping("/line/{lineId}")
    @ResponseStatus(HttpStatus.CREATED)
    void save(
            @PathVariable(name = "lineId") final long lineId,
            @RequestBody @Valid final BreakdownCommandDto breakdownCommandDto
    ) {
        breakdownCommandService.save(breakdownCommandDto, lineId);
    }

    @PatchMapping("/status/progress/{breakdownId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void changeStatusToProgress(
            @PathVariable(name = "breakdownId") final long breakdownId,
            @RequestParam(name = "umup") @Length(min = 3) final String umupNumber
    ) {
        breakdownCommandService.changeStatusInProgress(breakdownId, umupNumber);
    }

    @PatchMapping("/status/close/{breakdownId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void changeStatusToClose(
            @PathVariable(name = "breakdownId") final long breakdownId
    ) {
        breakdownCommandService.changeStatusClose(breakdownId);
    }

    @GetMapping("/status/active/{lineId}")
    List<BreakdownQueryDto> findActiveByLineId(
            @PathVariable(name = "lineId") final long lineId
    ) {
        return breakdownQueryService.findByActiveAndLineIdQueryDto(lineId);
    }
}
