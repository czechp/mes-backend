package pl.bispol.mesbispolserver.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.product.dto.ProductQueryDto;

import java.util.List;
import java.util.Optional;

@Service()
public class ProductQueryService {
    private final ProductQueryRepository productQueryRepository;

    @Autowired()
    ProductQueryService(final ProductQueryRepository productQueryRepository) {
        this.productQueryRepository = productQueryRepository;
    }

    public List<ProductQueryDto> findAll() {
        return productQueryRepository.findAllQuery();
    }


    public Optional<ProductQueryDto> findById(final long productId) {
        return productQueryRepository.findQueryById(productId);
    }


    Optional<ProductQueryDto> findByName(String productName) {
        return productQueryRepository.findByName(productName)
                .map(ProductFactory::toQueryDto);
    }
}
