package pl.bispol.mesbispolserver.product;

interface ProductPropertyCommandRepository {
    void deleteById(final long id);

    boolean existsById(long productPropertyId);
}
