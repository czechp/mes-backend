package pl.bispol.mesbispolserver.report;

import pl.bispol.mesbispolserver.breakdown.BreakdownFactory;
import pl.bispol.mesbispolserver.downtime.DowntimeExecutedFactory;
import pl.bispol.mesbispolserver.quality_control.QualityControlFactory;
import pl.bispol.mesbispolserver.report.dto.ReportListWithStatsQueryDto;
import pl.bispol.mesbispolserver.report.dto.ReportQueryDto;
import pl.bispol.mesbispolserver.report.dto.WorkingTimeDto;
import pl.bispol.mesbispolserver.used_raw_material.UsedRawMaterialFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ReportFactory {
    public static ReportQueryDto toQueryDto(Report report) {
        ReportQueryDto queryDto = new ReportQueryDto();
        queryDto.setId(report.getId());
        queryDto.setCreateOperator(report.getCreateOperator());
        queryDto.setFinishOperator(report.getFinishOperator());
        queryDto.setProductType(report.getLine().getProductionType());
        queryDto.setCreationDate(report.getCreationDate());
        queryDto.setFinishDate(report.getFinishDate());
        queryDto.setAmount(Math.abs(report.getReportState() == ReportState.CLOSED ? report.getAmount() : report.getLine().getCurrentCounter() - report.getStartCounter()));
        queryDto.setTrashAmount(report.getTrashAmount());
        queryDto.setTargetAmount(report.getTargetAmount());
        queryDto.setReportState(report.getReportState());
        queryDto.setLineName(report.getLine().getName());
        queryDto.setReportWorkShift(report.getReportWorkShift());
        queryDto.setProductName(report.getProductName());
        queryDto.setQualityControls(report.getQualityControls().stream().map(QualityControlFactory::toQueryDto).collect(Collectors.toList()));
        queryDto.setDowntimeExecutedQueryDtoList(report.getDowntimeExecutedList().stream().map(DowntimeExecutedFactory::toDto).collect(Collectors.toList()));
        queryDto.setReportStatisticsDto(ReportStatisticCalculator.createStatistics(report, queryDto.getAmount()));
        queryDto.setBreakdowns(report.getBreakdowns().stream().map(BreakdownFactory::toDto).collect(Collectors.toList()));
        queryDto.setMaterials(report.getMaterials().stream().map(UsedRawMaterialFactory::toDto).collect(Collectors.toList()));
        return queryDto;
    }

    public static ReportListWithStatsQueryDto toQueryWithStatistics(List<Report> reports) {
        List<ReportQueryDto> reportsDto = reports.stream().map(ReportFactory::toQueryDto).collect(Collectors.toList());

        ReportListWithStatsQueryDto result = new ReportListWithStatsQueryDto();
        result.setReports(reportsDto);
        result.setSize(reports.size());

        WorkingTimeDto workingTotalTime = new WorkingTimeDto(
                reportsDto.stream()
                        .map(reportDto -> reportDto.getReportStatisticsDto().getWorkingTime().getTotalMinutes())
                        .reduce(0L, (total, element) -> total += element));

        result.setTotalTimes(workingTotalTime);

        WorkingTimeDto workingTimeWithoutStop = new WorkingTimeDto(reportsDto.stream()
                .map(reportDto -> reportDto.getReportStatisticsDto().getWorkingTimeWithoutStop().getTotalMinutes())
                .reduce(0L, (total, element) -> total += element));

        result.setTotalTimesWithoutStop(workingTimeWithoutStop);

        return result;
    }
}
