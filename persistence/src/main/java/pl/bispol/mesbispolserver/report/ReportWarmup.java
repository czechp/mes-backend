package pl.bispol.mesbispolserver.report;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.bispol.mesbispolserver.line.Line;
import pl.bispol.mesbispolserver.line.LineQueryService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Profile("development")
@Component()
class ReportWarmup {
    private final ReportRepository reportRepository;
    private final LineQueryService lineQueryService;
    private final Logger logger;

    ReportWarmup(ReportRepository reportRepository, LineQueryService lineQueryService) {
        this.reportRepository = reportRepository;
        this.lineQueryService = lineQueryService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    void init() {
        logger.info("<-------> Warmup for BREAKDOWN entity <------->");
        createReports();
    }

    void createReports() {
        List<Line> lines = lineQueryService.findAll();
        Report openReport = new Report(lines.get(0), 28000, 0, ReportWorkShift.FIRST);
        openReport.setCreationDate(LocalDateTime.now().withHour(7).withMinute(0));

        Report closedReport = new Report(lines.get(0), 1000, lines.get(0).getCurrentCounter(), ReportWorkShift.SECOND);
        LocalDateTime startDate = LocalDateTime.now().withHour(7).withMinute(0);
        LocalDateTime finishDate = LocalDateTime.now().withHour(15).withMinute(0).plusHours(1L);
        closedReport.setCreationDate(startDate);
        closedReport.setFinishDate(finishDate);
        closedReport.setTrashAmount(0);
        closedReport.setAmount(500);
        closedReport.setReportState(ReportState.CLOSED);
        closedReport.setFinishOperator("John Wick");

        List<Report> reports = Arrays.asList(
                openReport,
                closedReport
        );

        reportRepository.saveAll(reports);
    }
}
