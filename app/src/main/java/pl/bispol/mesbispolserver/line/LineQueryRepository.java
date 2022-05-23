package pl.bispol.mesbispolserver.line;


import pl.bispol.mesbispolserver.product.ProductType;

import java.util.List;
import java.util.Optional;

interface LineQueryRepository {

    Optional<Line> findByName(String lineName);

    List<Line> findByProductionType(ProductType productType);

    List<Line> findAll();

    Optional<Line> findById(final long lineId);

    Optional<Line> findByIdAndOperatorExists(long lineId);
}
