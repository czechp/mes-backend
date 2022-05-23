package pl.bispol.mesbispolserver.product;

import java.util.Optional;

interface ProductCommandRepository {

    Product save(Product product);

    Optional<Product> findById(long productId);

    boolean existsById(long productId);

    void deleteById(long productId);
}
