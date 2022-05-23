package pl.bispol.mesbispolserver.product;

interface ProductEfficientCommandRepository {
    void deleteById(long productEfficientId);

    boolean existsById(long productEfficientId);
}
