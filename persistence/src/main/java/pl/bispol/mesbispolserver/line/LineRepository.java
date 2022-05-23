package pl.bispol.mesbispolserver.line;

import org.springframework.data.repository.Repository;
import pl.bispol.mesbispolserver.product.ProductType;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository()
interface LineRepository extends Repository<Line, Long> {
    Line save(Line line);

    Optional<Line> findById(long lineId);

    void deleteById(long lineId);

    boolean existsById(long lineId);

    Optional<Line> findByName(String name);

    List<Line> findByProductionType(ProductType productType);

    List<Line> findBy();

    Optional<Line> findByIdAndOperatorNot(long lineId, String s);
}
