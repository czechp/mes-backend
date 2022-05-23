package pl.bispol.mesbispolserver.downtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.bispol.mesbispolserver.line.LineQueryService;

import java.util.Arrays;

@Component
@Profile("development")
class DowntimeWarmup {
    private final DowntimeRepository downTimeRepository;
    private final LineQueryService lineQueryService;
    private final Logger logger;

    @Autowired
    DowntimeWarmup(DowntimeRepository downTimeRepository, LineQueryService lineQueryService) {
        this.downTimeRepository = downTimeRepository;
        this.lineQueryService = lineQueryService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @EventListener(ApplicationReadyEvent.class)
    void init() {
        logger.info("<-------> Warmup for DOWNTIME entity <------->");
        lineQueryService.findById(1L)
                .ifPresent(l -> {
                    Arrays.asList(
                            new Downtime("1st downtime", l),
                            new Downtime("2nd downtime", l),
                            new Downtime("3rd downtime", l),
                            new Downtime("4th downtime", l),
                            new Downtime("5th downtime", l),
                            new Downtime("6th downtime", l),
                            new Downtime("7th downtime", l),
                            new Downtime("8th downtime", l)
                    ).forEach(downTimeRepository::save);
                });
    }
}
