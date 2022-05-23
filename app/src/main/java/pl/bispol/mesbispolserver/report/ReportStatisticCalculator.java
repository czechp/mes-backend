package pl.bispol.mesbispolserver.report;

import pl.bispol.mesbispolserver.TimeCalculator;
import pl.bispol.mesbispolserver.downtime.DowntimeExecuted;
import pl.bispol.mesbispolserver.line.WorkingHours;
import pl.bispol.mesbispolserver.report.dto.ReportStatisticsDto;
import pl.bispol.mesbispolserver.report.dto.WorkingTimeDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

final class ReportStatisticCalculator {
    private static final int MINUTES_IN_8_HR = (8 * 60) + 20;
    private static final int MINUTES_IN_12_HR = (12 * 60) + 20 + 20;

    static ReportStatisticsDto createStatistics(Report report, long amount) {
        ReportStatisticsDto statistics = new ReportStatisticsDto();
        statistics.setWorkingTime(countWorkingTime(report));
        statistics.setTotalDowntimes(countTotalDowntimes(report));
        statistics.setTotalBreakdowns(countTotalBreakdowns(report));
        statistics.setWorkingTimeWithoutStop(countTotalStopTime(report));
        statistics.setExpectedProduction(countExpectedProduction(report, statistics.getWorkingTime().getTotalMinutes()));
        statistics.setCurrentProductionPercent(countProductionPercent(amount, report.getTargetAmount()));
        statistics.setExpectedProductionPercent(countProductionPercent(statistics.getExpectedProduction(), report.getTargetAmount()));
        statistics.setExpectedProductionPerHour(countProductionPerHour(report.getTargetAmount(), report.getLine().getWorkingHours().getHoursAmount()));
        statistics.setCurrentProductionPerHour(countProductionPerHour(amount, report.getLine().getWorkingHours().getHoursAmount()));
        statistics.setOee(countOee(
                amount,
                countWorkingTime(report).getHoursDecimal(),
                report.getTargetAmount(),
                report.getLine().getWorkingHours().getHoursAmount()
        ));
        return statistics;
    }

    protected static int countOee(long goodPieces, float workingTime, long targetAmount, float hoursAmount) {
        int expectedPieces = (int) ((workingTime * targetAmount) / hoursAmount);
        return (int) (((float) goodPieces / (float) expectedPieces) * 100f);
    }

    protected static int countProductionPerHour(long targetAmount, float workingHours) {
        return (int) (targetAmount / workingHours);
    }

    private static WorkingTimeDto countTotalStopTime(Report report) {
        return new WorkingTimeDto(countWorkingTime(report).getTotalMinutes() - (countTotalDowntimes(report).getTotalMinutes() + countTotalBreakdowns(report).getTotalMinutes()));
    }

    private static WorkingTimeDto countTotalBreakdowns(Report report) {
        LocalDateTime reportStartDate = report.getCreationDate();
        LocalDateTime reportCloseDate = Optional.ofNullable(report.getFinishDate()).orElse(LocalDateTime.now());

        Long totalTime = report.getBreakdowns()
                .stream()
                .map(breakdown -> {
                    LocalDateTime breakdownStart = breakdown.getCreationDate().isBefore(report.getCreationDate()) ?
                            report.getCreationDate() : breakdown.getCreationDate();
                    LocalDateTime auxiliaryBreakdownFinish =
                            Optional.ofNullable(breakdown.getCloseDate()).orElse(Optional.ofNullable(report.getFinishDate()).orElse(LocalDateTime.now()));
                    LocalDateTime breakdownFinish = auxiliaryBreakdownFinish.isAfter(Optional.ofNullable(report.getFinishDate()).orElse(LocalDateTime.now()))
                            ? Optional.ofNullable(report.getFinishDate()).orElse(LocalDateTime.now()) : auxiliaryBreakdownFinish;
                    return TimeCalculator.timeBetweenToMinutes(breakdownStart, breakdownFinish);
                })
                .reduce(0L, Long::sum);
        return new WorkingTimeDto(totalTime);
    }

    private static WorkingTimeDto countTotalDowntimes(Report report) {
        return new WorkingTimeDto(report.getDowntimeExecutedList().stream().map(DowntimeExecuted::countDuration).reduce(0L, Long::sum));
    }

    private static WorkingTimeDto countWorkingTime(Report report) {
        LocalDateTime finishDate = report.getReportState() == ReportState.CLOSED ? report.getFinishDate() : LocalDateTime.now();
        return new WorkingTimeDto(Duration.between(report.getCreationDate(), finishDate).toMinutes());
    }

    private static long countExpectedProduction(Report report, long minutes) {
        long basisMinutes = (report.getLine().getWorkingHours() == WorkingHours.HOURS8 ? MINUTES_IN_8_HR : MINUTES_IN_12_HR);
        long minutesFromDownTimes = countTotalDowntimes(report).getTotalMinutes();
        long minutesFromBreakdowns = countTotalBreakdowns(report).getTotalMinutes();
        long minutesToSubtract = basisMinutes + minutesFromDownTimes + minutesFromBreakdowns;
        float piecesPerMin = (float) report.getTargetAmount() / (float) minutesToSubtract;
        return Math.round(piecesPerMin * minutes);
    }

    private static double countProductionPercent(double current, double target) {
        return (current / target) * 100.00;
    }
}
