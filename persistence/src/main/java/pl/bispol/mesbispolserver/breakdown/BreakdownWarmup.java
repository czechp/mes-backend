package pl.bispol.mesbispolserver.breakdown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.bispol.mesbispolserver.line.Line;
import pl.bispol.mesbispolserver.line.LineQueryService;
import pl.bispol.mesbispolserver.report.Report;
import pl.bispol.mesbispolserver.report.ReportQueryService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Profile("development")
class BreakdownWarmup {
    private final BreakdownRepository breakdownRepository;
    private final LineQueryService lineQueryService;
    private final ReportQueryService reportQueryService;
    private final Logger logger;

    @Autowired
    BreakdownWarmup(BreakdownRepository breakdownRepository, LineQueryService lineQueryService, ReportQueryService reportQueryService) {
        this.breakdownRepository = breakdownRepository;
        this.lineQueryService = lineQueryService;
        this.reportQueryService = reportQueryService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        logger.info("<-------> Warmup for BREAKDOWN entity <------->");


        List<Line> lines = lineQueryService.findAll();
        Optional<Report> optionalReport = reportQueryService.findById(2L);
        Optional.of(lines.get(0)).ifPresent(l -> {
//            Breakdown openBreakdown = new Breakdown("Open content", l, "12345678", "Open operator name");
//            Breakdown inProgressBreakdown = new Breakdown("In progress content", l, "987654321", "In progress operator name");
            Breakdown closeBreakdown = new Breakdown("Close content", l, "1232143", "Close operator name");
//            inProgressBreakdown.inProgress("321456", "Maintenance in progress", "M0001");
            closeBreakdown.inProgress("342141241", "Maintenance close", "M0002");
            closeBreakdown.close();
            closeBreakdown.setCloseDate(LocalDateTime.now().plusHours(1L));

            optionalReport.ifPresent(r -> {
//                openBreakdown.addReport(r);
//                inProgressBreakdown.addReport(r);
                closeBreakdown.addReport(r);
            });

            breakdownRepository.saveAll(Arrays.asList(closeBreakdown));
        });
    }
}
