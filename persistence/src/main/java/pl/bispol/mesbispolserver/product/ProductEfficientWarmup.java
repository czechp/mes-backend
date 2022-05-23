package pl.bispol.mesbispolserver.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component()
@Profile("development")
class ProductEfficientWarmup {
    private final ProductEfficientRepository productEfficientRepository;
    private final ProductRepository productRepository;
    private final Logger logger;

    @Autowired()
    public ProductEfficientWarmup(ProductEfficientRepository productEfficientRepository, ProductRepository productRepository) {
        this.productEfficientRepository = productEfficientRepository;
        this.productRepository = productRepository;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }


    @Order(2)
    @EventListener(ApplicationReadyEvent.class)
    @Transactional()
    public void init() {
        logger.info("<-------> Warmup for PRODUCT EFFICIENT entity <------->");
        List<Product> products = productRepository.findBy();
        products.get(0).addProductEfficient(new ProductEfficient("L-01", 28800));
        products.get(0).addProductEfficient(new ProductEfficient("L-04", 4000));
        products.get(1).addProductEfficient(new ProductEfficient("L-02", 3000));
        products.get(2).addProductEfficient(new ProductEfficient("L-03", 5000));
        products.get(3).addProductEfficient(new ProductEfficient("L-01", 7000));
    }
}
