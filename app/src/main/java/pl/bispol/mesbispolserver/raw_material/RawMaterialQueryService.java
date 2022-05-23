package pl.bispol.mesbispolserver.raw_material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.raw_material.dto.RawMaterialQueryDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class RawMaterialQueryService {
    private final RawMaterialQueryRepository rawMaterialQueryRepository;

    @Autowired
    RawMaterialQueryService(RawMaterialQueryRepository rawMaterialQueryRepository) {
        this.rawMaterialQueryRepository = rawMaterialQueryRepository;
    }

    Optional<RawMaterialQueryDto> findById(long id) {
        return rawMaterialQueryRepository.findById(id)
                .map(RawMaterialFactory::toDto);
    }

    List<RawMaterialQueryDto> findAll(Optional<Integer> limit) {
        return limit.map(this::findByWithLimit)
                .orElseGet(this::findBy);
    }

    private List<RawMaterialQueryDto> findByWithLimit(Integer limit) {
        return rawMaterialQueryRepository.findBy(PageRequest.of(0, limit))
                .stream()
                .map(RawMaterialFactory::toDto)
                .collect(Collectors.toList());
    }

    private List<RawMaterialQueryDto> findBy() {
        return rawMaterialQueryRepository.findBy(Pageable.unpaged())
                .stream()
                .map(RawMaterialFactory::toDto)
                .collect(Collectors.toList());
    }

    List<RawMaterialQueryDto> findByLineId(long lineId) {
        return rawMaterialQueryRepository.findByLineId(lineId)
                .stream()
                .map(RawMaterialFactory::toDto)
                .collect(Collectors.toList());
    }
}
