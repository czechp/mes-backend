package pl.bispol.mesbispolserver.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.report.dto.ReportListWithStatsQueryDto;
import pl.bispol.mesbispolserver.report.dto.ReportQueryDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service()
public class ReportQueryService {
    private final ReportQueryRepository reportQueryRepository;
    private final ReportSpreadSheetGenerator reportSpreadSheetGenerator;

    @Autowired()
    public ReportQueryService(ReportQueryRepository reportQueryRepository, ReportSpreadSheetGenerator reportSpreadSheetGenerator) {
        this.reportQueryRepository = reportQueryRepository;
        this.reportSpreadSheetGenerator = reportSpreadSheetGenerator;
    }

    public List<ReportQueryDto> findByClosedStateQueryDto() {
        return reportQueryRepository.findByReportState(ReportState.CLOSED)
                .stream()
                .map(ReportFactory::toQueryDto)
                .collect(Collectors.toList());
    }

    public Optional<ReportQueryDto> findActiveReport(final long lineId) {
        return reportQueryRepository
                .findByReportStateAndLineId(ReportState.OPEN, lineId)
                .map(ReportFactory::toQueryDto);
    }

    List<ReportQueryDto> findByLineId(final long lineId) {
        return reportQueryRepository.findAllByReportStateAndLineId(ReportState.CLOSED, lineId)
                .stream()
                .map(ReportFactory::toQueryDto)
                .collect(Collectors.toList());
    }

    public Optional<ReportQueryDto> findQueryDtoById(long reportId) {
        return reportQueryRepository.findById(reportId)
                .map(ReportFactory::toQueryDto);
    }

    public Optional<Report> findById(long reportId) {
        return reportQueryRepository.findById(reportId);
    }

    public List<Report> findAll() {
        return reportQueryRepository.findAll();
    }

    public Optional<Report> findActiveByLineId(Long lineId) {
        return reportQueryRepository.findByReportStateAndLineId(ReportState.OPEN, lineId);
    }

    ReportListWithStatsQueryDto findAllWithStatistics() {
        return ReportFactory.toQueryWithStatistics(reportQueryRepository.findAll());
    }

    ReportListWithStatsQueryDto findByLineIdWithStatistics(long lineId) {
        return ReportFactory.toQueryWithStatistics(reportQueryRepository.findAllByReportStateAndLineId(ReportState.CLOSED, lineId));
    }

    List<ReportQueryDto> findByCreationDateBetween(LocalDateTime start, LocalDateTime end) {
        return reportQueryRepository.findByCreationDateBetween(prepareStartDate(start), prepareStopDate(end)).stream()
                .map(ReportFactory::toQueryDto)
                .collect(Collectors.toList());
    }

    List<ReportQueryDto> findByLineIdCreationDateBetween(long lineId, LocalDateTime start, LocalDateTime end) {
        return reportQueryRepository.findByLineIdCreateDateBetween(lineId, prepareStartDate(start), prepareStopDate(end))
                .stream()
                .map(ReportFactory::toQueryDto)
                .collect(Collectors.toList());
    }

    String generateReportsSpreadSheetByLine(long lineId, LocalDateTime start, LocalDateTime end) throws IOException {
        List<Report> reports = reportQueryRepository.findByLineIdCreateDateBetween(lineId, prepareStartDate(start), prepareStopDate(end));
        return generateSpreadSheet(reports, start, end);
    }


    String generateReportsSpreadSheetByLine(LocalDateTime start, LocalDateTime end) throws IOException {
        List<Report> reports = reportQueryRepository.findByCreationDateBetween(prepareStartDate(start), prepareStopDate(end));
        return generateSpreadSheet(reports, start, end);
    }

    private String generateSpreadSheet(List<Report> reports, LocalDateTime start, LocalDateTime end) throws IOException {
        String fileName = "Raporty_" + start.toLocalDate().toString() + "_" + end.toLocalDate().toString();
        List<ReportQueryDto> reportsDto = reports
                .stream()
                .map(ReportFactory::toQueryDto)
                .collect(Collectors.toList());
        return reportSpreadSheetGenerator.generateReportsSpreadSheet(reportsDto, fileName);

    }

    private LocalDateTime prepareStartDate(LocalDateTime start) {
        return start.withHour(6).withMinute(58);
    }

    private LocalDateTime prepareStopDate(LocalDateTime stop) {
        return stop.plusDays(1).withHour(6).withMinute(55);
    }

}
