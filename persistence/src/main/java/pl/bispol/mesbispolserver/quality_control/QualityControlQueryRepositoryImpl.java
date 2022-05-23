package pl.bispol.mesbispolserver.quality_control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository()
class QualityControlQueryRepositoryImpl implements QualityControlQueryRepository {
    private final QualityControlRepository qualityControlRepository;

    @Autowired()
    QualityControlQueryRepositoryImpl(QualityControlRepository qualityControlRepository) {
        this.qualityControlRepository = qualityControlRepository;
    }

    @Override
    public List<QualityControl> findAll(Pageable pageable) {
        return qualityControlRepository.findByOrderByIdDesc(pageable);
    }

    @Override
    public Optional<QualityControl> findById(long qualityControlId) {
        return qualityControlRepository.findById(qualityControlId);
    }

    @Override
    public List<QualityControl> findByLineId(long lineId) {
        return qualityControlRepository.findByReport_Line_IdOrderByIdDesc(lineId);
    }
}
