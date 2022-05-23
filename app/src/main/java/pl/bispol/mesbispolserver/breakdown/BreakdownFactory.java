package pl.bispol.mesbispolserver.breakdown;

import pl.bispol.mesbispolserver.TimeCalculator;
import pl.bispol.mesbispolserver.breakdown.dto.BreakdownCommandDto;
import pl.bispol.mesbispolserver.breakdown.dto.BreakdownEmailMsgDto;
import pl.bispol.mesbispolserver.breakdown.dto.BreakdownQueryDto;
import pl.bispol.mesbispolserver.line.Line;

import java.time.LocalDateTime;
import java.util.Optional;

public class BreakdownFactory {

    public static BreakdownQueryDto toDto(Breakdown breakdown) {
        return new BreakdownQueryDto(
                breakdown.getId(),
                breakdown.getBreakdownStatus(),
                breakdown.getContent(),
                breakdown.getLine().getName(),
                breakdown.getOperatorRfid(),
                breakdown.getOperatorName(),
                breakdown.getMaintenanceRfid(),
                breakdown.getMaintenanceName(),
                breakdown.getUmupNumber(),
                breakdown.getMaintenanceArrivedTime(),
                breakdown.getCreationDate(),
                breakdown.getCloseDate(),
                TimeCalculator.timeBetweenToMinutes(breakdown.getCreationDate(), breakdown.getCloseDate()),
                TimeCalculator.timeBetweenToMinutes(breakdown.getCreationDate(), breakdown.getMaintenanceArrivedTime())

        );
    }

    public static BreakdownEmailMsgDto toEmailDto(Breakdown breakdown) {
        return new BreakdownEmailMsgDto(
                breakdown.getLine().getName(),
                breakdown.getContent(),
                Optional.ofNullable(breakdown.getCreationDate()).orElseGet(LocalDateTime::now),
                breakdown.getLine().getProductionType().toString(),
                breakdown.getLine().getOperator()
        );
    }

    static Breakdown toEntity(BreakdownCommandDto dto, Line line, String operatorRfid, String operatorName) {
        return new Breakdown(
                dto.getContent(),
                line,
                operatorRfid,
                operatorName
        );
    }
}
