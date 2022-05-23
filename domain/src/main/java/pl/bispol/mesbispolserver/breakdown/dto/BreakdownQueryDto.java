package pl.bispol.mesbispolserver.breakdown.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.bispol.mesbispolserver.breakdown.BreakdownStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BreakdownQueryDto {
    private long id;

    private BreakdownStatus breakdownStatus;

    private String content;

    private String lineName;

    private String operatorRfid = "";

    private String operatorName = "";

    private String maintenanceRfid = "";

    private String maintenanceName = "";

    private String umupNumber = "";

    private LocalDateTime maintenanceArrivedTime;

    private LocalDateTime creationDate;

    private LocalDateTime closeDate;

    private long breakdownTotalTime;

    private long maintenanceArrivedTotalTime;
}
