package pl.bispol.mesbispolserver.quality_control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository()
class QualityControlCommandRepositoryImpl implements QualityControlCommandRepository {
    private final QualityControlRepository qualityControlRepository;

    @Autowired()
    QualityControlCommandRepositoryImpl(QualityControlRepository qualityControlRepository) {
        this.qualityControlRepository = qualityControlRepository;
    }

    @Override
    public QualityControl save(QualityControl qualityControl) {
        return qualityControlRepository.save(qualityControl);
    }

    @Override
    public Optional<QualityControl> findById(long qualityControlId) {
        return qualityControlRepository.findById(qualityControlId);
    }

    @Override
    public void deleteById(long id) {
        qualityControlRepository.deleteById(id);
    }
}
