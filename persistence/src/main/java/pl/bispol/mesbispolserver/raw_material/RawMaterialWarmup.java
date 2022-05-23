package pl.bispol.mesbispolserver.raw_material;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.bispol.mesbispolserver.line.Line;
import pl.bispol.mesbispolserver.line.LineQueryService;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@Profile("development")
class RawMaterialWarmup {
    private final LineQueryService lineQueryService;
    private final RawMaterialRepository rawMaterialRepository;
    private final Logger logger;

    @Autowired
    RawMaterialWarmup(LineQueryService lineQueryService, RawMaterialRepository rawMaterialRepository) {
        this.lineQueryService = lineQueryService;
        this.rawMaterialRepository = rawMaterialRepository;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        logger.info("<-------> Warmup for RAW MATERIAL entity <------->");

        final Optional<Line> line = lineQueryService.findById(1L);

        line.ifPresent((l) -> {
            Stream.of(
                            new RawMaterial(123, "First provider1", "3First name1", l),
                            new RawMaterial(456, "Second provider2", "1Second name2", l),
                            new RawMaterial(789, "Third provider3", "2Third name3", l)
                    )
                    .forEach(rawMaterialRepository::save);
        });

    }
}
