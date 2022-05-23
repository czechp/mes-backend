package pl.bispol.mesbispolserver.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
class ProductEfficientCommandRepositoryImpl implements ProductEfficientCommandRepository {
    private final ProductEfficientRepository productEfficientRepository;

    @Autowired()
    ProductEfficientCommandRepositoryImpl(ProductEfficientRepository productEfficientRepository) {
        this.productEfficientRepository = productEfficientRepository;
    }

    @Override
    public void deleteById(long productEfficientId) {
        productEfficientRepository.deleteById(productEfficientId);
    }

    @Override
    public boolean existsById(long productEfficientId) {
        return productEfficientRepository.existsById(productEfficientId);
    }
}
