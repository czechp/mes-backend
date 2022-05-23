package pl.bispol.mesbispolserver.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportListWithStatsQueryDto {
    private List<ReportQueryDto> reports;
    private int size;
    private WorkingTimeDto totalTimes;
    private WorkingTimeDto totalTimesWithoutStop;
}
