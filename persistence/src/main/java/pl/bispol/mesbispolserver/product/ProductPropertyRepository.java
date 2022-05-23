package pl.bispol.mesbispolserver.product;

import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository()
interface ProductPropertyRepository extends Repository<ProductProperty, Long> {
    void deleteById(long id);

    boolean existsById(long productPropertyId);
}
