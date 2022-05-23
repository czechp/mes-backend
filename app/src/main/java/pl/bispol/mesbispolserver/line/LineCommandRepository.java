package pl.bispol.mesbispolserver.line;

import java.util.Optional;

interface LineCommandRepository {
    Optional<Line> findById(final long lineId);

    Line save(Line line);

    void deleteById(long lineId);

    boolean existsById(long lineId);
}
