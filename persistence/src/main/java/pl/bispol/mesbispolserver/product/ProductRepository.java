package pl.bispol.mesbispolserver.product;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.bispol.mesbispolserver.product.dto.ProductQueryDto;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository()
interface ProductRepository extends Repository<Product, Long> {
    Product save(Product product);

    @Query("SELECT p FROM Product p")
    List<ProductQueryDto> findAllQuery();

    @Query("SELECT p FROM Product p WHERE p.id=?1")
    Optional<ProductQueryDto> findQueryById(long productId);

    Optional<Product> findById(long productId);

    boolean existsById(long productId);

    void deleteById(long productId);

    List<Product> findBy();

    Optional<Product> findByName(String productName);
}
