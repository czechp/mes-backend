package pl.bispol.mesbispolserver.downtime;

import pl.bispol.mesbispolserver.downtime.dto.DowntimeCommandDto;
import pl.bispol.mesbispolserver.downtime.dto.DowntimeQueryDto;

class DowntimeFactory {
    static DowntimeQueryDto toDto(Downtime downtime) {
        DowntimeQueryDto result = new DowntimeQueryDto();
        result.setId(downtime.getId());
        result.setContent(downtime.getContent());
        result.setLineName(downtime.getLine().getName());
        return result;
    }

    static Downtime toEntity(DowntimeCommandDto downtimeCommandDto) {
        Downtime result = new Downtime();
        result.setContent(downtimeCommandDto.getContent());
        return result;
    }
}
