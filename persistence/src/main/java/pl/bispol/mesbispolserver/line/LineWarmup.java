package pl.bispol.mesbispolserver.line;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.bispol.mesbispolserver.product.ProductType;

import java.util.Arrays;
import java.util.List;

@Profile("development")
@Component()
class LineWarmup {
    private final LineRepository lineRepository;
    private final Logger logger;

    @Autowired()
    LineWarmup(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(1)
    void init() {
        logger.info("<-------> Warmup for LINE entity <------->");
        createLines();
    }

    private void createLines() {
        List<Line> lines = Arrays.asList(
                new Line("L-01", ProductType.PTS, WorkingHours.HOURS8),
                new Line("L-02", ProductType.TEALIGHT, WorkingHours.HOURS8),
                new Line("L-03", ProductType.CANDLE, WorkingHours.HOURS12),
                new Line("L-04", ProductType.PTS, WorkingHours.HOURS12)
        );

        lines.get(0).setProductName("First product");
        lines.get(1).setProductName("Product2");
        lines.get(2).setProductName("Product3");
        lines.get(3).setProductName("Product4");

        lines.get(0).setLineStatus(LineStatus.ACTIVE);
        lines.get(1).setLineStatus(LineStatus.BREAKDOWN);
        lines.get(2).setLineStatus(LineStatus.BREAKDOWN);
        lines.get(3).setLineStatus(LineStatus.BREAKDOWN);

        lines.get(0).setOperator("John Travolta");
        lines.get(1).setOperator("Steven Segal");
        lines.get(2).setOperator("Johny bravo");
        lines.get(3).setOperator("Kniaz Jarema");


        lines.forEach(line -> lineRepository.save(line));
    }
}
