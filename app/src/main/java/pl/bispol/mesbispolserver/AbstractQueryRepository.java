package pl.bispol.mesbispolserver;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AbstractQueryRepository<T> {
    List<T> findBy(Pageable pageable);

    Optional<T> findById(long id);
}
