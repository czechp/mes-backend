package pl.bispol.mesbispolserver.downtime;

import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.downtime.dto.DowntimeCommandDto;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.line.Line;
import pl.bispol.mesbispolserver.line.LineQueryService;

@Service
class DowntimeCommandService {
    private final DowntimeCommandRepository downtimeCommandRepository;
    private final LineQueryService lineQueryService;

    DowntimeCommandService(DowntimeCommandRepository downtimeCommandRepository, LineQueryService lineQueryService) {
        this.downtimeCommandRepository = downtimeCommandRepository;
        this.lineQueryService = lineQueryService;
    }

    void deleteById(long id) {
        if (downtimeCommandRepository.existsById(id))
            downtimeCommandRepository.deleteById(id);
        else
            throw new NotFoundException(Statements.DOWN_TIME_NOT_EXISTS);
    }

    void save(DowntimeCommandDto downtimeCommandDto, long lineId) {
        Line line = lineQueryService.findById(lineId)
                .orElseThrow(() -> new NotFoundException(Statements.LINE_NOT_EXISTS));
        Downtime downtime = DowntimeFactory.toEntity(downtimeCommandDto);
        line.addDownTime(downtime);
        downtimeCommandRepository.save(downtime);
    }
}
