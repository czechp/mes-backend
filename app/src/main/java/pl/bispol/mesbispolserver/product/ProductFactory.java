package pl.bispol.mesbispolserver.product;

import pl.bispol.mesbispolserver.product.dto.ProductCommandDto;
import pl.bispol.mesbispolserver.product.dto.ProductEfficientQueryDto;
import pl.bispol.mesbispolserver.product.dto.ProductPropertyQueryDto;
import pl.bispol.mesbispolserver.product.dto.ProductQueryDto;

import java.util.List;
import java.util.stream.Collectors;

class ProductFactory {
    static Product toEntity(ProductCommandDto productCommandDto) {
        Product product = new Product(productCommandDto.getName(), productCommandDto.getProductType());
        return product;
    }

    static ProductQueryDto toQueryDto(final Product product) {
        return new ProductQueryDto() {
            @Override
            public long getId() {
                return product.getId();
            }

            @Override
            public String getName() {
                return product.getName();
            }

            @Override
            public ProductType getProductType() {
                return product.getProductType();
            }

            @Override
            public List<ProductPropertyQueryDto> getProductProperties() {
                return product.getProductProperties()
                        .stream()
                        .map(ProductPropertyFactory::toQueryDto)
                        .collect(Collectors.toList());
            }

            @Override
            public List<ProductEfficientQueryDto> getProductEfficients() {
                return product.getProductEfficients()
                        .stream()
                        .map(ProductEfficientFactory::toQueryDto)
                        .collect(Collectors.toList());
            }
        };
    }
}
