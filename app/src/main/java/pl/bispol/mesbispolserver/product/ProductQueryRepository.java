package pl.bispol.mesbispolserver.product;

import pl.bispol.mesbispolserver.product.dto.ProductQueryDto;

import java.util.List;
import java.util.Optional;

interface ProductQueryRepository {
    List<ProductQueryDto> findAllQuery();

    Optional<ProductQueryDto> findQueryById(long productId);

    Optional<Product> findByName(String productName);
}
