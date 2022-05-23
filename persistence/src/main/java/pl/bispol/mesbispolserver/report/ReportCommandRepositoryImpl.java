package pl.bispol.mesbispolserver.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.line.WorkingHours;

import java.util.List;
import java.util.Optional;

@Service()
class ReportCommandRepositoryImpl implements ReportCommandRepository {
    private final ReportRepository reportRepository;

    @Autowired()
    ReportCommandRepositoryImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public boolean existsByStateAndLineId(ReportState state, long lineId) {
        return reportRepository.existsByReportStateAndLine_Id(state, lineId);
    }

    @Override
    public void save(Report report) {
        reportRepository.save(report);
    }

    @Override
    public Optional<Report> findById(long reportId) {
        return reportRepository.findById(reportId);
    }

    @Override
    public List<Report> findByReportStateAndLineWorkingHours(ReportState state, WorkingHours workingHours) {
        return reportRepository.findByReportStateAndLine_WorkingHoursOrderByIdDesc(state, workingHours);
    }

    @Override
    public void deleteById(long reportId) {
        reportRepository.deleteById(reportId);
    }

    @Override
    public boolean existsById(long reportId) {
        return reportRepository.existsById(reportId);
    }
}
