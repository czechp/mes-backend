package pl.bispol.mesbispolserver.downtime;

import pl.bispol.mesbispolserver.downtime.dto.DowntimeExecutedCommandDto;
import pl.bispol.mesbispolserver.downtime.dto.DowntimeExecutedQueryDto;

public class DowntimeExecutedFactory {
    public static DowntimeExecutedQueryDto toDto(DowntimeExecuted downtimeExecuted) {
        return new DowntimeExecutedQueryDto(
                downtimeExecuted.getId(),
                downtimeExecuted.getContent(),
                downtimeExecuted.getReport().getId(),
                downtimeExecuted.getCreationDate(),
                downtimeExecuted.getCloseDate(),
                downtimeExecuted.getDowntimeExecutedState(),
                downtimeExecuted.getOperatorRfid(),
                downtimeExecuted.getOperatorName(),
                downtimeExecuted.countDuration()
        );
    }


    public static DowntimeExecuted toEntity(DowntimeExecutedCommandDto downtimeExecutedCommandDto) {
        DowntimeExecuted result = new DowntimeExecuted();
        result.setContent(downtimeExecutedCommandDto.getContent());
        return result;
    }
}
