package pl.bispol.mesbispolserver.downtime.dto;

import lombok.*;
import pl.bispol.mesbispolserver.downtime.DowntimeExecutedState;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class DowntimeExecutedQueryDto {
    private long id;
    private String content;
    private long reportId;
    private LocalDateTime creationDate;
    private LocalDateTime closeDate;
    private DowntimeExecutedState downtimeExecutedState;
    private String operatorRfid;
    private String operatorName;
    private long totalMinutes;
}
