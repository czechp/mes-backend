package pl.bispol.mesbispolserver.quality_control;

import java.util.Optional;

interface QualityControlCommandRepository {
    QualityControl save(QualityControl qualityControl);

    Optional<QualityControl> findById(long qualityControlId);

    void deleteById(long id);
}
