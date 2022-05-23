package pl.bispol.mesbispolserver.product.dto;

import pl.bispol.mesbispolserver.product.ProductType;

import java.util.List;

public interface ProductQueryDto {
    long getId();

    String getName();

    ProductType getProductType();

    List<ProductPropertyQueryDto> getProductProperties();

    List<ProductEfficientQueryDto> getProductEfficients();
}
