package pl.bispol.mesbispolserver.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service()
class ReportQueryRepositoryImpl implements ReportQueryRepository {
    private final ReportRepository reportRepository;

    @Autowired()
    ReportQueryRepositoryImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> findByReportState(ReportState state) {
        return reportRepository.findByReportStateOrderByIdDesc(state);
    }

    @Override
    public Optional<Report> findByReportStateAndLineId(ReportState state, long lineId) {
        return reportRepository.findByReportStateAndLine_Id(state, lineId);
    }

    public List<Report> findBy() {
        return reportRepository.findByOrderByIdDesc();
    }

    @Override
    public List<Report> findAllByReportStateAndLineId(ReportState state, long lineId) {
        return reportRepository.findAllByReportStateAndLineId(state, lineId);
    }

    @Override
    public Optional<Report> findById(long reportId) {
        return reportRepository.findById(reportId);
    }

    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public List<Report> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime stopDate) {
        return reportRepository.findByCreationDateBetween(startDate, stopDate);
    }

    @Override
    public List<Report> findByLineIdCreateDateBetween(long lineId, LocalDateTime start, LocalDateTime end) {
        return reportRepository.findByLine_IdAndCreationDateBetween(lineId, start, end);
    }
}
