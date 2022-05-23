package pl.bispol.mesbispolserver.report.dto;


import lombok.Data;

@Data()
public class ReportStatisticsDto {
    private WorkingTimeDto workingTime;
    private WorkingTimeDto workingTimeWithoutStop;
    private WorkingTimeDto totalDowntimes;
    private WorkingTimeDto totalBreakdowns;
    private double currentProductionPercent;
    private long expectedProduction;
    private double expectedProductionPercent;
    private int expectedProductionPerHour;
    private int currentProductionPerHour;
    private int oee;
}

