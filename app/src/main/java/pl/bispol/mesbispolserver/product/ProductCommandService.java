package pl.bispol.mesbispolserver.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.BadRequestException;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.line.LineQueryService;
import pl.bispol.mesbispolserver.line.dto.LineQueryDto;
import pl.bispol.mesbispolserver.product.dto.*;

import javax.transaction.Transactional;

@Service()
class ProductCommandService {
    private final ProductCommandRepository productCommandRepository;
    private final LineQueryService lineQueryService;

    @Autowired()
    ProductCommandService(final ProductCommandRepository productCommandRepository, LineQueryService lineQueryService) {
        this.productCommandRepository = productCommandRepository;
        this.lineQueryService = lineQueryService;
    }

    ProductQueryDto save(final ProductCommandDto productCommandDto) {
        return ProductFactory.toQueryDto(productCommandRepository.save(ProductFactory.toEntity(productCommandDto)));
    }

    @Transactional()
    public ProductPropertyQueryDto addProperty(final long productId, final ProductPropertyCommandDto productPropertyCommandDto) {
        Product product = productCommandRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(Statements.PRODUCT_NOT_EXISTS));
        ProductProperty productProperty = ProductPropertyFactory.toEntity(productPropertyCommandDto);
        product.addProperty(productProperty);
        return ProductPropertyFactory.toQueryDto(productProperty);
    }

    void deleteProductById(final long productId) {
        if (productCommandRepository.existsById(productId)) {
            productCommandRepository.deleteById(productId);
        } else
            throw new NotFoundException(Statements.PRODUCT_NOT_EXISTS);
    }

    @Transactional()
    public void addProductEfficient(long productId, ProductEfficientCommandDto productEfficientCommandDto) {
        Product product = productCommandRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(Statements.PRODUCT_NOT_EXISTS));
        LineQueryDto line = lineQueryService.findByNameBasicDto(productEfficientCommandDto.getLineName())
                .orElseThrow(() -> new NotFoundException(Statements.LINE_NOT_EXISTS));

        if (!(product.getProductType() == line.getProductionType()))
            throw new BadRequestException(Statements.PRODUCT_TYPE_AND_LINE_NOT_EQUALS);

        boolean productAlreadyHaveThisLine = product.getProductEfficients()
                .stream()
                .anyMatch(e -> e.getLineName().equals(productEfficientCommandDto.getLineName()));

        if (!productAlreadyHaveThisLine) {
            ProductEfficient productEfficient = ProductEfficientFactory.toEntity(productEfficientCommandDto);
            product.addProductEfficient(productEfficient);
        } else {
            ProductEfficient productEfficient = product.getProductEfficients()
                    .stream()
                    .filter(e -> e.getLineName().equals(productEfficientCommandDto.getLineName()))
                    .findAny()
                    .get();
            productEfficient.setAmount(productEfficientCommandDto.getAmount());
        }
    }
}
