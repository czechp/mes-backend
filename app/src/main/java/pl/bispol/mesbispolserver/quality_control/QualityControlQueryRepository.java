package pl.bispol.mesbispolserver.quality_control;


import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

interface QualityControlQueryRepository {
    List<QualityControl> findAll(Pageable pageable);

    Optional<QualityControl> findById(long qualityControlId);

    List<QualityControl> findByLineId(long lineId);
}
