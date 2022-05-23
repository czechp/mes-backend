package pl.bispol.mesbispolserver.downtime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.bispol.mesbispolserver.AbstractController;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.downtime.dto.DowntimeExecutedCommandDto;
import pl.bispol.mesbispolserver.downtime.dto.DowntimeExecutedQueryDto;
import pl.bispol.mesbispolserver.exception.NotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/downtimes-executed")
@CrossOrigin("*")
class DowntimeExecutedController implements AbstractController<DowntimeExecutedCommandDto, DowntimeExecutedQueryDto> {
    private final DowntimeExecutedQueryService downtimeExecutedQueryService;
    private final DowntimeExecutedCommandService downtimeExecutedCommandService;

    @Autowired
    DowntimeExecutedController(DowntimeExecutedQueryService downtimeExecutedQueryService, DowntimeExecutedCommandService downtimeExecutedCommandService) {
        this.downtimeExecutedQueryService = downtimeExecutedQueryService;
        this.downtimeExecutedCommandService = downtimeExecutedCommandService;
    }

    @Override
    public List<DowntimeExecutedQueryDto> findBy(@RequestParam(name = "limit") Optional<Integer> limit
    ) {
        return downtimeExecutedQueryService.findBy();
    }

    @Override
    public DowntimeExecutedQueryDto findById(long id) {
        return downtimeExecutedQueryService.findById(id)
                .orElseThrow(() -> new NotFoundException(Statements.DOWN_TIME_NOT_EXISTS));
    }

    @Override
    public void deleteById(long id) {
        downtimeExecutedCommandService.deleteById(id);
    }

    @PostMapping("/line/{lineId}")
    @ResponseStatus(HttpStatus.CREATED)
    void save(
            @PathVariable(name = "lineId") final long lineId,
            @RequestBody @Valid DowntimeExecutedCommandDto downtimeExecutedCommandDto) {
        downtimeExecutedCommandService.save(downtimeExecutedCommandDto, lineId);
    }

    @PatchMapping("/status/close/{downtimeId}")
    @ResponseStatus(HttpStatus.OK)
    void close(@PathVariable(name = "downtimeId") final long downtimeId) {
        downtimeExecutedCommandService.closeDowntimeExecuted(downtimeId);
    }

    @GetMapping("/status/open/line/{lineId}")
    DowntimeExecutedQueryDto findActiveByLineId(
            @PathVariable(name = "lineId") final long lineId
    ) {
        return downtimeExecutedQueryService.findByLineIdAndOpenStatus(lineId)
                .orElseThrow(() -> new NotFoundException(Statements.DOWN_TIME_NOT_EXISTS));
    }

    @GetMapping("/line/{lineId}")
    List<DowntimeExecutedQueryDto> findByLineId(
            @PathVariable(name = "lineId") final long lineId
    ) {
        return downtimeExecutedQueryService.findByLineId(lineId);
    }

}
