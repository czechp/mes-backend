package pl.bispol.mesbispolserver.product;

import pl.bispol.mesbispolserver.product.dto.ProductEfficientCommandDto;
import pl.bispol.mesbispolserver.product.dto.ProductEfficientLineQueryDto;
import pl.bispol.mesbispolserver.product.dto.ProductEfficientQueryDto;

import java.util.stream.Collectors;

class ProductEfficientFactory {
    static ProductEfficient toEntity(ProductEfficientCommandDto productEfficientCommandDto) {
        ProductEfficient productEfficient = new ProductEfficient(productEfficientCommandDto.getLineName(), productEfficientCommandDto.getAmount());
        return productEfficient;
    }

    public static ProductEfficientQueryDto toQueryDto(ProductEfficient productEfficient) {
        return new ProductEfficientQueryDto() {
            @Override
            public long getId() {
                return productEfficient.getId();
            }

            @Override
            public String getLineName() {
                return productEfficient.getLineName();
            }

            @Override
            public long getAmount() {
                return productEfficient.getAmount();
            }
        };
    }

    public static ProductEfficientLineQueryDto toQueryLineDto(final ProductEfficient productEfficient) {
        ProductEfficientLineQueryDto dto = new ProductEfficientLineQueryDto();
        dto.setId(productEfficient.getId());
        dto.setProductId(productEfficient.getProduct().getId());
        dto.setProductName(productEfficient.getProduct().getName());
        dto.setAmount(productEfficient.getAmount());
        dto.setLineName(productEfficient.getLineName());
        dto.setProductProperties(
                productEfficient.getProduct().getProductProperties()
                        .stream()
                        .map(el -> ProductPropertyFactory.toQueryDto(el))
                        .collect(Collectors.toList())
        );
        return dto;
    }
}
