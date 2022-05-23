package pl.bispol.mesbispolserver.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.product.dto.ProductQueryDto;

import java.util.List;
import java.util.Optional;

@Service()
class ProductQueryRepositoryImpl implements ProductQueryRepository {
    private final ProductRepository productRepository;

    @Autowired()
    ProductQueryRepositoryImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductQueryDto> findAllQuery() {
        return productRepository.findAllQuery();
    }

    @Override
    public Optional<ProductQueryDto> findQueryById(final long productId) {
        return productRepository.findQueryById(productId);
    }

    @Override
    public Optional<Product> findByName(String productName) {
        return productRepository.findByName(productName);
    }
}
