package pl.bispol.mesbispolserver.product;

import pl.bispol.mesbispolserver.product.dto.ProductPropertyCommandDto;
import pl.bispol.mesbispolserver.product.dto.ProductPropertyQueryDto;

class ProductPropertyFactory {
    public static ProductPropertyQueryDto toQueryDto(ProductProperty productProperty) {
        return new ProductPropertyQueryDto() {
            @Override
            public long getId() {
                return productProperty.getId();
            }

            @Override
            public String getContent() {
                return productProperty.getContent();
            }
        };
    }

    static ProductProperty toEntity(final ProductPropertyCommandDto productPropertyCommandDto) {
        ProductProperty result = new ProductProperty();
        result.setContent(productPropertyCommandDto.getContent());
        return result;
    }

}
