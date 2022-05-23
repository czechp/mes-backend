package pl.bispol.mesbispolserver.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
class ProductPropertyCommandRepositoryImpl implements ProductPropertyCommandRepository {
    private final ProductPropertyRepository productPropertyRepository;


    @Autowired()
    ProductPropertyCommandRepositoryImpl(final ProductPropertyRepository productPropertyRepository) {
        this.productPropertyRepository = productPropertyRepository;
    }

    @Override
    public void deleteById(final long id) {
        productPropertyRepository.deleteById(id);
    }

    @Override
    public boolean existsById(final long productPropertyId) {
        return productPropertyRepository.existsById(productPropertyId);
    }
}
