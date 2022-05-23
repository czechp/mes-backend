package pl.bispol.mesbispolserver.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service()
class ProductEfficientQueryRepositoryImpl implements ProductEfficientQueryRepository {
    private final ProductEfficientRepository productEfficientRepository;

    @Autowired()
    public ProductEfficientQueryRepositoryImpl(ProductEfficientRepository productEfficientRepository) {
        this.productEfficientRepository = productEfficientRepository;
    }

    @Override
    public List<ProductEfficient> findByLineName(String lineName) {
        return productEfficientRepository.findByLineName(lineName);
    }

    @Override
    public Optional<ProductEfficient> findByProductNameAndLineName(String productName, String lineName) {
        return productEfficientRepository.findByProduct_NameAndLineName(productName, lineName);
    }

}
