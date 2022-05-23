package pl.bispol.mesbispolserver.report;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

interface ReportQueryRepository {

    List<Report> findByReportState(ReportState state);

    Optional<Report> findByReportStateAndLineId(ReportState state, long lineId);


    List<Report> findAllByReportStateAndLineId(ReportState closed, long lineId);

    Optional<Report> findById(long reportId);

    List<Report> findAll();

    List<Report> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime stopDate);

    List<Report> findByLineIdCreateDateBetween(long lineId, LocalDateTime start, LocalDateTime end);
}
