package pl.bispol.mesbispolserver.quality_control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.bispol.mesbispolserver.report.Report;
import pl.bispol.mesbispolserver.report.ReportQueryService;
import pl.bispol.mesbispolserver.user.UserRole;

import java.util.List;

@Profile("development")
@Component()
@DependsOn({"reportWarmup"})
class QualityControlWarmup {
    private final QualityControlRepository qualityControlRepository;
    private final ReportQueryService reportQueryService;
    private final Logger logger;

    @Autowired()
    QualityControlWarmup(QualityControlRepository qualityControlRepository, ReportQueryService reportQueryService) {
        this.qualityControlRepository = qualityControlRepository;
        this.reportQueryService = reportQueryService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(3)
    void init() {
        logger.info("<-------> Warmup for QUALITY CONTROL entity <------->");
        List<Report> reports = reportQueryService.findAll();
        QualityControl qualityControl = new QualityControl("Gandalf Szary", UserRole.PRODUCTION);
        qualityControl.addQualityInspection(new QualityInspection("First property", true));
        qualityControl.addQualityInspection(new QualityInspection("Second property", true));
        qualityControl.addQualityInspection(new QualityInspection("Third property", true));
        qualityControl.setReport(reports.get(0));
        qualityControlRepository.save(qualityControl);
    }

}
