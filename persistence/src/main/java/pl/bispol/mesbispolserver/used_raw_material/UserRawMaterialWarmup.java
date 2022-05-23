package pl.bispol.mesbispolserver.used_raw_material;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.bispol.mesbispolserver.report.ReportQueryService;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
@Profile("development")
class UserRawMaterialWarmup {
    private final ReportQueryService reportQueryService;
    private final UsedRawMaterialRepository usedRawMaterialRepository;
    private final Logger logger;

    UserRawMaterialWarmup(ReportQueryService reportQueryService, UsedRawMaterialRepository usedRawMaterialRepository) {
        this.reportQueryService = reportQueryService;
        this.usedRawMaterialRepository = usedRawMaterialRepository;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        logger.info("<-------> Warmup for USED RAW MATERIAL WARMUP entity <------->");
        reportQueryService.findById(1L)
                .ifPresent(report -> {
                    List<UsedRawMaterial> materials = Arrays.asList(new UsedRawMaterial(1L, "Some provider1", "Some name1", "Part nr1", "Date1", report),
                            new UsedRawMaterial(2L, "Some provider2", "Some name2", "Part nr2", "Date2", report),
                            new UsedRawMaterial(3L, "Some provider3", "Some name3", "Part nr3", "Date3", report));

                    usedRawMaterialRepository.saveAll(materials);
                });
    }
}
