package pl.bispol.mesbispolserver.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.NotFoundException;

@Service()
class ProductPropertyCommandService {
    private final ProductPropertyCommandRepository productPropertyCommandRepository;


    @Autowired()
    ProductPropertyCommandService(final ProductPropertyCommandRepository productPropertyCommandRepository) {
        this.productPropertyCommandRepository = productPropertyCommandRepository;
    }

    void deleteById(final long productPropertyId) {
        if (productPropertyCommandRepository.existsById(productPropertyId))
            productPropertyCommandRepository.deleteById(productPropertyId);
        else
            throw new NotFoundException(Statements.PRODUCT_PROPERTY_NOT_EXISTS);
    }
}
