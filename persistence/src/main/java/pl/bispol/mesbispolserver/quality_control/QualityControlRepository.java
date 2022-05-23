package pl.bispol.mesbispolserver.quality_control;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository()
interface QualityControlRepository extends org.springframework.data.repository.Repository<QualityControl, Long> {
    QualityControl save(QualityControl qualityControl);

    List<QualityControl> findByOrderByIdDesc(Pageable pageable);

    Optional<QualityControl> findById(long id);

    void deleteById(long id);

    List<QualityControl> findByReport_Line_IdOrderByIdDesc(long lineId);
}
