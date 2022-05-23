package pl.bispol.mesbispolserver.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.product.dto.ProductEfficientLineQueryDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service()
public class ProductEfficientQueryService {
    private final ProductEfficientQueryRepository productEfficientQueryRepository;

    @Autowired()
    public ProductEfficientQueryService(ProductEfficientQueryRepository productEfficientQueryRepository) {
        this.productEfficientQueryRepository = productEfficientQueryRepository;
    }

    public List<ProductEfficientLineQueryDto> findByLineName(final String lineName) {
        System.out.println(productEfficientQueryRepository.findByLineName(lineName));
        return productEfficientQueryRepository.findByLineName(lineName)
                .stream()
                .map(ProductEfficientFactory::toQueryLineDto)
                .collect(Collectors.toList());
    }

    public Optional<ProductEfficient> findByProductNameAndLineName(final String productName, final String lineName
    ) {
        return productEfficientQueryRepository.findByProductNameAndLineName(productName, lineName);
    }
}
