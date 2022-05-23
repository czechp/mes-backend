package pl.bispol.mesbispolserver.product;

import java.util.List;
import java.util.Optional;

interface ProductEfficientQueryRepository {

    List<ProductEfficient> findByLineName(String lineName);

    Optional<ProductEfficient> findByProductNameAndLineName(String productName, String lineName);
}
