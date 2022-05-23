package pl.bispol.mesbispolserver.product;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository()
interface ProductEfficientRepository extends org.springframework.data.repository.Repository<ProductEfficient, Long> {
    void save(ProductEfficient productEfficient);

    Optional<ProductEfficient> findById(long id);

    void deleteById(long productEfficientId);

    boolean existsById(long productEfficientId);

    List<ProductEfficient> findByLineName(String lineName);

    Optional<ProductEfficient> findByProduct_NameAndLineName(String productName, String lineName);

}
