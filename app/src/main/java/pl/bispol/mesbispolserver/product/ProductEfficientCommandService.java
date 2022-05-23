package pl.bispol.mesbispolserver.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.NotFoundException;

@Service()
class ProductEfficientCommandService {
    private final ProductEfficientCommandRepository produceEfficientCommandRepository;

    @Autowired()
    public ProductEfficientCommandService(ProductEfficientCommandRepository produceEfficientCommandRepository) {
        this.produceEfficientCommandRepository = produceEfficientCommandRepository;
    }

    public void deleteById(final long productEfficientId) {
        if (produceEfficientCommandRepository.existsById(productEfficientId))
            produceEfficientCommandRepository.deleteById(productEfficientId);
        else
            throw new NotFoundException(Statements.PRODUCT_EFFICIENT_NOT_EXISTS);

    }
}
