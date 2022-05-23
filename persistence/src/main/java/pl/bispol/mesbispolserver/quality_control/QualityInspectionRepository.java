package pl.bispol.mesbispolserver.quality_control;

import org.springframework.stereotype.Repository;

@Repository()
interface QualityInspectionRepository extends org.springframework.data.repository.Repository<QualityInspection, Long> {
}
