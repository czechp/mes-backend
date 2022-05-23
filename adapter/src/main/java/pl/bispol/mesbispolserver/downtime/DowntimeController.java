package pl.bispol.mesbispolserver.downtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.bispol.mesbispolserver.AbstractController;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.downtime.dto.DowntimeCommandDto;
import pl.bispol.mesbispolserver.downtime.dto.DowntimeQueryDto;
import pl.bispol.mesbispolserver.exception.NotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/downtimes")
@CrossOrigin("*")
@Validated
class DowntimeController implements AbstractController<DowntimeCommandDto, DowntimeQueryDto> {
    private final DowntimeQueryService downtimeQueryService;
    private final DowntimeCommandService downtimeCommandService;

    @Autowired
    DowntimeController(DowntimeQueryService downtimeQueryService, DowntimeCommandService downtimeCommandService) {
        this.downtimeQueryService = downtimeQueryService;
        this.downtimeCommandService = downtimeCommandService;
    }

    @Override
    public List<DowntimeQueryDto> findBy(@RequestParam(name = "limit") Optional<Integer> limit
    ) {
        return downtimeQueryService.findBy();
    }

    @Override
    public DowntimeQueryDto findById(long id) {
        return downtimeQueryService.findById(id)
                .orElseThrow(() -> new NotFoundException(Statements.DOWN_TIME_NOT_EXISTS));
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERUSER"})
    @Override
    public void deleteById(long id) {
        downtimeCommandService.deleteById(id);
    }


    @PostMapping("/line/{lineId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_ADMIN", "ROLE_SUPERUSER"})
    void save(
            @RequestBody @Valid final DowntimeCommandDto downtimeCommandDto,
            @PathVariable(name = "lineId") final long lineId) {
        downtimeCommandService.save(downtimeCommandDto, lineId);
    }

    @GetMapping("/line/{lineId}")
    List<DowntimeQueryDto> findByLineId(@PathVariable(name = "lineId") final long lineId) {
        return downtimeQueryService.findByLineId(lineId);
    }
}
