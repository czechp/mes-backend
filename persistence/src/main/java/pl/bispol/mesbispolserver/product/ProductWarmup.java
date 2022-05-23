package pl.bispol.mesbispolserver.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component()
@Profile("development")
class ProductWarmup {
    private final ProductRepository productRepository;
    private final Logger logger;

    @Autowired()
    ProductWarmup(final ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Order(1)
    @EventListener(ApplicationReadyEvent.class)
    void init() {
        logger.info("<-------> Warmup for PRODUCT entity <------->");
        save();
    }

    private void save() {
        List<Product> products = Arrays.asList(
                new Product("First product", ProductType.PTS),
                new Product("Second product", ProductType.CANDLE),
                new Product("Third product", ProductType.TEALIGHT),
                new Product("Fourth product", ProductType.TEALIGHT)
        );

        for (int i = 0; i < products.size(); i++) {
            for (int j = 0; j < 3; j++) {
                products.get(i).addProperty(new ProductProperty("Product property " + j));
            }
        }

        products
                .stream()
                .forEach(product -> productRepository.save(product));
    }
}
