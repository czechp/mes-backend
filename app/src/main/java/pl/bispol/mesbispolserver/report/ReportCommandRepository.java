package pl.bispol.mesbispolserver.report;

import pl.bispol.mesbispolserver.line.WorkingHours;

import java.util.List;
import java.util.Optional;

interface ReportCommandRepository {
    boolean existsByStateAndLineId(ReportState open, long lineId);

    void save(Report report);

    Optional<Report> findById(long reportId);

    List<Report> findByReportStateAndLineWorkingHours(ReportState state, WorkingHours workingHours);

    void deleteById(long reportId);

    boolean existsById(long reportId);
}
