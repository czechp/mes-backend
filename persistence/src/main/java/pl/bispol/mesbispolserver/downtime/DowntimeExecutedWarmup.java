package pl.bispol.mesbispolserver.downtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.bispol.mesbispolserver.report.Report;
import pl.bispol.mesbispolserver.report.ReportQueryService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Profile("development")
class DowntimeExecutedWarmup {
    private final DowntimeExecutedRepository downtimeExecutedRepository;
    private final ReportQueryService reportQueryService;
    private final Logger logger;

    @Autowired
    DowntimeExecutedWarmup(DowntimeExecutedRepository downtimeExecutedRepository, ReportQueryService reportQueryService) {
        this.downtimeExecutedRepository = downtimeExecutedRepository;
        this.reportQueryService = reportQueryService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @EventListener(ApplicationReadyEvent.class)
    void init() {
        logger.info("<-------> Warmup for DOWNTIMEEXECUTED entity <------->");

        List<Report> reports = reportQueryService.findAll();
        Optional.ofNullable(reports.get(1)).ifPresent(r -> {
            DowntimeExecuted downtimeExecutedClosed = new DowntimeExecuted();
            downtimeExecutedClosed.setReport(r);
            downtimeExecutedClosed.setOperatorRfid("1234567");
            downtimeExecutedClosed.setOperatorName("Some operator name");
            LocalDateTime closeDate = LocalDateTime.now().withHour(LocalDateTime.now().getHour() + 1);
            downtimeExecutedClosed.setCloseDate(closeDate);
            downtimeExecutedClosed.setDowntimeExecutedState(DowntimeExecutedState.CLOSE);
            downtimeExecutedClosed.setContent("Some downtime executed content");
            downtimeExecutedRepository.save(downtimeExecutedClosed);

//
//            DowntimeExecuted downtimeExecutedOpen = new DowntimeExecuted();
//            downtimeExecutedOpen.setReport(r);
//            downtimeExecutedOpen.setOperatorRfid("1234567");
//            downtimeExecutedOpen.setOperatorName("Some operator name");
//            downtimeExecutedOpen.setDowntimeExecutedState(DowntimeExecutedState.OPEN);
//            downtimeExecutedOpen.setContent("Some downtime executed content");
//            downtimeExecutedRepository.save(downtimeExecutedOpen);

        });
    }
}
