package pl.bispol.mesbispolserver.report;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.bispol.mesbispolserver.line.WorkingHours;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository()
interface ReportRepository extends CrudRepository<Report, Long> {

    boolean existsByReportStateAndLine_Id(ReportState state, long lineId);

    List<Report> findByReportStateOrderByIdDesc(ReportState state);

    List<Report> findByOrderByIdDesc();

    Optional<Report> findByReportStateAndLine_Id(ReportState state, long lineId);

    @Query("SELECT r FROM Report r WHERE r.reportState=?1 AND r.line.id=?2 ORDER BY r.id DESC")
    List<Report> findAllByReportStateAndLineId(ReportState state, long lineId);

    List<Report> findByReportStateAndLine_WorkingHoursOrderByIdDesc(ReportState state, WorkingHours workingHours);

    List<Report> findAll();

    List<Report> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime stopDate);

    List<Report> findByLine_IdAndCreationDateBetween(long lineId, LocalDateTime start, LocalDateTime end);
}
