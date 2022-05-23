package pl.bispol.mesbispolserver.product;


import org.springframework.stereotype.Service;

import java.util.Optional;

@Service()
class ProductCommandRepositoryImpl implements ProductCommandRepository {
    private final ProductRepository productRepository;

    ProductCommandRepositoryImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(final Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(final long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public boolean existsById(final long productId) {
        return productRepository.existsById(productId);
    }

    @Override
    public void deleteById(final long productId) {
        productRepository.deleteById(productId);
    }
}
