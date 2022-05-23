package pl.bispol.mesbispolserver;

import java.util.Optional;

public interface AbstractCommandRepository<T> {
    Optional<T> findById(long id);

    void save(T t);

    void deleteById(long id);

    boolean existsById(long id);
}
