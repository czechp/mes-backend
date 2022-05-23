package pl.bispol.mesbispolserver.quality_control;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.quality_control.dto.QualityControlQueryDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service()
public class QualityControlQueryService {
    private final QualityControlQueryRepository qualityControlQueryRepository;

    public QualityControlQueryService(QualityControlQueryRepository qualityControlQueryRepository) {
        this.qualityControlQueryRepository = qualityControlQueryRepository;
    }

    List<QualityControlQueryDto> findAll(Optional<Integer> limit, boolean onlyNok) {
        Pageable pageable = limit.map(x -> (Pageable) PageRequest.of(0, x))
                .orElse(Pageable.unpaged());

        if (onlyNok)
            return qualityControlQueryRepository.findAll(Pageable.unpaged())
                    .stream()
                    .filter(q -> q.getInspections().stream().anyMatch(i -> !i.isQualityOK()))
                    .map(QualityControlFactory::toQueryDto)
                    .collect(Collectors.toList());
        else
            return qualityControlQueryRepository.findAll(pageable)
                    .stream()
                    .map(QualityControlFactory::toQueryDto)
                    .collect(Collectors.toList());

    }

    Optional<QualityControlQueryDto> findById(final long qualityControlId) {
        return qualityControlQueryRepository.findById(qualityControlId).map(QualityControlFactory::toQueryDto);
    }

    List<QualityControlQueryDto> findByLineId(long lineId) {
        return qualityControlQueryRepository.findByLineId(lineId)
                .stream()
                .map(QualityControlFactory::toQueryDto)
                .collect(Collectors.toList());
    }
}
